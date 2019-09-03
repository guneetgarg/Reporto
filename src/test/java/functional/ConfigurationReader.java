package functional;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;

public class ConfigurationReader {
    //   https://www.javaworld.com/article/2073030/organize-applications--multiple-environment-configurations.html?page=2

    Configuration config = null;

    public ConfigurationReader() {
    }

    public void load() {
        try {
            ConfigurationFactory factory = new ConfigurationFactory();
            factory.setConfigurationFileName(this.getEnvConfigXMLPath());
            config = factory.getConfiguration();
        } catch (Exception exc) {
            exc.getMessage();
        }
    }

    public Configuration getConfiguration() {
        if (config == null) {
            load();
        }
        return config;
    }

    private String getEnvConfigXMLPath() {
        StringBuffer srtBuffer = new StringBuffer("configuration/");
        // System env variable is mapped to configuration/[env] folder
        srtBuffer.append(System.getProperty("env"));
        srtBuffer.append("/config.xml");
        return srtBuffer.toString();
    }
}