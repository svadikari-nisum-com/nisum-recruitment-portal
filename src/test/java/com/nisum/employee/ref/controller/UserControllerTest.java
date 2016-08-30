package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.service.UserService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.UserInfoDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController = new UserController();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(userController)
				.setHandlerExceptionResolvers(
						ExceptionHandlerAdviceUtil.createExceptionResolver())
				.build();
	}

	@Test
	public void testRegisterUser() throws Exception {
		doNothing().when(userService).registerUserByEmailId(
				"skaranam@nisum.com");
		mockMvc.perform(
				post("/user").contentType(MediaType.APPLICATION_JSON).content(
						MockTestUtil.convertToJsonFormat(new UserInfo())))
				.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserTest() throws Exception {
		String emailId = "rgangadhari@nisum.com";
		doNothing().when(userService).deleteUser(emailId);;
		mockMvc.perform(delete("/user").param("userId", "rgangadhari@nisum.com")).andExpect(status().isOk());
   }

	@Test
	public void testRetrieveUsersByUserId() throws Exception {
		UserInfoDTO userInfo = new UserInfoDTO();
		List<UserInfoDTO> userInfoList = new ArrayList<>();
		userInfo.setEmailId("karanam@nisum.com");
		userInfoList.add(userInfo);
		Mockito.when((userService).retrieveUserById(any(String.class)))
				.thenReturn(userInfoList);
		mockMvc.perform(get("/user").param("emailId", "skaranam@nisum.com"))
				.andExpect(status().isOk());
	}

	@Test
	public void testRetrieveUsersByUserName() throws Exception {
		UserInfoDTO userInfo = new UserInfoDTO();
		List<UserInfoDTO> userInfoList = new ArrayList<>();
		userInfo.setEmailId("karanam@nisum.com");
		userInfoList.add(userInfo);
		Mockito.when((userService).retrieveUserById(any(String.class)))
				.thenReturn(userInfoList);
		mockMvc.perform(get("/user").param("name", "skaranam")).andExpect(
				status().isOk());
	}

	@Test
	public void testRetrieveUsersByClientName() throws Exception {
		UserInfoDTO userInfo = new UserInfoDTO();
		List<UserInfoDTO> userInfoList = new ArrayList<>();
		userInfo.setEmailId("karanam@nisum.com");
		userInfoList.add(userInfo);
		Mockito.when((userService).retrieveUserById(any(String.class)))
				.thenReturn(userInfoList);
		mockMvc.perform(get("/user").param("clientName", "Nisum")).andExpect(
				status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {
		UserInfoDTO userInfo = new UserInfoDTO();
		userInfo.setEmailId("karanam@nisum.com");
		doNothing().when(userService).updateUser(any(UserInfoDTO.class));
		mockMvc.perform(
				put("/user").contentType(MediaType.APPLICATION_JSON).content(
						MockTestUtil.convertToJsonFormat(userInfo))).andExpect(
				status().isOk());

	}
}
