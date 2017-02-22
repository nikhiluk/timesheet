package co.uk.nikhil.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CurrentDateService {

    private String currentDateString;

    public Date getCurrentDate() {
        return this.currentDateString == null ? new Date() : getDateToTest(currentDateString);
    }

    public void setCurrentDate(String currentDateString) {
        this.currentDateString = currentDateString;
    }

    private java.util.Date getDateToTest(String dateString)  {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

}
