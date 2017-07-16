package NLP.Annotate;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public class AnnotateTool {

    public static List<CoreLabel> getAnnotated(String text) {
        Annotation annotation = new Annotation(text);

        StanfordCoreNLP coreNLP = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
        coreNLP.annotate(annotation);

        // Print
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap sentence = sentences.get(0);

        // token.getString(CoreAnnotations.TextAnnotation.class);
        return sentence.get(CoreAnnotations.TokensAnnotation.class);
    }
}
