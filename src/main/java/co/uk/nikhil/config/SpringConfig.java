package co.uk.nikhil.config;

import co.uk.nikhil.service.CurrentDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

    private java.util.Date getDateToTest(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }
}
