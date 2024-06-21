package com.myspring.service;

import java.lang.StackWalker.Option;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspring.model.Employee;
import com.myspring.repository.EmployeeRepository;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService{
	@Autowired
	private EmployeeRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<Employee> employee= repository.findByUsername(username);
		return employee.orElseThrow(() -> new UsernameNotFoundException("user not found"+username));
	}

}
