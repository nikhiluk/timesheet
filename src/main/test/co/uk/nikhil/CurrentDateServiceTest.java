package co.uk.nikhil;

import org.junit.Test;

import java.util.Date;

import static co.uk.nikhil.TestUtils.getDateToTest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CurrentDateServiceTest {

    @Test
    public void getCurrentDateForToday() throws Exception {
        CurrentDateService currentDateService = new CurrentDateService();

        Date currentDate = currentDateService.getCurrentDate();
        assertThat(currentDate, is(new Date()));

    }

    @Test
    public void getCurrentDateForInitialisedDate() throws Exception {
        CurrentDateService currentDateService = new CurrentDateService();
        currentDateService.setCurrentDate(getDateToTest("22/6/2017"));

        Date currentDate = currentDateService.getCurrentDate();
        assertThat(currentDate, is(getDateToTest("22/6/2017")));

    }
}