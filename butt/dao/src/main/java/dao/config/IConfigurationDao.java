package dao.config;

import model.config.Configuration;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2017/7/12
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
public interface IConfigurationDao {

    void addConfiguration(@Param("configuration") Configuration configuration) throws Exception;
    List<Configuration> getConfiguration() throws Exception;
}
