package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @RequestMapping(value = "/today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addToday() {
        timesheetService.addToday();
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMonthTillToday() {
        timesheetService.addMonthTillToday();
    }


    public void setTimesheetService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }
}
