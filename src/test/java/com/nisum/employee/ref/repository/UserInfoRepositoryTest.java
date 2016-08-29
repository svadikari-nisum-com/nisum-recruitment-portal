package com.nisum.employee.ref.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import com.nisum.employee.ref.domain.UserInfo;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public UserInfoRepository userInfoRepository;
	
	@Before
	public void init() throws Exception {
		
	}

	@Test
	public final void testRegisterUserByEmailId() {
		Mockito.doNothing().when(mongoOperations).save(UserInfo.class);
		userInfoRepository.registerUserByEmailId("nbolla@nisum.com");
	}

	@Test
	public final void testRetrieveUser() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(Arrays.asList(getUserInfo()));
		List<UserInfo> userInfo = userInfoRepository.retrieveUser();
		
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("nbolla@nisum.com", userInfo.get(0).getEmailId());
		
	}

	@Test
	public final void testRetrieveUserById() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(Arrays.asList(getUserInfo()));
		List<UserInfo> userInfo = userInfoRepository.retrieveUserById("623");
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("nbolla@nisum.com", userInfo.get(0).getEmailId());
	}

	@Test
	public final void testRetrieveUserByName() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(Arrays.asList(getUserInfo()));
		List<UserInfo> userInfo =  userInfoRepository.retrieveUserByName("Naga");
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("nbolla@nisum.com", userInfo.get(0).getEmailId());
		
	}

	@Test
	public final void testCreateUserInfo() {

		UserInfo userInfo = userInfoRepository.createUserInfo("Naga");
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("Naga", userInfo.getEmailId());
	}

	@Test
	public final void testUpdateUser() {
		Mockito.when(mongoOperations.findOne(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(getUserInfo());
		Mockito.doAnswer( new Answer<WriteResult>() {
			@Override
			public WriteResult answer(final InvocationOnMock invocation) throws Throwable
			{
				return null;
			}
		}).when(mongoOperations).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(UserInfo.class));
		
		userInfoRepository.updateUser(getUserInfo());
	}
	@Test
	public final void testDeleteUser() {
		Mockito.when(mongoOperations.findOne(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(getUserInfo());
		Mockito.doAnswer( new Answer<WriteResult>() {
			@Override
			public WriteResult answer(final InvocationOnMock invocation) throws Throwable
			{
				return null;
			}
		}).when(mongoOperations).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(UserInfo.class));
		
		userInfoRepository.deleteUser(getUserInfo());
	}
	@Test
	public final void testRetrieveUserByClient() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(Arrays.asList(getUserInfo()));
		List<UserInfo> userInfo = userInfoRepository.retrieveUserByClient("Nisum");
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("nbolla@nisum.com", userInfo.get(0).getEmailId());
	}

	@Test
	public final void testRetrieveUserByRole() {
		
		Mockito.when(mongoOperations.find(Mockito.any(Query.class),Mockito.eq(UserInfo.class))).thenReturn(Arrays.asList(getUserInfo()));
		List<UserInfo> userInfo = userInfoRepository.retrieveUserByRole("User");
		Assert.assertNotNull(userInfo);
		Assert.assertEquals("nbolla@nisum.com", userInfo.get(0).getEmailId());
	}

	public UserInfo getUserInfo() {
		List<String> defualtRoles = new ArrayList<String>();
		defualtRoles.add("ROLE_USER");
		UserInfo userInfo = new UserInfo();
		userInfo.setEmailId("nbolla@nisum.com");
		userInfo.setRoles(defualtRoles);
		return userInfo;
	}
	
}
