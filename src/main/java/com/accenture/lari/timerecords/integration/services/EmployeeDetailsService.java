package com.accenture.lari.timerecords.integration.services;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accenture.lari.timerecords.integration.services.vo.EmployeeDetails;

@FeignClient(name = "employees")
public interface EmployeeDetailsService {
	
	@RequestMapping(value = "/employees/{empId}",method = RequestMethod.GET)
	public EmployeeDetails getEmployeeDetails(@PathVariable ("empId") long id);
	
	@RequestMapping(value = "/employees",method = RequestMethod.GET)
	public List<EmployeeDetails> getAllEmployees();
	
	@RequestMapping(value = "/employees/{empId}",method = RequestMethod.GET)
	public EmployeeDetails getEmployeeDetails(@RequestHeader("correlationId") String correlationId, @PathVariable ("empId") long id);
	

}
