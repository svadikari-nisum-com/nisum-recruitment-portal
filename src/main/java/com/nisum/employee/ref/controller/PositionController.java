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
@RequestMapping("/positions")
public class PositionController {

	@Autowired
	private PositionService  positionService;
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO position) {
		log.info("creating new position");
		positionService.preparePosition(position);
		return new ResponseEntity<PositionDTO>(position, HttpStatus.OK);
	}

	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updatePosition(@RequestBody Position position) {
		positionService.updatePosition(position);
		String jsonObj="{\"msg\":\"position successfully Updated\"}";
		return new ResponseEntity<String>(jsonObj, HttpStatus.ACCEPTED);
	}
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PositionDTO>> retrievePositions(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		List<PositionDTO> positionsDetails;
		    if ( !(StringUtils.isEmpty(searchKey)) || !(StringUtils.isEmpty(searchValue))){
		    	 positionsDetails = positionService.retrieveAllPositions(searchKey,searchValue);
		    } else {
		    	 positionsDetails = positionService.retrieveAllPositions();
		    }
		   return new ResponseEntity<List<PositionDTO>>(positionsDetails, HttpStatus.OK);
	}
	
	@Secured({"ROLE_HR","ROLE_RECRUITER","ROLE_ADMIN","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/getPositionsByAggregation", method = RequestMethod.GET)
	public ResponseEntity<List<PositionAggregate>> retrieveAllPositionsAggregate() {
		List<PositionAggregate> positionsDetail = positionService.retrieveAllPositionsAggregate();
		return (null == positionsDetail) ? new ResponseEntity<List<PositionAggregate>>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<PositionAggregate>>(positionsDetail, HttpStatus.OK);
	} 
}
