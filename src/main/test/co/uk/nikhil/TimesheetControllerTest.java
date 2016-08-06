package co.uk.nikhil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TimesheetControllerTest {

    private RestTemplate restTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        this.jdbcTemplate.execute("delete from days_worked");
    }

    @Test
    public void addToday() {
        String url = "http://localhost:8080/add/today";
        restTemplate.exchange(url, HttpMethod.GET, null, Void.class, new HashMap<String, Object>());

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(new Date(date.getTime())));
    }
}