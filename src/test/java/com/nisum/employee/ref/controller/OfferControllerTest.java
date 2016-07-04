package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class OfferControllerTest {
	private MockMvc mockMvc;

	@Mock
	private OfferRepository offerRepository;

	@InjectMocks
	private OfferController offerController = new OfferController();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(offerController)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}
	@Test
	public void shouldSaveOfferDetails() throws Exception {
	    doNothing().when(offerRepository).saveOffer(any(Offer.class));
	  	 mockMvc.perform(
				post("/save-offer").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Offer()))).andExpect(status().isOk());
	}
}
