package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.converter.ProfileConverter;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.domain.Round;
import com.nisum.employee.ref.repository.InterviewDetailsRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.ProfileDTO;

@RunWith(MockitoJUnitRunner.class)
public class InterviewDetailsServiceTest {

	@InjectMocks
	private InterviewDetailsService interviewDetailsService;

	@Mock
	private InterviewDetailsRepository interviewDetailsRepository;

	@Mock
	private INotificationService notificationService;
	
	@Spy
	private ProfileConverter profileConverter = new ProfileConverter();


	@Mock
	private ProfileService profileService;

	private InterviewFeedback interviewFeedback;
	private InterviewSchedule interviewSchedule;
	private InterviewDetails interviewDetails;

	private List<ProfileDTO> profiles;
	private List<InterviewDetails> interviewDetailList;

	@Before
	public void setUp() {
		MockMvcBuilders.standaloneSetup(interviewDetailsRepository, notificationService, profileService)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		interviewDetailList = new ArrayList<>();

		interviewFeedback = new InterviewFeedback();
		interviewFeedback.setCandidateId("can_email@gmail.com");
		interviewFeedback.setCandidateName("can_name");
		interviewFeedback.setJobcode("Java_SE1");
		interviewFeedback.setDuration("30");
		interviewFeedback.setInterviewerName("interview_name");
		interviewFeedback.setInterviewerEmail("inter@nisum.com");
		interviewFeedback.setStatus("no");

		interviewSchedule = new InterviewSchedule();
		interviewSchedule.setCandidateId("can001");
		interviewSchedule.setRoundName("1st round");
		interviewSchedule.setInterviewerName("int_name");
		interviewSchedule.setEmailIdInterviewer("inter@nisum.com");

		interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("can_email@gmail.com");
		interviewDetails.setCandidateName("can_name");
		interviewDetails.setClientName("WF");
		interviewDetails.setDesignation("SE1");
		interviewDetails.setInterviewerId("java_037");
		interviewDetails.setInterviewerEmail("inter_email@nisum.com");

		interviewDetailList.add(interviewDetails);

		List<Round> rounds = new ArrayList<>();
		Round round = new Round(interviewSchedule.getRoundName(), interviewSchedule, interviewFeedback);
		rounds.add(round);

		interviewDetails.setRounds(rounds);

		profiles = new ArrayList<>();
		ProfileDTO profile = new ProfileDTO();
		profile.setCandidateName("can_name");
		profile.setAddress("Hyderabad");
		profile.setEmailId("can_email@gmail.com");
		profile.setMobileNo("9988776655");
		profile.setSkypeId("can_sky@skype.com");
		profiles.add(profile);
	}

	@Test
	public void testSaveFeedbackIfFeedbackNotNull() throws MessagingException {
		when(interviewDetailsRepository.getInterviewDetailsById(anyString())).thenReturn(interviewDetails);
		doNothing().when(interviewDetailsRepository).scheduleInterview(any(InterviewDetails.class));
		doNothing().when(notificationService).sendFeedbackMail(any(InterviewFeedback.class));
		InterviewDetails interviewDetailList = interviewDetailsService.saveFeedback(interviewFeedback);
		assertNotNull(interviewDetailList);
		assertEquals("can_email@gmail.com", interviewDetailList.getCandidateEmail());
	}
	
	@Test
	public void testSaveFeedbackIfFeedbackNull() throws MessagingException {
		List<Round> rounds = new ArrayList<>();
		Round round = new Round(interviewSchedule.getRoundName(), interviewSchedule, null);
		rounds.add(round);

		interviewDetails.setRounds(rounds);
		when(interviewDetailsRepository.getInterviewDetailsById(anyString())).thenReturn(interviewDetails);
		doNothing().when(interviewDetailsRepository).scheduleInterview(any(InterviewDetails.class));
		doNothing().when(notificationService).sendFeedbackMail(any(InterviewFeedback.class));
		InterviewDetails interviewDetailList = interviewDetailsService.saveFeedback(interviewFeedback);
		assertNotNull(interviewDetailList);
		assertEquals("can_email@gmail.com", interviewDetailList.getCandidateEmail());
	}

	@Test
	public void testScheduleInterviewIfRoundsNotNull() throws Exception {
		when(interviewDetailsRepository.getInterviewDetailsById(anyString())).thenReturn(interviewDetails);
		doNothing().when(interviewDetailsRepository).scheduleInterview(interviewDetails);
		when(profileService.retrieveCandidateDetails(anyString())).thenReturn(profiles);
		when(notificationService.sendScheduleMail(any(InterviewSchedule.class), anyString(), anyString(), anyString()))
				.thenReturn("");
		InterviewDetails actualInterviewDetails = interviewDetailsService.scheduleInterview(interviewSchedule);
		assertNotNull(actualInterviewDetails);
		assertEquals("WF", actualInterviewDetails.getClientName());
	}
	
