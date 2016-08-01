package co.uk.nikhil;

import java.util.Date;

public class TimesheetService {

    private TimesheetDao timesheetDao;

    public void setTimesheetDao(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    public void addToday() {
        if (!timesheetDao.dateExists(new Date())) {
            timesheetDao.addToday();
        }
    }
}
