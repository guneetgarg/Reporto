package functional;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.Configuration;

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


    public static void main(String[] args) {
        try {
            ConfigurationReader configurationReader = new ConfigurationReader();
            Configuration config = configurationReader.getConfiguration();
            System.out.print(config.getString("name.first") + " ");

            // Printing environment-specific greetings
       /*     System.out.println(config.getString("env.greetings"));
            // Configuration parameters from hello.properties
            System.out.print(config.getString("sample.hello") + " ");
            System.out.println(config.getString("sample.world"));
            // Configuration parameters from author.xml
            System.out.print("author: ");*/
      /*      System.out.print(config.getString("name.first") + " ");
            System.out.println(config.getString("name.last"));*/
           /* System.out.println(config.getString("url"));
            // Printing environment-specific goodbye
            System.out.println(config.getString("env.goodbye"));*/
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}