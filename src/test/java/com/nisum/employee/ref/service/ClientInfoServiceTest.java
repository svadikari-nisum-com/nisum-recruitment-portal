package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.Interviewer;
import com.nisum.employee.ref.domain.RoundUser;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.repository.ClientInfoRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class ClientInfoServiceTest {

	@InjectMocks
	private ClientInfoService clientInfoService;

	@Mock
	private ClientInfoRepository clientInfoRepository;

	private List<ClientInfo> clientInfos;
	private ClientInfo clientInfo;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(clientInfoRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		clientInfos = new ArrayList<>();
		clientInfo = new ClientInfo();
		clientInfo.setClientId("12");
		clientInfo.setClientName("durga prasad");

		Interviewer interviewer = new Interviewer();
		interviewer.setTechnicalRound1(getTechRound());
		interviewer.setTechnicalRound2(getTechRound());
		interviewer.setManagerRound(getTechRound());
		interviewer.setHrRound(getTechRound());

		clientInfo.setInterviewers(interviewer);
		clientInfo.setLocations("Hyderabad");

		clientInfos.add(clientInfo);
	}

	@Test
	public void testGetClientDetails() {
		when(clientInfoRepository.getClientDetails()).thenReturn(clientInfos);
		List<ClientInfo> clientDetails = clientInfoService.getClientDetails();
		assertNotNull(clientDetails);
		assertEquals(clientInfos.get(0).getClientId(), clientDetails.get(0).getClientId());
	}

	@Test
	public void testGetClientDetailsByClient() {
		when(clientInfoRepository.getClientDetailsByClient(Mockito.anyString())).thenReturn(clientInfos);
		List<ClientInfo> clientDetailsByClient = clientInfoService.getClientDetailsByClient("durga prasad");
		assertNotNull(clientDetailsByClient);
		assertEquals("durga prasad", clientDetailsByClient.get(0).getClientName());
	}

	@Test
	public void testGetClientNames() {
		List<String> clientNames = new ArrayList<>();
		clientNames.add("name1");
		clientNames.add("name2");
		when(clientInfoRepository.getClientNames()).thenReturn(clientNames);
		
		List<String> expClientNames = clientInfoService.getClientNames();
		assertNotNull(expClientNames);
		assertEquals("name1", expClientNames.get(0));
	}

	@Test
	public void testGetInterviewerNames() {
		when(clientInfoRepository.getClientDetails()).thenReturn(clientInfos);
		List<String> interviewerNames = clientInfoService.getInterviewerNames();
		assertNotNull(interviewerNames);
		assertEquals("client_name", interviewerNames.get(0));
	}

	@Test
	public void testGetClientById() {
		when(clientInfoRepository.getClientById(Mockito.anyString())).thenReturn(clientInfos);
		List<ClientInfo> clientInfoList = clientInfoService.getClientById("999");
		assertNotNull(clientInfoList);
		assertEquals("12", clientInfoList.get(0).getClientId());
	}

	@Test
	public void testFetchAllUsers() {
		List<UserInfo> userInfos = new ArrayList<>();
		UserInfo userInfo = new UserInfo();
		userInfo.setClientName("master_client");
		userInfos.add(userInfo);
		
		when(clientInfoRepository.fetchAllUsers()).thenReturn(userInfos);
		List<UserInfo> expuserInfos = clientInfoService.fetchAllUsers();
		assertNotNull(expuserInfos);
		assertEquals("master_client", expuserInfos.get(0).getClientName());
	}

	@Test
	public void testDeleteClient() {
		doNothing().when(clientInfoRepository).deleteClient(Mockito.anyString());
		clientInfoService.deleteClient("c1");
	}

	@Test
	public void testCreateClient() {
		doNothing().when(clientInfoRepository).createClient(Mockito.any(ClientInfo.class));
		clientInfoService.createClient(clientInfo);
	}

	@Test
	public void testUpdateClient() {
		doNothing().when(clientInfoRepository).updateClient(Mockito.any(ClientInfo.class));
		clientInfoService.updateClient(clientInfo);
	}
	
	private List<RoundUser> getTechRound() {
		List<RoundUser> roundUsers = new ArrayList<>();

		RoundUser roundUser = new RoundUser();
		roundUser.setEmailId("client_email@gmail.com");
		roundUser.setName("client_name");
		roundUsers.add(roundUser);
		return roundUsers;
	}
	
}
