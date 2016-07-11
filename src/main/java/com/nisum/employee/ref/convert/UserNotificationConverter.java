package com.nisum.employee.ref.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import com.nisum.employee.ref.domain.UserNotification;

import com.nisum.employee.ref.view.UserNotificationDTO;

@Slf4j
@Component
public class UserNotificationConverter extends TwowayConverter<UserNotification, UserNotificationDTO>{
	@SuppressWarnings("finally")
	@Override
	public UserNotificationDTO convertToDTO(UserNotification userNotification) {
		UserNotificationDTO userNotificationDTO = new UserNotificationDTO();
		try {
			BeanUtils.copyProperties(userNotificationDTO, userNotification);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return userNotificationDTO;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public UserNotification convertToEntity(UserNotificationDTO userNotificationDTO) {
		UserNotification userNotification = new UserNotification();
		try {
			BeanUtils.copyProperties(userNotification, userNotificationDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
		} finally {
			return userNotification;
		}
	}

	public List<UserNotificationDTO> convertToDTOs(List<UserNotification> userNotifications) {
		List<UserNotificationDTO> dtos = new ArrayList<>();
		userNotifications.stream()
				.forEach(userNotification -> dtos.add(convertToDTO(userNotification)));
		return dtos;
	}

}
