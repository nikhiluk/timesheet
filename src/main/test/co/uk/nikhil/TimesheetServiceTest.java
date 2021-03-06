package co.uk.nikhil;

import co.uk.nikhil.config.SpringConfig;
import co.uk.nikhil.service.CurrentDateService;
import co.uk.nikhil.service.TimesheetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.nikhil.TestUtils.assertDates;
import static co.uk.nikhil.TestUtils.getDateToTest;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = { SpringConfig.class})
public class TimesheetServiceTest {

    @Autowired
    private TimesheetService timesheetService;

    private CurrentDateService currentDateServiceWithTestDate = new CurrentDateService();

    private CurrentDateService currentDateService = new CurrentDateService();

    @Autowired
    TestDbUtil testDbUtil;

    @Before
    public void setUp() throws Exception {
        testDbUtil.clearTimesheetTable();
        currentDateServiceWithTestDate.setCurrentDate("13/1/2017");
        currentDateService.setCurrentDate("13/4/2015");

    }

//    @Test
//    public void addToday() {
//        timesheetService.addToday();
//
//        int count = testDbUtil.countAllInTimesheetTable();
//        assertThat(count, is(1));
//
//        Date date = testDbUtil.getSingleDateFromTimesheetTable();
//        assertThat(date, is(new Date(date.getTime())));
//    }

    @Test
    public void addTodayGivenADateSetByTheExternalService() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);

        timesheetService.addToday();

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(1));

        Date date = testDbUtil.getSingleDateFromTimesheetTable();
        assertThat(date, is(getDateToTest("13/1/2017")));

    }

    @Test
    public void addTodayAndIfItAlreadyExistsDoNothing() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        testDbUtil.insertDayInTimesheetTable("13/1/2017");

        timesheetService.addToday();

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(1));

        Date date = testDbUtil.getSingleDateFromTimesheetTable();
        assertThat(date, is(new Date(date.getTime())));
    }


    @Test
    public void addMonthTillToday() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);

        timesheetService.addMonthTillToday();

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(10));

        List<Date> dates = testDbUtil.getAllDatesFromTimesheetTable();
        assertDates(dates.stream().map(d -> new java.util.Date(d.getTime())).collect(Collectors.toList()), asList(2, 3, 4, 5, 6, 9, 10, 11, 12, 13));
    }


    @Test
    public void addMonthTillDateAndIfAnyDateAlreadyExistsDoNotAddAgain() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        testDbUtil.addDatesToDb(Arrays.asList("11/1/2017", "12/1/2017"));

        timesheetService.addMonthTillToday();

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(10));

        List<Date> dates = testDbUtil.getAllDatesFromTimesheetTable();
        assertDates(dates.stream().map(d -> new java.util.Date(d.getTime())).collect(Collectors.toList()), asList(2, 3, 4, 5, 6, 9, 10, 11, 12, 13));
    }


    @Test
    public void getDaysWorkedInCurrentMonth() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        testDbUtil.addDatesToDb(Arrays.asList("11/1/2017", "12/1/2017"));

        int daysWorkedThisMonth = timesheetService.getDaysWorkedThisMonth();
        assertThat(daysWorkedThisMonth, is(2));

        testDbUtil.addDatesToDb(Arrays.asList("9/1/2017", "13/1/2017", "13/1/2016"));


        daysWorkedThisMonth = timesheetService.getDaysWorkedThisMonth();
        assertThat(daysWorkedThisMonth, is(4));
    }

    @Test
    public void clearCurrentMonth() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        testDbUtil.addDatesToDb(Arrays.asList("23/3/2017", "2/1/2017", "3/1/2017", "23/1/2017", "30/1/2017"));

        timesheetService.clearCurrentMonth();

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(1));

        Date date = testDbUtil.getSingleDateFromTimesheetTable();
        assertThat(date, is(getDateToTest("23/3/2017")));

    }

}