/**
 * 
 */
package com.accenture.leanarchri.timerecords.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.leanarchri.timerecords.domain.EmployeeTimerecords;
import com.accenture.leanarchri.timerecords.service.TimeRecordsService;

import io.swagger.annotations.ApiOperation;

/**
 * @author j.venugopalan
 *
 */
@RestController
public class TimeRecordsController {
	

	private static final Logger log = LoggerFactory.getLogger(TimeRecordsController.class);
	
	
	@Autowired
	TimeRecordsService timeRecordsService;
	
	/**
	 * 
	 * @param: Employee id
	 * @return: Collection<EmployeeAttendance>
	 */
	@RequestMapping(value = "/timerecords/{empId}", method=RequestMethod.GET)
    @ApiOperation(value = "GetEmployeeTimerecords", nickname = "GetEmployeeTimerecords") 
	public Collection<EmployeeTimerecords> getEmployeeAttendance(@PathVariable("empId") Integer id){
		log.debug("AttendanceController ::: getEmployeeAttendance ::: START");
		Collection<EmployeeTimerecords> employeeAttendance = new ArrayList<>();
			employeeAttendance = timeRecordsService.getCalculateAttendanceEmployee(id);
			log.debug("day attendance: "+employeeAttendance.toString());
		log.debug("AttendanceController ::: getEmployeeAttendance ::: END");
		return employeeAttendance;
	}
	/**
	 * @param: no parameters
	 * @return: Collection<EmployeeAttendance>
	 */
	@RequestMapping(value = "/timerecords", method = RequestMethod.GET)
	@ApiOperation(value = "GetAllEmployeeTimerecords", nickname = "GetAllEmployeeTimerecords") 
	public Collection<EmployeeTimerecords> getEmployeeAttendance(){
		log.debug("AttendanceController ::: getEmployeeAttendance::: All employees ::: START");
		Collection<EmployeeTimerecords> employeeAttendance = new ArrayList<EmployeeTimerecords>();
		employeeAttendance = timeRecordsService.getCalculateAttendanceEmployees();
		log.debug("Employee Attendance: "+employeeAttendance.toString());
		log.debug("AttendanceController ::: getEmployeeAttendance::: All employees ::: END");
		return	employeeAttendance;
	}
	
	@RequestMapping(value = "/timerecords/submit", method=RequestMethod.POST)
	public Boolean submitEmployeeAttanceService(@RequestBody EmployeeTimerecords employeeTimerecords){
		
		Boolean submitStatus = timeRecordsService.submitEmployeeTimerecords(employeeTimerecords);
		return submitStatus;
	}
	
	/*@RequestMapping(value = "/timerecords/attendance/delete", method=RequestMethod.DELETE)
	public Boolean deleteEmployeeAttance(@RequestBody Collection<EmployeeAttendance> employeeAttendance){
		EmployeeAttendance employeeAttendance2 = new EmployeeAttendance();
		for(EmployeeAttendance emplAtendance: employeeAttendance){
			employeeAttendance2 = emplAtendance;
		}
		log.debug("request received: "+employeeAttendance2.toString());
		this.attendanceCalculator.deleteEmployeeAttandance(employeeAttendance2);
		return null;
	}*/

}
