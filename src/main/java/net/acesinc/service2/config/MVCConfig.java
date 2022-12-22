/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.service2.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 *
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = "net.acesinc")
public class MVCConfig implements WebMvcConfigurer {

    private Logger log = LoggerFactory.getLogger(MVCConfig.class);

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(WebApplicationContext wac) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        
        SpringTemplateEngine templateEngine = templateEngine(wac);
        
        viewResolver.setTemplateEngine(templateEngine);

        // Disable caching for dev profile
        if (isDevProfileActive(env)) {
            log.info("DEV Profile is active. Disabling template caching.");
//            templateEngine.setCacheable(false);
            templateEngine.setCacheManager(null);
            viewResolver.setCache(false);
        }
        return viewResolver;
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(WebApplicationContext wac){
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver(wac));
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(layoutDialect());
        return templateEngine;
    }
    
    @Bean
    public SpringResourceTemplateResolver templateResolver(WebApplicationContext wac){
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(wac);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        
        return templateResolver;
    }

    

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    /**
     * Returns true if the dev profile is active. Otherwise returns false.
     */
     public static boolean isDevProfileActive(Environment env) {
         return Arrays.asList(env.getActiveProfiles()).stream().anyMatch(s -> s.contains("dev"));
     }


}
