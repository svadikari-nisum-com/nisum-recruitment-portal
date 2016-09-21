package com.nisum.employee.ref.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "UserInfo")
public class UserInfo { 
	@Id
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
	List<InterviewRoundsAllocation> interviewRoundsAllocation;
	List<TimeSlots> timeSlots;
	Boolean isNotAvailable = false;
	Boolean active;
}
