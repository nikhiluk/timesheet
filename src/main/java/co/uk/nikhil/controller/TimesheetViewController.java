package co.uk.nikhil.controller;

import co.uk.nikhil.service.CurrentDateService;
import co.uk.nikhil.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;

@Controller
public class TimesheetViewController   {

    @Autowired
    private CurrentDateService currentDateService;
     @Autowired
     private TimesheetService timesheetService;

    @RequestMapping(value = "/timesheet")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView timesheet() {
        ModelAndView modelAndView = new ModelAndView("timesheet");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String format = simpleDateFormat.format(currentDateService.getCurrentDate());
        modelAndView.addObject("currentDate", format);
        modelAndView.addObject("daysWorked", timesheetService.getDaysWorkedThisMonth());
        return modelAndView;
    }

}
