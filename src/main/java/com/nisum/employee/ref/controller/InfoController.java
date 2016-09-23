package com.nisum.employee.ref.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.IAppInfoService;
import com.nisum.employee.ref.view.InfoEntityDTO;


@Controller
@RequestMapping(value="/info")
public class InfoController {

	@Autowired
	private IAppInfoService infoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<InfoEntityDTO>> retrieveInfo() {
		List<InfoEntityDTO> info = infoService.retrieveSkills();
        return new ResponseEntity <List<InfoEntityDTO>>(info, HttpStatus.OK);
	}
	

	@Secured({"ROLE_ADMIN","ROLE_HR"})
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void updateInfo(@RequestBody InfoEntityDTO info) {
		infoService.updateInfo(info);
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_HR"})
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteInfo(@RequestBody InfoEntityDTO info) throws ServiceException {
		infoService.updateDesigInfo(info);
	}
}
