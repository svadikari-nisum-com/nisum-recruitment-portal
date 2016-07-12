package com.nisum.employee.ref.repository;

import static org.mockito.Mockito.doNothing;

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
public class InformationRepositoryTest {
	
	@Mock
	public  MongoOperations mongoOperations;

	@InjectMocks
	public InformationRepository informationRepository;
	
	@Before
	public  void init() throws Exception {
	}

	@Test
	public final void testSave() 
	{
		doNothing().when(mongoOperations).save(InfoEntity.class);
		informationRepository.save(getInfoEntity());
		
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
