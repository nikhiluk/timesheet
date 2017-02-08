package co.uk.nikhil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @RequestMapping(value = "/today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addToday(HttpServletResponse response) {
        timesheetService.addToday();
        redirectToMainPage(response);
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMonthTillToday(HttpServletResponse response) {
        timesheetService.addMonthTillToday();
        redirectToMainPage(response);
    }

    @RequestMapping(value = "/month-till-today", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getMonthTillToday() {
        return timesheetService.getDaysWorkedThisMonth().toString();
    }

    private void redirectToMainPage(HttpServletResponse response) {
        try {
            response.sendRedirect("/timesheetApp/timesheet.do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
