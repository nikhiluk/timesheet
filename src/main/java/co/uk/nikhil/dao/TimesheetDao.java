package co.uk.nikhil.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TimesheetDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addDate(Date date) {
        String sql = "insert into timesheet_dev.days_worked values (?)";
        jdbcTemplate.update(sql, new Object[]{new java.sql.Date(date.getTime())});
    }

    public boolean dateExists(Date date) {
        int count = jdbcTemplate.queryForInt("select count(*) from timesheet_dev.days_worked where day = ?", new Object[]{new java.sql.Date(date.getTime())});
        return count == 1;
    }

    public Integer getDaysByMonthAndYear(int month, int year) {
        return jdbcTemplate.queryForInt("select count(*) from timesheet_dev.days_worked where date_part('month', day) = ? AND date_part('year', day) = ?", month, year);
    }

    public void deleteDaysForMonth(int month) {
        jdbcTemplate.update("delete from timesheet_dev.days_worked where date_part('month', day) = ?", month);
    }
}
