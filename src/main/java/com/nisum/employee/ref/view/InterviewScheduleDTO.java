package com.nisum.employee.ref.view;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InterviewScheduleDTO {
	
	private String roundName;
	private String interviewerName;
	private String jobcode;
	private String interviewerMobileNumber;
	private String skypeId;
	private String interviewDateTime;
	private String typeOfInterview;
	private String interviewLocation;
	private String emailIdInterviewer;
	private String additionalNotes;
	private String candidateId;
	private String candidateName;
	private ArrayList<String> candidateSkills;

}
