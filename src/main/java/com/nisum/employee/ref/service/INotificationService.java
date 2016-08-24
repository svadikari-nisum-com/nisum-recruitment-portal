package com.nisum.employee.ref.service;

import javax.mail.MessagingException;

import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.view.OfferDTO;

public interface INotificationService {
	void sendFeedbackMail(InterviewFeedback interviewFeedback) throws MessagingException;
	String sendScheduleMail(InterviewSchedule interviewSchedule, String mobileNo, String altMobileNo, String skypeId) throws Exception;
	void sendOfferNotificationMail(OfferDTO offer) throws ServiceException;
}
