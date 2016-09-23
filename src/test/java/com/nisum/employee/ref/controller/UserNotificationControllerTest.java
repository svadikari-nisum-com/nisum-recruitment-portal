package com.nisum.employee.ref.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.service.UserNotificationService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.UserNotificationDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserNotificationControllerTest {
	private MockMvc mockMvc;

    @Mock
    private UserNotificationService userNotificationService;
    @InjectMocks
    private UserNotificationController userNotificationController = new UserNotificationController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(userNotificationController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   
   @Test
   public void shouldRetrieveNotification() throws Exception {
	   List<UserNotificationDTO> userInfoList = new ArrayList<>();
	   UserNotificationDTO userNotification = new UserNotificationDTO();
	   userNotification.setPk("1");
	   userNotification.setRead("");
	   userNotification.setUserId("1");
	   userInfoList.add(userNotification);
	   Mockito.when(
			   (userNotificationService).getUserNotifications("1"))
			   .thenReturn(userInfoList);
	   mockMvc.perform(get("/userNotification").param("userId", "1")).andExpect(status().isOk());
   }
   @Test
   public void shouldReadNotification() throws Exception {
	   String userId = "1";
	   String message = "hi";
	   doNothing().when(userNotificationService).readNotification(userId, message);
		mockMvc.perform(post("/userNotification").param("userId", "1").param("meaasge","hii")).andExpect(status().isOk());
   }
   @Test
   public void shouldRetrieveNoNotification() throws Exception {
	   List<UserNotificationDTO> userInfoList = new ArrayList<>();
	   UserNotificationDTO userNotification = new UserNotificationDTO();
	   userNotification.setPk("1");
	   userNotification.setRead("");
	   userNotification.setUserId("1");
	   userInfoList.add(userNotification);
	   Mockito.when(
			   (userNotificationService).getUserNoNotifications("1"))
			   .thenReturn(userInfoList);
	   mockMvc.perform(get("/noNotification").param("userId", "1")).andExpect(status().isOk());
   }
   @Test
   public void testUserNotificationCount() throws Exception {
	  long userInfo = 10;
	   Mockito.when(
			   (userNotificationService).getUserNotificationCount("1"))
			   .thenReturn(userInfo);
	   mockMvc.perform(get("/getNotificationCount").param("userId", "1")).andExpect(status().isOk());
   }
   
}
