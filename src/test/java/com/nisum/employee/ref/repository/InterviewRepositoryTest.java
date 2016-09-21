package com.nisum.employee.ref.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.nisum.employee.ref.domain.InterviewDetails;

@RunWith(MockitoJUnitRunner.class)
public class InterviewRepositoryTest {

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public InterviewRepository interviewRepository;
	
	@Before
	public  void init() throws Exception {
	}


	@Test
	public final void testSave() {
		doNothing().when(mongoOperations).save(InterviewDetails.class);
		interviewRepository.save(getInterviewDetails());
		
	}
	@Test
	public final void interviewCheck()
	{
		when(mongoOperations.find(any(Query.class), eq(InterviewDetails.class))).thenReturn(Arrays.asList(getInterviewDetails()));
		List<InterviewDetails> interviewDetails = interviewRepository.interviewCheck("nbolla@nisum.com");
		Assert.assertNotNull(interviewDetails);
		Assert.assertSame(null, "nbolla@nisum.com", interviewDetails.get(0).getCandidateEmail());
		
	}
	public InterviewDetails getInterviewDetails()
	{
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("nbolla@nisum.com");
		interviewDetails.setCandidateName("Naga");
		interviewDetails.setClientName("Nisum");
		return interviewDetails;
	}

}
