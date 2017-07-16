package service.sentences.impl;

import dao.sentences.IButtDao;
import model.sentences.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sentences.IButtService;

import java.util.List;

/**
 * 2017/7/15
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("buttService")
public class IButtImpl implements IButtService {

    @Autowired
    private IButtDao buttDao;

    @Override
    public int insert(Tree tree) throws Exception {
        return buttDao.insert(tree);
    }

    @Override
    public List<Tree> random() throws Exception {
        return buttDao.random();
    }
}
