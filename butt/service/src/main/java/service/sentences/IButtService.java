package service.sentences;

import model.sentences.Tree;

import java.util.List;

/**
 * 2017/7/15
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface IButtService {
    int insert(Tree tree) throws Exception;
    List<Tree> random() throws Exception;
}
