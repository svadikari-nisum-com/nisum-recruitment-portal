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
	String interviewerId;
	String candidateName;
	List<String> positionId;
	String currentPositionId;
	List<String> candidateSkills;
	String candidateEmail;
	String interviewerEmail;
	String clientName;
	String progress;
	String designation;
	String hrAssigned;
	List <Round> rounds;
	
	@Override
	public String getId() {
		return interviewerId;
	}

}
