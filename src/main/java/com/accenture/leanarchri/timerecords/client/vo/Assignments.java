package com.accenture.leanarchri.timerecords.client.vo;


import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
/**
* @author mahima.agarwal
*
*/

@Document
public class Assignments {
	
	@Field
	public String chargeCode;
	


	public Assignments() {}
	
	
	public Assignments(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getChargeCode() {
		return chargeCode;
	}


	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DayAttendance [chargeCode=" + chargeCode + "]";
	}
}
