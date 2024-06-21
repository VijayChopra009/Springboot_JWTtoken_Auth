package com.myspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.dto.ApiResponseMsg;
import com.myspring.dto.EmployeeDTO;
import com.myspring.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Controller
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO employeeDTO){
		EmployeeDTO empdto=service.create(employeeDTO);
		return new ResponseEntity<EmployeeDTO>(empdto,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getbyid(@Valid @PathVariable String id){
		EmployeeDTO empdtoid=service.getById(id);
		return new ResponseEntity<EmployeeDTO>(empdtoid,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO,@PathVariable String id){
		EmployeeDTO empdtoupt=service.update(employeeDTO, id);
		return new ResponseEntity<EmployeeDTO>(empdtoupt,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseMsg> deleleemp(@PathVariable String id){
		service.delete(id);
		ApiResponseMsg empdelete=ApiResponseMsg.builder()
				.message("Employee deleted ")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponseMsg>(empdelete,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> findall(){
		List<EmployeeDTO> allemp=service.getAll();
		return new ResponseEntity<List<EmployeeDTO>>(allemp,HttpStatus.OK);
	}
	
}
