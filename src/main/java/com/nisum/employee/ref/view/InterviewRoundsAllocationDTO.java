package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InterviewRoundsAllocationDTO extends BaseDTO
{
	
	private static final long serialVersionUID = 1L;
	
	private String department;
	
	private List<String> interviewRounds; 
	

}
