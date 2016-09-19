package com.nisum.employee.ref.view;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDTO {
	private String Location;
	private LocalDateTime startDateTime; //yyyy-MM-dd HH:mm
	private LocalDateTime endDateTime; //yyyy-MM-dd HH:mm
	private String description;
}
