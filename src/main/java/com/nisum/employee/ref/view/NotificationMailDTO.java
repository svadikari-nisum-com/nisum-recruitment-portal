package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMailDTO {
	private String organizer;
	private List<String> attendees;
	private List<String> cc;
	private String subject;
	private CalendarDTO calendarDTO;
}
