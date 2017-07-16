package service.sentences;

import model.sentences.Sentences;
import model.sentences.Tree;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface ISentencesService {
    List<Sentences> selectAll() throws Exception;
    Sentences selectWithContent(String content) throws Exception;
    List<Sentences> selectWithTrees(List<Tree> treeList) throws Exception;
    List<Sentences> selectWithButts(List<Tree> list) throws Exception;
    int insert(Sentences sentences) throws Exception;
}
