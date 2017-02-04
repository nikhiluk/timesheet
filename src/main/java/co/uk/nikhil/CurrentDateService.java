package co.uk.nikhil;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CurrentDateService {

    public Date currentDate;

    public Date getCurrentDate() {
        return this.currentDate == null ? new Date() :  this.currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}
