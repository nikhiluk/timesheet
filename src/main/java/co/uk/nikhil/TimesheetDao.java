package co.uk.nikhil;


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
        int count = jdbcTemplate.queryForInt("select count(*) from days_worked where MONTH(day) = ? AND YEAR(day) = ?", month, year);

        return count;
    }
}
