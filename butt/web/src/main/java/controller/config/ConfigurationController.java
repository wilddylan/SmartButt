package controller.config;

import model.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import service.config.IConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2017/7/12
 * Created by dylan.
 * Home: http://www.devdylan.cn
 */
@Controller
@RequestMapping("/config")
public class ConfigurationController {
    private final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    private final IConfigurationService configurationService;

    @Autowired
    public ConfigurationController(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @RequestMapping("/index")
    public String indexPage() {
        Configuration configuration = new Configuration();
        configuration.setId("2");

        try {
            configurationService.addConfiguration(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }

    @RequestMapping(value = "/get")
    public ModelAndView getConfig() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        try {
            modelAndView.addObject("list", configurationService.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }
}
