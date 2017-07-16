package service.config;

import model.config.Configuration;

import java.util.List;

/**
 * 2017/7/12
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface IConfigurationService {
    void addConfiguration(Configuration configuration) throws Exception;
    List<Configuration> getConfiguration() throws Exception;
}
