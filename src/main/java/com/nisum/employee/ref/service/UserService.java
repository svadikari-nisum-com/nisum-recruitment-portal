package com.nisum.employee.ref.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.UserInfoConverter;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.InterviewDetailsRepository;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.util.InterviewerRoundInfoComparator;
import com.nisum.employee.ref.view.InterviewRoundsDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserInfoConverter userInfoConverter;
	
	@Autowired
	private InterviewDetailsRepository interviewDetailsRepository;

	@Override
	public void registerUserByEmailId(String emailId) {
		userInfoRepository.registerUserByEmailId(emailId);
	}
	@Override
	public List<UserInfoDTO> retrieveUser() {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUser());
	}
	@Override
	public List<UserInfoDTO> retrieveUserById(String userId) {
		
		List<UserInfo> userInfos = userInfoRepository.retrieveUserById(userId);
		return userInfoConverter.convertToDTOs(userInfos);
	}
	@Override
	public List<UserInfoDTO> retrieveUserByName(String name) {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByName(name));
	}
	@Override
	public UserInfoDTO createUserInfo(String userName) {
		return userInfoConverter.convertToDTO(userInfoRepository.createUserInfo(userName));
	}
	@Override
	public void updateUser(UserInfoDTO userDTO) {
		userInfoRepository.updateUser(userInfoConverter.convertToEntity(userDTO));
	}
	@Override
	public void deleteUser(String emailId) {
		userInfoRepository.deleteUser(emailId);
	}
	@Override
	public List<UserInfoDTO> retrieveUserByClient(String clientName) {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByClient(clientName));
	}
	
	@Override
	public List<UserInfoDTO> retrieveUserByRole(String role) {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByRole(role));
	}
	@Override
	public List<UserInfoDTO> retrieveUserByRole(String round,String department) throws ServiceException {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByRole(round,department));
	}
	
	@Override
	public List<InterviewRoundsDTO> getInterviewers(String round,String functionalGroup,String role) throws ServiceException {
		List<InterviewDetails> interviewDetails = null;
		List<InterviewRoundsDTO> interviewerRoundsInfo = new ArrayList<InterviewRoundsDTO>();
		InterviewRoundsDTO interviewRoundsDTO = null;
		if("ROLE_HR, ROLE_MANAGER".contains(role)) {
			round=functionalGroup=StringUtils.EMPTY;
		}
		List<UserInfo> interviewers = userInfoRepository.getUserInfo(round,functionalGroup,role);
		
		for(UserInfo interviewer : interviewers){
			interviewRoundsDTO = new InterviewRoundsDTO();
			interviewRoundsDTO.setEmailId(interviewer.getEmailId());
			interviewRoundsDTO.setName(interviewer.getName());
			interviewRoundsDTO.setTimeSlots(interviewer.getTimeSlots());
			interviewDetails = interviewDetailsRepository.getInterviewByInterviewer(interviewer.getEmailId());
			interviewRoundsDTO.setNoOfRoundsScheduled(interviewDetails.size());
			interviewerRoundsInfo.add(interviewRoundsDTO);
		}
		
		Collections.sort(interviewerRoundsInfo,new InterviewerRoundInfoComparator());
		
		return interviewerRoundsInfo;
	}
	
	@Override
	public List<UserInfoDTO> retrieveUserByRoleAndLocation(String role,String location) throws ServiceException {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByRoleAndLocation(role,location));
	}
}
