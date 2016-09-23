package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.DesignationConverter;
import com.nisum.employee.ref.repository.DesignationRepository;
import com.nisum.employee.ref.view.DesignationDTO;;

@Service
public class DesignationService implements IDesignationService{
	
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	private DesignationConverter designationConverter;

	@Override
	public List<DesignationDTO> retrieveDesignations() {
		return designationConverter.convertToDTOs(designationRepository.retrieveDesignations());
	}

	@Override
	public void prepareDesignation(DesignationDTO designation) {
			designationRepository.prepareDesignations(designationConverter.convertToEntity(designation));
	}

	@Override
	public void updateDesignation(DesignationDTO designation) {
		designationRepository.updateDesignations(designationConverter.convertToEntity(designation));
	}

	@Override
	public void deleteDesignation(String designation) {
		designationRepository.removeDesignations(designation);
		
	}	
}
