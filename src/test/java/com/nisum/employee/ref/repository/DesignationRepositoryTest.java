package com.nisum.employee.ref.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import com.nisum.employee.ref.domain.Designation;

@RunWith(MockitoJUnitRunner.class)
public class DesignationRepositoryTest 
{

	@Mock
	public MongoOperations mongoOperations;
	
	@InjectMocks
	public DesignationRepository designationRepository;
	
	
	@Before
	public void runBeforeEveryTest(){
	}
	

/*	@SuppressWarnings("serial")
	@Test
	public final void testRetrieveDesignations() 
	{
		when(mongoOperations.findAll(Designation.class)).thenReturn(
				new ArrayList<Designation>()
				{{add(getDesignations());}});
		ArrayList<Designation> designations = designationRepository.retrieveDesignations();
		Assert.assertNotNull(designations);
		System.out.println("RetrieveDesignations::"+designations.get(0).getDesignation());
		
	}*/

	@Test
	public final void testPrepareDesignations() 
	{
		doNothing().when(mongoOperations).save(Designation.class);
		designationRepository.prepareDesignations(getDesignations());
	}

	@Test
	public final void testUpdateDesignations() 
	{
		when(mongoOperations.findOne(any(Query.class), eq(Designation.class))).thenReturn(getDesignations());
		doAnswer(new Answer<WriteResult>() {
		    public WriteResult answer(final InvocationOnMock invocation) throws Throwable {
	            return null ;
	        }
		}).when(mongoOperations).updateFirst(any(Query.class), any(Update.class), eq(Designation.class));
		
		designationRepository.updateDesignations(getDesignations());
	}

	@Test
	public final void testRemoveDesignations() 
	{
		when(mongoOperations.findOne(any(Query.class), eq(Designation.class))).thenReturn(getDesignations());
		when(mongoOperations.remove(Designation.class)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "Hi Remove";
            } });
		List<String> designations = Arrays.asList(
				"Test1",
				"Soft",
				"Soft*"
				);
		for(String designation : designations)
		{
			designationRepository.removeDesignations(designation);
			
		}
	}
	
	public Designation getDesignations()
	{
		Designation designation = new Designation();
		
		designation.setDesignation("Mocked Designaton Software Engineer");
		designation.setMaxExpYear("4");
		designation.setMinExpYear("2");
		designation.setSkills(Arrays.asList("Java","Spring","Hibernate"));
		
		return designation;
		
		
	}

}
