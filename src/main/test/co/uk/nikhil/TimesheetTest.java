package co.uk.nikhil;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimesheetTest {

    private Timesheet timesheet = new Timesheet();
    private Calendar c = Calendar.getInstance();

    @Test
    public void givenADateGetTheWeekTillNowStartingFromMonday() throws ParseException {
        List<Date> dates;

        dates = timesheet.getWorkingWeekTill(getDateToTest("11/07/2016"));
        assertDates(dates, asList(11));

        dates = timesheet.getWorkingWeekTill(getDateToTest("12/07/2016"));
        assertDates(dates, asList(11, 12));

        dates = timesheet.getWorkingWeekTill(getDateToTest("13/07/2016"));
        assertDates(dates, asList(11, 12, 13));

        dates = timesheet.getWorkingWeekTill(getDateToTest("14/07/2016"));
        assertDates(dates, asList(11, 12, 13, 14));

        dates = timesheet.getWorkingWeekTill(getDateToTest("15/07/2016"));
        assertDates(dates, asList(11, 12, 13, 14, 15));

        //////ignore weekends
        dates = timesheet.getWorkingWeekTill(getDateToTest("16/07/2016"));
        assertDates(dates, asList(11, 12, 13, 14, 15));

        dates = timesheet.getWorkingWeekTill(getDateToTest("17/07/2016"));
        assertDates(dates, asList(11, 12, 13, 14, 15));


        //////do not spill into previous month
        dates = timesheet.getWorkingWeekTill(getDateToTest("1/07/2016"));
        assertDates(dates, asList(1));

        /////If start day is weekend and week day is previous month, nothing should be returned
        dates = timesheet.getWorkingWeekTill(getDateToTest("1/05/2016"));
        assertThat(dates.size(), is(0));

    }

    @Test
    public void givenADateGetTheMonthTillNowStartingFromTheFirst() throws ParseException {
        List<Date> dates;

        dates = timesheet.getWorkingMonthTill(getDateToTest("13/07/2016"));
        assertDates(dates, asList(1, 4, 5, 6, 7, 8, 11, 12, 13));

        dates = timesheet.getWorkingMonthTill(getDateToTest("1/07/2016"));
        assertDates(dates, asList(1));

        dates = timesheet.getWorkingMonthTill(getDateToTest("2/07/2016"));
        assertDates(dates, asList(1));

        dates = timesheet.getWorkingMonthTill(getDateToTest("3/07/2016"));
        assertDates(dates, asList(1));

        dates = timesheet.getWorkingMonthTill(getDateToTest("31/07/2016"));
        assertDates(dates, asList(1, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 25, 26, 27, 28, 29));
    }

    private void assertDates(List<Date> dates, List<Integer> expectedDates) {
        assertThat(dates.size(), is(expectedDates.size()));
        for (int i = 0; i < dates.size(); i++) {
            c.setTime(dates.get(i));
            assertThat(c.get(DAY_OF_MONTH), is(expectedDates.get(i)));
        }
    }

    private Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }
}