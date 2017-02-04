package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class TestConfig {


    @Bean
    @Autowired
    public CurrentDateService currentDateServiceWithTestDate() {
        CurrentDateService currentDateService = new CurrentDateService();
        currentDateService.setCurrentDate(getDateToTest("13/1/2017"));
        return currentDateService;
    }

    @Bean
    @Autowired
    public CurrentDateService currentDateService() {
        CurrentDateService currentDateService = new CurrentDateService();
        currentDateService.setCurrentDate(new Date());
        return currentDateService;
    }

    private java.util.Date getDateToTest(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
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
