package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.service.OfferService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.OfferDTO;

@RunWith(MockitoJUnitRunner.class)
public class OfferControllerTest {
	private MockMvc mockMvc;

	@Mock
	private OfferService offerService;

	@InjectMocks
	private OfferController offerController = new OfferController();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(offerController)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}
	@Test
	public void shouldSaveOfferDetails() throws Exception {
	    doNothing().when(offerService).saveOffer(any(OfferDTO.class));
	  	 mockMvc.perform(
				post("/offers").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new OfferDTO()))).andExpect(status().isOk());
	}
	
	@Test
	public void getOffersTest() throws Exception{
		List<OfferDTO> offers=new ArrayList<OfferDTO>();
		OfferDTO offer=new OfferDTO();
		offer.setEmailId("rgangadhari@nisum.com");
		offers.add(offer);
		Mockito.when((offerService).getOffers()).thenReturn(offers);
		mockMvc.perform(get("/offers").param("managerEmail","rgangadhari@nisum.com")).andExpect(status().isOk());
		mockMvc.perform(get("/offers")).andExpect(status().isOk());
	}
	
	@Test
	public void uploadOfferLetter() throws Exception
	{
		Mockito.doNothing().when(offerService).saveResumeInBucket(Mockito.anyObject(),Mockito.anyString());
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/offers/{candidateId}/upload-offer-letter",7).file(getMultipartFile())).andExpect(status().isOk());
	}
	
	@Test
	public void getOffer() throws Exception
	{
		OfferDTO offer=new OfferDTO();
		offer.setEmailId("nbolla@nisum.com");
		Mockito.when((offerService).getOffer(Mockito.anyString())).thenReturn(offer);
		mockMvc.perform(get("/offers/{emailId}","nbolla@nisum.com")).andExpect(status().isOk());
	}
	
	@Test
	public void getNextStatuses() throws Exception
	{
		mockMvc.perform(get("/offers/nextStatuses","selected")).andExpect(status().isOk());
	}
	
	public MockMultipartFile  getMultipartFile()
	{
		MockMultipartFile  file = new MockMultipartFile("file", "Hi Heloo".getBytes());
		return file;
	}
}
