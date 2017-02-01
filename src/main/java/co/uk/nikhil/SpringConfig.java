package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
@PropertySource("classpath:app.properties")
@EnableWebMvc
public class SpringConfig {

    @Autowired
    Environment env;

    @Bean
    @Autowired
    public TimesheetService timesheetService(TimesheetDao timesheetDao) {
        TimesheetService timesheetService = new TimesheetService();
        timesheetService.setTimesheetDao(timesheetDao);
        String currentDate = env.getProperty("current.date");
        if (currentDate != null && !currentDate.isEmpty()) {
            try {
                timesheetService.setCurrentDateService(new CurrentDateService(getDateToTest(currentDate)));
            } catch (ParseException e) {
                timesheetService.setCurrentDateService(new CurrentDateService());
            }
        } else {
            timesheetService.setCurrentDateService(new CurrentDateService());
        }
        return timesheetService;
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
        String dbName = env.getProperty("database.name");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName);
        driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

        return driverManagerDataSource;
    }

    private java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }
}
