package com.nisum.employee.ref.view;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8000086484512172781L;
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

}
