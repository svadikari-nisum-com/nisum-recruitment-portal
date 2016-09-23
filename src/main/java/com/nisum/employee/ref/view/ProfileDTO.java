package com.nisum.employee.ref.view;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter

public class ProfileDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String candidateName ;
	String emailId;
	String qualification;
	ArrayList<String> primarySkills;
	String expYear;
	String expMonth;
	String uploadedFileName;
	String mobileNo;
	String designation;
	String pancardNo;
	String passportNo;
	String stream;
	String address;
	String notes;
	String altmobileNo;
	String currentEmployer;
	String profilecreatedBy;
	String profileTimeStamp;
	String profileModifiedTimeStamp;
	String profileModifiedBy;
	String referredBy;
	String hrAssigned;
	ArrayList<String> jobcodeProfile;
	Boolean interviewSet;
	String skypeId;
	String status;
	String interviewProgress;
	Boolean active;


}
