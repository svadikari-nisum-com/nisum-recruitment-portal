package com.nisum.employee.ref.domain;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Position")
public class Position extends AuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String jobcode;
	String designation;
	String minExpYear;
	String maxExpYear;
	ArrayList<String> primarySkills;
	ArrayList<String> interviewRounds;
	String secondarySkills;
	String jobProfile;
	String location;
	String client;
	String hiringManager;
	String priority;
	Integer noOfPositions;
	String interviewer;
	String jobType;
	String salary;
	String functionalGroup; 
	String jobHeader;
	String locationHead;
	String status;
	Date positionApprovedDt;
	@Override
	public String getId() {
		return jobcode;
	}

}
