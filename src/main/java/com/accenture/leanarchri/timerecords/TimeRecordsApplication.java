package com.accenture.leanarchri.timerecords;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.accenture.leanarchri.utils.CorrelationHeaderFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Service
@EnableAutoConfiguration
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableResourceServer
@SpringBootApplication
public class TimeRecordsApplication {

	private static final Logger log = LoggerFactory.getLogger(TimeRecordsApplication.class);

	@Configuration
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/hystrix.stream").permitAll().and()
					.authorizeRequests().anyRequest().authenticated();

		}

	}

	@Bean
	public FilterRegistrationBean correlationHeaderFilter() {
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(new CorrelationHeaderFilter());
		filterRegBean.setUrlPatterns(Arrays.asList("/*"));

		return filterRegBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(TimeRecordsApplication.class, args);
	}

	@Bean
	public Docket newAPITimeRecords() {
		return new Docket(DocumentationType.SWAGGER_2)

				.groupName("timerecords").apiInfo(apiInfo()).select().paths(regex("/timerecords.*")).build();
	}

	@Component
	@Primary
	public class CustomObjectMapper extends ObjectMapper {
		public CustomObjectMapper() {
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			enable(SerializationFeature.INDENT_OUTPUT);
		}
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Employee time records")
				.description("This API is for fetching the employee timerecords for a spcific or last fortnight.")
				.version("1.0")
				.contact(new Contact("ATA Lean Architecture Team", "", "ATA.Lean.Arch.Group@accenture.com"))
				// .contact("ATA Lean Architecture Team")
				.license("Accenture License Version")
				// .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
				.build();
	}
}
