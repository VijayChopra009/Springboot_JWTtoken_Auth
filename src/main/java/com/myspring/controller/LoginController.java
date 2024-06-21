package com.myspring.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.dto.EmployeeDTO;
import com.myspring.dto.JWTRequest;
import com.myspring.dto.JWTResponse;
import com.myspring.model.Employee;
import com.myspring.util.JwtUtil;

import exception.BadApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
public class LoginController {
    @Autowired
	private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    ModelMapper modelMapper;
    
   @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
    	UsernamePasswordAuthenticationToken authenication=  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    	try {
    		authenticationManager.authenticate(authenication);
		} catch (BadCredentialsException e) {
			throw new BadApiRequestException("Invalid Username and Password ! ");
		}
    	Employee userdetails=(Employee) userDetailsService.loadUserByUsername(request.getUsername());
    	String generatedtoken=this.jwtUtil.generateToken(userdetails);
    	JWTResponse response = JWTResponse.builder()
    			.jwttoken(generatedtoken)
    			.employee(modelMapper.map(userdetails, EmployeeDTO.class))
    			.build();
    	return new ResponseEntity<JWTResponse>(response,HttpStatus.OK);
    }
   
   @GetMapping("/current")
   public ResponseEntity<EmployeeDTO> getcurrentuser(@RequestBody Principal principal){
	   String name=principal.getName();
	  Employee usedetails=(Employee) userDetailsService.loadUserByUsername(name);
	  EmployeeDTO userdto=modelMapper.map(usedetails, EmployeeDTO.class);
	  return new ResponseEntity<EmployeeDTO>(userdto,HttpStatus.OK);
   }
    
}
