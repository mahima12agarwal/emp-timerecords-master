/**
 * 
 */
package com.accenture.lari.timerecords.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 * @author j.venugopalan
 *
 */
@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.accenture.lari.timerecords" })
public class Config extends AbstractCouchbaseConfiguration {
	
	@Value("${couchbaseDB.url}")
	private String couchbaseDBUrl; 
	
    @Override
    protected List<String> getBootstrapHosts() {
    	return Arrays.asList(couchbaseDBUrl, couchbaseDBUrl);
       
    }

    @Override
    protected String getBucketName() {
        return "attendance";
    }

    @Override
    protected String getBucketPassword() {
        return "ACC@123*";
    }
  
    @Bean    
    public RequestInterceptor requestTokenBearerInterceptor() {

        return new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate requestTemplate) {

                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)

                SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

                requestTemplate.header("Authorization",
                        "bearer " + details.getTokenValue());

            }

        };

    }
}
