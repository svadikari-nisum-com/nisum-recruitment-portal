package com.nisum.employee.ref.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.repository.InterviewRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class InterviewServiceTest {

	@InjectMocks
	private InterviewService interviewService;

	@Mock
	private InterviewRepository interviewRepository;

	private List<InterviewDetails> interviewDetails;
	private InterviewDetails interview;

	@Before
	public void setUp() {
		MockMvcBuilders.standaloneSetup(interviewRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		interviewDetails = new ArrayList<>();

		interview = new InterviewDetails();
		interview.setCandidateName("Durga Prasad");
		interview.setCandidateEmail("dnarikalapa@nisum.com");
		interview.setCandidateSkills((List<String>) Arrays.asList("Java"));

		interviewDetails.add(interview);
	}

	@Test
	public void testPrepareInterview() {
		doNothing().when(interviewRepository).save(isA(InterviewDetails.class));
		interviewService.prepareInterview(interview);
	}

	@Test
	public void testInterviewCheck() {
		when(interviewRepository.interviewCheck(any(String.class))).thenReturn(interviewDetails);
		List<InterviewDetails> expInterviewDetails = interviewService.interviewCheck("9999");
		assertNotNull(expInterviewDetails);

	}

}
