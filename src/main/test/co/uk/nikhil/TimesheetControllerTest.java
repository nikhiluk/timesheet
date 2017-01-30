package co.uk.nikhil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TimesheetControllerTest {

    private RestTemplate restTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Autowired
//    TimesheetService timesheetService;
//
//    @Autowired
//    CurrentDateService currentDateService;

    @Before
    public void setup() {
//        timesheetService.setCurrentDateService(currentDateService);
        restTemplate = new RestTemplate();
        this.jdbcTemplate.execute("delete from days_worked");
    }

    @Test
    public void addToday() throws ParseException {
        String url = "http://localhost:8080/timesheetApp/add/today";
        restTemplate.exchange(url, HttpMethod.POST, null, Void.class, new HashMap<String, Object>());

        int count = jdbcTemplate.queryForInt("select count(*) from days_worked");
        assertThat(count, is(1));

        Date date = (Date) jdbcTemplate.queryForObject("select day from days_worked", Date.class);
        assertThat(date, is(getDateToTest("13/4/2015")));
    }

    private java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }


    @Configuration
    static class TimesheetTestConfig {

        @Bean
        @Autowired
        public TimesheetService timesheetService(TimesheetDao timesheetDao) {
            TimesheetService timesheetService = new TimesheetService();
            timesheetService.setTimesheetDao(timesheetDao);
            timesheetService.setCurrentDateService(currentDateService());
            return timesheetService;
        }


        @Bean
        @Autowired
        public CurrentDateService currentDateService () {
            return new CurrentDateService(getDateToTest("13/4/2015"));
        }

        private java.util.Date getDateToTest(String dateString)  {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Bean
        @Autowired
        public TimesheetDao timesheetDao(JdbcTemplate jdbcTemplate) {
            TimesheetDao timesheetDao = new TimesheetDao();
            timesheetDao.setJdbcTemplate(jdbcTemplate);
            return timesheetDao;
        }

        @Bean
        @Autowired
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/timesheet_dev");
            driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

            return driverManagerDataSource;
        }

    }
}