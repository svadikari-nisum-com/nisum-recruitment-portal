package com.nisum.employee.ref.repository;

import static org.mockito.Mockito.doNothing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;

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
	public InterviewDetails getInterviewDetails()
	{
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("nbolla@nisum.com");
		interviewDetails.setCandidateName("Naga");
		interviewDetails.setClientName("Nisum");
		return interviewDetails;
	}

}
