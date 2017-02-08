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
import java.util.Arrays;
import java.util.List;

import static co.uk.nikhil.TestUtils.getDateToTest;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, TestConfig.class})
public class TimesheetDaoTest {

    @Autowired
    TimesheetDao timesheetDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.jdbcTemplate.execute("delete from days_worked");
    }

    @Test
    public void addDate() throws ParseException {
        timesheetDao.addDate(getDateToTest("01/07/2016"));

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(getDateToTest("01/07/2016")));
    }

    @Test
    public void dateExists() throws ParseException {
        addDateToDb(Arrays.asList("1/7/2016", "22/7/2016", "22/11/2016", "1/11/2016"));

        assertTrue(timesheetDao.dateExists(getDateToTest("01/07/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("22/07/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("01/11/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("22/11/2016")));
        assertFalse(timesheetDao.dateExists(getDateToTest("23/11/2016")));
        assertFalse(timesheetDao.dateExists(getDateToTest("04/11/2016")));
    }

    @Test
    public void getDaysByMonthAndYear() throws ParseException {

        addDateToDb(Arrays.asList("1/1/2016", "11/1/2016", "1/7/2016", "22/7/2016", "22/7/2015", "22/11/2016", "1/11/2016"));

        assertThat(timesheetDao.getDaysByMonthAndYear(1, 2016), is(2) );
        assertThat(timesheetDao.getDaysByMonthAndYear(7, 2016), is(2) );
        assertThat(timesheetDao.getDaysByMonthAndYear(7, 2015), is(1) );
        assertThat(timesheetDao.getDaysByMonthAndYear(11, 2016), is(2) );
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