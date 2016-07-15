package co.uk.nikhil;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Calendar.*;

public class Timesheet {

    private static final int FIRST_OF_THE_MONTH = 1;

    public List<Date> getWeekTillNow(Date startDate) {
        List<Calendar> calendars = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int currentMonth = c.get(MONTH);

        calendars.add(getCal(c));
        while (c.get(DAY_OF_WEEK) != MONDAY) {
            c.add(DAY_OF_MONTH, -1);
            calendars.add(getCal(c));
        }


        List<Calendar> filteredCalendars = calendars.stream()
                .filter(calendar -> calendar.get(MONTH) == currentMonth)
                .filter(calendar -> calendar.get(DAY_OF_WEEK) != SATURDAY && calendar.get(DAY_OF_WEEK) != SUNDAY)
                .collect(Collectors.toList());

        List<Date> dates = filteredCalendars.stream().map(Calendar::getTime).collect(Collectors.toList());
        Collections.reverse(dates);
        return dates;
    }

    public List<Date> getMonthTillNow(Date startDate) {
        List<Calendar> calendars = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);

        calendars.add(getCal(c));
        while (c.get(DAY_OF_MONTH) != FIRST_OF_THE_MONTH) {
            c.add(DAY_OF_MONTH, -1);
            calendars.add(getCal(c));
        }


        List<Calendar> filteredCalendars = calendars.stream()
                .filter(calendar -> calendar.get(DAY_OF_WEEK) != SATURDAY && calendar.get(DAY_OF_WEEK) != SUNDAY)
                .collect(Collectors.toList());

        List<Date> dates = filteredCalendars.stream().map(Calendar::getTime).collect(Collectors.toList());
        Collections.reverse(dates);
        return dates;
    }

    private GregorianCalendar getCal(Calendar c) {
        return new GregorianCalendar(c.get(YEAR), c.get(MONTH), c.get(DAY_OF_MONTH));
    }
}
