package com.schooldost.gateway.mongo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role" )
public class Role extends Base{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 252658429284544540L;
	
	private String role;
	private List<String> permissions;

	public List<String> getPermissions() {
		return permissions;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Role [role=" + role + ", permissions=" + permissions + ", getId()=" + getId() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}

}
