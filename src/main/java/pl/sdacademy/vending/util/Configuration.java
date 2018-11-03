package pl.sdacademy.vending.util;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final Properties properties;

    public Configuration() {
        properties = new Properties();
        try (InputStream propertiesStream
                     = ClassLoader
                        .getSystemResourceAsStream(
                                "application.properties")) {
            properties.load(propertiesStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        properties.list(System.out);
    }

    public Long getLongProperty(String paramName, Long defaultValue) {
        String propertyValue = properties.getProperty(paramName);
        if (propertyValue == null) {
            return defaultValue;
        }
        return Long.parseLong(propertyValue);
    }

    public String getStringProperty(String paramName, String defaultValue) {
        return properties.getProperty(paramName, defaultValue);
    }
}
