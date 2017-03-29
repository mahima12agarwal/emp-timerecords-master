package com.accenture.leanarchri.timerecords;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.accenture.lari.timerecords.domain.TimerecordLineItem;
import com.accenture.lari.timerecords.domain.Timerecords;
import com.accenture.lari.timerecords.integration.services.AssignmentsService;
import com.accenture.lari.timerecords.integration.services.ChargeCodeService;
import com.accenture.lari.timerecords.integration.services.EmployeeDetailsService;
import com.accenture.lari.timerecords.integration.services.vo.EmployeeDetails;
import com.accenture.lari.timerecords.repository.TimerecordsRepository;
import com.accenture.lari.timerecords.service.impl.TimeRecordsServiceImpl;
import com.accenture.lari.timerecords.utility.RecordValidator;


/**
 * @author j.chandra.mali
 *
 */

//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(SpringRunner.class) 
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeRecordsServiceImpl.class)
//@PrepareForTest(TimeRecordsApplication.class) 
public class TimerecordsServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(TimerecordsServiceTest.class);
	
	@Autowired
	private TimeRecordsServiceImpl timerecordService;
	
	@MockBean
	private RecordValidator recordvalidator;
	
	@MockBean
	ChargeCodeService chargecodeService;
	
	@MockBean
	AssignmentsService assignmentService;
	
	@MockBean
	private EmployeeDetailsService employeeDetailsService;
	
	@MockBean
	private TimerecordsRepository timerecordsRepository;
	
	@Test
	public void TestTimerecordsForNullTimerecords(){
		
		List<Timerecords> employeeTimerecords=Arrays.asList(new Timerecords(1000, 1000, null));
		//PowerMockito.mock(Correlation.class);
		//given(Correlation.getId()).willReturn("correlationId-test");
		EmployeeDetails employeeDetails = new EmployeeDetails("Amith", "Bhandari",55,"Bangalore", "amith@gmail.com", 1000);
		given(this.employeeDetailsService.getEmployeeDetails(null,1000)).willReturn(employeeDetails);
		given(this.timerecordsRepository.findByEmployeeId(1000)).willReturn(employeeTimerecords);
		Collection<Timerecords> result= timerecordService.getCalculateAttendanceEmployee(1000);
		assertThat(((List<Timerecords>)result).get(0).employeeId).isEqualTo(1000);
	}
	
	@Test
	public void TestTimerecordsForValidTimerecords(){
		
		TimerecordLineItem timerecords = new TimerecordLineItem("01-02-2017", 9, "AAAAA","Bangalore-6");
		Collection<TimerecordLineItem> timerecordsList = new ArrayList<>();
		timerecordsList.add(timerecords);
		
		List<Timerecords> employeeTimerecords=Arrays.asList(new Timerecords(1000, 1000, timerecordsList));
		//PowerMockito.mock(Correlation.class);
		//given(Correlation.getId()).willReturn("correlationId-test");
		EmployeeDetails employeeDetails = new EmployeeDetails("Amith", "Bhandari",55,"Bangalore", "amith@gmail.com", 1000);
		given(this.employeeDetailsService.getEmployeeDetails(null,1000)).willReturn(employeeDetails);
		given(this.timerecordsRepository.findByEmployeeId(1000)).willReturn(employeeTimerecords);
		Collection<Timerecords> result= timerecordService.getCalculateAttendanceEmployee(1000);
		assertThat(((List<Timerecords>)result).get(0).employeeId).isEqualTo(1000);
	}
	
	/*@Test
	public void getCalculateAttendanceEmployeeForSingleDayAttendanceTest(){
			
		Collection<EmployeeTimerecords> employeeAttendance=Arrays.asList(new EmployeeTimerecords(1, 1000, Arrays.asList(new Timerecord("24/07/2016",9, "A54678", "Bangalore"))));
		ResponseEntity<EmployeeDetails> responseEntity = new ResponseEntity<EmployeeDetails>(new EmployeeDetails("Amith", "Bhandari",55,"Bangalore", "amith@gmail.com", 1000),HttpStatus.OK);
		given(this.restTemplate.exchange(any(), any(HttpMethod.class),any(), eq(EmployeeDetails.class))).willReturn(responseEntity);
		given(this.timerecordService.getEmployeeAttendance(1000)).willReturn(employeeAttendance);
		Collection<EmployeeTimerecords> result= timerecordService.getCalculateAttendanceEmployee(1000);
		List<Timerecord> dayAttendenaceList=(List)((List<EmployeeTimerecords>)result).get(0).getTimerecord();
		assertThat(dayAttendenaceList.get(0).hours).isEqualTo(9);
		
		
	}
	
	@Test
	public void getCalculateAttendanceEmployeeForNoEmployeeExistsTest(){
			
		Collection<EmployeeTimerecords> employeeAttendance=Arrays.asList(new EmployeeTimerecords(1, 1000, Arrays.asList(new Timerecord("24/07/2016",9, "A54678", "Bangalore"))));
		ResponseEntity<EmployeeDetails> responseEntity = new ResponseEntity<EmployeeDetails>(new EmployeeDetails("Amith", "Bhandari",55,"Bangalore", "amith@gmail.com", 1000),HttpStatus.OK);
		given(this.restTemplate.exchange(any(), any(HttpMethod.class),any(), eq(EmployeeDetails.class))).willReturn(responseEntity);
		given(this.timerecordService.getEmployeeAttendance(1000)).willReturn(employeeAttendance);
		Collection<EmployeeTimerecords> result= timerecordService.getCalculateAttendanceEmployee(1000);
		List<Timerecord> dayAttendenaceList=(List)((List<EmployeeTimerecords>)result).get(0).getTimerecord();
		assertThat(dayAttendenaceList.get(0).hours).isEqualTo(9);
		
		
	}
	
	
	@Test
	public void getCalculateAttendanceEmployeesTest(){
			
		Collection<EmployeeTimerecords> employeeAttendance=Arrays.asList(new EmployeeTimerecords(1, 1000, null));
		given(this.timerecordService.getCalculateAttendanceEmployees()).willReturn(employeeAttendance);
		Collection<EmployeeTimerecords> result= timerecordService.getCalculateAttendanceEmployees();
		assertThat(((List<EmployeeTimerecords>)result).get(0).employeeId).isEqualTo(1000);
		
		
	}
	*/
	/*@Test
	public void submitEmployeeAttendanceWebTest(){
		
		DayAttendance dayAttendance=new DayAttendance();
		dayAttendance.setChargeCode("A12456");
		dayAttendance.setDate("24/06/2016");
		dayAttendance.setHours(9);
		dayAttendance.setLocation("Bangalore");
		Collection<DayAttendance> dayAttendanceList=Arrays.asList(dayAttendance);
		
		EmployeeAttendance employeeAttendance=new EmployeeAttendance(1, 1000, dayAttendanceList);
		AttendanceCalculator.submitEmployeeAttendanceWeb(employeeAttendance);
		assertEquals(employeeRepository.findOne("A12456"),employeeAttendance);
		
		
		
	}*/
	
	

}
