package com.nisum.employee.ref.repository;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.nisum.employee.ref.domain.SequenceId;

@RunWith(MockitoJUnitRunner.class)
public class SequenceRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public SequenceRepository sequenceRepositoryTest;
	
	@Before
	public void init() throws Exception {
		
	}


	@Test
	public final void testGetNextSequenceId() {
		
		Mockito.when(mongoOperations.findOne(Mockito.any(Query.class), Mockito.eq(SequenceId.class))).thenReturn(null);
		Mockito.doAnswer( new Answer<SequenceId>() {
			@Override
			public SequenceId answer(final InvocationOnMock invocation) throws Throwable
			{
				return getSequenceId();
			}
		}).when(mongoOperations).findAndModify(Mockito.any(Query.class), Mockito.any(Update.class),Mockito.any(FindAndModifyOptions.class), Mockito.eq(SequenceId.class));
		
		long test = sequenceRepositoryTest.getNextSequenceId("11");
		
		Assert.assertNotNull(test);
		Assert.assertEquals(623, test);
		
	}
	public SequenceId getSequenceId()
	{
		SequenceId sequenceId = new SequenceId();
		
		sequenceId.setId("7");
		sequenceId.setSeq(623);
		return sequenceId;
	}

}
