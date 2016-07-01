package com.nisum.employee.ref.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class ReportsServiceTest {

	@InjectMocks
	ReportsService reportsService;

	@Mock
	ProfileRepository profileRepository;

	@Mock
	InterviewDetailsService interviewDetailsService;
	
	@Mock
	ReportsVO reportsVO;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(profileRepository, interviewDetailsService)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}

	@Test
	public void testGetDataByJobCode() {
		List<Profile> profiles = new ArrayList<>();
		
		Profile profile = new Profile();
		profile.setEmailId("dprasad@nisum.com");
		
		profiles.add(profile);
		
		when(profileRepository.retrieveProfileByJobCode(any(String.class))).thenReturn(profiles);
		
		List<InterviewDetails> interviewDetailsList = new ArrayList<>();
		
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("dprasad@nisum.com");
		
		interviewDetailsList.add(interviewDetails);
		
		when(interviewDetailsService.getInterviewByJobCode(any(String.class))).thenReturn(interviewDetailsList);
		
		reportsVO = reportsService.getDataByJobCode("Java");
		
		assertNotNull(reportsVO);
	}

}
