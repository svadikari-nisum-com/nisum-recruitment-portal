package com.nisum.employee.ref.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotificationDTO {
	private String pk;
	private String userId;
	private String message;
	private String read;
}
