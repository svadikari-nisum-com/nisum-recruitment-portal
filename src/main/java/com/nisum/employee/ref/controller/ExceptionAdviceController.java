package com.nisum.employee.ref.controller;

import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nisum.employee.ref.common.ErrorCodes;
import com.nisum.employee.ref.service.NotificationService;
import com.nisum.employee.ref.view.BaseDTO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionAdviceController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@ExceptionHandler({ InterruptedException.class, RuntimeException.class,
			ExecutionException.class })
	public ResponseEntity<BaseDTO> handleRuntimeErrors(Exception e) {
		log.error(e.getMessage(), e);
		notificationService.sendExcetionLog(e);
		BaseDTO baseDTO = new BaseDTO();
		baseDTO.addError(ErrorCodes.NRP0001,
				messageSourceAccessor.getMessage(ErrorCodes.NRP0001));
		return new ResponseEntity<BaseDTO>(baseDTO,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ Exception.class, MessagingException.class })
	public ResponseEntity<BaseDTO> handleSQLException(Exception e) {
		log.error(e.getMessage(), e);
		notificationService.sendExcetionLog(e);
		BaseDTO baseDTO = new BaseDTO();
		baseDTO.addError(ErrorCodes.NRP0001,
				messageSourceAccessor.getMessage(ErrorCodes.NRP0001));
		return new ResponseEntity<BaseDTO>(baseDTO,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}