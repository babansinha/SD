package com.schooldost.gateway.mongo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public abstract class Base implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7385590384667489006L;
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Base base = (Base) o;

		if (id != null ? !id.equals(base.id) : base.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Base [id=" + id + "]";
	}
	
}
