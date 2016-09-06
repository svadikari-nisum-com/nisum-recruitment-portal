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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.Offer;
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
				content(MockTestUtil.convertToJsonFormat(new Offer()))).andExpect(status().isOk());
	}
	
	@Test
	public void getOffersTest() throws Exception{
		List<OfferDTO> offers=new ArrayList<OfferDTO>();
		OfferDTO offer=new OfferDTO();
		offer.setEmailId("rgangadhari@nisum.com");
		offers.add(offer);
		Mockito.when((offerService).getOffers()).thenReturn(offers);
		mockMvc.perform(get("/offers").param("emailId","rgangadhari@nisum.com")).andExpect(status().isOk());
		
		
	}
	
}
