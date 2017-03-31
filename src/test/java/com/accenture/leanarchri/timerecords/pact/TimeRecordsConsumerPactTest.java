package com.accenture.leanarchri.timerecords.pact;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.accenture.lari.timerecords.integration.services.vo.ChargeCode;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

/**
 * @author j.chandra.mali
 *
 */
@Profile("pact_test")
public class TimeRecordsConsumerPactTest {
	
	
	 @Rule
	    public PactProviderRule rule = new PactProviderRule("chargecodes", this);

	    @Pact(provider="chargecodes", consumer="timerecords")
	    public PactFragment createFragment(PactDslWithProvider builder) {
	       	       
	    	Map<String, String> responseHeaders = new HashMap<>();
	    	responseHeaders.put("content-type", "application/json");
	    	Map<String, String> requestHeaders = new HashMap<>();
	    	String accessToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNTA2NDI2MzY3LCJqdGkiOiIxM2NmZDU0MS1kM2FhLTQ5NTctYTZmZC02ODc3OGYyZDdlY2UiLCJjbGllbnRfaWQiOiJpbnRlcm5hbCJ9.SiH5I5hfo5OMH51HJdIhVr14coo56aa91tuAFXmIWKP-c8zAFqK0xo0aAPTGHPjZyy0MhiMFLc-yfJVf_wIys04xjF_cUMnRMEIvBfXRRx3JjwY1xEg6C_JHeFh-8WOVPHr335e10oH4urQen5pKvOeukGrfKdAfJhW0iBxXMiQ0kjj0Ey6kktSY460G2sKlSZIur1uwr2FsVGRXSwrxNu2q8iqQCc4wG0_kOxMusG8OQKYSt1zMtkZkzP3_ReMkf1uBH_fWzKD-uQ1GXQA3V9GCb36kMz7KU5WK6oCZ8TAzcVjQxQIAN1-MueBN1RzZMxx3SIgbxtxIf_V4fSOr-Q";
	    	requestHeaders.put("Authorization","Bearer "+accessToken);

	        return builder
	        		.given("given charge code A56789 exists")
	        		.uponReceiving("A request for charge code details")
	                .path("/chargecodes/A56789")
	                .method("GET")
	                .headers(requestHeaders)
	                .willRespondWith()
	                .status(200)
	                .headers(responseHeaders)
	                .body("[ { \"chargeCode\" : \"A56789\", \"engagement\" : \"COE\", \"company\" : \"Accenture\", \"status\" : \"Active\", "
	                		+ "\"authorizedEmployees\" :  [ {\"employeeId\" : 11111,"+"\"name\" : \"Prithvi\",\"address\" : \"Bangalore\""+
	                		"} ] } ]").toFragment();
	    }

	    @Test
	    @PactVerification("chargecodes")
	    public void runTest() {
	    	
	    	final RestTemplate call = new RestTemplate();
	    	Collection<ChargeCode> chargecodeList=new ArrayList<ChargeCode>();
	    	chargecodeList.add(new ChargeCode("A56789","COE","Accenture","Active",null));
	    	MultiValueMap<String, String> requestHeaders = new LinkedMultiValueMap<String, String>();
	    	String accessToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNTA2NDI2MzY3LCJqdGkiOiIxM2NmZDU0MS1kM2FhLTQ5NTctYTZmZC02ODc3OGYyZDdlY2UiLCJjbGllbnRfaWQiOiJpbnRlcm5hbCJ9.SiH5I5hfo5OMH51HJdIhVr14coo56aa91tuAFXmIWKP-c8zAFqK0xo0aAPTGHPjZyy0MhiMFLc-yfJVf_wIys04xjF_cUMnRMEIvBfXRRx3JjwY1xEg6C_JHeFh-8WOVPHr335e10oH4urQen5pKvOeukGrfKdAfJhW0iBxXMiQ0kjj0Ey6kktSY460G2sKlSZIur1uwr2FsVGRXSwrxNu2q8iqQCc4wG0_kOxMusG8OQKYSt1zMtkZkzP3_ReMkf1uBH_fWzKD-uQ1GXQA3V9GCb36kMz7KU5WK6oCZ8TAzcVjQxQIAN1-MueBN1RzZMxx3SIgbxtxIf_V4fSOr-Q";
	        requestHeaders.add("Authorization","Bearer "+accessToken);
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization","Bearer "+accessToken);
	        HttpEntity entity = new HttpEntity(headers);
	        
	        ResponseEntity<List> forEntity = call.exchange(rule.getConfig().url() + "/chargecodes/A56789",HttpMethod.GET, entity, List.class);


	    }
	    
	    

}
