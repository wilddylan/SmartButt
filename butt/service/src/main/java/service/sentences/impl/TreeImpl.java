package service.sentences.impl;

import dao.sentences.ITreeDao;
import model.sentences.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sentences.ITreeService;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("treeService")
public class TreeImpl implements ITreeService {

    @Autowired
    private ITreeDao treeDao;

    @Override
    public int insert(List<Tree> tree) throws Exception {
        return treeDao.insert(tree);
    }

    @Override
    public List<Tree> simplyGetNext(Integer qid) throws Exception {
        return treeDao.simplyGetNext(qid);
    }
}
