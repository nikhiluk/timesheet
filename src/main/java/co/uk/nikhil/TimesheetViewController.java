package co.uk.nikhil;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class TimesheetViewController   {

    @RequestMapping(value = "/timesheet")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView timesheet() {
        ModelAndView modelAndView = new ModelAndView("timesheet");
        return modelAndView;
    }

}
