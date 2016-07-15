package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewerDTO {
	List<RoundUserDTO> technicalRound1;
	List<RoundUserDTO> technicalRound2;
	List<RoundUserDTO> hrRound;
	List<RoundUserDTO> managerRound;
}
