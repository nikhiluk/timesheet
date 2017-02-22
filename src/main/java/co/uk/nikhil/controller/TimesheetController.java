package co.uk.nikhil.controller;

import co.uk.nikhil.service.CurrentDateService;
import co.uk.nikhil.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private CurrentDateService currentDateService;

    @RequestMapping(value = "/today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addToday(HttpServletResponse response) {
        timesheetService.addToday();
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMonthTillToday(HttpServletResponse response) {
        timesheetService.addMonthTillToday();
    }


    @RequestMapping(value = "/set-current-date", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void setCurrentDate(HttpServletRequest request) {
        String currentDate = (String)request.getParameter("currentDate");
        System.out.println("currentDate = " + currentDate);
        currentDateService.setCurrentDate(currentDate);
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getMonthTillToday() {
        return timesheetService.getDaysWorkedThisMonth().toString();
    }


    @RequestMapping(value = "/current-month", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void clearCurrentMonth(HttpServletResponse response) {
        timesheetService.clearCurrentMonth();
    }

    private void redirectToMainPage(HttpServletResponse response) {
        try {
            response.sendRedirect("/timesheetApp/timesheet.do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
