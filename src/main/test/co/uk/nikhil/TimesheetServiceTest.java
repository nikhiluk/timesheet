package co.uk.nikhil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TimesheetServiceTest {

    @Autowired
    private TimesheetService timesheetService;

    private CurrentDateService currentDateService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.jdbcTemplate.execute("delete from days_worked");
    }

    @Test
    public void addToday() {
        timesheetService.setCurrentDateService(new CurrentDateService());
        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }

    @Test
    public void addTodayGivenADateSetByTheExternalService() throws ParseException {
        currentDateService = new CurrentDateService(getDateToTest("13/1/2017"));
        timesheetService.setCurrentDateService(currentDateService);

        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(getDateToTest("13/1/2017")));

    }

    @Test
    public void addTodayAndIfItAlreadyExistsDoNothing() {
        timesheetService.setCurrentDateService(new CurrentDateService());
        jdbcTemplate.update("insert into days_worked values (?)", new Object[]{new Date(new java.util.Date().getTime())});

        timesheetService.addToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }


    @Test
    public void addMonthTillToday() throws ParseException {
        timesheetService.setCurrentDateService(new CurrentDateService(getDateToTest("13/1/2017")));
        timesheetService.addMonthTillToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(10));

//        List<Date> dates = jdbcTemplate.queryForList("select day from days_worked", Date.class);
//        Calendar c = Calendar.getInstance();
//        c.setTime();

//        assertThat(date, is(new Date(date.getTime())));
    }


    @Test
    public void addMonthTillDateAndIfAnyDateAlreadyExistsDoNotAddAgain() throws ParseException {
        timesheetService.setCurrentDateService(new CurrentDateService(getDateToTest("13/1/2017")));
        jdbcTemplate.update("insert into days_worked values (?)", new Object[]{new Date(getDateToTest("11/1/2017").getTime())});
        jdbcTemplate.update("insert into days_worked values (?)", new Object[]{new Date(getDateToTest("12/1/2017").getTime())});

        timesheetService.addMonthTillToday();

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(10));

//        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
//        assertThat(date, is(new Date(date.getTime())));
    }


    private java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }

}