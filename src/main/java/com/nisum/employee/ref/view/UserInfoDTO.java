package com.nisum.employee.ref.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO extends BaseDTO{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4222813386609824870L;
	
	String emailId;
	String name;
	String dob;
	String doj;
	String clientName;
	String projectName;
	String location;
	List<String> roles;
	ArrayList<String> skills;
	String mobileNumber;
	String skypeId;
	List<String> categories;
	List<TimeSlotDTO> timeSlots;
	Boolean isNotAvailable = false;
}
