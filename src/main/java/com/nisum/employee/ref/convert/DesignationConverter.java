package com.nisum.employee.ref.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.Designation;
import com.nisum.employee.ref.view.DesignationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DesignationConverter extends TwowayConverter<Designation, DesignationDTO> {

	@SuppressWarnings("finally")
	@Override
	public DesignationDTO convertToDTO(Designation designation) {
		DesignationDTO designationDTO = new DesignationDTO();
		try {
			BeanUtils.copyProperties(designationDTO, designation);
			designationDTO.setSkills(designation.getSkills());
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		}finally {
			return designationDTO;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Designation convertToEntity(DesignationDTO designationDTO) {
		Designation designation = new Designation();
		try {
			BeanUtils.copyProperties(designation, designationDTO);
			designation.setSkills(designationDTO.getSkills());
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return designation;
		}
	}

	public List<DesignationDTO> convertToDTOs(List<Designation> designations) {
		List<DesignationDTO> designationDTOs = new ArrayList<>();
				
		designations.stream()
			.forEach(designationDTO -> designationDTOs.add(convertToDTO(designationDTO)));
		return designationDTOs;
	}

}
