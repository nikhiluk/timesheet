package co.uk.nikhil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
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
    public void addToday() {
        timesheetDao.addToday();
        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }
}