package co.uk.nikhil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TimesheetPageTest {


    @Test
    public void testPage() {
        Capabilities capabilities = new DesiredCapabilities("chrome", "49.0", Platform.VISTA);
System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Downloads\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver(capabilities);
        webDriver.get("http://localhost:8080/");
        String title = webDriver.getTitle();
        System.out.println("title = " + title);
        webDriver.quit();
    }
}
