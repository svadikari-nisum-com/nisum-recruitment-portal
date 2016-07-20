package com.nisum.employee.ref.security.authorization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.nisum.employee.ref.exception.AuthorizationException;
import com.nisum.employee.ref.service.UserService;
import com.nisum.employee.ref.view.UserInfoDTO;

@Component("RoleAuthorization")
public class RoleAuthorization implements IAuthorization {

	@Autowired
	private UserService userService;

	@Override
	public List<GrantedAuthority> authorize(String userId) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		List<UserInfoDTO> users = userService.retrieveUserById(userId);
		if (!CollectionUtils.isEmpty(users)) {
			List<String> userRoles = users.get(0).getRoles();
			for (String userRole : userRoles) {
				grantedAuthorities.add(new GrantedAuthorityImpl(userRole
						.toUpperCase()));
			}
		} else {
			grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		}

		if (grantedAuthorities.isEmpty()) {
			throw new AuthorizationException(
					"User is not authorized to view this page");
		}
		return grantedAuthorities;
	}
}
