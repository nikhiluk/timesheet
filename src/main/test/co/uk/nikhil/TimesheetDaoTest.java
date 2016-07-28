package co.uk.nikhil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TimesheetDaoTest {

    @Autowired
    TimesheetDao timesheetDao;

    @Autowired
    DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("delete from days_worked");
        ps.execute();
        ps.close();
    }

    @Test
    public void addToday() {
        timesheetDao.addDate();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from days_worked");
            ResultSet resultSet = ps.executeQuery();
            int count = 0;
            while(resultSet.next()) {
                Date date = resultSet.getDate(1);
                assertThat(date, is(new Date(date.getTime())));
                count++;
            }
            assertThat(count, is(1));
            ps.close();
        } catch (SQLException e) {
            fail();
        }
    }
}