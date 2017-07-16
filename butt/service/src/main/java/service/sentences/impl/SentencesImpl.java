package service.sentences.impl;

import dao.sentences.ISentencesDao;
import model.sentences.Sentences;
import model.sentences.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sentences.ISentencesService;

import java.util.List;

/**
 * 2017/7/14
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("sentencesService")
public class SentencesImpl implements ISentencesService {

    @Autowired
    private ISentencesDao sentencesDao;

    @Override
    public List<Sentences> selectAll() throws Exception {
        return sentencesDao.selectAll();
    }

    @Override
    public Sentences selectWithContent(String content) throws Exception {
        return sentencesDao.selectWithContent(content);
    }

    @Override
    public List<Sentences> selectWithTrees(List<Tree> treeList) throws Exception {
        return sentencesDao.selectWithTrees(treeList);
    }

    @Override
    public List<Sentences> selectWithButts(List<Tree> treeList) throws Exception {
        return sentencesDao.selectWithButts(treeList);
    }

    @Override
    public int insert(Sentences sentences) throws Exception {
        return sentencesDao.insert(sentences);
    }
}
