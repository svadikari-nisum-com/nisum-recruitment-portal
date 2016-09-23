package com.nisum.employee.ref.view;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8000086484512172781L;
	private String jobcode;
	private String designation;
	private String minExpYear;
	private String maxExpYear;
	private List<String> primarySkills;
	private List<String> interviewRounds;
	private String secondarySkills;
	private String jobProfile;
	private String location;
	private String client;
	private String hiringManager;
	private String priority;
	private Integer noOfPositions;
	private String interviewer;
	private String jobType;
	private String salary;
	private String functionalGroup;
	private String jobHeader;
	private String locationHead;	
	private String status;
	private Date positionApprovedDt;
}
