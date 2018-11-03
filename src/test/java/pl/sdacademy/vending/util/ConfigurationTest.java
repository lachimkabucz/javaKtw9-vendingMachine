package pl.sdacademy.vending.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private Configuration testedConfig;

    @Before
    public void init() {
        testedConfig = new Configuration();
    }

    @Test
    public void shouldReturnDefaultStringValueWhenPropertyIsUnknown() {
        // given
        String unknownPropertyName = "adltnvaeyikbtqewybikyia";
        String expectedDefault = "javaIsAwesome";

        // when
        String propertyValue = testedConfig
                .getStringProperty(unknownPropertyName, expectedDefault);

        // then
        assertEquals(expectedDefault, propertyValue);
    }

    @Test
    public void shouldReturnKnownLongProperty() {
        // given
        String propertyName = "test.property.long";
        Long defaultValue = 1337L;

        // when
        Long propertyValue =
                testedConfig.getLongProperty(propertyName, defaultValue);

        // then
        assertEquals((Long) 7L, propertyValue);
    }
}