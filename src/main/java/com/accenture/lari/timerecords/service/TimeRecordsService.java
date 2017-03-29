package com.accenture.lari.timerecords.service;



import java.util.Collection;

import com.accenture.lari.timerecords.domain.Timerecords;



public interface TimeRecordsService {
	
	/*public ChargeCode getChargeCodeDetails(String wbs);
	
	public ChargeCode getChargeCodeDetailsOfAnEmployee(String wbs,Integer empid);
	
	public EmployeeAssignments getEmployeeAssignment(Integer id);
	
	public EmployeeDetails getEmployeeDetails(long id) ;
	
	public List<EmployeeDetails> getAllEmployees();*/
	
	public Collection<Timerecords> getCalculateAttendanceEmployee(Integer empId);
	
	public Collection<Timerecords> getCalculateAttendanceEmployees();
	
	public Boolean submitEmployeeTimerecords(Timerecords employeeTimerecords);

}
