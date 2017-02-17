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
        String sql = "insert into days_worked values (?)";
        jdbcTemplate.update(sql, new Object[]{new java.sql.Date(date.getTime())});
    }

    public boolean dateExists(Date date) {
        int count = jdbcTemplate.queryForInt("select count(*) from days_worked where day = ?", new Object[]{new java.sql.Date(date.getTime())});
        return count == 1;
    }

    public Integer getDaysByMonthAndYear(int month, int year) {
        return jdbcTemplate.queryForInt("select count(*) from days_worked where MONTH(day) = ? AND YEAR(day) = ?", month, year);
    }

    public void deleteDaysForMonth(int month) {
        jdbcTemplate.update("delete from days_worked where MONTH(day) = ?", month);
    }
}
