package com.nisum.employee.ref.controller;


import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.service.InterviewDetailsService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class InterviewControllerTest {
	private MockMvc mockMvc;

    @Mock
    private InterviewDetailsService interviewDetailsService;
    @InjectMocks
    private InterviewController interviewController = new InterviewController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(interviewController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   @Test
  	public void testCreateInterviewSchedule() throws Exception {
	   InterviewSchedule interviewSchedule = new InterviewSchedule();
       interviewSchedule.setCandidateId("1");
       interviewSchedule.setCandidateName("swati");
       InterviewDetails interviewDetails = new InterviewDetails();
       interviewDetails.setCandidateName("swati");
       interviewDetails.setClientName("Nisum");
       Mockito.when(
				(interviewDetailsService).scheduleInterview(interviewSchedule))
				.thenReturn(interviewDetails);
       mockMvc.perform(
  			post("/interviewSchedule").contentType(MediaType.APPLICATION_JSON).
  			content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

      }
   @Test
 	public void testReScheduleInterviewSchedule() throws Exception {
	   InterviewSchedule interviewSchedule = new InterviewSchedule();
      interviewSchedule.setCandidateId("1");
      interviewSchedule.setCandidateName("swati");
      InterviewDetails interviewDetails = new InterviewDetails();
      interviewDetails.setCandidateName("swati");
      interviewDetails.setClientName("Nisum");
      Mockito.when(
				(interviewDetailsService).scheduleInterview1(interviewSchedule))
				.thenReturn(interviewDetails);
      mockMvc.perform(
 			post("/interviewRe-Schedule").contentType(MediaType.APPLICATION_JSON).
 			content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

     }
   @Test
 	public void testSaveFeedBack() throws Exception {
	   InterviewFeedback interviewFeedBack = new InterviewFeedback();
	   interviewFeedBack.setCandidateId("1");
	   interviewFeedBack.setCandidateName("swathi");
	   interviewFeedBack.setTypeOfInterview("TechnicalRound");
	   InterviewDetails interviewDetails = new InterviewDetails();
	      interviewDetails.setCandidateName("swati");
	      interviewDetails.setClientName("Nisum"); 
      Mockito.when(
				(interviewDetailsService).saveFeedback(interviewFeedBack))
				.thenReturn(interviewDetails);
      mockMvc.perform(
 			post("/interviewFeedback").contentType(MediaType.APPLICATION_JSON).
 			content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

     }
   @Test
	public void testCreateInterviewDetails() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     Mockito.when(
				(interviewDetailsService).createInterview(interviewDetails))
				.thenReturn(interviewDetails);
     mockMvc.perform(
			post("/createInterview").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

    }
   @Test
  	public void testGetInterviewByJobCode() throws Exception {
       InterviewDetails interviewDetails = new InterviewDetails();
       interviewDetails.setCandidateName("swati");
       interviewDetails.setClientName("Nisum");
       List<InterviewDetails> checkDetails  = new ArrayList<>();
       checkDetails.add(interviewDetails);
       Mockito.when(
  				(interviewDetailsService).getInterviewByJobCode("1"))
  				.thenReturn(checkDetails);
       mockMvc.perform(
  			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
  			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

      }
   @Test
 	public void testGetInterviewByCandidateId() throws Exception {
      InterviewDetails interviewDetails = new InterviewDetails();
      interviewDetails.setCandidateName("swati");
      interviewDetails.setClientName("Nisum");
      List<InterviewDetails> checkDetails  = new ArrayList<>();
      checkDetails.add(interviewDetails);
      Mockito.when(
 				(interviewDetailsService).getInterviewByCandidateId("1"))
 				.thenReturn(checkDetails);
      mockMvc.perform(
 			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
 			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

     }
   @Test
	public void testGetInterviewByClient() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     List<InterviewDetails> checkDetails  = new ArrayList<>();
     checkDetails.add(interviewDetails);
     Mockito.when(
				(interviewDetailsService).getInterviewByClient("NISUM"))
				.thenReturn(checkDetails);
     mockMvc.perform(
			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

    }
   @Test
  	public void testGetInterviewByProgress() throws Exception {
       InterviewDetails interviewDetails = new InterviewDetails();
       interviewDetails.setCandidateName("swati");
       interviewDetails.setClientName("Nisum");
       List<InterviewDetails> checkDetails  = new ArrayList<>();
       checkDetails.add(interviewDetails);
       Mockito.when(
  				(interviewDetailsService).getInterviewByClient("Passed"))
  				.thenReturn(checkDetails);
       mockMvc.perform(
  			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
  			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

      }
   @Test
 	public void testGetInterviewBySkill() throws Exception {
      InterviewDetails interviewDetails = new InterviewDetails();
      interviewDetails.setCandidateName("swati");
      interviewDetails.setClientName("Nisum");
      List<InterviewDetails> checkDetails  = new ArrayList<>();
      checkDetails.add(interviewDetails);
      Mockito.when(
 				(interviewDetailsService).getInterviewByClient("JAVA"))
 				.thenReturn(checkDetails);
      mockMvc.perform(
 			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
 			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

     }
   @Test
	public void testGetInterviewByDesignation() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     List<InterviewDetails> checkDetails  = new ArrayList<>();
     checkDetails.add(interviewDetails);
     Mockito.when(
				(interviewDetailsService).getInterviewByClient("SSE"))
				.thenReturn(checkDetails);
     mockMvc.perform(
			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

    }
    @Test
	public void testGetInterviewByinterviewId() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     List<InterviewDetails> checkDetails  = new ArrayList<>();
     checkDetails.add(interviewDetails);
     Mockito.when(
				(interviewDetailsService).getInterviewByClient("123"))
				.thenReturn(checkDetails);
     mockMvc.perform(
			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

    }
    @Test
	public void testGetAll() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     List<InterviewDetails> checkDetails  = new ArrayList<>();
     checkDetails.add(interviewDetails);
     Mockito.when(
				(interviewDetailsService).getAll())
				.thenReturn(checkDetails);
     mockMvc.perform(
			get("/getInterviewByParam").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

    }
    @Test
	public void testGetInterviewByInterviewerAndJobCode() throws Exception {
     InterviewDetails interviewDetails = new InterviewDetails();
     interviewDetails.setCandidateName("swati");
     interviewDetails.setClientName("Nisum");
     List<InterviewDetails> checkDetails  = new ArrayList<>();
     checkDetails.add(interviewDetails);
     Mockito.when(
				(interviewDetailsService).getInterviewByInterviewerAndJobCode("skaranam@gmail.com","1"))
				.thenReturn(checkDetails);
     /*mockMvc.perform(
			get("/getInterviewByInterviewer").contentType(MediaType.APPLICATION_JSON).
			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());*/
		mockMvc.perform
		       (get("/getInterviewByInterviewer").param("interviewerEmail", "skaranam@gmail.com")
		    		   .param("jobCode","1")).andExpect(status().isOk());

    }
    @Test
   	public void testGetInterviewByInterviewer() throws Exception {
        InterviewDetails interviewDetails = new InterviewDetails();
        interviewDetails.setCandidateName("swati");
        interviewDetails.setClientName("Nisum");
        List<InterviewDetails> checkDetails  = new ArrayList<>();
        checkDetails.add(interviewDetails);
        Mockito.when(
   				(interviewDetailsService).getInterviewByInterviewer("skaranam@gmail.com"))
   				.thenReturn(checkDetails);
   		mockMvc.perform
   		       (get("/getInterviewByInterviewer").param("interviewerEmail", "skaranam@gmail.com")).andExpect(status().isOk());

       }
    @Test
   	public void testupdateIntewrviewDetails() throws Exception {
        InterviewDetails interviewDetails = new InterviewDetails();
        interviewDetails.setCandidateName("swati");
        interviewDetails.setClientName("Nisum");
		doNothing().when(interviewDetailsService).updateInterviewDetails(interviewDetails);

        mockMvc.perform(
   			put("/interview").contentType(MediaType.APPLICATION_JSON).
   			content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());
       }
   

}
