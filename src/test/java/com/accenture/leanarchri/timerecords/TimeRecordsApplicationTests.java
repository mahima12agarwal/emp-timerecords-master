package com.accenture.leanarchri.timerecords;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import com.accenture.lari.chargecode.domain.ChargeCodeEntity;
//import com.accenture.lari.chargecode.domain.ChargeCodeEntity;
//import com.accenture.lari.employees.domain.EmployeeDetails;
//import com.accenture.lari.employees.domain.EmployeeDetails;
import com.accenture.lari.timerecords.domain.TimerecordLineItem;
import com.accenture.lari.timerecords.domain.Timerecords;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=TimeRecordsApplicationTests.class)
public class TimeRecordsApplicationTests {

	public static final Logger log = LoggerFactory.getLogger(TimeRecordsApplicationTests.class);

		
		private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));
		
		private MockMvc mockMvc;
	    
	    //  private HttpMessageConverter mappingJackson2HttpMessageConverter;
	    
		@Autowired
	    private WebApplicationContext webApplicationContext;
	    @Before
	    public void setup() throws Exception {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	    }
	    
	  

	    
		
		/**
		 * 
		 * @return TimeRecords list generator method
		 */
		public List<TimerecordLineItem> createTimeRecordslist(){
			List<TimerecordLineItem> alltimerecordslist = new ArrayList<>();
			TimerecordLineItem timerecord1 = new TimerecordLineItem("10/12/2017", 21, "AAAA", "Bangalore");
			TimerecordLineItem timerecord2= new TimerecordLineItem("12/10/2017", 27, " BBBB", "Hyderabad");
			TimerecordLineItem timerecord3 = new TimerecordLineItem("10/02/2017", 29, "AAAA", "pune");
			TimerecordLineItem timerecord4= new TimerecordLineItem("10/03/2017", 21, "AAAA", "delhi");
			TimerecordLineItem timerecord5 = new TimerecordLineItem("10/09/2017", 21, "AAAA", "vellore");
			TimerecordLineItem timerecord6 = new TimerecordLineItem("10/4/2017", 21, "AAAA", "chennai");
			TimerecordLineItem timerecord7 = new TimerecordLineItem("10/8/2017", 21, "AAAA", "chittoor");
			alltimerecordslist.add(timerecord1);
			alltimerecordslist.add(timerecord2);
			alltimerecordslist.add(timerecord3);
			alltimerecordslist.add(timerecord4);
			alltimerecordslist.add(timerecord5);
			alltimerecordslist.add(timerecord6);
			alltimerecordslist.add(timerecord7);
			return alltimerecordslist;
			
		}  
	
	    @Test
		public void getEmployeeAttendance() throws Exception {
			log.info("Result::: "+mockMvc.perform(get("/timerecords")));
			Collection<TimerecordLineItem> timerecordsreflist =createTimeRecordslist();
			MvcResult timerecordsEntityList = mockMvc.perform(get("/timerecords")).andDo(print()).andReturn();
			Collection<TimerecordLineItem> timerec=new ArrayList<TimerecordLineItem>();
			timerec.addAll(timerecordsreflist);
			assertThat(timerec.equals(timerecordsEntityList));
		}

		
	    @Test
		public void getEmployeeAttendanceofemployee() throws Exception {
			log.info("Result::: "+mockMvc.perform(get("/timerecords/1234")));
			Collection<TimerecordLineItem> timerecordsreflist =createTimeRecordslist();
			MvcResult timerecordsEntityList = mockMvc.perform(get("/timerecords/1234")).andDo(print()).andReturn();
			Collection<TimerecordLineItem> timerec=new ArrayList<TimerecordLineItem>();
			timerec.addAll(timerecordsreflist);
			assertThat(timerec.equals(timerecordsEntityList));
		}
	    @Test
		public void SubmitEmployeeTimerecords() throws Exception {
			log.info("Result::: "+mockMvc.perform(post("/timerecords")));
			Collection<TimerecordLineItem> timerecordsreflist =createTimeRecordslist();
			MvcResult timerecordsEntityList = mockMvc.perform(post("/timerecords")).andDo(print()).andReturn();
			Collection<TimerecordLineItem> timerec=new ArrayList<TimerecordLineItem>();
			timerec.addAll(timerecordsreflist);
			assertThat(timerec.equals(timerecordsEntityList));
		}

	}
