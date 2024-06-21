package com.myspring.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.myspring.model.Employee;

public class CustomEmployeeDetails implements UserDetails{
	
	private String username;
	private String password;
	
	public CustomEmployeeDetails(Employee emp) {
		this.username=emp.getUsername();
		this.password=emp.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return password;
	}
	

}
