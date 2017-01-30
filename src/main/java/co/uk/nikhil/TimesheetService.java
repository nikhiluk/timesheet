package co.uk.nikhil;

import java.util.Date;

public class TimesheetService {

    private TimesheetDao timesheetDao;
    private CurrentDateService currentDateService;

    public void setTimesheetDao(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    public void addToday() {
        Date currentDate = currentDateService.getCurrentDate();
        if (!timesheetDao.dateExists(currentDate)) {
            timesheetDao.addDate(currentDate);
        }
    }

    public void setCurrentDateService(CurrentDateService currentDateService) {
        this.currentDateService = currentDateService;
    }
}
