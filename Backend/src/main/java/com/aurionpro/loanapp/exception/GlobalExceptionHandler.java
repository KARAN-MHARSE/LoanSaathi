package com.aurionpro.loanapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aurionpro.loanapp.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException exception){
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorResponse.setSuccess(false);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException exception) {
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setSuccess(false);
		errorResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalStateException(IllegalStateException exception) {
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
		errorResponse.setSuccess(false);
		errorResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
	
	
	
//	@ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException exception) {
//		ErrorResponseDto errorResponse = new ErrorResponseDto();
//		errorResponse.setMessage(exception.getMessage());
//		errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
//		errorResponse.setSuccess(false);
//		errorResponse.setTimestamp(LocalDateTime.now());
//		return new ResponseEntity<ErrorResponseDto>(errorResponse,HttpStatus.BAD_REQUEST);
//    }

}
