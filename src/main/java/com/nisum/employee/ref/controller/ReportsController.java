package com.nisum.employee.ref.controller;


import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.service.ReportsService;

@RequestMapping("/reports")
@Controller
public class ReportsController {
	
	@Autowired
	private ReportsService reportsService;
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getReportData(@RequestParam(value = "hiringManager", required = false) String hiringManager,
			@RequestParam(value = "recruiter", required = false) String recruiterEmail) throws ParseException {
		
		List<ReportsVO> reportList = reportsService.getReportByHiringManager(hiringManager,recruiterEmail);
		return (null == reportList) ? new ResponseEntity<String>("Reports are not found", HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<ReportsVO>>(reportList, HttpStatus.OK);
	}
}
