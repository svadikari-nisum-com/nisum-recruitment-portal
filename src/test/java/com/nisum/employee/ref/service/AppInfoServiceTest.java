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
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.converter.InfoEntityConverter;
import com.nisum.employee.ref.domain.InfoEntity;
import com.nisum.employee.ref.repository.InfoRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.InfoEntityDTO;

@RunWith(MockitoJUnitRunner.class)
public class AppInfoServiceTest {

	@InjectMocks
	private AppInfoService appInfoService;

	@Mock
	private InfoRepository infoRepository;

	private List<InfoEntity> infoEntities;
	private InfoEntity infoEntity;
	
	@Spy
	private InfoEntityConverter infoEntityConverter = new InfoEntityConverter();

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
	public void retrieveSkills() {
		when(infoRepository.retrieveSkills()).thenReturn(infoEntities);
		List<InfoEntityDTO> infoEntityList = appInfoService.retrieveSkills();
		assertNotNull(infoEntityList);
		assertEquals(infoEntities.get(0).getKey(), infoEntityList.get(0).getKey());
	}

	@Test
	public void prepareInfo() {
		doNothing().when(infoRepository).prepareInfo(infoEntity);
		appInfoService.prepareInfo(infoEntityConverter.convertToDTO(getInfoEntity()));
	}

	@Test
	public void updateInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateInfo(infoEntityConverter.convertToDTO(getInfoEntity()));
	}

	@Test
	public void updateDesigInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateDesigInfo(infoEntityConverter.convertToDTO(getInfoEntity()));
	}

	@Test
	public void updateInterviewRoundsInfo() {
		doNothing().when(infoRepository).updateInfo(infoEntity);
		appInfoService.updateInterviewRoundsInfo(infoEntityConverter.convertToDTO(getInfoEntity()));
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
