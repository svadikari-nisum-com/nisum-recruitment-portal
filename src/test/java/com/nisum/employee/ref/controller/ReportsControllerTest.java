package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.service.ReportsService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;


@RunWith(MockitoJUnitRunner.class)
public class ReportsControllerTest {
 
	private MockMvc mockMvc; 
	
	@Mock
	private ReportsService reportsService;
	
	@InjectMocks
	private ReportsController reportsController = new ReportsController();
	
	@Before
	public void init()
	{
		 mockMvc = MockMvcBuilders.standaloneSetup(reportsController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}
	@Test
	public void getReportData() throws Exception {
		
		List<ReportsVO> reportsVO = new ArrayList<ReportsVO>();
		ReportsVO vo = new ReportsVO();
		vo.setNoOfOpenPositions(7);
		reportsVO.add(vo);
		//Mockito.when((reportsService).getReportByHiringManager(anyString(), null)).thenReturn(reportsVO);
		Mockito.when((reportsService).getReportByHiringManager(any(String.class),any(String.class))).thenReturn(reportsVO);
		mockMvc.perform(get("/reports").param("hiringManager", "nbolla@nisum.com").param("recruiter", "nbolla")).andExpect(status().isOk());
		
	}

}
