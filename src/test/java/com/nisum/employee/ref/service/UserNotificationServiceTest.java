package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.converter.UserNotificationConverter;
import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.repository.UserNotificationRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.UserNotificationDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserNotificationServiceTest {

	@InjectMocks
	private UserNotificationService userNotificationService;

	@Spy
	private UserNotificationConverter userNotificationConverter = new UserNotificationConverter();
	
	@Mock
	private UserNotificationRepository userNotificationRepository;

	private List<UserNotification> actualUserNotifications;
	private List<UserNotificationDTO> expectedUserNotifications;
	private UserNotification userNotification;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(userNotificationRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		actualUserNotifications = new ArrayList<>();
		userNotification = new UserNotification();
		userNotification.setUserId("9999");
		userNotification.setMessage("User Message");
		actualUserNotifications.add(userNotification);
	}

	@Test
	public void testGetUserNotifications() {
		when(userNotificationRepository.retrieveNotifications(any(String.class))).thenReturn(actualUserNotifications);

		expectedUserNotifications = userNotificationService.getUserNotifications("9999");
		assertNotNull(expectedUserNotifications);
		assertEquals(expectedUserNotifications.get(0).getUserId(), actualUserNotifications.get(0).getUserId());
	}

	@Test
	public void testGetUserNoNotifications() {
		when(userNotificationRepository.retrieveNoNotifications(any(String.class))).thenReturn(actualUserNotifications);

		expectedUserNotifications = userNotificationService.getUserNoNotifications("9999");
		assertNotNull(expectedUserNotifications);
		assertEquals(expectedUserNotifications.get(0).getUserId(), actualUserNotifications.get(0).getUserId());
	}

	@Test
	public void testReadNotification() {
		doNothing().when(userNotificationRepository).readNotifications(any(String.class), any(String.class));
		userNotificationService.readNotification("9999", "User Message");
	}

	@Test
	public void testCreateNotification() {
		doNothing().when(userNotificationRepository).createNotifications(userNotification);
		userNotification = new UserNotification();
		userNotification.setUserId("1234");
		userNotificationService.createNotification(userNotification);
	}

	@Test
	public void testGetUserNotificationCount() {
		long count = 10;
		when(userNotificationRepository.getUserNotificationCount(any(String.class))).thenReturn(count);
		long noficationCount = userNotificationService.getUserNotificationCount("9999");
		assertEquals(noficationCount, count);
	}

}
