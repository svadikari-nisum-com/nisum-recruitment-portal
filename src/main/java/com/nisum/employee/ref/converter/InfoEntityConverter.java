package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.InfoEntity;
import com.nisum.employee.ref.view.InfoEntityDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InfoEntityConverter extends TwowayConverter<InfoEntity, InfoEntityDTO> {

	@Override
	public InfoEntityDTO convertToDTO(InfoEntity infoEntity) {
		InfoEntityDTO infoEntityDTO = new InfoEntityDTO();
		try {
			BeanUtils.copyProperties(infoEntityDTO, infoEntity);
			infoEntityDTO.setValue(infoEntity.getValue());
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return infoEntityDTO;
		} 
		return infoEntityDTO;
	}

	@Override
	public InfoEntity convertToEntity(InfoEntityDTO infoEntityDTO) {
		InfoEntity infoEntity = new InfoEntity();
		try {
			BeanUtils.copyProperties(infoEntity, infoEntityDTO);
			infoEntity.setValue(infoEntityDTO.getValue());
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return infoEntity;
		}
		return infoEntity;
	}

	public List<InfoEntityDTO> convertToDTOs(List<InfoEntity> infoEntities) {
		List<InfoEntityDTO> infoEntityDTOs = new ArrayList<>();
		infoEntities.stream()
			.forEach(infoEntity -> infoEntityDTOs.add(convertToDTO(infoEntity)));
		return infoEntityDTOs;
	}

}
