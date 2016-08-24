package com.nisum.employee.ref.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSlotDTO extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531000502214451780L;
	
	private String day;
	private String time;
	private String hour;
	private Boolean isNotAvailable = false;
	private String fromDate;
	private String toDate;
}
