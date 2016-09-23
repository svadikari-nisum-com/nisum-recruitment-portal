package com.nisum.employee.ref.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceAccessor;

import com.nisum.employee.ref.service.NotificationService;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionAdviceControllerTest {
	
	@Mock
	private NotificationService notificationService;
	@Mock
	private MessageSourceAccessor messageSourceAccessor;
	@InjectMocks
	private ExceptionAdviceController exceptionAdviceController;

	
	@Test
	public void handleRuntimeErrors() 
	{
		Mockito.doNothing().when(notificationService).sendExcetionLog(new Exception());
		Mockito.when(messageSourceAccessor.getMessage("Bug")).thenReturn("Bolla");

		exceptionAdviceController.handleRuntimeErrors(new Exception("Bolla"));
		
	}

	@Test
	public void handleSQLException() 
	{
		Mockito.doNothing().when(notificationService).sendExcetionLog(new Exception());
		Mockito.when(messageSourceAccessor.getMessage("Bug")).thenReturn("Bolla");

		exceptionAdviceController.handleSQLException(new Exception("Bolla"));
		
	}

}