	@Test
	public void testScheduleInterviewIfRoundsNull() throws Exception {
		interviewDetails.setRounds(null);
		when(interviewDetailsRepository.getInterviewDetailsById(anyString())).thenReturn(interviewDetails);
		doNothing().when(interviewDetailsRepository).scheduleInterview(interviewDetails);
		when(profileService.retrieveCandidateDetails(anyString())).thenReturn(profiles);
		when(notificationService.sendScheduleMail(any(InterviewSchedule.class), anyString(), anyString(), anyString()))
				.thenReturn("");
		InterviewDetails actualInterviewDetails = interviewDetailsService.scheduleInterview(interviewSchedule);
		assertNotNull(actualInterviewDetails);
		assertEquals("WF", actualInterviewDetails.getClientName());
	}

	@Test
	public void testScheduleInterview1() throws Exception {
		when(interviewDetailsRepository.getInterviewDetailsById(anyString())).thenReturn(interviewDetails);
		doNothing().when(interviewDetailsRepository).scheduleInterview(interviewDetails);
		when(profileService.retrieveCandidateDetails(anyString())).thenReturn(profiles);
		when(notificationService.sendScheduleMail(any(InterviewSchedule.class), anyString(), anyString(), anyString()))
				.thenReturn("");

		InterviewDetails actualInterviewDetails = interviewDetailsService.updateInterview(interviewSchedule);
		assertNotNull(actualInterviewDetails);
		assertEquals("can_name", actualInterviewDetails.getCandidateName());
	}

	@Test
	public void testCreateInterview() {
		doNothing().when(interviewDetailsRepository).scheduleInterview(interviewDetails);
		InterviewDetails actualInterviewDetails = interviewDetailsService.createInterview(interviewDetails);
		assertNotNull(actualInterviewDetails);
		assertEquals("inter_email@nisum.com", actualInterviewDetails.getInterviewerEmail());
	}

	@Test
	public void testGetInterview() {
		when(interviewDetailsRepository.getInterview(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService.getInterview(interviewDetails.getCandidateEmail());
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_name", actuaInterviewDetails.get(0).getCandidateName());
	}

	@Test
	public void testGetInterviewByInterviewer() {
		when(interviewDetailsRepository.getInterviewByInterviewer(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByInterviewer(interviewDetails.getInterviewerEmail());
		assertNotNull(actuaInterviewDetails);
		assertEquals("inter_email@nisum.com", actuaInterviewDetails.get(0).getInterviewerEmail());
	}

	@Test
	public void testGetInterviewByInterviewerAndJobCode() {
		when(interviewDetailsRepository.getInterviewByInterviewerAndJobCode(anyString(), anyString()))
				.thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByInterviewerAndJobCode("inter_email@nisum.com", "SE_01");
		assertNotNull(actuaInterviewDetails);
		assertEquals("SE1", actuaInterviewDetails.get(0).getDesignation());
	}

	@Test
	public void testGetAll() {
		when(interviewDetailsRepository.getAll()).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService.getAll();
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_name", actuaInterviewDetails.get(0).getCandidateName());
	}

	@Test
	public void testGetInterviewByJobCode() {
		when(interviewDetailsRepository.getInterviewByJobCode(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService.getInterviewByJobCode("Java_SE1");
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_email@gmail.com", actuaInterviewDetails.get(0).getCandidateEmail());
	}

	@Test
	public void testGetInterviewByCandidateId() {
		when(interviewDetailsRepository.getInterviewByCandidateId(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByCandidateId(interviewDetails.getCandidateEmail());
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_email@gmail.com", actuaInterviewDetails.get(0).getCandidateEmail());
	}

	@Test
	public void testGetInterviewByClient() {
		when(interviewDetailsRepository.getInterviewByClient(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByClient(interviewDetails.getClientName());
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_email@gmail.com", actuaInterviewDetails.get(0).getCandidateEmail());
	}

	@Test
	public void testGetInterviewByProgress() {
		when(interviewDetailsRepository.getInterviewByProgress(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByProgress(interviewDetails.getProgress());
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_email@gmail.com", actuaInterviewDetails.get(0).getCandidateEmail());
	}

	@Test
	public void testGetInterviewBySkill() {
		when(interviewDetailsRepository.getInterviewBySkill(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewBySkill(interviewDetails.getCandidateEmail());
		assertNotNull(actuaInterviewDetails);
		assertEquals("can_email@gmail.com", actuaInterviewDetails.get(0).getCandidateEmail());
	}

	@Test
	public void testUpdateInterviewDetails() {
		doNothing().when(interviewDetailsRepository).updateinterviewDetails(interviewDetails);
		interviewDetailsService.updateInterviewDetails(interviewDetails);
	}

	@Test
	public void testGetInterviewByDesignation() {
		when(interviewDetailsRepository.getInterviewByDesignation(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByDesignation(interviewDetails.getDesignation());
		assertNotNull(actuaInterviewDetails);
		assertEquals("SE1", actuaInterviewDetails.get(0).getDesignation());
	}

	@Test
	public void testGetInterviewByinterviewId() {
		when(interviewDetailsRepository.getInterviewByinterviewId(anyString())).thenReturn(interviewDetailList);
		List<InterviewDetails> actuaInterviewDetails = interviewDetailsService
				.getInterviewByinterviewId(interviewDetails.getInterviewerId());
		assertNotNull(actuaInterviewDetails);
		assertEquals("java_037", actuaInterviewDetails.get(0).getInterviewerId());
	}
}
