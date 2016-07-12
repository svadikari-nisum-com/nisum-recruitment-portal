package com.nisum.employee.ref.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;

@RunWith(MockitoJUnitRunner.class)
public class InterviewDetailsRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public InterviewDetailsRepository interviewDetailsRepositoryTest;

	@Before
	public void init() throws Exception 
	{
		
		
		
		
	}


	@Test
	public final void testGetInterviewDetailsById() 
	{
		when(mongoOperations.findOne(any(Query.class), eq(InterviewDetails.class))).thenReturn(getInterviewDetails());
		InterviewDetails in = interviewDetailsRepositoryTest.getInterviewDetailsById("7");
		Assert.assertNotNull(in);
		Assert.assertSame(null, "nbolla@nisum.com", in.getCandidateEmail());
	}

	@Test
	public final void testScheduleInterview() {
		doNothing().when(mongoOperations).save(InterviewDetails.class);
		interviewDetailsRepositoryTest.scheduleInterview(getInterviewDetails());
	}

	@Test
	public final void testSaveFeedback() {
		doNothing().when(mongoOperations).save(InterviewDetails.class);
		interviewDetailsRepositoryTest.saveFeedback(getInterviewFeedback());
	}

	@Test
	public final void testUpdateinterviewDetails() {
		when(mongoOperations.findOne(any(Query.class), eq(InterviewDetails.class))).thenReturn(getInterviewDetails());
		doAnswer(new Answer<WriteResult>() {
		    public WriteResult answer(final InvocationOnMock invocation) throws Throwable {
	            return null ;
	        }
		}).when(mongoOperations).updateFirst(any(Query.class), any(Update.class), eq(InterviewDetails.class));
		interviewDetailsRepositoryTest.updateinterviewDetails(getInterviewDetails());
	
	}

	public InterviewDetails getInterviewDetails()
	{
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("nbolla@nisum.com");
		interviewDetails.setCandidateName("Naga");
		interviewDetails.setClientName("Nisum");
		return interviewDetails;
	}
	public InterviewFeedback getInterviewFeedback()
	{
		InterviewFeedback interviewFeedback = new InterviewFeedback();
		
		interviewFeedback.setAdditionalSkills("Maven");
		interviewFeedback.setInterviewerName("Naga");
		return interviewFeedback;
	}
}
