package com.accenture.leanarchri.timerecords.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accenture.leanarchri.timerecords.client.vo.EmployeeAssignments;



@FeignClient(name = "assignments")
@SpringBootApplication
public interface AssignmentsService {
	
	@RequestMapping(value = "/employees/assignments/{empId}", method=RequestMethod.GET)
	public EmployeeAssignments getEmployeeAssignment(@PathVariable("empId") Integer id);

}