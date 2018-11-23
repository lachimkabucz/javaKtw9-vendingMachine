package pl.sdacademy.vending.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Class that loads properties configured in application.properties file, so they can be used in various places in application.
 */
public class Configuration {

    private final Properties properties;

    public Configuration() {
        // creating empty properties object.
        properties = new Properties();
        try (InputStream propertiesStream
                     = ClassLoader
                .getSystemResourceAsStream(
                        "application.properties")) {
            // loading everything from properties file into java object
            properties.load(propertiesStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // debug line for printing all loaded properties.
//        properties.list(System.out);
    }

    /**
     * Loads property from configuration file and tries to convert it to Long value
     */
    public Long getLongProperty(String paramName, Long defaultValue) {
        String propertyValue = properties.getProperty(paramName);
        if (propertyValue == null) {
            return defaultValue;
        }
        return Long.parseLong(propertyValue);
    }

    /**
     * Loads property from configuration file
     */
    public String getStringProperty(String paramName, String defaultValue) {
        return properties.getProperty(paramName, defaultValue);
    }
}