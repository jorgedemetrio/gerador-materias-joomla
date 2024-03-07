/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config;

import java.util.Collections;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafView;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Jorge Demetrio
 * @since 4 de mar. de 2024 23:01:03
 * @version 1.0.0
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class SpringWebConfig implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  public SpringWebConfig() {
  }

  public void addFormatters(final FormatterRegistry registry) {
    registry.addFormatter(dateFormatter());
  }

  /* ******************************************************************* */
  /* GENERAL CONFIGURATION ARTIFACTS */
  /* Static Resources, i18n Messages, Formatters (Conversion Service) */
  /* ******************************************************************* */

  public void addResourceHandlers(final ResourceHandlerRegistry registry) {

    registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
  }

  @Bean
  public DateFormatter dateFormatter() {
    return new DateFormatter();
  }

  @Bean
  @Scope("prototype")
  public ThymeleafView mainView() {
    final ThymeleafView view = new ThymeleafView("layout");
    view.setMarkupSelector("#content");
    view.setStaticVariables(Collections.singletonMap("footer", "The ACME Fruit Company"));
    return view;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("Messages");
    return messageSource;
  }

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(this.applicationContext);
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(true);
    return templateResolver;
  }

  @Bean
  public ThymeleafViewResolver viewResolver() {
    final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    return viewResolver;
  }

}