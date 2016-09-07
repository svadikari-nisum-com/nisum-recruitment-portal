package com.nisum.employee.ref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;

@Service
public class ReportsService {
	
    ReportsVO reportsVO = new ReportsVO();
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	OfferService offerService;
	
	@Autowired
	InterviewDetailsService interviewDetailsService;
	
	public ReportsVO getDataByJobCode(String jobcodeProfile) {
		List<Profile> profiles = profileRepository.retrieveProfileByJobCode(jobcodeProfile);
		List<InterviewDetails> interviewDetails = interviewDetailsService.getInterviewByJobCode(jobcodeProfile);
		reportsVO.setProfiles(profiles);
		reportsVO.setInterviewDetails(interviewDetails);
		return reportsVO;
	}
	
    public List<ReportsVO> getReportByHiringManager(String hiringManager){
		
		List<InterviewDetails> listOfInteviewDetails ;
		List<OfferDTO> offers;
		ReportsVO reportsVO = new ReportsVO();
		List<ReportsVO> reportList = new ArrayList<ReportsVO>();
		
		String[] hiringManagers=hiringManager.split(",");
		for (String  hrmgr : hiringManagers) {
			List<PositionDTO> positions=positionService.retrieveAllPositions("hiringManager", hrmgr);			
		for(PositionDTO position : positions){
			reportsVO = new ReportsVO();
			reportsVO.setFunctionalGrp(position.getFunctionalGroup());
			reportsVO.setNoOfOpenPositions(position.getNoOfPositions());
			listOfInteviewDetails = interviewDetailsService.getInterviewByJobCode(position.getJobcode());
			/*if(listOfInteviewDetails != null){
				listOfAllInteviewDetails.addAll(listOfInteviewDetails);
			}*/
			for(InterviewDetails interviewDetails : listOfInteviewDetails){
				if(interviewDetails.getProgress().contains("Technical Round 1")){
					reportsVO.setProfilesInTechnicalRound1(reportsVO.getProfilesInTechnicalRound1() + 1);
				}else if(interviewDetails.getProgress().contains("Technical Round 2")){
					reportsVO.setProfilesInTechnicalRound2(reportsVO.getProfilesInTechnicalRound2() + 1);
				}else if(interviewDetails.getProgress().contains("Manager Round")){
					reportsVO.setProfilesInManagerRound(reportsVO.getProfilesInManagerRound() + 1);
				}else if(interviewDetails.getProgress().contains("Hr Round")){
					reportsVO.setProfilesInHRRound(reportsVO.getProfilesInHRRound() + 1);
				}
			}
			
			offers = offerService.getOffersByJobcode(position.getJobcode());
			for(OfferDTO offerDetails : offers){
				if(offerDetails.getStatus().equalsIgnoreCase(OfferState.APPROVED.name()) ||
						offerDetails.getStatus().equalsIgnoreCase(OfferState.RELEASED.name()) || 
						offerDetails.getStatus().equalsIgnoreCase(OfferState.ACCEPTED.name()) ||
						offerDetails.getStatus().equalsIgnoreCase(OfferState.JOINED.name())){
					reportsVO.setOffered(reportsVO.getOffered() + 1);
				}
				if(offerDetails.getStatus().equalsIgnoreCase(OfferState.CLOSED.name())){
					reportsVO.setClosed(reportsVO.getClosed() + 1);
				}
			}
			
			reportList.add(reportsVO);
		}
		}
		return reportList;
	}
}
