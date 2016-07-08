/**
 * 
 */
package com.nisum.employee.ref.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;

/**
 * @author NISUM CONSULTING
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PositionRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@Mock
	public MongoTemplate mongoTemplate;
	
	@Mock
	public AggregationResults<PositionAggregate> agg;

	@InjectMocks
	private PositionRepository positionRepository = new PositionRepository();

	
	@Before
	public void init() {

		
		
		
		
		
		
		
		
		
		when(agg.getMappedResults()).thenReturn(Arrays.asList(getPositionAggregate()));
	}

	@Test
	public void shouldRetrievePositionByClient() throws Exception {
		
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> listPosition = positionRepository.retrievePositionByClient("GAP");
		assertNotNull(listPosition);
		assertEquals("SSE", listPosition.get(0).getJobcode());
	}
	
	@Test
	public void save()
	{
		Mockito.doNothing().when(mongoOperations).save(Position.class);
		positionRepository.save(getPosition());
	}
	
	@Test
	public void preparePosition()
	{
		Mockito.doNothing().when(mongoOperations).save(Position.class);
		positionRepository.preparePosition(getPosition());
		
	}
	
	@Test
	public void updatePosition()
	{
		doAnswer(new Answer<WriteResult>() {
		    public WriteResult answer(final InvocationOnMock invocation) throws Throwable {
	            return null ;
	        }
		}).when(mongoOperations).updateFirst(any(Query.class), any(Update.class), eq(Position.class));
		
		positionRepository.updatePosition(getPosition());
	}
	
	@Test
	public void retrievePositionByClient()
	{
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> position = positionRepository.retrievePositionByClient("Nisum");
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void retrieveAllPositions()
	{
		when(mongoOperations.findAll(Position.class)).thenReturn(
				new ArrayList<Position>()
				{{add(getPosition());}});
		List<Position> position = positionRepository.retrieveAllPositions();
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	@Test
	public void retrievePositionsbasedOnDesignation()
	{
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> position = positionRepository.retrievePositionsbasedOnDesignation("SSE");
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	
	@Test
	public void retrievePositionsbasedOnJobCode()
	{
		when(mongoOperations.findOne(any(Query.class), eq(Position.class))).thenReturn(getPosition());
		Position position = positionRepository.retrievePositionsbasedOnJobCode("SSE");
		assertNotNull(position);
		assertEquals("SSE", position.getJobcode());
	}
	
	@Test
	public void deletePositionBasedOnJC()
	{
		when(mongoOperations.findAndRemove(any(Query.class), eq(Position.class))).thenReturn(getPosition());
		Position position = positionRepository.deletePositionBasedOnJC("SSE");
		assertNotNull(position);
		assertEquals("SSE", position.getJobcode());
	}
	
	@Test
	public void retrievePositionbasedOnLocation()
	{
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> position = positionRepository.retrievePositionbasedOnLocation("SSE");
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	@Test
	public void retrieveAllPositionsAggregate()
	{
		doAnswer(new Answer<AggregationResults<PositionAggregate>>() {
	        public AggregationResults<PositionAggregate> answer(InvocationOnMock invocation)
	                throws Throwable {
	            return agg;

	        }
	    }).when(mongoTemplate).aggregate(any(Aggregation.class), eq(Position.class), eq(PositionAggregate.class));
		
		List<PositionAggregate> positionAggregate = positionRepository.retrieveAllPositionsAggregate();
		assertNotNull(positionAggregate);
		assertEquals("SSE", positionAggregate.get(0).getDesignation());
	}
	
	public Position getPosition()
	{
		Position pos = new Position();
		pos.setJobcode("SSE");
		pos.setDesignation("Sr. Software Engineer");
		
		return pos;
	}
	public PositionAggregate getPositionAggregate()
	{
		PositionAggregate positionAggregate = new PositionAggregate();
		
		positionAggregate.setDesignation("SSE");
		
		return positionAggregate;
	}
}
