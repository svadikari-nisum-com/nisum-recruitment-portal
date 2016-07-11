package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class InterviewDetailsDTO {
	
	private String interviewerId;
	private String candidateName;
	private List<String> positionId;
	private String currentPositionId;
	private List<String> candidateSkills;
	private String candidateEmail;
	private String interviewerEmail;
	private String clientName;
	private String progress;
	private String designation;
	private String hrAssigned;
	private List <RoundDTO> rounds;
}
