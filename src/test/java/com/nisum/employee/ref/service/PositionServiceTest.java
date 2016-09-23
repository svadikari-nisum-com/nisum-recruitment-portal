package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.converter.PositionConverter;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.repository.PositionRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.PositionDTO;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest {

	@Mock
	private PositionRepository positionRepository;

	@Mock
	private NotificationService notificationService;
	
	@Spy
	private PositionConverter positionConverter = new PositionConverter();

	@InjectMocks
	private PositionService service = new PositionService();

	private List<Position> positions;

	private Position position;

	@Before
	public void init() {
		MockMvcBuilders.standaloneSetup(service)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		positions = new ArrayList<>();
		position = new Position();
		position.setJobcode("SSE");
		position.setDesignation("Sr. Software Engineer");
		position.setLocation("Hyderabad");
		positions.add(position);
	}
	
	@Test
	public void savePosition() throws MessagingException {
		doNothing().when(positionRepository).preparePosition(position);
		notificationService.sendpositionCreationMail(positionConverter.convertToDTO(position));
		service.preparePosition(positionConverter.convertToDTO(position));
	
	}
	
	@Test
	public void updatePosition() {
		boolean expected = true;
		when((positionRepository).updatePosition(position)).thenReturn(expected);
		boolean result = service.updatePosition(positionConverter.convertToDTO(position));
		assertFalse(result);
	}

	@Test
	public void retrieveAllPositions() {
		when(positionRepository.retrieveAllPositions(Mockito.anyString(),Mockito.anyString())).thenReturn(positions);
		List<PositionDTO> listPosition = service.retrieveAllPositions("hiringManager","swathi");
		assertNotNull(listPosition);
		assertEquals("SSE", listPosition.get(0).getJobcode());
	}
	
	@Test
	public void deletePositionsByJobCode() {
		when(positionRepository.deletePositionBasedOnJC(Mockito.anyString())).thenReturn(position);
		PositionDTO actualPosition = service.deletePositionBasedOnJC("JSSE");

		assertNotNull(actualPosition);
		assertEquals("SSE", actualPosition.getJobcode());
	}
	
	@Test
	public void retrieveAllPositionsAggregate() {
		List<PositionAggregate> aggregates = new ArrayList<>();
		PositionAggregate positionAggregate = new PositionAggregate();
		positionAggregate.setDesignation("SSE");
		positionAggregate.setTotal(6);
		aggregates.add(positionAggregate);
		
		when(positionRepository.retrieveAllPositionsAggregate()).thenReturn(aggregates);
		List<PositionAggregate> positionAggregates = service.retrieveAllPositionsAggregate();

		assertNotNull(positionAggregates);
		assertEquals("SSE", positionAggregates.get(0).getDesignation());
	}
	
	@Test
	public void updatePositionStatus() throws MessagingException {
		doNothing().when(positionRepository).preparePosition(position);
		service.updatePositionStatus("SEN_ATS_HYD_1682016_229", "Approved");
	}
	
	@Test
	public void retrievePositionByJobCodeTest(){
		List<Position> positions = new ArrayList<>();
		Position position = new Position();
		position.setJobcode("DEV_GAP-GID_HYD_382016_642");
		position.setLocation("Hyderabad");
		positions.add(position);
		when(positionRepository.retrievePositionByJobCode(Mockito.anyString())).thenReturn(position);
		PositionDTO positionDTO = service.retrievePositionByJobCode("DEV_GAP-GID_HYD_382016_642");
		assertEquals("Hyderabad", positionDTO.getLocation());
	}
	
}
