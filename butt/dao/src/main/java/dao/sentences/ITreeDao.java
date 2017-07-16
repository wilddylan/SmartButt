package dao.sentences;

import model.sentences.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface ITreeDao {
    int insert(List<Tree> list) throws Exception;
    List<Tree> simplyGetNext(Integer qid) throws Exception;
}
