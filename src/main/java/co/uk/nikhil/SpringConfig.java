package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@ComponentScan(basePackages = "co.uk.nikhil")
//@EnableWebMvc
public class SpringConfig {

    @Autowired
    Environment env;


    @Bean
    @Autowired
    public CurrentDateService currentDateService() {
        String currentDate = env.getProperty("current.date");
        CurrentDateService currentDateService = new CurrentDateService();
        if (currentDate != null && !currentDate.isEmpty()) {
            try {
                currentDateService.setCurrentDate(getDateToTest(currentDate));
            } catch (ParseException e) {
                currentDateService.setCurrentDate(new Date());
            }
        } else {
            currentDateService.setCurrentDate(new Date());

        }
        return currentDateService;
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
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
