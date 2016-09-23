/**
 * 
 */
package com.nisum.employee.ref.em;

/**
 * @author NISUM CONSULTING
 *
 */
public enum UserRole {

	ROLE_ADMIN("Administrator"), ROLE_HR("HR Manager"), ROLE_RECRUITER("HR Recruiter"), ROLE_MANAGER(
			"Manager"), ROLE_USER("Employee"), ROLE_INTERVIEWER("Interviewer"),ROLE_LOCATIONHEAD("location Head"), ROLE_INVALID(
			"Invalid");

	private final String roleDesc;

	UserRole(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	public UserRole getUserRole(String role) {
		for (UserRole userRole : UserRole.values()) {
			if (userRole.name().equalsIgnoreCase(role)) {
				return userRole;
			}
		}
		return ROLE_INVALID;
	}
}
