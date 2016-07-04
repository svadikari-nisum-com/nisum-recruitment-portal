package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.domain.Designation;;

public interface IDesignationService {
	List<Designation> retrieveDesignations();
	void prepareDesignation(Designation designation);
	void updateDesignation(Designation designation);
	void deleteDesignation(String designation);
}
