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

	@SuppressWarnings("finally")
	@Override
	public UserInfoDTO convertToDTO(UserInfo userInfo) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		try {
			BeanUtils.copyProperties(userInfoDTO, userInfo);
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
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return userInfoDTO;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public UserInfo convertToEntity(UserInfoDTO userInfoDTO) {
		UserInfo userInfo = new UserInfo();
		try {
			BeanUtils.copyProperties(userInfo, userInfoDTO);
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
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return userInfo;
		}
	}

	public List<UserInfoDTO> convertToDTOs(List<UserInfo> userInfos) {
		List<UserInfoDTO> dtos = new ArrayList<>();
		userInfos.stream()
				.forEach(userInfo -> dtos.add(convertToDTO(userInfo)));
		return dtos;
	}
}
