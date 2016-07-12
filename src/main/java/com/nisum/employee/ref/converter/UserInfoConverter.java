/**
 * 
 */
package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.TimeSlots;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.view.TimeSlotDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

/**
 * @author NISUM CONSULTING
 *
 */

@Slf4j
@Component
public class UserInfoConverter extends TwowayConverter<UserInfo, UserInfoDTO> {

	@Override
	public UserInfoDTO convertToDTO(UserInfo userInfo) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		try {
			BeanUtils.copyProperties(userInfoDTO, userInfo);
			if (CollectionUtils.isNotEmpty(userInfo.getTimeSlots())) {
				List<TimeSlotDTO> timeSlotDTOs = new ArrayList<>();
				userInfo.getTimeSlots().stream().forEach(timeSlot -> {
					TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
					try {
						BeanUtils.copyProperties(timeSlotDTO, timeSlot);
						timeSlotDTOs.add(timeSlotDTO);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				});
				userInfoDTO.setTimeSlots(timeSlotDTOs);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return userInfoDTO;
		}
		return userInfoDTO;
	}

	@Override
	public UserInfo convertToEntity(UserInfoDTO userInfoDTO) {
		UserInfo userInfo = new UserInfo();
		try {
			BeanUtils.copyProperties(userInfo, userInfoDTO);
			if (CollectionUtils.isNotEmpty(userInfoDTO.getTimeSlots())) {
				List<TimeSlots> timeSlots = new ArrayList<>();
				userInfoDTO.getTimeSlots().stream().forEach(timeSlotDTO -> {
					TimeSlots timeSlot = new TimeSlots();
					try {
						BeanUtils.copyProperties(timeSlot, timeSlotDTO);
						timeSlots.add(timeSlot);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				});
				userInfo.setTimeSlots(timeSlots);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return userInfo;
		}
		return userInfo;
	}

	public List<UserInfoDTO> convertToDTOs(List<UserInfo> userInfos) {
		List<UserInfoDTO> dtos = new ArrayList<>();
		userInfos.stream()
				.forEach(userInfo -> dtos.add(convertToDTO(userInfo)));
		return dtos;
	}
}
