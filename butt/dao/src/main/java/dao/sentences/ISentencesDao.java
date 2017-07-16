package dao.sentences;

import model.sentences.Sentences;
import model.sentences.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface ISentencesDao {
    List<Sentences> selectAll() throws Exception;
    Sentences selectWithContent(String content) throws Exception;
    List<Sentences> selectWithTrees(List<Tree> list) throws Exception;
    List<Sentences> selectWithButts(List<Tree> list) throws Exception;
    int insert(@Param("sentence") Sentences sentences) throws Exception;
}
