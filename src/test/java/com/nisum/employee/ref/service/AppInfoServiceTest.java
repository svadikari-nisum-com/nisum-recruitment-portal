package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.InfoEntity;
import com.nisum.employee.ref.repository.InfoRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;

@RunWith(MockitoJUnitRunner.class)
public class AppInfoServiceTest {

	@InjectMocks
	private AppInfoService appInfoService;

	@Mock
	private InfoRepository infoRepository;

	private List<InfoEntity> infoEntities;
	private InfoEntity infoEntity;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(infoRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		infoEntities = new ArrayList<>();
		infoEntity = new InfoEntity();
		infoEntity.setKey("java");

		List<String> values = new ArrayList<>();
		values.add("value");
		infoEntity.setValue(values);
		
		infoEntities.add(infoEntity);
	}

	@Test
	public void testRetrieveSkills() {
		when(infoRepository.retrieveSkills()).thenReturn(infoEntities);
		List<InfoEntity> infoEntityList = appInfoService.retrieveSkills();
		assertNotNull(infoEntityList);
		assertEquals(infoEntities.get(0).getKey(), infoEntityList.get(0).getKey());
	}

	@Test
	public void testPrepareInfo() {
		doNothing().when(infoRepository).prepareInfo(infoEntity);
		appInfoService.prepareInfo(getInfoEntity());
	}

	

	@Test
	public void testUpdateInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateInfo(getInfoEntity());
	}

	@Test
	public void testUpdateDesigInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateDesigInfo(getInfoEntity());
	}

	@Test
	public void testUpdateInterviewRoundsInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateInterviewRoundsInfo(getInfoEntity());
	}
	
	private InfoEntity getInfoEntity() {
		InfoEntity infoEntity = new InfoEntity();
		infoEntity.setKey("java");

		List<String> values = new ArrayList<>();
		values.add("value");
		infoEntity.setValue(values);
		return infoEntity;
	}

}
