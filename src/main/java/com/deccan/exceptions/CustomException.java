package com.deccan.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	private String INCORRECT_REQUEST = "INCORRECT_REQUEST";
	private String BAD_REQUEST = "BAD_REQUEST";

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException(RecordNotFoundException exception,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(exception.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccountLockedException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(AccountLockedException exception,
			WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(exception.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
}
