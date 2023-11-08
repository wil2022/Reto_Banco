package com.sophos.retoSpringBoot.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *<h1>ExceptionConfig</h1>
 *Esta clase permite manejar todas las excepciones, asignandoles a cada una su
 *respectivo codigo de error y la forma en que se mostraran al ser llamadas.
 * 
 *@author Wilson David Pachon.
 *@version 1.0.0 2022
 *@since 1.0.0
 */

@ControllerAdvice
public class ExceptionConfig {
	
	@ResponseBody
	@ExceptionHandler(UnderAgeClientException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> exceptionHandler(UnderAgeClientException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}
	
	
	@ResponseBody
	@ExceptionHandler(ClientWithAccountsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> exceptionHandler(ClientWithAccountsException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}	
	
	
	@ResponseBody
	@ExceptionHandler(AccountWithMovementsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> exceptionHandler(AccountWithMovementsException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}		
	
	@ResponseBody
	@ExceptionHandler(MovementActiveStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> exceptionHandler(MovementActiveStateException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}	
	
	
	
	@ResponseBody
	@ExceptionHandler(ClientNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> exceptionHandler(ClientNotFound exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}	
	
	@ResponseBody
	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> exceptionHandler(AccountNotFoundException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}
	
	@ResponseBody
	@ExceptionHandler(MovementNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> exceptionHandler(MovementNotFoundException exception){
		
		Map<String, String> errorMap=new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}

}
