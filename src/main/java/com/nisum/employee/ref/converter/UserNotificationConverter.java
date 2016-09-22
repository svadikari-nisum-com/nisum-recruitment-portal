package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.view.UserNotificationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserNotificationConverter extends TwowayConverter<UserNotification, UserNotificationDTO>{
	
	@Override
	public UserNotificationDTO convertToDTO(UserNotification userNotification) {
		UserNotificationDTO userNotificationDTO = new UserNotificationDTO();
		try {
			BeanUtils.copyProperties(userNotificationDTO, userNotification);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return userNotificationDTO;
		}
		return userNotificationDTO;
	}

	@Override
	public UserNotification convertToEntity(UserNotificationDTO userNotificationDTO) {
		UserNotification userNotification = new UserNotification();
		try {
			BeanUtils.copyProperties(userNotification, userNotificationDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return userNotification;
		}
		return userNotification;
	}

	public List<UserNotificationDTO> convertToDTOs(List<UserNotification> userNotifications) {
		List<UserNotificationDTO> dtos = new ArrayList<>();
		userNotifications.stream()
				.forEach(userNotification -> dtos.add(convertToDTO(userNotification)));
		return dtos;
	}

}
