package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @RequestMapping(value = "/today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView addToday(ModelAndView modelAndView) {
        timesheetService.addToday();
        modelAndView.setViewName("/timesheet.do");
        return modelAndView;

    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView addMonthTillToday(ModelAndView modelAndView) {
        timesheetService.addMonthTillToday();
        modelAndView.setViewName("/timesheet.do");
        return modelAndView;
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getMonthTillToday() {
        return timesheetService.getDaysWorkedThisMonth().toString();
    }
}
