package com.accenture.leanarchri.timerecords;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.accenture.leanarchri.timerecords.client.EmployeeDetailsService;
import com.accenture.leanarchri.timerecords.client.vo.EmployeeDetails;
import com.accenture.leanarchri.timerecords.domain.EmployeeTimerecords;
import com.accenture.leanarchri.timerecords.domain.Timerecord;
import com.accenture.leanarchri.timerecords.repository.TimerecordsRepository;
import com.accenture.leanarchri.timerecords.service.impl.TimeRecordsServiceImpl;


/**
 * @author j.chandra.mali
 *
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeRecordsApplication.class)
public class TimerecordsServiceTest {
	
	@Autowired
	private TimeRecordsServiceImpl timerecordService;
	
	@MockBean
	private EmployeeDetailsService employeeDetailsService;
	
	private TimerecordsRepository timerecordsRepository;
	
	/*@MockBean
	private AttendanceAggregator attendanceAggregator;
	
	@MockBean
	private RestClient restClient;*/
	
	/*@MockBean
	private RestTemplate restTemplate;*/
	
/*	@MockBean
	private EmployeeRepository employeeRepository;
*/
	
	@Test
	public void getCalculateAttendanceEmployeeForNoAttendanceTest(){
		
		//Collection<EmployeeTimerecords> employeeTimerecords = new ArrayList<>();
		//employeeTimerecords.add(new EmployeeTimerecords(1, 1000, null));
		List<EmployeeTimerecords> employeeAttendance=Arrays.asList(new EmployeeTimerecords(1, 1000, null));
		EmployeeDetails employeeDetails = new EmployeeDetails("Amith", "Bhandari",55,"Bangalore", "amith@gmail.com", 1000);
		given(this.employeeDetailsService.getEmployeeDetails("Ti8776652",1000)).willReturn(employeeDetails);
		given(this.timerecordsRepository.findByEmployeeId(1000)).willReturn(employeeAttendance);
		Collection<EmployeeTimerecords> result= timerecordService.getCalculateAttendanceEmployee(1000);
		assertThat(((List<EmployeeTimerecords>)result).get(0).employeeId).isEqualTo(1000);
		
		
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
