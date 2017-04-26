package com.accenture.leanarchri.timerecords.pact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.accenture.lari.timerecords.integration.services.vo.EmployeeDetails;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

/**
 * @author j.venugopalan
 *
 */
@Profile("pact_test")
public class TimeRecordsEmployeeDetailsPactTest {
	
	
	 @Rule
	    public PactProviderRule rule = new PactProviderRule("employeedetails", this);

	    @Pact(provider="employeedetails", consumer="timerecords")
	    public PactFragment createFragment(PactDslWithProvider builder) {
	       	       
	    	Map<String, String> responseHeaders = new HashMap<>();
	    	responseHeaders.put("content-type", "application/json");
	    	Map<String, String> requestHeaders = new HashMap<>();
	    	String accessToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNTA2NDI2MzY3LCJqdGkiOiIxM2NmZDU0MS1kM2FhLTQ5NTctYTZmZC02ODc3OGYyZDdlY2UiLCJjbGllbnRfaWQiOiJpbnRlcm5hbCJ9.SiH5I5hfo5OMH51HJdIhVr14coo56aa91tuAFXmIWKP-c8zAFqK0xo0aAPTGHPjZyy0MhiMFLc-yfJVf_wIys04xjF_cUMnRMEIvBfXRRx3JjwY1xEg6C_JHeFh-8WOVPHr335e10oH4urQen5pKvOeukGrfKdAfJhW0iBxXMiQ0kjj0Ey6kktSY460G2sKlSZIur1uwr2FsVGRXSwrxNu2q8iqQCc4wG0_kOxMusG8OQKYSt1zMtkZkzP3_ReMkf1uBH_fWzKD-uQ1GXQA3V9GCb36kMz7KU5WK6oCZ8TAzcVjQxQIAN1-MueBN1RzZMxx3SIgbxtxIf_V4fSOr-Q";
	    	requestHeaders.put("Authorization","Bearer "+accessToken);

	        return builder
	        		.given("given employee exists for employee id 1233 exists")
	        		.uponReceiving("A request employee details")
	                .path("/employees/1233")
	                .method("GET")
	                .headers(requestHeaders)
	                .willRespondWith()
	                .status(200)
	                .headers(responseHeaders)
	                .body("{ \"id\" : 1233, \"firstName\" : \"Zann\", \"lastName\" : \"Nke\", \"age\" : 53, \"address\" : \"Cochin\",\"email\":\"zann.nke@gmail.com\"}").toFragment();
	    }

	    @Test
	    @PactVerification("employeedetails")
	    public void runTest() {
	    	
	    	final RestTemplate call = new RestTemplate();
	    	
	    	MultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<String, String>();
	    	
	    	EmployeeDetails employeeDetails = new EmployeeDetails();  
	    	employeeDetails = generateEmployeeEnity();
	    	
	    	String accessToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNTA2NDI2MzY3LCJqdGkiOiIxM2NmZDU0MS1kM2FhLTQ5NTctYTZmZC02ODc3OGYyZDdlY2UiLCJjbGllbnRfaWQiOiJpbnRlcm5hbCJ9.SiH5I5hfo5OMH51HJdIhVr14coo56aa91tuAFXmIWKP-c8zAFqK0xo0aAPTGHPjZyy0MhiMFLc-yfJVf_wIys04xjF_cUMnRMEIvBfXRRx3JjwY1xEg6C_JHeFh-8WOVPHr335e10oH4urQen5pKvOeukGrfKdAfJhW0iBxXMiQ0kjj0Ey6kktSY460G2sKlSZIur1uwr2FsVGRXSwrxNu2q8iqQCc4wG0_kOxMusG8OQKYSt1zMtkZkzP3_ReMkf1uBH_fWzKD-uQ1GXQA3V9GCb36kMz7KU5WK6oCZ8TAzcVjQxQIAN1-MueBN1RzZMxx3SIgbxtxIf_V4fSOr-Q";
	        requestHeaders.add("Authorization","Bearer "+accessToken);
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization","Bearer "+accessToken);
	        HttpEntity entity = new HttpEntity(headers);
	        
	        ResponseEntity forEntity = call.exchange(rule.getConfig().url() + "/employees/1233",HttpMethod.GET, entity, Map.class);


	    }
	    /**
	     * 
	     * @return
	     */
	   public EmployeeDetails generateEmployeeEnity(){
		   EmployeeDetails employeeDetails = new EmployeeDetails();
		   employeeDetails.setId(1233);
		   employeeDetails.setFirstName("Zann");
		   employeeDetails.setLastName("Nke");
		   employeeDetails.setAge(53);
		   employeeDetails.setAddress("Cochin");
		   employeeDetails.setEmail("zann.nke@gmail.com");
		   
		   return employeeDetails;
	   }

}
