package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundUserDTO {
	String name;
	String emailId;
	List<String> skillSet;
}
