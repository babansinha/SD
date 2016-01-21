/**
 * 
 */
package com.schooldost.gateway.auth.util;

import java.util.Arrays;
import java.util.List;

import com.schooldost.gateway.constants.AuthConstants;

/**
 * @author sinha.baban@gmail.com
 *
 */
public class AuthUtils {

	private static final List<String> ROLE_SUPER_ADMIN;
	private static final List<String> ROLE_ADMIN;
	private static final List<String> ROLE_PRINCIPLE;
	private static final List<String> ROLE_TEACHER;
	private static final List<String> ROLE_TUTION_TEACHER;
	private static final List<String> ROLE_STUDENT;
	private static final List<String> ROLE_PARENTS;
	private static final List<String> ROLE_USER;
	private static final List<String> ROLE_GUEST;

	static {
		ROLE_SUPER_ADMIN = Arrays.asList(AuthConstants.ROLE_ADMIN, AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE, AuthConstants.ROLE_CREATE);
		ROLE_ADMIN = Arrays.asList(AuthConstants.ROLE_ADMIN, AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE, AuthConstants.ROLE_CREATE);
		ROLE_PRINCIPLE = Arrays.asList(AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE, AuthConstants.ROLE_CREATE);
		ROLE_TEACHER = Arrays.asList(AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE, AuthConstants.ROLE_CREATE);
		ROLE_TUTION_TEACHER = Arrays.asList(AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE,
				AuthConstants.ROLE_CREATE);
		ROLE_STUDENT = Arrays.asList(AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE);
		ROLE_PARENTS = Arrays.asList(AuthConstants.ROLE_READ, AuthConstants.ROLE_WRITE);
		ROLE_USER = Arrays.asList(AuthConstants.ROLE_USER, AuthConstants.ROLE_READ);
		ROLE_GUEST = Arrays.asList(AuthConstants.ROLE_READ);
	}

	/**
	 * Method will return list of permission by given role
	 * 
	 * @param role
	 * @return List of permission
	 */
	public static List<String> getPermissions(final String role) {
		switch (role) {
		case AuthConstants.ROLE_SUPER_ADMIN:
			return ROLE_SUPER_ADMIN;
		case AuthConstants.ROLE_ADMIN:
			return ROLE_ADMIN;
		case AuthConstants.ROLE_PRINCIPLE:
			return ROLE_PRINCIPLE;
		case AuthConstants.ROLE_TEACHER:
			return ROLE_TEACHER;
		case AuthConstants.ROLE_TUTION_TEACHER:
			return ROLE_TUTION_TEACHER;
		case AuthConstants.ROLE_STUDENT:
			return ROLE_STUDENT;
		case AuthConstants.ROLE_PARENTS:
			return ROLE_PARENTS;
		case AuthConstants.ROLE_USER:
			return ROLE_USER;
		default:
			return ROLE_GUEST;
		}
	}
}
