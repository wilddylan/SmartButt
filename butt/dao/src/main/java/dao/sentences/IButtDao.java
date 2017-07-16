package dao.sentences;

import model.sentences.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2017/7/15
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface IButtDao {
    int insert(@Param("tree") Tree tree) throws Exception;
    List<Tree> random() throws Exception;
}
