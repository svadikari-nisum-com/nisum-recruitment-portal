package com.nisum.employee.ref.controller;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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

import com.nisum.employee.ref.domain.Designation;
import com.nisum.employee.ref.service.IDesignationService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.DesignationDTO;

@RunWith(MockitoJUnitRunner.class)
public class DesignationControllerTest {
	private MockMvc mockMvc;

    @Mock
    private IDesignationService designationService;
    @InjectMocks
    private DesignationController designationController = new DesignationController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(designationController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   @Test
   public void shouldRetrieveDesignation() throws Exception {
	   ArrayList<DesignationDTO> designationList = new ArrayList<>();
	   DesignationDTO designation = new DesignationDTO();
	   designation.setDesignation("SE");
	   designation.setMaxExpYear("2");
	   designation.setMinExpYear("1");
	   designationList.add(designation);
	   Mockito.when(
			   (designationService).retrieveDesignations())
			   .thenReturn(designationList);
	   mockMvc.perform(
				get("/designations").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Designation()))).andExpect(status().isOk());
   }
   @Test
	public void shouldSaveDesignation() throws Exception {
	   DesignationDTO designation = new DesignationDTO();
	    designation.setDesignation("SE");
	    designation.setMaxExpYear("2");
	    designation.setMinExpYear("1");
		doNothing().when(designationService).prepareDesignation(designation);
		mockMvc.perform(
				post("/designations").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Designation()))).andExpect(status().isOk());

	}
   @Test
  	public void shouldUpdateDesignation() throws Exception {
	   DesignationDTO designation = new DesignationDTO();
  	    designation.setDesignation("SE");
  	    designation.setMaxExpYear("2");
  	    designation.setMinExpYear("1");
  		doNothing().when(designationService).updateDesignation(designation);
  		mockMvc.perform(
  				put("/designations").contentType(MediaType.APPLICATION_JSON).
  				content(MockTestUtil.convertToJsonFormat(new Designation()))).andExpect(status().isOk());

  	}
}
