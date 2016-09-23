package com.nisum.employee.ref.domain;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewSchedule extends AuditEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roundName;
	String interviewerName;
	String jobcode;
	String interviewerMobileNumber;
	String skypeId;
	String interviewDateTime;
	String typeOfInterview;
	String interviewLocation;
	String emailIdInterviewer;
	String additionalNotes;
	String candidateId;
	String candidateName;
	ArrayList<String> candidateSkills;
	@Override
	public String getId() {
		return candidateId;
	}
	
}
