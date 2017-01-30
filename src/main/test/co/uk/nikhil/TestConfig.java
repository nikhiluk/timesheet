package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @Bean
    @Autowired
    public TimesheetService timesheetService(TimesheetDao timesheetDao) {
        TimesheetService timesheetService = new TimesheetService();
        timesheetService.setTimesheetDao(timesheetDao);
        timesheetService.setCurrentDateService(new CurrentDateService() );
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
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/timesheet_dev");
        driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

        return driverManagerDataSource;
    }
}
