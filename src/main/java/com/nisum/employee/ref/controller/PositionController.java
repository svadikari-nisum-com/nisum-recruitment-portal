package com.nisum.employee.ref.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.service.PositionService;
import com.nisum.employee.ref.view.PositionDTO;


@Slf4j
@Controller
public class PositionController {

	@Autowired
	private PositionService  positionService;
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN"})
	@RequestMapping(value="/position", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Position> createPosition(@RequestBody Position position) {
		log.info("creating new position");
		positionService.preparePosition(position);
		return new ResponseEntity<Position>(position, HttpStatus.OK);
	}

	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value="/position", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updatePosition(@RequestBody Position position) {
		positionService.updatePosition(position);
		String jsonObj="{\"msg\":\"position successfully Updated\"}";
		return new ResponseEntity<String>(jsonObj, HttpStatus.ACCEPTED);
	}
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/position", method = RequestMethod.GET)
	public ResponseEntity<List<PositionDTO>> retrievePositionByClient(@RequestParam(value = "client", required = false) String client,
			@RequestParam(value = "designation", required = false) String designation) {
		List<PositionDTO> positionsDetails;
		if(!StringUtils.isEmpty(designation)) {
			positionsDetails = positionService.retrievePositionsbasedOnDesignation(designation);
		} else {
			positionsDetails = (!StringUtils.isEmpty(client)) ? positionService.retrievePositionByClient(client) : positionService.retrieveAllPositions();
		}
		return (null == positionsDetails) ? new ResponseEntity<List<PositionDTO>>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<PositionDTO>>(positionsDetails, HttpStatus.OK);
	}
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/searchPositionsBasedOnJobCode", method = RequestMethod.GET)
	public ResponseEntity<PositionDTO> retrievePositionsBasedOnJobCode(@RequestParam(value = "jobcode", required = true) String jobcode) {
		PositionDTO positionsDetail = positionService.retrievePositionsbasedOnJobCode(jobcode);
		return (null == positionsDetail) ? new ResponseEntity<PositionDTO>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<PositionDTO>(positionsDetail, HttpStatus.OK);
	} 
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/searchPositionBasedOnLocation", method = RequestMethod.GET)
	public ResponseEntity<List<PositionDTO>> retrievesearchPositionbasedOnLocation(@RequestParam(value = "location", required = true) String location,@RequestParam(value = "expYear", required = false) String expYear,@RequestParam(value = "primarySkills", required = false) String primarySkills) {
		List<PositionDTO> positionsDetail = positionService.retrievePositionbasedOnLocation(location);
		return (null == positionsDetail) ? new ResponseEntity<List<PositionDTO>>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<PositionDTO>>(positionsDetail, HttpStatus.OK);
	} 
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/getPositionsByAggregation", method = RequestMethod.GET)
	public ResponseEntity<List<PositionAggregate>> retrieveAllPositionsAggregate() {
		List<PositionAggregate> positionsDetail = positionService.retrieveAllPositionsAggregate();
		return (null == positionsDetail) ? new ResponseEntity<List<PositionAggregate>>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<PositionAggregate>>(positionsDetail, HttpStatus.OK);
	} 
}
