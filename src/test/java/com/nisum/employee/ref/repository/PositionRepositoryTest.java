/**
 * 
 */
package com.nisum.employee.ref.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
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
		Mockito.when(mongoOperations.findOne(Mockito.any(Query.class),Mockito.eq(Position.class))).thenReturn(getPosition());
		doAnswer(new Answer<WriteResult>() {
		    public WriteResult answer(final InvocationOnMock invocation) throws Throwable {
	            return null ;
	        }
		}).when(mongoOperations).updateFirst(any(Query.class), any(Update.class), eq(Position.class));
		
		boolean res = positionRepository.updatePosition(getPosition());
		assertFalse(res);
	}
	
	@Test
	public void retrievePositionByClient()
	{
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> position = positionRepository.retrieveAllPositions("client","Nisum");
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	@SuppressWarnings("serial")
	@Ignore
	public void retrieveAllPositions()
	{
		when(mongoOperations.findAll(Position.class)).thenReturn(
				new ArrayList<Position>()
				{{add(getPosition());}});
		List<Position> position = positionRepository.retrieveAllPositions("hiringManager","Swathoi");
		assertNotNull(position);
		assertEquals("Swathi", position.get(0).getHiringManager());
	}
	
	@Test
	public void retrievePositionsByDesignation()
	{
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(getPosition()));
		List<Position> position = positionRepository.retrieveAllPositions("designation","developer");
		assertNotNull(position);
		assertEquals("SSE", position.get(0).getJobcode());
	}
	
	
	@Test
	public void deletePositionByJC()
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
		List<Position> position = positionRepository.retrieveAllPositions("location","SF");
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
	
	@Test
	public void retrieveAllPositionsByHiringManager() {
		Position pos = new Position();
		pos.setJobcode("DEV_GAP-GID_HYD_582016_845");
		pos.setHiringManager("Shyam Vadikari");
		Mockito.when(mongoOperations.find(any(Query.class), eq(Position.class))).thenReturn(Arrays.asList(pos));
		List<Position> position = positionRepository.retrieveAllPositions("hiringManager","Swathoi");
		assertNotNull(position);
		assertEquals("DEV_GAP-GID_HYD_582016_845", position.get(0).getJobcode());
	}
	
	@Test
	public void updatePositionStatus() {
		Position pos = new Position();
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(Position.class))).thenReturn(pos);
		Mockito.doNothing().when(mongoOperations).save(Position.class);
		positionRepository.updatePositionStatus("SEN_ATS_HYD_1682016_229", "Approved");
	}
	
	@Test
	public void retrievePositionByJobCodeTest(){
		List<Position> positions = new ArrayList<>();
		Position position = new Position();
		position.setJobcode("DEV_GAP-GID_HYD_382016_642");
		position.setLocation("Hyderabad");
		positions.add(position);
		Mockito.when(mongoOperations.findOne(any(Query.class), eq(Position.class))).thenReturn(position);
		positionRepository.retrievePositionByJobCode("DEV_GAP-GID_HYD_382016_642");
		assertEquals("Hyderabad", position.getLocation());
	}
}
