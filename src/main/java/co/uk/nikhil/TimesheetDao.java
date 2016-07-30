package co.uk.nikhil;


import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Date;

public class TimesheetDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToday() {
        String sql = "insert into days_worked values (?)";
        jdbcTemplate.update(sql, new Object[]{new java.sql.Date(new Date().getTime())});
    }
}
