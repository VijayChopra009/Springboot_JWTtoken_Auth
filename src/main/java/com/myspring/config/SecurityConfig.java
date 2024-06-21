package com.myspring.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myspring.security.JwtAuthenticationFilter;
import com.myspring.security.JwtAutheticationEntryPoint;
import com.myspring.service.CustomEmployeeDetails;
import com.myspring.service.CustomEmployeeDetailsService;

import lombok.CustomLog;
@Configuration
public class SecurityConfig {
     @Bean
	 public ModelMapper modelMapper() {
		 return new ModelMapper();
	 }
     @Bean
     public PasswordEncoder passwordEncoder() {
    	 return new BCryptPasswordEncoder();
     }
     @Autowired
     private  CustomEmployeeDetailsService customEmployeeDetailsService;
     
     @Autowired
     private JwtAutheticationEntryPoint jwtAutheticationEntryPoint;
     
     @Autowired
     private JwtAuthenticationFilter jwtAuthenticationFilter;
     
     @Bean
     public  DaoAuthenticationProvider daoAuthenticationProvider() {
    	 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    	 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    	 daoAuthenticationProvider.setUserDetailsService(customEmployeeDetailsService);
    	 return daoAuthenticationProvider;
     }
     
     @Bean     
     public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
    	 
    	 
    		 security
			 //.csrf(csrf->csrf.disable())
			 //.cors(auth->auth.disable())
			 .csrf(AbstractHttpConfigurer :: disable)
			 .cors(AbstractHttpConfigurer::disable)
			 .authorizeHttpRequests(auth->{
				 auth
				 .requestMatchers(HttpMethod.POST,"/employee","/login").permitAll()
				 .requestMatchers(HttpMethod.PUT,"/employee").permitAll()
				 .anyRequest()
				 .authenticated();
			 	 })
			 .authenticationProvider(daoAuthenticationProvider())
			 .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAutheticationEntryPoint))
			 .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		 
    	 return security.build();
     }
     
     @Bean
     public  AuthenticationManager authenticationManager(AuthenticationConfiguration builder)throws Exception{
    	 
    	 return builder.getAuthenticationManager();
     }
     
     
}