package com.nisum.employee.ref.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.UserInfoConverter;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.view.UserInfoDTO;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserInfoConverter userInfoConverter;

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
		
		
		if(userInfos.size() <= 0)
		{
			//User is not existed. That means new user is logged into the application.
			//If the user is new user then we need to set default Role as "ROLE_USER"
			UserInfo userInfo = new UserInfo();
			userInfo.setRoles(Arrays.asList("ROLE_USER"));
			userInfos.add(userInfo);
		}
		
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
	public List<UserInfoDTO> retrieveUserByClient(String clientName) {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByClient(clientName));
	}
	
	@Override
	public List<UserInfoDTO> retrieveUserByRole(String role) {
		return userInfoConverter.convertToDTOs(userInfoRepository.retrieveUserByRole(role));
	}
}
