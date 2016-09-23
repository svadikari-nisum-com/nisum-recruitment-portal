package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.view.DesignationDTO;;

public interface IDesignationService {
	List<DesignationDTO> retrieveDesignations();
	void prepareDesignation(DesignationDTO designation);
	void updateDesignation(DesignationDTO designation);
	void deleteDesignation(String designation);
}
