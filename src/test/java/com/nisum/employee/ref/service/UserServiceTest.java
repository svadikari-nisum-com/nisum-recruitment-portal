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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserInfoRepository userInfoRepository = new UserInfoRepository();

	private List<UserInfo> actualUserInfos;
	private List<UserInfo> expectedUserInfos;
	private UserInfo actualUserInfo;
	private UserInfo expectedUserInfo;

	@Before
	public void setUp() {
		MockMvcBuilders.standaloneSetup(userInfoRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		actualUserInfos = new ArrayList<>();

		actualUserInfo = new UserInfo();
		actualUserInfo.setName("Durga Prasad Narikalapa");
		actualUserInfo.setEmailId("dprasad@nisum.com");
		actualUserInfo.setClientName("TestCient");

		List<String> actualRoles = new ArrayList<String>();
		actualRoles.add("ROLE_USER");
		actualUserInfo.setRoles(actualRoles);

		actualUserInfos.add(actualUserInfo);
	}

	@Test
	public void registerUserByEmailIdTest() {
		doNothing().when(userInfoRepository).registerUserByEmailId("dprasad@nisum.com");
		userService.registerUserByEmailId("dprasad@nisum.com");
	}

	@Test
	public void retrieveUserTest() {

		when(userInfoRepository.retrieveUser()).thenReturn(actualUserInfos);
		expectedUserInfos = userService.retrieveUser();

		assertNotNull(expectedUserInfos);
		assertEquals(expectedUserInfos.get(0).getEmailId(), actualUserInfos.get(0).getEmailId());
	}

	@Test
	public void retrieveUserByIdTest() {
		when(userInfoRepository.retrieveUserById(any(String.class))).thenReturn(actualUserInfos);

		expectedUserInfos = userService.retrieveUserById("dprasad@nisum.com");

		assertNotNull(expectedUserInfos);
		assertEquals(expectedUserInfos.get(0).getEmailId(), actualUserInfos.get(0).getEmailId());
	}

	@Test
	public void retrieveUserByNameTest() {
		when(userInfoRepository.retrieveUserByName(any(String.class))).thenReturn(actualUserInfos);

		expectedUserInfos = userService.retrieveUserByName("Durga Prasad Narikalapa");
		assertNotNull(expectedUserInfos);
		assertEquals(expectedUserInfos.get(0).getName(), actualUserInfos.get(0).getName());
	}

	@Test
	public void createUserInfoTest() {
		when(userInfoRepository.createUserInfo(any(String.class))).thenReturn(actualUserInfo);

		expectedUserInfo = userService.createUserInfo("dprasad@nisum.com");
		assertNotNull(expectedUserInfo);
		assertEquals(expectedUserInfo.getEmailId(), actualUserInfo.getEmailId());
	}

	@Test
	public void updateUserTest() {
		doNothing().when(userInfoRepository).updateUser(actualUserInfo);

		userService.updateUser(actualUserInfo);
	}

	@Test
	public void retrieveUserByClientTest() {
		when(userInfoRepository.retrieveUserByClient(any(String.class))).thenReturn(actualUserInfos);

		expectedUserInfos = userService.retrieveUserByClient("TestCient");
		assertNotNull(expectedUserInfos);
		assertEquals(expectedUserInfos.get(0).getClientName(), actualUserInfos.get(0).getClientName());
	}
	
	@Test
	public void retrieveUserByRoleTest() {
		when(userInfoRepository.retrieveUserByRole(any(String.class))).thenReturn(actualUserInfos);

		expectedUserInfos = userService.retrieveUserByRole("1");
		assertNotNull(expectedUserInfos);
		assertEquals(expectedUserInfos.get(0).getRoles().get(0), actualUserInfos.get(0).getRoles().get(0));
	}
}
