package co.uk.nikhil;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CurrentDateService {


    private Date dateToTest;

    public CurrentDateService() {
        dateToTest = new Date();
    }

    public CurrentDateService(Date dateToTest) {
        this.dateToTest = dateToTest;
    }

    public Date getCurrentDate() {
        return dateToTest;
    }
}
