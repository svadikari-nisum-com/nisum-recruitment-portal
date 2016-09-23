package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
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

import com.nisum.employee.ref.converter.DesignationConverter;
import com.nisum.employee.ref.domain.Designation;
import com.nisum.employee.ref.repository.DesignationRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.DesignationDTO;

@RunWith(MockitoJUnitRunner.class)
public class DesignationServiceTest {

	@InjectMocks
	private DesignationService designationService;

	@Mock
	private DesignationRepository designationRepository;

	private List<Designation> designations;
	private Designation designation;
	
	@Spy
	private DesignationConverter designationConverter = new DesignationConverter();

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(designationRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		designations = new ArrayList<>();
		designations.add(getDesignation());
	}

	@Test
	public void testRetrieveDesignations() {
		when(designationRepository.retrieveDesignations()).thenReturn(designations);
		List<DesignationDTO> retrieveDesignations = designationService.retrieveDesignations();
		assertNotNull(retrieveDesignations);
		assertEquals(designations.get(0).getDesignation(), retrieveDesignations.get(0).getDesignation());
	}

	@Test
	public void testPrepareDesignation() {
		doNothing().when(designationRepository).prepareDesignations(any(Designation.class));
		designationService.prepareDesignation(designationConverter.convertToDTO(getDesignation()));
	}

	@Test
	public void testUpdateDesignation() {
		doNothing().when(designationRepository).updateDesignations(any(Designation.class));
		designationService.updateDesignation(designationConverter.convertToDTO(getDesignation()));
	}

	@Test
	public void testDeleteDesignation() {
		doNothing().when(designationRepository).removeDesignations(any(String.class));
		designationService.deleteDesignation("Software Engineer");
	}

	private Designation getDesignation() {
		designation = new Designation();
		designation.setDesignation("Software Engineer");
		designation.setMinExpYear("3");
		designation.setMaxExpYear("5");

		List<String> skills = new ArrayList<>();
		skills.add("java");
		skills.add("spring");
		designation.setSkills(skills);
		return designation;
	}

}
