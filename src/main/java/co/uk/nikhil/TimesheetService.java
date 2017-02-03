package co.uk.nikhil;

import java.util.Date;
import java.util.List;

public class TimesheetService {

    private TimesheetDao timesheetDao;
    private CurrentDateService currentDateService;
    private Timesheet timesheet;

    public void setTimesheetDao(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    public void addToday() {
        Date currentDate = currentDateService.getCurrentDate();
        if (!timesheetDao.dateExists(currentDate)) {
            timesheetDao.addDate(currentDate);
        }
    }

    public void addMonthTillToday() {
        Date currentDate = currentDateService.getCurrentDate();
        List<Date> dates = timesheet.getWorkingMonthTill(currentDate);
        for (Date date : dates) {
            if (!timesheetDao.dateExists(date)) {
                timesheetDao.addDate(date);
            }
        }

    }

    public void setCurrentDateService(CurrentDateService currentDateService) {
        this.currentDateService = currentDateService;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
}
