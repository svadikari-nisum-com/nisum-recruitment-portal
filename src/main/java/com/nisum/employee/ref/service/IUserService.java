package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.view.UserInfoDTO;

public interface IUserService {
	void registerUserByEmailId(String emailId);

	List<UserInfoDTO> retrieveUser();

	List<UserInfoDTO> retrieveUserById(String userId);

	List<UserInfoDTO> retrieveUserByName(String name);

	UserInfoDTO createUserInfo(String userName);

	void updateUser(UserInfoDTO userdTO);

	List<UserInfoDTO> retrieveUserByClient(String clientName);
	
	List<UserInfoDTO> retrieveUserByRole(String role);
	
	List<UserInfoDTO> retrieveUserByRole(String role,String department);
}
