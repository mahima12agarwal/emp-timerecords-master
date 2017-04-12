package com.accenture.lari.timerecords.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.lari.timerecords.domain.TimerecordLineItem;
import com.accenture.lari.timerecords.domain.Timerecords;
import com.accenture.lari.timerecords.integration.services.AssignmentsService;
import com.accenture.lari.timerecords.integration.services.ChargeCodeService;
import com.accenture.lari.timerecords.integration.services.EmployeeDetailsService;
import com.accenture.lari.timerecords.integration.services.vo.ChargeCode;
import com.accenture.lari.timerecords.integration.services.vo.EmployeeAssignments;
import com.accenture.lari.timerecords.integration.services.vo.EmployeeDetails;
import com.accenture.lari.timerecords.repository.TimerecordsRepository;
import com.accenture.lari.timerecords.service.TimeRecordsService;
import com.accenture.lari.timerecords.utility.RecordValidator;
import com.accenture.lari.utils.Correlation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class TimeRecordsServiceImpl implements TimeRecordsService {
	
	public static final Logger log = LoggerFactory.getLogger(TimeRecordsServiceImpl.class);
	
	@Autowired
	private ChargeCodeService chargeCodeService;
	
	@Autowired
	private AssignmentsService assignmentsService;
	
	@Autowired
	private EmployeeDetailsService employeeDetailsService;
	
	@Autowired
	RecordValidator recordValidator;
		
	private final TimerecordsRepository timerecordsRepository;
	
	@Autowired
	public TimeRecordsServiceImpl(TimerecordsRepository timerecordsRepository) {
		this.timerecordsRepository = timerecordsRepository;
	}
	
	@HystrixCommand(fallbackMethod="handleGetCalculateAttendanceEmployee")
	public Collection<Timerecords> getCalculateAttendanceEmployee(Integer empId){
		Collection<Timerecords> employeeTimerecords = new ArrayList<>();
		String correlationId = Correlation.getId();
		log.info("getCalculateAttendanceEmployee::: START");
		EmployeeDetails employeeDetails = employeeDetailsService.getEmployeeDetails(correlationId, empId);
        log.debug("Employee Details got from mock obj :"+employeeDetails.toString());
		if(employeeDetails != null){
			log.debug("Service:: EmployeeId passed: "+empId);
			 employeeTimerecords = timerecordsRepository.findByEmployeeId(empId);
			log.debug("Service:: EmployeeAttendance Attendance retreived: "+employeeTimerecords.toString());
		}else{
			log.info("getCalculateAttendanceEmployee ::: Employee is invalid"+empId);
		}
		log.info("getCalculateAttendanceEmployee ::: END");
		return  employeeTimerecords;
	}
	
	public Collection<Timerecords> handleGetCalculateAttendanceEmployee(Integer empId,Throwable e ){
		log.error("handleGetCalculateAttendanceEmployee ::: Fall in to hystrix fallback method: "+e);
		Collection<Timerecords> employeeAttendances = new ArrayList<>(); 
		Timerecords employeeAttendance = new Timerecords();
		employeeAttendance.setEmployeeId(null);
		employeeAttendance.setTimerecord(null);
		employeeAttendances.add(employeeAttendance);
		return employeeAttendances;
	}
	
	@HystrixCommand(fallbackMethod="handleGetCalculateAttendanceEmployees")
	public Collection<Timerecords> getCalculateAttendanceEmployees(){
		log.info("Attendance Calculator::: getCalculateAttendanceEmployees::: Start");
		Collection<Timerecords>  employeesTimerecords = new ArrayList<>();
		employeesTimerecords = (Collection<Timerecords>) timerecordsRepository.findAll();
		log.debug("received from bd: "+employeesTimerecords.toString());
		log.info("Attendance Calculator::: getCalculateAttendanceEmployees::: End");
		return  employeesTimerecords;
	}

	public Collection<Timerecords> handleGetCalculateAttendanceEmployees(Throwable e){
		log.debug("Fall in to hystrix fallback method: "+e);
		return  Arrays.asList(new Timerecords());
	}
	
	@HystrixCommand(fallbackMethod="handleTimerecordSubmitFailure")
	public Boolean submitEmployeeTimerecords(Timerecords employeeTimerecords){
		log.info("submitEmployeeTimerecords ::: START");
		String correlationId = Correlation.getId();
		Collection<ChargeCode> chareCodeEntityList = null;
		String[] chargeCodeList;
		Boolean validateTR = false;
		
		EmployeeDetails employeeDetails = employeeDetailsService.getEmployeeDetails(correlationId, employeeTimerecords.getEmployeeId());
        log.debug("Employee Details :"+employeeDetails.toString());
        Boolean submitStatus = false;
        if(employeeDetails != null){
        	log.debug("Checking charge code");
        	chargeCodeList = getChargeCodeList(employeeTimerecords);
        	try {
        		chareCodeEntityList = chargeCodeService.getChargeCodes(correlationId, chargeCodeList);
                log.debug("Chargecode list: "+chareCodeEntityList.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
		    Timerecords empTimerecords;
        	if(chareCodeEntityList != null){
        		validateTR = recordValidator.validateTimeRecords(employeeTimerecords);
        		if(validateTR){
        			empTimerecords = constructEmployeeTimerecord(employeeTimerecords.getEmployeeId(), employeeTimerecords.getTimerecord());
        			Timerecords employeeAttenanceSaved = timerecordsRepository.save(empTimerecords);
        			if(employeeAttenanceSaved.toString().equals(empTimerecords.toString())){
        				submitStatus = true;
        			}else{
        				submitStatus = false;
        			}
        			
        		}else{
        			log.info("Validation faile: Date format is wrong or Hours submitted are not valid");
        		}
        	}else{
        		log.info("Chargecodes submitted are not valid.");
        	}
        }else{
        	log.info("Employee is not valid.");
        }
        log.info("submitEmployeeTimerecords ::: END");
		return submitStatus;
	}
	
	public Boolean handleTimerecordSubmitFailure(Timerecords employeeTimerecords){
		log.info("Employee failed to submit the timerecord: "+employeeTimerecords.getEmployeeId());
		return false;
	}
	
	/**
	 * Delete functionality
	 * @param employeeAttendance
	 * @return
	 *//*
	public Boolean deleteEmployeeAttandance(EmployeeAttendance employeeAttendance){
		this.attendanceAggregator.deleteEmployeeAttenance(employeeAttendance);
		return null;
	}*/
	
	
	/**
	 * Get the list of all chargecodes for the the submission
	 * @param employeeTimerecords
	 * @return
	 */
	public String[] getChargeCodeList(Timerecords employeeTimerecords){
		Collection<TimerecordLineItem> timerecords = employeeTimerecords.getTimerecord();
		//int len = timerecords.size();
		log.debug("length of timerecords list: "+timerecords.toString());
		String[] chargeCodeList = new String[timerecords.size()];
		int i = 0;
		try {
			for(TimerecordLineItem dayAtd: timerecords){
				chargeCodeList[i] = dayAtd.getChargeCode();
				i++;
			}
			log.debug("Chargecode for submitted are: "+chargeCodeList.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return chargeCodeList;
	}
	
	/*@Override
	@HystrixCommand(fallbackMethod="handleGetChargeCodeDetails")
	public ChargeCode getChargeCodeDetails(String wbs) {
	
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetails(wbs);
		
		return	chargeCode;
	}*/
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getChargeCodeDetails
	 */
	
	public ChargeCode handleGetChargeCodeDetails(String wbs,Throwable t) {
		
		log.info("fallback method  handleGetChargeCodeDetails called,the error thrown is: "+getErrorStackTrace(t));
		
		ChargeCode chargeCode=new ChargeCode();
		
		return	chargeCode;
	}

	/*@Override
	@HystrixCommand(fallbackMethod="handleGetChargeCodeDetailsOfAnEmployee")
	public ChargeCode getChargeCodeDetailsOfAnEmployee(String wbs, Integer empid) {
		
		
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetailsOfAnEmployee(wbs, empid);
		
		return	chargeCode;
	}*/
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getChargeCodeDetailsOfAnEmployee
	 */
	public ChargeCode handleGetChargeCodeDetailsOfAnEmployee(String wbs, Integer empid,Throwable t) {
		
		log.info("fallback method  handleGetChargeCodeDetailsOfAnEmployee called,the error thrown is: "+getErrorStackTrace(t));
		
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetailsOfAnEmployee(wbs, empid);
		
		return	chargeCode;
	}
	
/*	@Override
	@HystrixCommand(fallbackMethod="handleGetEmployeeAssignment")
	public EmployeeAssignments getEmployeeAssignment(Integer id){
		
		EmployeeAssignments employeeAssignments=assignmentsService.getEmployeeAssignment(id);
		
		return employeeAssignments;
		
	}
	*/
	/*
	 *  hystrix circuitbreaker fallbackMethod for handleGetEmployeeAssignment
	 */
	
	public EmployeeAssignments handleGetEmployeeAssignment(Integer id,Throwable t){
		
		log.info("fallback method  handleGetEmployeeAssignment called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeAssignments employeeAssignments=new EmployeeAssignments();
		
		return employeeAssignments;
		
	}
	

	public String getErrorStackTrace(Throwable t){
		
		if(t!=null){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			return sw.toString();
		}else {
			return null;
		}
		
	}
	
	/*@Override
	@HystrixCommand(fallbackMethod="handleGetEmployeeDetails")
	public EmployeeDetails getEmployeeDetails(long id){
		
		EmployeeDetails employeeDetails=employeeDetailsService.getEmployeeDetails(id);
		
		return employeeDetails;
		
	}*/
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getEmployeeDetails
	 */
	
	public EmployeeDetails handleGetEmployeeDetails(long id,Throwable t){
		
		log.info("fallback method  handleGetEmployeeDetails called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeDetails employeeDetails=new EmployeeDetails();
		
		return employeeDetails;
		
	}
	
	
	/*@Override
	@HystrixCommand(fallbackMethod="handleGetAllEmployees")
	public List<EmployeeDetails> getAllEmployees(){
		
		 List<EmployeeDetails> employeeDetailsList =employeeDetailsService.getAllEmployees();
		
		return employeeDetailsList;
		
	}
	*/
	/*
	 *  hystrix circuitbreaker fallbackMethod for getEmployeeDetails
	 */
	
	public List<EmployeeDetails> handleGetAllEmployees(Throwable t){
		
		log.info("fallback method  handleGetAllEmployees called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeDetails employeeDetails=new EmployeeDetails();
		
		return Arrays.asList(employeeDetails);
		
	}
	
	public Timerecords constructEmployeeTimerecord(Integer employeeId, Collection<TimerecordLineItem> timerecords){
		Timerecords employeeTimerecords = new Timerecords();
		log.info("Employee ID: "+employeeId);
		employeeTimerecords.setId(employeeId);
		employeeTimerecords.setEmployeeId(employeeId);
		Collection<Timerecords> employeeTR = getEmployeeAttendance(employeeId);
		Collection<TimerecordLineItem> dayAttendance = new ArrayList<TimerecordLineItem>();
		for(Timerecords ea : employeeTR){
			dayAttendance = ea.getTimerecord();
		}
		Collection<TimerecordLineItem> dayAttConsolidated = new ArrayList<TimerecordLineItem>();
		dayAttConsolidated.addAll(dayAttendance);
		dayAttConsolidated.addAll(timerecords);
		employeeTimerecords.setTimerecord(dayAttConsolidated);
		return employeeTimerecords;
		
	}
	
	public Collection<Timerecords> getEmployeeAttendance(Integer employeeId){
		log.info("Service:: EmployeeId passed: "+employeeId);
		Collection<Timerecords> employeeAttendance = timerecordsRepository.findByEmployeeId(employeeId);
		log.info("Service:: EmployeeAttendance Attendance retreived: "+employeeAttendance.toString());
		return employeeAttendance;
	}

}
