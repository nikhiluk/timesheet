package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TimesheetService {

    @Autowired
    private TimesheetDao timesheetDao;
    @Autowired
    private CurrentDateService currentDateService;
    @Autowired
    private Timesheet timesheet;


    public void addToday() {
        Date currentDate = currentDateService.getCurrentDate();
        addDate(currentDate);
    }

    public void addMonthTillToday() {
        Date currentDate = currentDateService.getCurrentDate();
        List<Date> dates = timesheet.getWorkingMonthTill(currentDate);
        dates.forEach(this::addDate);
    }

    private void addDate(Date date) {
        if (!timesheetDao.dateExists(date)) {
            timesheetDao.addDate(date);
        }
    }

    public void setCurrentDateService(CurrentDateService currentDateService) {
        this.currentDateService = currentDateService;
    }
}
