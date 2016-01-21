package com.schooldost.gateway.service.auth;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.schooldost.gateway.auth.util.AuthUtils;
import com.schooldost.gateway.constants.AuthConstants;
import com.schooldost.gateway.mongo.model.Role;
import com.schooldost.gateway.mongo.model.User;
import com.schooldost.gateway.service.user.UserService;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username : " + username);
		init();
		User user = userService.findByUsername(username);
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
					AuthConstants.ENABLED, AuthConstants.ENABLED, AuthConstants.ENABLED, AuthConstants.ENABLED, getAuthorities(user.getRole().getPermissions()));
		}
		
		return null;
	}

	public List<SimpleGrantedAuthority> getAuthorities(List<String> permissions) {
		List<SimpleGrantedAuthority> authList = permissions.stream()
		        .map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList());
		System.out.println("authList :: " + authList);
		return authList;
	}
	
	public void init() {
		// Drop existing collections
		mongoTemplate.dropCollection("role");
		mongoTemplate.dropCollection("user");
		mongoTemplate.dropCollection("student");
		mongoTemplate.dropCollection("teacher");
		mongoTemplate.dropCollection("parents");

		// Create new records
		Role adminRole = new Role();
		adminRole.setId(UUID.randomUUID().toString());
		adminRole.setRole(AuthConstants.ROLE_ADMIN);
		adminRole.setPermissions(AuthUtils.getPermissions(AuthConstants.ROLE_ADMIN) );
		
		Role userRole = new Role();
		userRole.setId(UUID.randomUUID().toString());
		userRole.setRole(AuthConstants.ROLE_USER);
		userRole.setPermissions(AuthUtils.getPermissions(AuthConstants.ROLE_USER));
		
		Role studentRole = new Role();
		studentRole.setId(UUID.randomUUID().toString());
		studentRole.setRole(AuthConstants.ROLE_STUDENT);
		studentRole.setPermissions(AuthUtils.getPermissions(AuthConstants.ROLE_STUDENT));
		
		Role teacherRole = new Role();
		teacherRole.setId(UUID.randomUUID().toString());
		teacherRole.setRole(AuthConstants.ROLE_TEACHER);
		teacherRole.setPermissions(AuthUtils.getPermissions(AuthConstants.ROLE_TEACHER));
		
		Role parentsRole = new Role();
		parentsRole.setId(UUID.randomUUID().toString());
		parentsRole.setRole(AuthConstants.ROLE_PARENTS);
		parentsRole.setPermissions(AuthUtils.getPermissions(AuthConstants.ROLE_PARENTS));
		
		User admin = new User();
		admin.setId(UUID.randomUUID().toString());
		admin.setFirstName("Admin");
		admin.setLastName("Sinha");
		admin.setPassword("admin");
		admin.setRole(adminRole);
		admin.setUsername("admin@schooldost.com");
		
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstName("User");
		user.setLastName("Sinha");
		user.setPassword("user");
		user.setRole(userRole);
		user.setUsername("user@schooldost.com");
		
		User teacher = new User();
		teacher.setId(UUID.randomUUID().toString());
		teacher.setFirstName("Teacher");
		teacher.setLastName("Sinha");
		teacher.setPassword("teacher");
		teacher.setRole(teacherRole);
		teacher.setUsername("teacher@schooldost.com");
		
		User student = new User();
		student.setId(UUID.randomUUID().toString());
		student.setFirstName("Student");
		student.setLastName("Sinha");
		student.setPassword("student");
		student.setRole(studentRole);
		student.setUsername("student@schooldost.com");
		
		User parents = new User();
		parents.setId(UUID.randomUUID().toString());
		parents.setFirstName("Parents");
		parents.setLastName("Sinha");
		parents.setPassword("parents");
		parents.setRole(parentsRole);
		parents.setUsername("parents@schooldost.com");
		
		// Insert to db
		userService.create(admin);
		userService.create(user);
		userService.create(teacher);
		userService.create(student);
		userService.create(parents);
		
	}

}
