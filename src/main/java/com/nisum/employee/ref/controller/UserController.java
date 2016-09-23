package com.nisum.employee.ref.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.domain.ResponseVO;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.UserService;
import com.nisum.employee.ref.view.InterviewRoundsDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> registerUser(@RequestBody UserInfo userInfo) {
		userService.registerUserByEmailId(userInfo.getEmailId());
		return new ResponseEntity<String>("User registered Successfully",
				HttpStatus.OK);
	}

	@Secured({ "ROLE_USER", "ROLE_HR", "ROLE_RECRUITER", "ROLE_ADMIN",
			"ROLE_MANAGER", "ROLE_INTERVIEWER","ROLE_LOCATIONHEAD" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserInfoDTO>> retrieveUsers(
			@RequestParam(value = "emailId", required = false) String emailId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "clientName", required = false) String clientName,
			@RequestParam(value = "clientRole", required = false) String clientRole,
			@RequestParam(value = "functionalGroup", required = false) String functionalGroup) throws ServiceException{

		List<UserInfoDTO> userInfos = null;
		if (emailId != null) {
			userInfos = userService.retrieveUserById(emailId);
		} else if (name != null) {
			userInfos = userService.retrieveUserByName(name);
		} else if (clientName != null) {
			userInfos = userService.retrieveUserByClient(clientName);
		} else if (clientRole != null && functionalGroup != null) {
			userInfos = userService.retrieveUserByRole(clientRole,
					functionalGroup);

		} else if (clientRole != null) {
			userInfos = userService.retrieveUserByRole(clientRole);
		} else {
			userInfos = userService.retrieveUser();
		}
		return (null == userInfos) ? new ResponseEntity<List<UserInfoDTO>>(
				HttpStatus.NOT_FOUND) : new ResponseEntity<List<UserInfoDTO>>(
				userInfos, HttpStatus.OK);
	}

	@Secured({ "ROLE_USER", "ROLE_HR", "ROLE_RECRUITER", "ROLE_ADMIN",
			"ROLE_MANAGER", "ROLE_INTERVIEWER" })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResponseVO<UserInfoDTO>> updateUser(
			@RequestBody UserInfoDTO user) {
		userService.updateUser(user);
		ResponseVO<UserInfoDTO> response = new ResponseVO<UserInfoDTO>();
		response.setDate(new Date());
		response.setData(user);
		response.setHttpStatus(200);
		response.setMessage("User Updated succesfully.");

		return new ResponseEntity<ResponseVO<UserInfoDTO>>(response,
				HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResponseVO<UserInfoDTO>> deleteUser(
			@RequestParam(value = "userId", required = true) String userId) {	
	
		userService.deleteUser(userId);
		ResponseVO<UserInfoDTO> response = new ResponseVO<UserInfoDTO>();	
		return new ResponseEntity<ResponseVO<UserInfoDTO>>(response,
				HttpStatus.OK);
	}
	
	@Secured({ "ROLE_HR", "ROLE_RECRUITER","ROLE_MANAGER", "ROLE_INTERVIEWER" })
	@RequestMapping(value="/getInterviewers",method = RequestMethod.GET)
	public ResponseEntity<List<InterviewRoundsDTO>> getInterviewers(
		@RequestParam(value = "interviewRound", required = false) String interviewRound,
		@RequestParam(value = "functionalGroup", required = false) String functionalGroup,
		@RequestParam(value = "role", required = false) String role) throws ServiceException{

		List<InterviewRoundsDTO> interviewers = userService.getInterviewers(interviewRound, functionalGroup, role);
		return (null == interviewers) ? new ResponseEntity<List<InterviewRoundsDTO>>(
			HttpStatus.NOT_FOUND) : new ResponseEntity<List<InterviewRoundsDTO>>(interviewers, HttpStatus.OK);
   }
}
