package com.nisum.employee.ref.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo {
	@Id
	String clientId;
	String clientName;
	String locations;
	Interviewer interviewer;
}
