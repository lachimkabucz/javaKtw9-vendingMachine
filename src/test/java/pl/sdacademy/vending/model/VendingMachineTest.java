package pl.sdacademy.vending.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sdacademy.vending.util.Configuration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class VendingMachineTest {

    @Test(expected = IllegalArgumentException.class)
    @Parameters
    public void shouldThrowExceptionWhenConfiguredSizeIsInvalid(Long configuredRows, Long configuredCols) {
        // given
        Configuration config = mock(Configuration.class);
        doReturn(configuredRows)
                .when(config)
                .getLongProperty(
                        eq("machine.size.rows"),
                        anyLong()
                );
        doReturn(configuredCols)
                .when(config)
                .getLongProperty(
                        eq("machine.size.cols"),
                        anyLong()
                );

        // when
        new VendingMachine(config);
        
        // then
        fail("Exception should be raised");
    }

    @Test
    public void shouldSucceedWithValidMachineCreation() {
        // given
        Configuration config = mock(Configuration.class);
        doReturn(6L)
                .when(config)
                .getLongProperty(
                        eq("machine.size.rows"),
                        anyLong()
                );
        doReturn(4L)
                .when(config)
                .getLongProperty(
                        eq("machine.size.cols"),
                        anyLong()
                );

        // when
        new VendingMachine(config);

        // then
        // nothing to do
    }

    public Object[] parametersForShouldThrowExceptionWhenConfiguredSizeIsInvalid() {
        return new Object[] {
                new Object[] {0L, 4L},
                new Object[] {6L, 0L},
                new Object[] {27L, 4L},
                new Object[] {6L, 10L}
        };
    }

}