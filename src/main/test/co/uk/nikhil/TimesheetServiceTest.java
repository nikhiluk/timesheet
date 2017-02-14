package co.uk.nikhil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.nikhil.TestUtils.assertDates;
import static co.uk.nikhil.TestUtils.getDateToTest;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {SpringConfig.class, TestConfig.class})
public class TimesheetServiceTest {

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private CurrentDateService currentDateServiceWithTestDate;

    @Autowired
    private CurrentDateService currentDateService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.jdbcTemplate.execute("delete from days_worked");
    }

    @Test
    public void addToday() {
        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }

    @Test
    public void addTodayGivenADateSetByTheExternalService() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);

        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(getDateToTest("13/1/2017")));

    }

    @Test
    public void addTodayAndIfItAlreadyExistsDoNothing() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        jdbcTemplate.update("insert into days_worked values (?)", getDateTime("13/1/2017"));

        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }


    @Test
    public void addMonthTillToday() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);

        timesheetService.addMonthTillToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(10));

        List<Date> dates = jdbcTemplate.queryForList("select day from days_worked", Date.class);
        assertDates(dates.stream().map(d -> new java.util.Date(d.getTime())).collect(Collectors.toList()), asList(2, 3, 4, 5, 6, 9, 10, 11, 12, 13));
    }


    @Test
    public void addMonthTillDateAndIfAnyDateAlreadyExistsDoNotAddAgain() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        addDateToDb(Arrays.asList("11/1/2017", "12/1/2017"));

        timesheetService.addMonthTillToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(10));

        List<Date> dates = jdbcTemplate.queryForList("select day from days_worked", Date.class);
        assertDates(dates.stream().map(d -> new java.util.Date(d.getTime())).collect(Collectors.toList()), asList(2, 3, 4, 5, 6, 9, 10, 11, 12, 13));
    }


    @Test
    public void getDaysWorkedInCurrentMonth() throws ParseException {
        timesheetService.setCurrentDateService(currentDateServiceWithTestDate);
        addDateToDb(Arrays.asList("11/1/2017", "12/1/2017"));

        int daysWorkedThisMonth = timesheetService.getDaysWorkedThisMonth();
        assertThat(daysWorkedThisMonth, is(2));

        addDateToDb(Arrays.asList("9/1/2017", "13/1/2017", "13/1/2016"));


        daysWorkedThisMonth = timesheetService.getDaysWorkedThisMonth();
        assertThat(daysWorkedThisMonth, is(4));
    }

    private void addDateToDb(List<String> dates) throws ParseException {
        for (String date : dates) {
            jdbcTemplate.update("insert into days_worked values (?)", getDateTime(date));
        }
    }

    private Date getDateTime(String dateString) throws ParseException {
        return new Date(getDateToTest(dateString).getTime());
    }
}