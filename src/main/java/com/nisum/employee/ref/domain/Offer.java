package com.nisum.employee.ref.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data()
@Document(collection = "offer")
public class Offer extends AuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
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
	@Override
	public String getId() {
		return emailId;
	}
}
