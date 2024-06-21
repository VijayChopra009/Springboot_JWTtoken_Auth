package com.myspring.dto;



import jakarta.persistence.Column;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
//data transfer object DTO

	private String id;
	@Size(min = 3, max=35, message="Invalid")
	private String name;
	@NotBlank(message = "username is required and should be Unique")
	private  String username;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message="Invalid Email")
	@NotBlank(message ="Email is required")
	private String email;
	@NotBlank
	private String password;
	private double salary;
}
