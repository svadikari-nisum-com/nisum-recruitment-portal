package com.nisum.employee.ref.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotificationDTO {
	String pk;
	String userId;
	String message;
	String read;
}
