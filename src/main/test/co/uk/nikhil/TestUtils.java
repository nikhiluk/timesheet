package co.uk.nikhil;

import org.hamcrest.CoreMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestUtils {


    static void assertDates(List<Date> dates, List<Integer> expectedDates) {
        Calendar c = Calendar.getInstance();
        dates.sort(java.util.Date::compareTo);
        assertThat(dates.size(), CoreMatchers.is(expectedDates.size()));
        for (int i = 0; i < dates.size(); i++) {
            c.setTime(dates.get(i));
            assertThat(c.get(DAY_OF_MONTH), CoreMatchers.is(expectedDates.get(i)));
        }
    }

    static java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }
}
