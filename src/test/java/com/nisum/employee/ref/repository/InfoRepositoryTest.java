package com.nisum.employee.ref.repository;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;

import com.nisum.employee.ref.domain.InfoEntity;

@RunWith(MockitoJUnitRunner.class)
public class InfoRepositoryTest {
	
	@Mock
	public MongoOperations  mongoOperations;
	
	@InjectMocks
	public InfoRepository infoRepository;

	@Before
	public void init() throws Exception {
	}
	
	@SuppressWarnings("serial")
	@Test
	public final void testRetrieveSkills() 
	{

		when(mongoOperations.findAll(InfoEntity.class)).thenReturn(
				new ArrayList<InfoEntity>()
				{{add(getInfoEntity());}});
		infoRepository.retrieveSkills();
		
	}

	@Test
	public final void testPrepareInfo() 
	{
		doNothing().when(mongoOperations).save(InfoEntity.class);
		infoRepository.prepareInfo(getInfoEntity());
	}

	@Test
	public final void testUpdateInfo() 
	{
		doNothing().when(mongoOperations).save(InfoEntity.class);
		infoRepository.updateInfo(getInfoEntity());
	}
	
	public InfoEntity getInfoEntity()
	{
		InfoEntity infoEntity = new InfoEntity();
		infoEntity.setKey("Nisum");
		
		@SuppressWarnings("serial")
		List<String> skills = new ArrayList<String>(){{ add("Java");add("Spring"); }};
		infoEntity.setValue(skills);
		return infoEntity;
	}

}
