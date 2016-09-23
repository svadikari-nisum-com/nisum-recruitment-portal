package com.nisum.employee.ref.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewFeedback implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roundName;
	private String interviewerName;
	private String interviewerEmail;
	private String jobcode;
	private String interviewDateTime;
	private String typeOfInterview;
	private List<Rating> rateSkills;
	private String additionalSkills;
	private String strengths;
	private String improvement;
	private String duration;
	private String candidateId;
	private String candidateName;
	private String status;

}
