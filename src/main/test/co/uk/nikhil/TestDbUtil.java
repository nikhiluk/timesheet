package co.uk.nikhil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import static co.uk.nikhil.TestUtils.getDateToTest;

@Component
public class TestDbUtil {


    @Autowired
    JdbcTemplate jdbcTemplate;

    void clearTimesheetTable() {
        this.jdbcTemplate.execute("delete from timesheet_dev.days_worked");
    }

    int countAllInTimesheetTable() {
        return jdbcTemplate.queryForInt("select count(*) from timesheet_dev.days_worked");
    }

    void insertDayInTimesheetTable(String date) {
        try {
            jdbcTemplate.update("insert into timesheet_dev.days_worked values (?)", getDateTime(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private Date getDateTime(String dateString) throws ParseException {
        return new Date(getDateToTest(dateString).getTime());
    }

    List<Date> getAllDatesFromTimesheetTable() {
        return jdbcTemplate.queryForList("select day from timesheet_dev.days_worked", Date.class);
    }

    Date getSingleDateFromTimesheetTable() {
        return (Date) jdbcTemplate.queryForObject("select day from timesheet_dev.days_worked", Date.class);
    }

    void addDatesToDb(List<String> dates) throws ParseException {
        dates.forEach(this::insertDayInTimesheetTable);
    }
}
