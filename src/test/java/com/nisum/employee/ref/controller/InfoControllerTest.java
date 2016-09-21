package com.nisum.employee.ref.controller;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.nisum.employee.ref.domain.InfoEntity;
import com.nisum.employee.ref.service.IAppInfoService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.InfoEntityDTO;

@RunWith(MockitoJUnitRunner.class)
public class InfoControllerTest {
     
	private MockMvc mockMvc;

    @Mock
    private IAppInfoService infoService;
    @InjectMocks
    private InfoController infoController = new InfoController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(infoController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   @Test
   public void retrieveInfo() throws Exception {
	    InfoEntity infoEntity = new InfoEntity();
	    infoEntity.setKey("1");
	    ArrayList<InfoEntityDTO> infoList = new ArrayList<>();
		Mockito.when((infoService).retrieveSkills()).thenReturn(infoList);
			mockMvc.perform(
					get("/info").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new InfoEntity()))).andExpect(status().isOk());
   }
   @Test
   public void updateInfo() throws Exception {
	   InfoEntityDTO infoEntity = new InfoEntityDTO();
	    infoEntity.setKey("1");
		  doNothing().when(infoService).updateInfo(infoEntity);
			mockMvc.perform(
					put("/info").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new InfoEntity()))).andExpect(status().isOk());
   }
   @Test
   public void deleteInfo() throws Exception {
	   InfoEntityDTO infoEntity = new InfoEntityDTO();
	    infoEntity.setKey("1");
		  doNothing().when(infoService).updateDesigInfo(infoEntity);
			mockMvc.perform(
					delete("/info").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new InfoEntity()))).andExpect(status().isOk());
   }
}
