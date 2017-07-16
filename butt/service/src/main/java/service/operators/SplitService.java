package service.operators;

import NLP.Annotate.AnnotateTool;
import com.alibaba.fastjson.JSON;
import edu.stanford.nlp.ling.CoreLabel;
import model.sentences.Sentences;
import model.sentences.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import service.sentences.IButtService;
import service.sentences.ISentencesService;

import java.util.List;

/**
 * 2017/7/15
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("splitService")
public class SplitService {
    private static final Logger logger = LoggerFactory.getLogger(SplitService.class);

    @Async(value = "executor")
    public void asyncSplitLearn(String content) {
        // Split this content and searching.
        if (content.isEmpty()) {
            return;
        }
        List<CoreLabel> list = AnnotateTool.getAnnotated(content);
        if (list.size() != 0) {
            logger.info(JSON.toJSONString(list));
        }
    }
}
