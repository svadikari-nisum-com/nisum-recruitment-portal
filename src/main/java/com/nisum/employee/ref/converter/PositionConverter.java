/**
 * 
 */
package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.view.PositionDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NISUM CONSULTING
 *
 */

@Slf4j
@Component
public class PositionConverter extends TwowayConverter<Position, PositionDTO> {

	@Override
	public PositionDTO convertToDTO(Position Position) {
		PositionDTO positionDTO = new PositionDTO();
		try {
			BeanUtils.copyProperties(positionDTO, Position);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return positionDTO;
		}
		return positionDTO;
	}

	@Override
	public Position convertToEntity(PositionDTO positionDTO) {
		Position position = new Position();
		try {
			BeanUtils.copyProperties(position, positionDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return position;
		}
		return position;
	}

	public List<PositionDTO> convertToDTOs(List<Position> positions) {
		List<PositionDTO> dtos = new ArrayList<>();
		positions.stream()
				.forEach(position -> dtos.add(convertToDTO(position)));
		return dtos;
	}

}
