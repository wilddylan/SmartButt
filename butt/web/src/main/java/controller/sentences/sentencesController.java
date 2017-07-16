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

    @RequestMapping(value = "/ask/{question}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAnswer(@PathVariable String question) {
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
            if (sentences != null) {
                List<Tree> treeList = treeService.simplyGetNext(sentences.getId());
                List<Sentences> nextSentences;
                if (treeList == null || treeList.size() == 0) {
                    // Get an another random unAnswered question
                    treeList = buttService.random();
                    nextSentences = sentencesService.selectWithButts(treeList);
                } else {
                    nextSentences = sentencesService.selectWithTrees(treeList);
                }
                return Response.SucceedResponse(nextSentences).JSONString();
            } else {
                // Question not found
                splitService.asyncSplitLearn(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.FailedResponse().JSONString();
    }
}
