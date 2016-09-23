package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.UserNotificationConverter;
import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.repository.UserNotificationRepository;
import com.nisum.employee.ref.view.UserNotificationDTO;


@Service
public class UserNotificationService implements IUserNotificationService{
	
	@Autowired
	UserNotificationRepository userNotificationRepository;
	
	@Autowired
	UserNotificationConverter userNotificationConverter;
	
	@Override
	public List<UserNotificationDTO> getUserNotifications(String userId){
		return userNotificationConverter.convertToDTOs(userNotificationRepository.retrieveNotifications(userId));
	}
	
	@Override
	public List<UserNotificationDTO> getUserNoNotifications(String userId){
		return userNotificationConverter.convertToDTOs((userNotificationRepository.retrieveNoNotifications(userId)));
	}
	
	@Override
	public void readNotification(String userId, String message){
		userNotificationRepository.readNotifications(userId, message);
	}
	
	@Override
	public void createNotification(UserNotification userNotification){
		userNotificationRepository.createNotifications(userNotification);
	}
	
	@Override
	public long getUserNotificationCount(String userId){
		return userNotificationRepository.getUserNotificationCount(userId);
	}
}
