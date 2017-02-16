package co.uk.nikhil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
public class MvcWebConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public ServletContextTemplateResolver getViewResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".html");
        return resolver;
    }

//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    @PostConstruct
//    public void extension() {
//        templateEngine.addTemplateResolver(getViewResolver());
//    }

}
