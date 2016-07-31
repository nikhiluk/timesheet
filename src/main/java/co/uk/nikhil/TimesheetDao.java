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

    public boolean dateExists(Date date) {
        int count = jdbcTemplate.queryForInt("select count(*) from days_worked where day = ?", new Object[]{new java.sql.Date(date.getTime())});
        return count == 1;
    }
}
