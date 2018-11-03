package pl.sdacademy.vending.model;

import org.junit.Test;
import pl.sdacademy.vending.util.Configuration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class VendingMachineTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenZeroRowsConfigured() {
        // given
        Configuration config = mock(Configuration.class);
        doReturn(0L)
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
        fail("Exception should be raised");
    }

}