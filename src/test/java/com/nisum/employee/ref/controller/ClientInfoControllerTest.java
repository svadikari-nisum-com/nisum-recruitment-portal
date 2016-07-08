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

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.service.ClientInfoService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class ClientInfoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientInfoService clientInfoService;
    @InjectMocks
    private ClientInfoController clientInfoController = new ClientInfoController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(clientInfoController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   @Test
   public void testGetClients() throws Exception {
	   List<String> clientNames = new ArrayList<>();
	   clientNames.add("Nisum");
	   Mockito.when((clientInfoService).getClientNames()).thenReturn(clientNames);
	   mockMvc.perform(get("/clientNames").param("clientNames", "NISUM","Nisum1")).andExpect(status().isOk());
   }
   @Test
   public void testGetClientDetailsByClient() throws Exception {
	    ClientInfo clientInfo = new ClientInfo();
		List<ClientInfo> clients = new ArrayList<>();
		clientInfo.setClientName("NISUM");
		clients.add(clientInfo);
		Mockito.when((clientInfoService).getClientDetailsByClient(any(String.class))).thenReturn(clients);
		mockMvc.perform(get("/clientInfo").param("clientName", "NISUM")).andExpect(status().isOk());
   }
   @Test
   public void testGetClientDetails() throws Exception {
	    ClientInfo clientInfo = new ClientInfo();
		List<ClientInfo> clients = new ArrayList<>();
		clientInfo.setClientName("NISUM");
		clients.add(clientInfo);
		Mockito.when((clientInfoService).getClientDetails()).thenReturn(clients);
		mockMvc.perform(get("/clientInfo").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new ClientInfo()))).andExpect(status().isOk());
   }
   @Test
   public void testGetInterviewerNames() throws Exception {
	   List<String> interviewerNames = new ArrayList<>();
	   interviewerNames.add("Swati");
	   Mockito.when((clientInfoService).getInterviewerNames()).thenReturn(interviewerNames);

	   mockMvc.perform(
			   get("/interviewerNames").param("interviewerNames", "swati","arjun")).andExpect(status().isOk());
   }
   @Test
   public void testClientById() throws Exception {
	    ClientInfo clientInfo = new ClientInfo();
		List<ClientInfo> clients = new ArrayList<>();
		clientInfo.setClientId("11");
		clients.add(clientInfo);
		Mockito.when((clientInfoService).getClientById(any(String.class))).thenReturn(clients);
		mockMvc.perform(get("/getClientById").param("clientId", "112")).andExpect(status().isOk());
   }
   @Test
   public void testDeleteClient() throws Exception {
	    String clientId = "11";
		doNothing().when(clientInfoService).deleteClient(clientId);
		mockMvc.perform(delete("/clientInfo").param("clientId", "11")).andExpect(status().isOk());
   }
   @Test
   public void testCreateClient() throws Exception {
	   ClientInfo clientInfo = new ClientInfo();
	   clientInfo.setClientId("1");
	   clientInfo.setClientName("Nisum");
	   clientInfo.setLocations("Hyderabad");
	   doNothing().when(clientInfoService).createClient(clientInfo);
	   mockMvc.perform(
				post("/clientInfo").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new ClientInfo()))).andExpect(status().isOk());
   }
   @Test
   public void testUpdateClient() throws Exception {
	   ClientInfo clientInfo = new ClientInfo();
	   clientInfo.setClientId("1");
	   clientInfo.setClientName("Nisum");
	   clientInfo.setLocations("Hyderabad");
	   doNothing().when(clientInfoService).updateClient(clientInfo);
	   mockMvc.perform(
				put("/clientInfo").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new ClientInfo()))).andExpect(status().isAccepted());
   }
   
}