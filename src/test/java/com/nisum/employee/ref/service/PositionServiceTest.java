/**
 * 
 */
package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.repository.PositionRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

/**
 * @author NISUM CONSULTING
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest {

	@Mock
	private PositionRepository positionRepository;

	@InjectMocks
	private PositionService service = new PositionService();

	@Before
	public void init() {
		MockMvcBuilders.standaloneSetup(service)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}

	@Test
	public void shouldRetrievePositionByClient() throws Exception {
		List<Position> positions = new ArrayList<>();
		Position pos = new Position();
		pos.setJobcode("SSE");
		pos.setDesignation("Sr. Software Engineer");
		positions.add(pos);
		Mockito.when(positionRepository.retrievePositionByClient(any(String.class))).thenReturn(positions);

		List<Position> listPosition = service.retrievePositionByClient("GAP");

		assertNotNull(listPosition);
		assertEquals("SSE", listPosition.get(0).getJobcode());
	}

}
