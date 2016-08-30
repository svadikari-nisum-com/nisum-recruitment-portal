package com.nisum.employee.ref.view;

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
	
	private String emailId;
	private String name;
	private String dob;
	private String doj;
	private String clientName;
	private String projectName;
	private String location;
	private List<String> roles;
	private List<String> skills;
	private String mobileNumber;
	private String skypeId;
	private List<String> categories;
	private List<InterviewRoundsAllocationDTO> interviewRoundsAllocation;
	private List<TimeSlotDTO> timeSlots;
	private Boolean isNotAvailable = false;
	private Boolean active;
}
