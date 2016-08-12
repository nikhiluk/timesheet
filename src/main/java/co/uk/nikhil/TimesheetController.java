package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimesheetController   {

    @Autowired
    private TimesheetService timesheetService;

    @RequestMapping(value = "/add/today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addToday() {
        timesheetService.addToday();
    }

    @RequestMapping(value = "/timesheet")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView timesheet() {
        ModelAndView modelAndView = new ModelAndView("timesheet");
        return modelAndView;
    }

    public void setTimesheetService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }
}
