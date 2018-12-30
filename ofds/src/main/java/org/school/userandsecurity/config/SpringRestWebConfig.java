package org.school.userandsecurity.config;

import java.util.List;
import java.util.Locale;

import org.openframework.common.rest.advice.RestResponseBodyAdvice;
import org.openframework.common.rest.advice.RestResponseEntityExceptionHandler;
import org.openframework.common.rest.interceptor.CustomRequestHandler;
import org.openframework.commons.spring.utils.LoggingBean;
import org.school.userandsecurity.rest.argumentresolver.UserProfileHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "org.school.userandsecurity" })
@EnableJpaRepositories("org.school.userandsecurity.service.repository")
@ComponentScan("org.openframework.common.rest.auth.permission")
@ComponentScan(basePackageClasses = { RestResponseBodyAdvice.class, RestResponseEntityExceptionHandler.class,
		LoggingBean.class })
public class SpringRestWebConfig implements WebMvcConfigurer {

	static {
		System.out.println("SpringRestWebConfig.static{} ");
	}

	@Autowired
	private UserProfileHandlerMethodArgumentResolver userProfileHandlerMethodArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CustomRequestHandler());
		/**
		 * can also be added the code as .addPathPatterns("/**");
		 */
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userProfileHandlerMethodArgumentResolver);
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
