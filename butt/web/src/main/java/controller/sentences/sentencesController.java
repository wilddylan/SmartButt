package controller.sentences;

import JSONResponse.Response;
import model.sentences.Sentences;
import model.sentences.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.operators.SplitService;
import service.sentences.IButtService;
import service.sentences.ISentencesService;
import service.sentences.ITreeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@RestController
@RequestMapping("/sentences")
public class sentencesController {
    private final Logger logger = LoggerFactory.getLogger(sentencesController.class);

    private final ISentencesService sentencesService;
    private final ITreeService treeService;
    private final SplitService splitService;
    private final IButtService buttService;

    @Autowired
    public sentencesController(ISentencesService sentencesService, ITreeService treeService, SplitService splitService, IButtService buttService) {
        this.sentencesService = sentencesService;
        this.treeService = treeService;
        this.splitService = splitService;
        this.buttService = buttService;
    }

    /**
     * 语料库入口
     * @param prevId prev sentence id, if not have, send -1
     * @param question sentence
     * @return Response
     */
    @RequestMapping(value = "/prev/{prevId}/next/{question}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAnswer(@PathVariable Integer prevId, @PathVariable String question) {
        /*
         * Begin
         * -> Prev messageId, New sentence.
         * -> If prev messageId.
         *      -> Insert prevMessageId as qid, This sentence as aid to corpus_base.
         * -> If new sentence
         *      -> (S Split, annotated, calculate Similarity degree with other sentence ) Add to corpus_base.
         * -> Check answers.
         * -> If without answer.
         *      -> Check butts, return random sentence.
         * End
         */
        try {
            Sentences sentences = sentencesService.selectWithContent(question);
            List<Sentences> nextSentences;
            List<Tree> treeList;
            if (sentences != null) {
                // Question founded
                treeList = treeService.simplyGetNext(sentences.getId());
                if (treeList == null || treeList.size() == 0) {
                    // Get an another random unAnswered question
                    treeList = buttService.random();
                    nextSentences = sentencesService.selectWithButts(treeList);
                } else {
                    nextSentences = sentencesService.selectWithTrees(treeList);
                }
            } else {
                // Question not found
                Sentences s = new Sentences();
                s.setContent(question);
                sentencesService.insert(s);

                Sentences inserted = sentencesService.selectWithContent(question);
                if (inserted != null) {
                    Tree tree = new Tree();
                    tree.setQid(inserted.getId());
                    buttService.insert(tree);

                    if (prevId != -1) {
                        List<Tree> addList = new ArrayList<>();
                        tree.setQid(prevId);
                        tree.setAid(inserted.getId());
                        addList.add(tree);
                        treeService.insert(addList);
                    }
                }
                treeList = buttService.random();
                nextSentences = sentencesService.selectWithButts(treeList);
                splitService.asyncSplitLearn(question);
            }
            return Response.SucceedResponse(nextSentences).JSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.FailedResponse().JSONString();
        }
    }
}
