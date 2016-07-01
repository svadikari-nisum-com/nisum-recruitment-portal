package com.nisum.employee.ref.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {
	
	@InjectMocks
	NotificationService notificationService;
	
	@Mock
	ProfileService profileService;
	
	@Mock
	UserService userService;
	
	@Mock
	UserNotificationService userNotificationService;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(profileService, userService, userNotificationService)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}

	@Test
	public void testSendScheduleMail() throws Exception {
		String[] resumes = {"resume_1"};
		
		InterviewSchedule interviewSchedule = new InterviewSchedule();
		interviewSchedule.setEmailIdInterviewer("dInterview@nisum.com");
		interviewSchedule.setCandidateName("Ramesh");
		interviewSchedule.setRoundName("first round");
		
		UserNotification userNotification = new UserNotification();
		userNotification.setUserId(interviewSchedule.getEmailIdInterviewer());
		userNotification.setMessage("Take interview of "+interviewSchedule.getCandidateName() + " for "+ interviewSchedule.getRoundName());
		userNotification.setRead("No");
		
		doNothing().when(userNotificationService).createNotification(userNotification);
		when(profileService.getResume(any(String.class))).thenReturn(resumes);
		
		String result = notificationService.sendScheduleMail(interviewSchedule, "99999", "88888", "idonthaveskypeid");
		assertNotNull(result);
	}

	@Test
	public void testSendFeedbackMail() {
		List<UserInfo> info = new ArrayList<>();
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmailId("dprasad@nisum.com");
		info.add(userInfo);
		
		when(userService.retrieveUserByRole("ROLE_HR")).thenReturn(info);
	}

}
