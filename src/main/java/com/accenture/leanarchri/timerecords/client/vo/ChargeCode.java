package com.accenture.leanarchri.timerecords.client.vo;

import java.util.Collection;

public class ChargeCode {

	private String chargeCode;

	private String engagement;

	private String company;

	private String status;
	
	private Collection<Employee> authorizedEmployees;


	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getEngagement() {
		return engagement;
	}

	public void setEngagement(String engagement) {
		this.engagement = engagement;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Collection<Employee> getAuthorizedEmployees() {
		return authorizedEmployees;
	}

	public void setAuthorizedEmployees(Collection<Employee> authorizedEmployees) {
		this.authorizedEmployees = authorizedEmployees;
	}
	
	
	

}
