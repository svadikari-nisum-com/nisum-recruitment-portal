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
		Mockito.when((interviewDetailsService).scheduleInterview(interviewSchedule)).thenReturn(interviewDetails);
		mockMvc.perform(post("/interview/schedule").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

	}

	@Test
	public void testUpdateSchedule() throws Exception {
		InterviewSchedule interviewSchedule = new InterviewSchedule();
		interviewSchedule.setCandidateId("1");
		interviewSchedule.setCandidateName("swati");
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateName("swati");
		interviewDetails.setClientName("Nisum");
		Mockito.when((interviewDetailsService).updateInterview(interviewSchedule)).thenReturn(interviewDetails);
		mockMvc.perform(put("/interview/schedule").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

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
		Mockito.when((interviewDetailsService).saveFeedback(interviewFeedBack)).thenReturn(interviewDetails);
		mockMvc.perform(post("/interview/feedback").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewSchedule()))).andExpect(status().isOk());

	}

	@Test
	public void testCreateInterviewDetails() throws Exception {
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateName("swati");
		interviewDetails.setClientName("Nisum");
		Mockito.when((interviewDetailsService).createInterview(interviewDetails)).thenReturn(interviewDetails);
		mockMvc.perform(post("/interview").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());

	}

	@Test
	public void testGetInterviewDetails() throws Exception {
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateName("swati");
		interviewDetails.setClientName("Nisum");
		List<InterviewDetails> checkDetails = new ArrayList<>();
		checkDetails.add(interviewDetails);

		// By interviewId
		Mockito.when((interviewDetailsService).getInterview("79")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("interviewerId", "79")).andExpect(status().isOk());

		// By Interviewer and JobCode
		Mockito.when((interviewDetailsService).getInterviewByInterviewerAndJobCode("skaranam@gmail.com", "1"))
				.thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("interviewerEmail", "skaranam@gmail.com").param("jobCode", "1"))
				.andExpect(status().isOk());

		// By Interviewer
		Mockito.when((interviewDetailsService).getInterviewByInterviewer("skaranam@gmail.com"))
				.thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("interviewerEmail", "skaranam@gmail.com")).andExpect(status().isOk());

		// By jobCode
		Mockito.when((interviewDetailsService).getInterviewByJobCode("NisumDeveloper")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("jobCode", "NisumDeveloper")).andExpect(status().isOk());

		// By candiateId
		Mockito.when((interviewDetailsService).getInterviewByCandidateId("79")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("candiateId", "79")).andExpect(status().isOk());

		// By Client
		Mockito.when((interviewDetailsService).getInterviewByClient("NISUM")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("client", "NISUM")).andExpect(status().isOk());

		// By Progress
		Mockito.when((interviewDetailsService).getInterviewByProgress("Passed")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("progress", "Passed")).andExpect(status().isOk());

		// By Skills
		Mockito.when((interviewDetailsService).getInterviewBySkill("JAVA")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("skill", "JAVA")).andExpect(status().isOk());

		// By Designation
		Mockito.when((interviewDetailsService).getInterviewByDesignation("SSE")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("designation", "SSE")).andExpect(status().isOk());

		// By InterviewrID
		Mockito.when((interviewDetailsService).getInterviewByDesignation("799")).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").param("interviewId", "799")).andExpect(status().isOk());

		// By All
		Mockito.when((interviewDetailsService).getAll()).thenReturn(checkDetails);
		mockMvc.perform(get("/interview").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());
	}

	@Test
	public void testupdateIntewrviewDetails() throws Exception {
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateName("swati");
		interviewDetails.setClientName("Nisum");
		doNothing().when(interviewDetailsService).updateInterviewDetails(interviewDetails);

		mockMvc.perform(put("/interview").contentType(MediaType.APPLICATION_JSON)
				.content(MockTestUtil.convertToJsonFormat(new InterviewDetails()))).andExpect(status().isOk());
	}

}
