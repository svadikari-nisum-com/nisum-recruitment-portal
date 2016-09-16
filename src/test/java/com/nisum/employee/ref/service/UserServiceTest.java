package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.converter.UserInfoConverter;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.TimeSlots;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.repository.InterviewDetailsRepository;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.InterviewRoundsDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserInfoRepository userInfoRepository = new UserInfoRepository();
	
	@Mock
	private InterviewDetailsRepository interviewDetailsRepository = new InterviewDetailsRepository();
	
	@Spy
	private UserInfoConverter userInfoConverter = new UserInfoConverter();
	
	private List<UserInfo> actualUserInfos;
	private UserInfo actualUserInfo;
	private List<UserInfoDTO> userInfoDTOs;

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
		
		List<TimeSlots> timeSlots = new ArrayList<TimeSlots>();
		TimeSlots time = new TimeSlots();
		time.setDay("Sunday");
		time.setHour("12");
		time.setTime("23");
		timeSlots.add(time);
		actualUserInfo.setTimeSlots(timeSlots);
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
		userInfoDTOs = userService.retrieveUser();

		assertNotNull(userInfoDTOs);
		assertEquals(userInfoDTOs.get(0).getEmailId(), actualUserInfos.get(0).getEmailId());
	}

	@Test
	public void retrieveUserByIdTest() {
		when(userInfoRepository.retrieveUserById(any(String.class))).thenReturn(actualUserInfos);
		userInfoDTOs = userService.retrieveUserById("dprasad@nisum.com");

		assertNotNull(userInfoDTOs);
		assertEquals("Sunday", userInfoDTOs.get(0).getTimeSlots().get(0).getDay());
		assertEquals(userInfoDTOs.get(0).getEmailId(), actualUserInfos.get(0).getEmailId());
	}

	@Test
	public void retrieveUserByNameTest() {
		when(userInfoRepository.retrieveUserByName(any(String.class))).thenReturn(actualUserInfos);
		userInfoDTOs = userService.retrieveUserByName("Durga Prasad Narikalapa");
		
		assertNotNull(userInfoDTOs);
		assertEquals(userInfoDTOs.get(0).getName(), actualUserInfos.get(0).getName());
	}

	@Test
	public void createUserInfoTest() {
		when(userInfoRepository.createUserInfo(any(String.class))).thenReturn(actualUserInfo);
		UserInfoDTO userInfoDTO = userService.createUserInfo("dprasad@nisum.com");
		assertNotNull(userInfoDTO);
		assertEquals(userInfoDTO.getEmailId(), actualUserInfo.getEmailId());
	}

	@Test
	public void updateUserTest() {
		doNothing().when(userInfoRepository).updateUser(actualUserInfo);
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setName("Durga Prasad Narikalapa");
		userInfoDTO.setEmailId("dprasad@nisum.com");
		userInfoDTO.setClientName("TestCient");
		userService.updateUser(userInfoDTO);
	}
	@Test
	public void deleteUserTest() {
		String emailId="rgangadhari@nisum.com";
		doNothing().when(userInfoRepository).updateUser(actualUserInfo);
		UserInfoDTO userInfoDTO = new UserInfoDTO();		
		userInfoDTO.setActive(true);
		userService.deleteUser(emailId);
	}
	@Test
	public void retrieveUserByClientTest() {
		when(userInfoRepository.retrieveUserByClient(any(String.class))).thenReturn(actualUserInfos);

		userInfoDTOs = userService.retrieveUserByClient("TestCient");
		assertNotNull(userInfoDTOs);
		assertEquals(userInfoDTOs.get(0).getClientName(), actualUserInfos.get(0).getClientName());
	}
	
	@Test
	public void retrieveUserByRoleTest() {
		when(userInfoRepository.retrieveUserByRole(any(String.class))).thenReturn(actualUserInfos);

		userInfoDTOs = userService.retrieveUserByRole("1");
		assertNotNull(userInfoDTOs);
		assertEquals(userInfoDTOs.get(0).getRoles().get(0), actualUserInfos.get(0).getRoles().get(0));
	}
	
	@Test
	public void retriveInterviewersAndTheirNoOfSchedledRounds() {
		
		actualUserInfos = new ArrayList<>();
		actualUserInfo = new UserInfo();
		/*actualUserInfo.setName("Ariel Lewis");
		actualUserInfo.setEmailId("alewis@nisum.com");
		//actualUserInfo.setNoOfRoundsScheduled(2);
		actualUserInfos.add(actualUserInfo);*/
		
		actualUserInfo = new UserInfo();
		actualUserInfo.setName("Vinayak Prabhu");
		actualUserInfo.setEmailId("vprabhu@nisum.com");
		//actualUserInfo.setNoOfRoundsScheduled(1);
		actualUserInfos.add(actualUserInfo);
		
		List<InterviewDetails> interviewDetails = new ArrayList<>();
		InterviewDetails interviewer = new InterviewDetails();
		interviewer.setCandidateEmail("changra@gmai.com");
		interviewer.setInterviewerEmail("vprabhu@nisum.com");
		interviewDetails.add(interviewer);
		
		/*interviewer = new InterviewDetails();
		interviewer.setCandidateEmail("satya@aaa.com");
		interviewer.setInterviewerEmail("alewis@nisum.com");
		interviewDetails.add(interviewer);
		
		interviewer = new InterviewDetails();
		interviewer.setCandidateEmail("vkjonnabhatla@gmail.com");
		interviewer.setInterviewerEmail("alewis@nisum.com");
		interviewDetails.add(interviewer);*/
		
		/*List<String> actualRoles = new ArrayList<String>();
		actualRoles.add("ROLE_USER");
		actualUserInfo.setRoles(actualRoles);*/
		
		when(userInfoRepository.getUserInfo(any(String.class),any(String.class),any(String.class))).thenReturn(actualUserInfos);
		
		when(interviewDetailsRepository.getInterviewByInterviewer(any(String.class))).thenReturn(interviewDetails);

		List<InterviewRoundsDTO> userInfoDTOs = userService.getInterviewers("Technical Round 1",null,"ROLE_INTERVIEWER");
		assertNotNull(userInfoDTOs);
		assertEquals(1,userInfoDTOs.get(0).getNoOfRoundsScheduled());
	}
	
	@Test
	public void retrieveUserByRoleAndLocationTest(){
		List<String> users = new ArrayList<String>();
		users.add("ROLE_HR");
		UserInfo  userInfo = new UserInfo();
		userInfo.setRoles(users);
		userInfo.setEmailId("vjonnabhatla@nisum.com");
		userInfo.setLocation("Hyderabad");
		
		when(userInfoRepository.retrieveUserByRoleAndLocation(any(String.class),any(String.class))).thenReturn(Arrays.asList(userInfo));
		
		List<UserInfoDTO> userDTOs = userService.retrieveUserByRoleAndLocation("ROLE_HR", "Hyderabad");
        assertNotNull(userDTOs);
        assertEquals("vjonnabhatla@nisum.com", userDTOs.get(0).getEmailId());
	}
}
