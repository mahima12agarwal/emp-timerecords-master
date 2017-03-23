package com.accenture.leanarchri.timerecords.service;



import java.util.Collection;

import com.accenture.leanarchri.timerecords.domain.EmployeeTimerecords;



public interface TimeRecordsService {
	
	/*public ChargeCode getChargeCodeDetails(String wbs);
	
	public ChargeCode getChargeCodeDetailsOfAnEmployee(String wbs,Integer empid);
	
	public EmployeeAssignments getEmployeeAssignment(Integer id);
	
	public EmployeeDetails getEmployeeDetails(long id) ;
	
	public List<EmployeeDetails> getAllEmployees();*/
	
	public Collection<EmployeeTimerecords> getCalculateAttendanceEmployee(Integer empId);
	
	public Collection<EmployeeTimerecords> getCalculateAttendanceEmployees();
	
	public Boolean submitEmployeeTimerecords(EmployeeTimerecords employeeTimerecords);

}
