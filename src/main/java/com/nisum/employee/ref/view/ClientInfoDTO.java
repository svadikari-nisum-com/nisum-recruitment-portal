package com.nisum.employee.ref.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientInfoDTO {
	String clientId;
	String clientName;
	String locations;
	@JsonProperty("interviewers")
	InterviewerDTO interviewerDTO;
}
