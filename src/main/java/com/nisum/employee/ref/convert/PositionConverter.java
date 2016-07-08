/**
 * 
 */
package com.nisum.employee.ref.convert;

import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.view.PositionDTO;

/**
 * @author NISUM CONSULTING
 *
 */

@Slf4j
@Component
public class PositionConverter implements Converter<Position, PositionDTO> {
	@SuppressWarnings("finally")
	@Override
	public PositionDTO convert(Position position) {
		PositionDTO positionDTO = new PositionDTO();
		try {
			BeanUtils.copyProperties(positionDTO, position);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return positionDTO;
		}
	}
}
