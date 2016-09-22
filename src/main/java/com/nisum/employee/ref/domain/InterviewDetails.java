package com.nisum.employee.ref.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "InterviewDetails")
public class InterviewDetails  extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
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
	private List<Round> rounds;
	
	@Override
	public String getId() {
		return interviewerId;
	}

}
