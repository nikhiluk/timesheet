package co.uk.nikhil;

import co.uk.nikhil.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean resourcesServlet() {
        return getServletRegistrationBean("resources1", "*.do");
    }

    @Bean
    public ServletRegistrationBean timesheetServlet() {
       return getServletRegistrationBean("timesheet", "/add/*", "/get/*", "/clear/*");
    }

    private ServletRegistrationBean getServletRegistrationBean(String registrationName, String... urlMappings) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(SpringConfig.class);
        dispatcherServlet.setApplicationContext(applicationContext);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, urlMappings);
        servletRegistrationBean.setName(registrationName);
        return servletRegistrationBean;
    }

}