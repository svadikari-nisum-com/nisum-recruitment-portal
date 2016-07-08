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

import com.nisum.employee.ref.domain.Designation;
import com.nisum.employee.ref.service.IDesignationService;
import com.nisum.employee.ref.util.Constants;

@Component
@Controller
public class DesignationController {

	@Autowired
	private IDesignationService designationService;

	@RequestMapping(value = "/design", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveDesignation() {

		List<Designation> designation = designationService.retrieveDesignations();
		return (null == designation)
				? new ResponseEntity<String>(Constants.designationNotFound, HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Designation>>(designation, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/design", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveDesignation(@RequestBody Designation designation) throws Exception {
		designationService.prepareDesignation(designation);
		return new ResponseEntity<String>( "{\"msg\":\"Created\"}" , HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/design", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateDesignation(@RequestBody Designation designation) throws Exception {
		designationService.updateDesignation(designation);
		return new ResponseEntity<String>( "{\"msg\":\"Updated\"}" , HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/design/{designation}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteDesignation(@PathVariable("designation") String designation) throws Exception {
		designationService.deleteDesignation(designation);
		return new ResponseEntity<String>("{\"msg\":\"Deleted\"}", HttpStatus.OK);
	}

}
