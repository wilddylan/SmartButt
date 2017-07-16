package dao.sentences;

import model.sentences.Faq;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface IFaqDao {
    List<Faq> selectAll() throws Exception;
}
