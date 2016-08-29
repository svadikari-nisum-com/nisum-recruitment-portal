package com.nisum.employee.ref.domain;
import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;


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
	
	@Override
	public String getId() {
		return emailId;
	}
}
