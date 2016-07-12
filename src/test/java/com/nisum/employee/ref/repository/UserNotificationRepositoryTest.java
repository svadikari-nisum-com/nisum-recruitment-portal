package com.nisum.employee.ref.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.nisum.employee.ref.domain.UserNotification;

@RunWith(MockitoJUnitRunner.class)
public class UserNotificationRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public UserNotificationRepository userNotificationRepository;
	
	@Before
	public void init() throws Exception {
		
	}


	@Test
	public final void testRetrieveNotifications() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class), Mockito.eq(UserNotification.class))).thenReturn(Arrays.asList(getUserNotification()));
		List<UserNotification> userNotifiactions = userNotificationRepository.retrieveNotifications("7");
		Assert.assertNotNull(userNotifiactions);
		Assert.assertEquals("Hello", userNotifiactions.get(0).getMessage());
		
	}

	@Test
	public final void testRetrieveNoNotifications() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class), Mockito.eq(UserNotification.class))).thenReturn(Arrays.asList(getUserNotification()));
		List<UserNotification> userNotifiactions = userNotificationRepository.retrieveNoNotifications("7");
		Assert.assertNotNull(userNotifiactions);
		Assert.assertEquals("Hello", userNotifiactions.get(0).getMessage());
	}

	@Test
	public final void testReadNotifications() {
		Mockito.doAnswer( new Answer<WriteResult>() {
			@Override
			public WriteResult answer(final InvocationOnMock invocation) throws Throwable
			{
				return null;
			}
		}).when(mongoOperations).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(UserNotification.class));
		
		userNotificationRepository.readNotifications("7", "Hi");
		
	}

	@Test
	public final void testCreateNotifications() {
		Mockito.doNothing().when(mongoOperations).save(UserNotification.class);
		userNotificationRepository.createNotifications(getUserNotification());
	}

	@Test
	public final void testGetUserNotificationCount() {
		
		Mockito.doAnswer( new Answer<Long>() {
			@Override
			public Long answer(final InvocationOnMock invocation) throws Throwable
			{
				return 79L;
			}
		}).when(mongoOperations).count(Mockito.any(Query.class), Mockito.eq(UserNotification.class));
		
		long userCount = userNotificationRepository.getUserNotificationCount("7");
		Assert.assertNotNull(userCount);
		Assert.assertEquals(79L, userCount);

	}

	public UserNotification getUserNotification()
	{
		UserNotification userNotification = new UserNotification();
		userNotification.setMessage("Hello");
		return userNotification;
		
		
	}
}
