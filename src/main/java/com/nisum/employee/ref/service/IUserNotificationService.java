package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.view.UserNotificationDTO;

public interface IUserNotificationService {
	public List<UserNotificationDTO> getUserNotifications(String userId);
	
	public List<UserNotificationDTO> getUserNoNotifications(String userId);
	
	public void readNotification(String userId, String message);
	
	public void createNotification(UserNotification userNotification);
	
	public long getUserNotificationCount(String userId);

}
