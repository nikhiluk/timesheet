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
        jdbcTemplate.update("insert into days_worked values ('2016-07-1')");
        jdbcTemplate.update("insert into days_worked values ('2016-07-22')");
        jdbcTemplate.update("insert into days_worked values ('2016-11-22')");
        jdbcTemplate.update("insert into days_worked values ('2016-11-1')");

        assertTrue(timesheetDao.dateExists(getDateToTest("01/07/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("22/07/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("01/11/2016")));
        assertTrue(timesheetDao.dateExists(getDateToTest("22/11/2016")));
        assertFalse(timesheetDao.dateExists(getDateToTest("23/11/2016")));
        assertFalse(timesheetDao.dateExists(getDateToTest("04/11/2016")));
    }
}