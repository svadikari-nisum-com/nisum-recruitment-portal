/**
 * 
 */
package com.nisum.employee.ref.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.exception.ServiceException;

/**
 * @author NISUM
 *
 */
@Component
@EnableScheduling
public class InterviewerStatusNotifier {

	@Autowired
	private NotificationService notificationService;
	
	@Scheduled(cron = "${interviewer.status.notification}")
    public void notifyInterviewerStatus() throws ServiceException
    {
        try {
			notificationService.sendInterviewersNotAvailableStatusNotification();
		} catch (MessagingException | ServiceException e) {
			throw new ServiceException(e);
		}
    }
}
