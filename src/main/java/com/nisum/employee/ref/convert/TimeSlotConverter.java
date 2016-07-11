/**
 * 
 */
package com.nisum.employee.ref.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.TimeSlots;
import com.nisum.employee.ref.view.TimeSlotDTO;

/**
 * @author NISUM CONSULTING
 *
 */

@Slf4j
@Component
public class TimeSlotConverter extends TwowayConverter<TimeSlots, TimeSlotDTO> {

	@Override
	public TimeSlotDTO convertToDTO(TimeSlots userInfo) {
		TimeSlotDTO userInfoDTO = new TimeSlotDTO();
		try {
			BeanUtils.copyProperties(userInfoDTO, userInfo);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return userInfoDTO;
		}
		return userInfoDTO;
	}

	@Override
	public TimeSlots convertToEntity(TimeSlotDTO userInfoDTO) {
		TimeSlots userInfo = new TimeSlots();
		try {
			BeanUtils.copyProperties(userInfo, userInfoDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return userInfo;
		}
		return userInfo;
	}

	public List<TimeSlotDTO> convertToDTOs(List<TimeSlots> userInfos) {
		List<TimeSlotDTO> dtos = new ArrayList<>();
		userInfos.stream()
				.forEach(userInfo -> dtos.add(convertToDTO(userInfo)));
		return dtos;
	}
}
