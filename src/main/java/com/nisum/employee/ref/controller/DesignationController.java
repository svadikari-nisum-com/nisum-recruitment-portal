package com.nisum.employee.ref.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.IDesignationService;
import com.nisum.employee.ref.view.DesignationDTO;

@Component
@Controller
@RequestMapping(value = "/designations")
public class DesignationController {

	@Autowired
	private IDesignationService designationService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DesignationDTO>> retrieveDesignation() {

		List<DesignationDTO> designation = designationService.retrieveDesignations();
		return  new ResponseEntity<List<DesignationDTO>>(designation, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveDesignation(@RequestBody DesignationDTO designation) throws ServiceException {
		designationService.prepareDesignation(designation);
		return new ResponseEntity<String>( "{\"msg\":\"Created\"}" , HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping( method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateDesignation(@RequestBody DesignationDTO designation) throws ServiceException {
		designationService.updateDesignation(designation);
		return new ResponseEntity<String>( "{\"msg\":\"Updated\"}" , HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteDesignation(@PathVariable("id") String designation) throws ServiceException {	
		designationService.deleteDesignation(designation);
		return new ResponseEntity<String>("{\"msg\":\"Deleted\"}", HttpStatus.OK);
	}

}
