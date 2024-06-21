package com.myspring.service;

import java.util.List;

import com.myspring.dto.EmployeeDTO;

public interface EmployeeService { 
	EmployeeDTO create(EmployeeDTO emp);
	EmployeeDTO update(EmployeeDTO emp,String id);
	void delete(String id);
	EmployeeDTO getById(String id);
	List<EmployeeDTO> getAll();
	

}
