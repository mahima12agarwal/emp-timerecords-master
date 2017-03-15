/**
 * 
 */
package com.accenture.leanarchri.timerecords.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.accenture.leanarchri.timerecords.domain.EmployeeTimerecords;
import com.accenture.leanarchri.timerecords.domain.Timerecord;




/**
 * @author j.venugopalan
 *
 */
@SpringBootApplication
public class RecordValidator {
	
	public static final Logger log = LoggerFactory.getLogger(RecordValidator.class);
	
	public Boolean validateDateString(Collection<Timerecord> timerecords){
		String pattern = ".*/.*/.*";
		boolean matches = false;
		for(Timerecord dAtt : timerecords){
			matches = Pattern.matches(pattern, dAtt.getDate());
			if(!matches)
				return matches;
		}
		return matches;
	}
	
	public Boolean validateHours(Collection<Timerecord> timerecords){
		Boolean hoursValidationStatus = false;
		HashMap<String, Integer> dayAttenanceConsolidateHours = new HashMap<String, Integer>();
		for(Timerecord dAtt: timerecords){
			//log.info("dAtt.getDate(): "+dAtt.getDate()+" dAtt.getHours() "+dAtt.getHours());
			if(!dayAttenanceConsolidateHours.containsKey(dAtt.getDate())){
				dayAttenanceConsolidateHours.put((String)dAtt.getDate(), (Integer)dAtt.getHours());
			}else{
				dayAttenanceConsolidateHours.computeIfPresent(dAtt.getDate(), (k, v) -> (Integer)v + (Integer)dAtt.getHours());
			}
		}
		 Set set = dayAttenanceConsolidateHours.entrySet();
	     Iterator i = set.iterator();
	     while(i.hasNext()) {
	         Map.Entry da = (Map.Entry)i.next();
	         log.debug("Key *** "+da.getKey() + ": ");
	         log.debug("Value *** "+da.getValue());
	         if((Integer)da.getValue() < 9 || (Integer)da.getValue() > 24){
	        	 hoursValidationStatus = false;
	        	 break;
	         }	
	         hoursValidationStatus = true;
	      }
	     log.debug("validation status: "+hoursValidationStatus);
		return hoursValidationStatus;
	}

	public Boolean validateTimeRecords(EmployeeTimerecords employeeTimerecords){
		Integer employeeId = employeeTimerecords.getEmployeeId();
		Boolean validateStatus = false;
		Boolean hoursValidationStatus  = false;
		Collection<Timerecord> timerecords = employeeTimerecords.getTimerecord();
		Boolean dateFormatValicaiton = true;
		dateFormatValicaiton = validateDateString(timerecords);
		hoursValidationStatus = validateHours(timerecords);
		log.info("hours validation status: "+hoursValidationStatus);
		if(dateFormatValicaiton && hoursValidationStatus){
			log.info("validation passed");
			validateStatus = true;
		}else{
			if(dateFormatValicaiton){
				log.info("dateFormatValicaiton passed");
			}else if(hoursValidationStatus){
				log.info("hoursValidationStatus passed");
			}else{
				log.info("Both validation are failed");
			}
			validateStatus = false;
		}
		return validateStatus;
	}

}
