package com.nisum.employee.ref.domain;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "Profile")
public class Profile extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1101137648677221577L;
	String candidateName ;
	@Id
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
	
	@Override
	public String getId() {
		return emailId;
	}
}
