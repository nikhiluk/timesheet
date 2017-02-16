package co.uk.nikhil;

import co.uk.nikhil.service.CurrentDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class TestConfig {


    @Bean
    @Autowired
    public CurrentDateService currentDateServiceWithTestDate() {
        CurrentDateService currentDateService = new CurrentDateService();
        currentDateService.setCurrentDate(getDateToTest("13/1/2017"));
        return currentDateService;
    }

//    @Bean
//    @Autowired
//    public CurrentDateService currentDateService() {
//        CurrentDateService currentDateService = new CurrentDateService();
//        currentDateService.setCurrentDate(new Date());
//        return currentDateService;
//    }

    private java.util.Date getDateToTest(String dateString) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
