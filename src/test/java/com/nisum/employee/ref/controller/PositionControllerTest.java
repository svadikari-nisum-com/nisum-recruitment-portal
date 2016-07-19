package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.nisum.employee.ref.domain.Position;
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
				post("/position").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Position()))).andExpect(status().isOk());

	}
	@Test
	public void shouldUpdatePosition() throws Exception {
		doNothing().when(positionService).updatePosition(any(Position.class));
		mockMvc.perform(
				put("/position").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Position()))).andExpect(status().isAccepted());

	}
	
	@Test
	public void testRetrievePositionByDesignation() throws Exception {
		List<PositionDTO> positions = new ArrayList<>();
		PositionDTO position = new PositionDTO();
		position.setJobcode("SE");
		position.setDesignation("Software Engineer");
		positions.add(position);
		
		Mockito.when(
				(positionService).retrievePositionsbasedOnDesignation(any(String.class)))
				.thenReturn(positions);
		mockMvc.perform(get("/position").param("designation", "Software Engineer")).andExpect(status().isOk());
	}
	@Test
	public void testRetrievePositionByClient() throws Exception {
		List<PositionDTO> positions = new ArrayList<>();
		PositionDTO position = new PositionDTO();
		position.setClient("GAP");
		positions.add(position);
		Mockito.when(
				(positionService).retrievePositionByClient(any(String.class)))
				.thenReturn(positions);
		mockMvc.perform(get("/position").param("client", "GAP")).andExpect(status().isOk());
	}
	@Test
	public void testRetrieveAllPositions() throws Exception {
		List<PositionDTO> positions = new ArrayList<>();
		PositionDTO position = new PositionDTO();
		position.setJobcode("SE");
		position.setDesignation("Software Engineer");
		positions.add(position);
		Mockito.when(
				(positionService).retrieveAllPositions())
				.thenReturn(positions);
		mockMvc.perform(get("/position").param("client", "GAP").param("designation", "Software Engineer")).andExpect(status().isOk());
	}
	@Test
	public void testRetrievePositionsBasedOnJobCode() throws Exception {
		PositionDTO positionsDetail = new PositionDTO();
		Mockito.when(
				(positionService).retrievePositionsbasedOnJobCode("SE"))
				.thenReturn(positionsDetail);
		mockMvc.perform(get("/searchPositionsBasedOnJobCode").param("jobcode", "SE")).andExpect(status().isOk());
	}
	@Test
	public void testRetrievesearchPositionbasedOnLocation() throws Exception {
		PositionDTO positionsDetail = new PositionDTO();
		List<PositionDTO> positions = new ArrayList<>();
		positionsDetail.setLocation("Banglore");
		positions.add(positionsDetail);
		Mockito.when(
				(positionService).retrievePositionbasedOnLocation(any(String.class)))
				.thenReturn(positions);
		mockMvc.perform(get("/searchPositionBasedOnLocation").param("location", "Banglore")).andExpect(status().isOk());
	}
	@Test
	public void testRetrieveAllPositionsAggregate() throws Exception {
		PositionAggregate positionAggregate = new PositionAggregate();
		List<PositionAggregate> positions = new ArrayList<>();
		positionAggregate.setDesignation("SSE");
		positionAggregate.setTotal(100);
		positions.add(positionAggregate);
		Mockito.when(
				(positionService).retrieveAllPositionsAggregate())
				.thenReturn(positions);
		mockMvc.perform(
				get("/getPositionsByAggregation").contentType(MediaType.APPLICATION_JSON).
				content(MockTestUtil.convertToJsonFormat(new Position()))).andExpect(status().isOk());
	}
}
