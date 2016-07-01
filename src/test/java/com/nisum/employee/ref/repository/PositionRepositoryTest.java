/**
 * 
 */
package com.nisum.employee.ref.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

/**
 * @author NISUM CONSULTING
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PositionRepositoryTest {

	@Mock
	private MongoOperations mongoOperations;

	@InjectMocks
	private PositionRepository repository = new PositionRepository();

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(repository)
				.setHandlerExceptionResolvers(
						ExceptionHandlerAdviceUtil.createExceptionResolver())
				.build();
	}

	@Test
	public void shouldRetrievePositionByClient() throws Exception {
		List<Position> positions = new ArrayList<>();
		Position pos = new Position();
		pos.setJobcode("SSE");
		pos.setDesignation("Sr. Software Engineer");
		positions.add(pos);
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(
				positions);

		List<Position> listPosition = repository.retrievePositionByClient("GAP");

		assertNotNull(listPosition);
		assertEquals("SSE", listPosition.get(0).getJobcode());
	}
}
