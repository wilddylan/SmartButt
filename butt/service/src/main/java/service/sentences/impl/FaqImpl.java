package service.sentences.impl;

import dao.sentences.IFaqDao;
import model.sentences.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.sentences.IFaqService;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("faqService")
public class FaqImpl implements IFaqService {

    @Autowired
    private IFaqDao faqDao;

    @Override
    public List<Faq> selectAll() throws Exception {
        return faqDao.selectAll();
    }
}
