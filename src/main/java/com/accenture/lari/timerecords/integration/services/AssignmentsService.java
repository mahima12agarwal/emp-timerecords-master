package com.accenture.lari.timerecords.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accenture.lari.timerecords.client.vo.EmployeeAssignments;



@FeignClient(name = "assignments")
public interface AssignmentsService {
	
	@RequestMapping(value = "/employees/assignments/{empId}", method=RequestMethod.GET)
	public EmployeeAssignments getEmployeeAssignment(@PathVariable("empId") Integer id);

}
