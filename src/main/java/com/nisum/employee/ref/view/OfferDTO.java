package com.nisum.employee.ref.view;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailId;
	private String candidateName;
	private String client;
	private String project;
	private String hrManager;
	private String reportingManager;
	private String imigrationStatus;
	private String relocationAllowance;
	private String singInBonus;
	private String comments;
	private Date joiningDate;
	private String jobcodeProfile;
	private String location;
	private String offerLetterName;
	private String status;
	private String mobileNo;
	private String ctc;
	private String designation;


}
