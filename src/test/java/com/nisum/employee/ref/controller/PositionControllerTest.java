package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.service.PositionService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.PositionDTO;

@RunWith(MockitoJUnitRunner.class)
public class PositionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PositionService positionService;

	@InjectMocks
	private PositionController controller = new PositionController();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}

	@Test
	public void shouldCreatePosition() throws Exception {
		doNothing().when(positionService).preparePosition(any(PositionDTO.class));
		mockMvc.perform(
				post("/positions").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new PositionDTO()))).andExpect(status().isOk());

	}
	@Test
	public void shouldUpdatePosition() throws Exception {
		boolean b = true;
		Mockito.when((positionService).updatePosition(any(PositionDTO.class)))
		.thenReturn(b);
		mockMvc.perform(
				put("/positions").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new PositionDTO()))).andExpect(status().isOk());
       
	}
	
	@Test
	public void retrieveAllPositions() throws Exception {
		List<PositionDTO> positions = new ArrayList<>();
		PositionDTO position = new PositionDTO();
		position.setJobcode("SE");
		position.setDesignation("Software Engineer");
		positions.add(position);
		Mockito.when(
				(positionService).retrieveAllPositions("hiringManager","Swathoi"))
				.thenReturn(positions);
		mockMvc.perform(get("/positions").param("searchKey", "GAP").param("searchValue", "Software Engineer")).andExpect(status().isOk());
	}
	
	@Test
	public void retrieveAllPositionsAggregate() throws Exception {
		PositionAggregate positionAggregate = new PositionAggregate();
		List<PositionAggregate> positions = new ArrayList<>();
		positionAggregate.setDesignation("SSE");
		positionAggregate.setTotal(100);
		positions.add(positionAggregate);
		Mockito.when(
				(positionService).retrieveAllPositionsAggregate())
				.thenReturn(positions);
		mockMvc.perform(
				get("/positions/positionsByAggregation")).andExpect(status().isOk());
	}
	
	@Test
	public void updateProfileStatus() throws Exception {
		doNothing().when(positionService).updatePositionStatus(anyString(), anyString());
		mockMvc.perform(
				put("/positions/updatePositionStatus")
				.param("jobCode", "SEN_ATS_HYD_1682016_229")
				.param("status", "Approved").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new PositionDTO()))).andExpect(status().isOk());
	}
}
