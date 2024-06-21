package exception;

import java.io.ObjectInputStream.GetField;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodArgumentBuilder;

import com.myspring.dto.ApiResponseMsg;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	//handle the resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMsg>  ResourceNotFoundException(ResourceNotFoundException ex){
		log.info("Exception Handler Invoked : {} ",ex.getMessage());
		ApiResponseMsg response=ApiResponseMsg.builder()
				.message(ex.getMessage())
				.success(true)
				.status(HttpStatus.NOT_FOUND)
				.build();
		return new ResponseEntity<ApiResponseMsg>(response,HttpStatus.OK);
	}
	
	
	//handle sql exception in primary key violation
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponseMsg>  handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		log.info("Exception Handler Invoked : {} ",ex.getMessage());
		ApiResponseMsg response=ApiResponseMsg.builder()
				.message(ex.getMessage())
				.success(true)
				.status(HttpStatus.BAD_REQUEST)
				.build();
		return new ResponseEntity<ApiResponseMsg>(response,HttpStatus.OK);
	}
	

	//handle method arguments exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String ,Object>>  handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		log.info("Exception Handler Invoked : {} ",ex.getMessage());
		List<ObjectError>    allerrors=ex.getBindingResult().getAllErrors();
		Map<String,Object> response=new HashMap<>();
		
		allerrors.forEach( ObjectError ->{
			String error= ObjectError.getDefaultMessage();
			String field = ((FieldError)ObjectError).getField();
			response.put(field, response);
		});
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
