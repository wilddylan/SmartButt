package service.sentences;

import model.sentences.Tree;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface ITreeService {
    int insert(List<Tree> tree) throws Exception;
    List<Tree> simplyGetNext(Integer qid) throws Exception;
}
