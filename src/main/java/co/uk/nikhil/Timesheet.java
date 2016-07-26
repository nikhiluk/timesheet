package co.uk.nikhil;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Calendar.*;

public class Timesheet {

    private static final java.util.function.Predicate<Calendar> WEEKDAYS = calendar -> calendar.get(DAY_OF_WEEK) != SATURDAY && calendar.get(DAY_OF_WEEK) != SUNDAY;
    private static final int FIRST_OF_THE_MONTH = 1;

    public List<Date> getWeekTillNow(Date startDate) {
        List<Calendar> calendars = new ArrayList<>();

        Calendar c = initializeCalendar(startDate);

        int currentMonth = c.get(MONTH);
        Predicate<Calendar> daysInCurrentMonth = calendar -> calendar.get(MONTH) == currentMonth;

        calendars.add(getCal(c));
        while (c.get(DAY_OF_WEEK) != MONDAY) {
            c.add(DAY_OF_MONTH, -1);
            calendars.add(getCal(c));
        }

        List<Calendar> filteredCalendars = filterCalendars(calendars, asList(WEEKDAYS, daysInCurrentMonth));
        return convertCalendarsToDates(filteredCalendars);
    }

    public List<Date> getMonthTillNow(Date startDate) {
        List<Calendar> calendars = new ArrayList<>();

        Calendar c = initializeCalendar(startDate);

        calendars.add(getCal(c));
        while (c.get(DAY_OF_MONTH) != FIRST_OF_THE_MONTH) {
            c.add(DAY_OF_MONTH, -1);
            calendars.add(getCal(c));
        }

        List<Calendar> filteredCalendars = filterCalendars(calendars, asList(WEEKDAYS));
        return convertCalendarsToDates(filteredCalendars);
    }

    private Calendar initializeCalendar(Date startDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        return c;
    }

    private List<Calendar> filterCalendars(List<Calendar> calendars, List<Predicate<Calendar>> filters) {
        Stream<Calendar> stream = calendars.stream();
        for (Predicate<Calendar> predicate : filters) {
            stream = stream.filter(predicate);
        }
        return stream.collect(Collectors.toList());
    }

    private List<Date> convertCalendarsToDates(List<Calendar> filteredCalendars) {
        List<Date> dates = filteredCalendars.stream().map(Calendar::getTime).collect(Collectors.toList());
        Collections.reverse(dates);
        return dates;
    }

    private GregorianCalendar getCal(Calendar c) {
        return new GregorianCalendar(c.get(YEAR), c.get(MONTH), c.get(DAY_OF_MONTH));
    }
}
