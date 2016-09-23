package com.nisum.employee.ref.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRoundsAllocation {

	private String department;
	
	private List<String> interviewRounds;

}
