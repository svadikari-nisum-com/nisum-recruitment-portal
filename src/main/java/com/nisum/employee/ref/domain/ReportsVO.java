package com.nisum.employee.ref.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Profile> profiles;
	private List<InterviewDetails> interviewDetails;
	
	public ReportsVO(){}

}