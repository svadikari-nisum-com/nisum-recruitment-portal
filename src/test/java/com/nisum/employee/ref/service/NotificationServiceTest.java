package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.Transport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.util.EnDecryptUtil;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.CalendarDTO;
import com.nisum.employee.ref.view.NotificationMailDTO;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Transport.class)
public class NotificationServiceTest {
	
	@InjectMocks
	private NotificationService notificationService;
	
	@Mock
	private ProfileService profileService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private EnDecryptUtil enDecryptUtil;
	
	@Mock
	private UserNotificationService userNotificationService;
	
	@Mock
	private ProfileRepository profileRepository;
	
	@Mock
	private UserInfoRepository userInfoRepository;
	
	@Mock 
	private OfferRepository offerRepository;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(profileService, userService, userNotificationService)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
		PowerMockito.mockStatic(Transport.class);
		notificationService = applyProperties(notificationService);
	}

	/**
	 * Test case not yet implemented completely.
	 */
	@Test
	public void testSendScheduleMail() throws Exception {
		String[] resumes = {"resume_1"};
		
		
		
		InterviewSchedule interviewSchedule = new InterviewSchedule();
		interviewSchedule.setEmailIdInterviewer("dInterview@nisum.com");
		interviewSchedule.setCandidateName("Ramesh");
		interviewSchedule.setRoundName("first round");
		interviewSchedule.setInterviewDateTime("2016-09-19T15:52:30.167Z");
		interviewSchedule.setCandidateId("nbolla@nisum.com");
		
		UserNotification userNotification = new UserNotification();
		userNotification.setUserId(interviewSchedule.getEmailIdInterviewer());
		userNotification.setMessage("Take interview of "+interviewSchedule.getCandidateName() + " for "+ interviewSchedule.getRoundName());
		userNotification.setRead("No");
		
		doNothing().when(userNotificationService).createNotification(userNotification);
		
		when(profileService.getResume(any(String.class))).thenReturn(resumes);
		when(enDecryptUtil.decrypt(any(String.class))).thenReturn("ramu");
		when(profileService.getResume(anyString())).thenReturn(new String[]{"Naga", "Ram"});
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		
		String result = notificationService.sendScheduleMail(interviewSchedule, "99999", "88888", "idonthaveskypeid");
		assertNotNull(result);
	}

	@Test
	public void testSendFeedbackMail() throws Exception {
		List<UserInfoDTO> info = new ArrayList<>();
		UserInfoDTO userInfo = new UserInfoDTO();
		userInfo.setEmailId("dprasad@nisum.com");
		info.add(userInfo);

		InterviewFeedback interviewFeedback = new InterviewFeedback();
		interviewFeedback.setAdditionalSkills("Java");
		interviewFeedback.setCandidateId("nbolla@nisum.com");
		interviewFeedback.setCandidateName("NagaRaju");
		interviewFeedback.setDuration("20");
		interviewFeedback.setImprovement("Hibernate");
		interviewFeedback.setInterviewDateTime("2016-09-19T15:52:30.167Z");
		interviewFeedback.setInterviewerEmail("nbolla@nisum.com");
		interviewFeedback.setInterviewerName("Naga");
		interviewFeedback.setJobcode("Nisum-Dev");
		
		Profile profile = new Profile();
		profile.setHrAssigned("Naga");
		
		when(userService.retrieveUserByRole("ROLE_HR")).thenReturn(info);
		when(profileRepository.retrieveCandidateDetails(anyString())).thenReturn(Arrays.asList(profile));
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		notificationService.sendFeedbackMail(interviewFeedback);
	}
	
	@Test
	public void testNotificationMail() throws Exception {
		String from = "dprasad@nisum.com";
		
		List<String> attendees = new ArrayList<>();
		attendees.add("dprasad@nisum.com");
		attendees.add("nbolla@nisum.com");
		
		NotificationMailDTO notificationMail = new NotificationMailDTO();
		notificationMail.setOrganizer(from);
		notificationMail.setAttendees(attendees);
		notificationMail.setSubject("Test Notification Mail :)");
		notificationMail.setOrganizer("dprasad@nisum.com");
		
		CalendarDTO calendarDTO = new CalendarDTO();
		calendarDTO.setLocation("Conference Hall - Hyderabad");
		calendarDTO.setStartDateTime(LocalDateTime.of(2016, 9, 22, 13, 00));
		calendarDTO.setEndDateTime(LocalDateTime.of(2016, 9, 22, 17, 00));
		calendarDTO.setDescription("This is test notification mail");
		
		notificationMail.setCalendarDTO(calendarDTO);
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		boolean sendNotificationMail = notificationService.sendNotificationMail(notificationMail);
		assertEquals(true, sendNotificationMail);
	}
	
	@Test
	public void sendInterviewersNotAvailableStatusNotification() throws Exception
	{
		UserInfo userInfo = new UserInfo();
		
		userInfo.setEmailId("nbolla@nisum.com");
		userInfo.setName("Naga");
		userInfo.setIsNotAvailable(true);
		
		when(userInfoRepository.retrieveUserByRole(anyString())).thenReturn(Arrays.asList(userInfo));
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		
		notificationService.sendInterviewersNotAvailableStatusNotification();
	}
	
	@Test
	public void sendOfferLetterNotificationMail() throws Exception
	{
		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setCandidateName("Naga");		
		offerDTO.setEmailId("nbolla@nisum.com");
		offerDTO.setDesignation("SoftwareDeveloper");
		offerDTO.setJoiningDate(new Date());
		when(offerRepository.getData(anyString())).thenReturn(new String[]{"Naga", "Ram"});
		notificationService.sendOfferLetterNotificationMail(offerDTO);
	}
	
	@Test
	public void sendpositionCreationMail() throws Exception
	{
		PositionDTO position = new PositionDTO();
		position.setHiringManager("nbolla");
		position.setLocationHead("Naga");
		position.setJobcode("Naga");
		position.setClient("Nisum");
		position.setNoOfPositions(2);
		position.setFunctionalGroup("DEV");
		position.setJobHeader("Nothing");
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setEmailId("nbolla@nisum.com");
		userInfo.setName("Naga");
		userInfo.setIsNotAvailable(true);
		
		when(userInfoRepository.retrieveUserById(anyString())).thenReturn(Arrays.asList(userInfo));
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		notificationService.sendpositionCreationMail(position);
	}
	
	@Test
	public void sendpositionStatusChangeMail() throws Exception
	{
		Position position = new Position();
		position.setHiringManager("nbolla");
		position.setLocationHead("Naga");
		position.setJobcode("Naga");
		position.setClient("Nisum");
		position.setNoOfPositions(2);
		position.setFunctionalGroup("DEV");
		position.setJobHeader("Nothing");
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setEmailId("nbolla@nisum.com");
		userInfo.setName("Naga");
		userInfo.setIsNotAvailable(true);
		
		when(userInfoRepository.retrieveUserById(anyString())).thenReturn(Arrays.asList(userInfo));
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		notificationService.sendpositionStatusChangeMail(position);
	}
	
	@Test
	public void sendOfferNotificationMail() throws Exception
	{
		PowerMockito.doNothing().when(Transport.class, "send", org.mockito.Matchers.any(Message.class));
		notificationService.sendOfferNotificationMail("Naga", "nbolla@nisum.com", "Naga", "Hi");
	}
	private NotificationService applyProperties(NotificationService notificationService)
	{
		
		notificationService.setSmtpAuthRequired("true");
		notificationService.setHost("smtp.gmail.com");	
		notificationService.setPort("587");
		notificationService.setPassword("1*ÒÄ$ôÇ¯æS¶º¢¿ $²¶");
		notificationService.setUsername("nisumportal@gmail.com");
		notificationService.setFrom("nisumportal@gmail.com");
		notificationService.setSRC_CANDIDATE_VM("candidate.vm");
		notificationService.setSRC_INTERVIEWER_VM("interviewer.vm");
		notificationService.setSRC_FEEDBACK_HR_VM("feedback_hr.vm");
		notificationService.setNOTIFICATION_MAIL_TEMPLATE("notificationMailTemplate.vm");
		notificationService.setINTERVIEWERS_NOTAVAILABLE_TEMPLATE("interviewersNotAvailable.vm");
		notificationService.setOFFER_LETTER_MAIL_BODY_TEMPLATE("offerMailBodyTemplate.html");
		notificationService.setSRC_POSITION_HEAD_VM("positionCreation.vm");
		notificationService.setSRC_POSITION_STATUS_CHANGE_VM("positionStatusChange.vm");
		notificationService.setCANDIDATE_JOINED_TEMPLATE("candidateJoinedTemplate.html");
		notificationService.setOFFER_INITIATED_TEMPLATE("offerInitiatedTemplate.html");
		return notificationService;
		
		
	}
}
