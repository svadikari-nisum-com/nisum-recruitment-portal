package com.nisum.employee.ref.util;

import java.util.Comparator;

import com.nisum.employee.ref.view.InterviewRoundsDTO;

public class InterviewerRoundInfoComparator implements Comparator<InterviewRoundsDTO>{

	@Override
	public int compare(InterviewRoundsDTO obj1, InterviewRoundsDTO obj2) {
		
		if(obj1.getNoOfRoundsScheduled() == obj2.getNoOfRoundsScheduled()){
			return 0;
		}else if(obj1.getNoOfRoundsScheduled() > obj2.getNoOfRoundsScheduled()){
			return 1;
		}else{
			return -1;
		}
	}

}
