package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignationDTO {
	String designation;
	List<String> skills;
	String minExpYear;
	String maxExpYear;
}
