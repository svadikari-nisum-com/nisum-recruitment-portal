package com.nisum.employee.ref.view;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewerDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<RoundUserDTO> technicalRound1;
	List<RoundUserDTO> technicalRound2;
	List<RoundUserDTO> hrRound;
	List<RoundUserDTO> managerRound;
}
