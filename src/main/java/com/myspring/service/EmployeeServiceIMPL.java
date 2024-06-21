package com.myspring.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myspring.dto.EmployeeDTO;
import com.myspring.model.Employee;
import com.myspring.repository.EmployeeRepository;

import exception.ResourceNotFoundException;


@Service
public class EmployeeServiceIMPL implements EmployeeService {
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmployeeDTO create(EmployeeDTO employeedto) {
	Employee employee=modelMapper.map(employeedto, Employee.class);
	String[] empid=UUID.randomUUID().toString().split("-");
	employee.setId(empid[0]);
	employee.setPassword(passwordEncoder.encode(employee.getPassword()));
	Employee saveemp=repository.save(employee);
	EmployeeDTO saveempdto=modelMapper.map(employeedto, EmployeeDTO.class);
		return saveempdto;
	}

	@Override
	public EmployeeDTO update(EmployeeDTO employeedto,String id) {
		Employee employee=modelMapper.map(employeedto, Employee.class);
		Employee employeeupt= repository.findById(id).orElseThrow(() ->new ResourceNotFoundException("employee with given id not found"));
		employeeupt.setName(employee.getName());
		employeeupt.setSalary(employee.getSalary());
		Employee updateemp=repository.save(employeeupt);
			return modelMapper.map(updateemp, EmployeeDTO.class);
	}

	@Override
	public void delete(String id) {
		Employee employeeid= repository.findById(id).orElseThrow(() ->new ResourceNotFoundException("employee with given id not found"));
    repository.delete(employeeid);
	}

	@Override
	public EmployeeDTO getById(String id) {
	Employee employeeid= repository.findById(id).orElseThrow(() ->new ResourceNotFoundException("employee with given id not found"));
		return modelMapper.map(employeeid, EmployeeDTO.class);
	}

	@Override
	public List<EmployeeDTO> getAll() {
		List<Employee> all= repository.findAll();
		 List<EmployeeDTO> allempdto = all.stream().map(employee ->modelMapper.map(employee,EmployeeDTO.class)).toList();
		return allempdto;
	}

}
