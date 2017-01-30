package co.uk.nikhil;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CurrentDateServiceTest {

    @Test
    public void getCurrentDateForToday() throws Exception {
        CurrentDateService currentDateService = new CurrentDateService();

        Date currentDate = currentDateService.getCurrentDate();

        assertThat(currentDate, is(new Date()));

    }



    @Test
    public void getCurrentDateForInitialisedDate() throws Exception {
        CurrentDateService currentDateService = new CurrentDateService(getDateToTest("22/6/2017"));

        Date currentDate = currentDateService.getCurrentDate();

        assertThat(currentDate, is(getDateToTest("22/6/2017")));

    }


    private java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }
}