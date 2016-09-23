package com.nisum.employee.ref.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.Interviewer;
import com.nisum.employee.ref.domain.RoundUser;
import com.nisum.employee.ref.domain.UserInfo;

@RunWith(MockitoJUnitRunner.class)
public class ClientInfoRepositoryTest {
	
	

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public ClientInfoRepository clientInfoRepository = new ClientInfoRepository() ;
	

	@Before
	public void runBeforeEveryTest() throws Exception {
		
		
	}

	@After
	public void runAfterEveryTest() throws Exception {
	}

	@Test
	public  void testGetClientDetails() 
	{
		when(mongoOperations.findAll(ClientInfo.class)).thenReturn(Arrays.asList(getClientInfo()));
		List<ClientInfo> clientInfo = clientInfoRepository.getClientDetails();
		Assert.assertNotNull(clientInfo);
		for(ClientInfo cInfo : clientInfo)
		{
			System.out.println("Id::"+cInfo.getClientId()+"::Name::"+cInfo.getClientName());
			Assert.assertNotNull(cInfo.getClientId());
		}
	}

	@Test
	public final void testGetClientById() 
	{
		when(mongoOperations.find(any(Query.class), eq(ClientInfo.class))).thenReturn(Arrays.asList(getClientInfo()));
		List<ClientInfo> clientInfo = clientInfoRepository.getClientById("12");
		Assert.assertNotNull(clientInfo);
		
		System.out.println("getClientById::"+clientInfo.get(0).getClientName());
			
	}

	@Test
	public final void testFetchAllUsers() 
	{
		when(mongoOperations.findAll(UserInfo.class)).thenReturn(Arrays.asList(getUsertInfo()));
		List<UserInfo> allUsers = clientInfoRepository.fetchAllUsers();
		Assert.assertNotNull(allUsers);
		
		System.out.println("featchAllUsers::"+allUsers.get(0).getClientName());
		
	}

	@Test
	public final void testDeleteClient() 
	{
		
			when(mongoOperations.findOne(any(Query.class), eq(ClientInfo.class))).thenReturn(getClientInfo());
			when(mongoOperations.remove(ClientInfo.class)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "Hi All";
            } });
		
			clientInfoRepository.deleteClient("Nisum");
			System.out.println("deleteClient() Name::");
	}
	
	@Test
	public final void testCreateClient() 
	{
		doNothing().when(mongoOperations).save(ClientInfo.class);
		clientInfoRepository.createClient(getClientInfo());
	}

	@Test
	public final void testUpdateClient() 
	{
		doNothing().when(mongoOperations).save(any(ClientInfo.class),eq("clientInfo"));
		clientInfoRepository.updateClient(getClientInfo());
	}
	
	
	public ClientInfo getClientInfo()
	{
		ClientInfo cInfo = new ClientInfo();
		cInfo.setClientId("1");
		cInfo.setClientName("nisumMocked");
		
		RoundUser roundUser = new RoundUser();
		roundUser.setEmailId("rsub@nisum.com");
		roundUser.setName("Rama22222");
		roundUser.setSkillSet(new ArrayList<String>(Arrays.asList("java","Hibernate")));
		
		Interviewer interviewer = new Interviewer();
		interviewer.setTechnicalRound1(Arrays.asList(roundUser));
		
		cInfo.setInterviewer(interviewer);
		
		return cInfo;
	}
	public UserInfo getUsertInfo()
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setClientName("Nisum");
		userInfo.setDob("1989-01-26");
		userInfo.setDoj("2016-06-27");
		userInfo.setEmailId("nbolla@nisum.com");
		userInfo.setIsNotAvailable(false);
		userInfo.setLocation("Hyderabad");
		userInfo.setMobileNumber("9391907756");
		userInfo.setName("NagaRaju");
		userInfo.setProjectName("RequirementPortal");
		return userInfo;
	}

}
