package service.config.impl;

import dao.config.IConfigurationDao;
import model.config.Configuration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import service.config.IConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2017/7/12
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Service("configurationService")
public class ConfigurationImpl implements IConfigurationService {

    @Autowired
    private IConfigurationDao configurationDao;

    @Override
    @CacheEvict(value = {"ConfigurationImpl.getConfiguration"}, allEntries = true)
    public void addConfiguration(Configuration configuration) throws Exception {
        configurationDao.addConfiguration(configuration);
    }

    @Override
    @Cacheable("ConfigurationImpl.getConfiguration")
    public List<Configuration> getConfiguration() throws Exception {
        return configurationDao.getConfiguration();
    }
}