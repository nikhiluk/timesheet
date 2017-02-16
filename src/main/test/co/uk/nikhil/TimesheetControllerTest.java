package co.uk.nikhil;

import co.uk.nikhil.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.nikhil.TestUtils.assertDates;
import static co.uk.nikhil.TestUtils.getDateToTest;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {SpringConfig.class, TestConfig.class})
public class TimesheetControllerTest {

    private RestTemplate restTemplate;

    @Autowired
    private TestDbUtil testDbUtil;


    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        testDbUtil.clearTimesheetTable();
    }

    @Test
    public void addToday() throws ParseException {
        String url = "http://localhost:8080/timesheetApp/add/today";
        restTemplate.exchange(url, HttpMethod.POST, null, Void.class, new HashMap<String, Object>());

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(1));

        Date date = testDbUtil.getSingleDateFromTimesheetTable();
        assertThat(date, is(getDateToTest("13/4/2015")));
    }

    @Test
    public void addMonthTillToday() {
        String url = "http://localhost:8080/timesheetApp/add/month-till-today";
        restTemplate.exchange(url, HttpMethod.POST, null, Void.class, new HashMap<String, Object>());

        int count = testDbUtil.countAllInTimesheetTable();
        assertThat(count, is(9));

        List<Date> dates = testDbUtil.getAllDatesFromTimesheetTable();
        assertDates(dates.stream().map(d -> new java.util.Date(d.getTime())).collect(Collectors.toList()), asList(1, 2, 3, 6, 7, 8, 9, 10, 13));
    }

    @Test
    public void getDaysWorkedThisMonth() throws ParseException {

        testDbUtil.addDatesToDb(Arrays.asList("23/3/2015", "2/4/2015", "3/4/2015"));

        String urlToGet = "http://localhost:8080/timesheetApp/get/month-till-today";
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlToGet, HttpMethod.GET, null, String.class, new HashMap<String, Object>());

        assertThat(responseEntity.getBody(), is("2"));

        testDbUtil.addDatesToDb(Arrays.asList("8/4/2015", "9/4/2015"));

        urlToGet = "http://localhost:8080/timesheetApp/get/month-till-today";
        responseEntity = restTemplate.exchange(urlToGet, HttpMethod.GET, null, String.class, new HashMap<String, Object>());

        assertThat(responseEntity.getBody(), is("4"));
    }
}