package com.nisum.employee.ref.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.domain.Round;
import com.nisum.employee.ref.repository.OfferRepository;
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
	@Autowired
	OfferRepository offerRepository;

	public ReportsVO getDataByJobCode(String jobcodeProfile) {
		List<Profile> profiles = profileRepository
				.retrieveProfileByJobCode(jobcodeProfile);
		List<InterviewDetails> interviewDetails = interviewDetailsService
				.getInterviewByJobCode(jobcodeProfile);
		reportsVO.setProfiles(profiles);
		reportsVO.setInterviewDetails(interviewDetails);
		return reportsVO;
	}

	public List<ReportsVO> getReportByHiringManager(String hiringManager,
			String recruiterEmail) throws ParseException {

		List<InterviewDetails> listOfInteviewDetails;
		List<OfferDTO> offers;
		ReportsVO reportsVO = new ReportsVO();
		List<ReportsVO> reportList = new ArrayList<ReportsVO>();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String[] hiringManagers = hiringManager.split(",");

		for (String hrmgr : hiringManagers) {
			List<PositionDTO> positions = positionService.retrieveAllPositions(
					"hiringManager", hrmgr);
			for (PositionDTO position : positions) {
				reportsVO = new ReportsVO();
				reportsVO.setPositionId(position.getJobcode());
				reportsVO.setFunctionalGrp(position.getFunctionalGroup());
				reportsVO.setNoOfOpenPositions(position.getNoOfPositions());

				List<Profile> profiles = profileRepository
						.retrieveProfileByJobCode(position.getJobcode());

				if (recruiterEmail != null && !recruiterEmail.isEmpty()) {
					profiles = profileRepository
							.retrieveProfileByRecruiterAndJobcode(
									recruiterEmail, position.getJobcode());

				}

				long prof_totalDiff = 0;
				for (Profile profile : profiles) {
					if (position.getPositionApprovedDt() != null
							&& profile.getCreateDtm() != null) {
						prof_totalDiff = prof_totalDiff
								+ (profile.getCreateDtm().getTime() - position
										.getPositionApprovedDt().getTime());
					}
				}

				long prof_avgTime = prof_totalDiff
						/ position.getNoOfPositions();
				reportsVO
						.setAvgProfileTime(convertMilliSecondsToMinutes(prof_avgTime));

				listOfInteviewDetails = interviewDetailsService
						.getInterviewByJobCode(position.getJobcode());
				long round1_totalDiff = 0;
				long round2_totalDiff = 0;
				long hrRound_totalDiff = 0;
				long offered_totDiff = 0;
				long closed_totDiff = 0;
				for (InterviewDetails interviewDetails : listOfInteviewDetails) {
					if (interviewDetails.getProgress().contains(
							"Technical Round 1")) {
						reportsVO.setProfilesInTechnicalRound1(reportsVO
								.getProfilesInTechnicalRound1() + 1);
					} else if (interviewDetails.getProgress().contains(
							"Technical Round 2")) {
						reportsVO.setProfilesInTechnicalRound2(reportsVO
								.getProfilesInTechnicalRound2() + 1);
					} else if (interviewDetails.getProgress().contains(
							"Manager Round")) {
						reportsVO.setProfilesInManagerRound(reportsVO
								.getProfilesInManagerRound() + 1);
					} else if (interviewDetails.getProgress().contains(
							"Hr Round")) {
						reportsVO.setProfilesInHRRound(reportsVO
								.getProfilesInHRRound() + 1);
					}

					List<Profile> candidateProfiles = profileRepository
							.retrieveCandidateDetails(interviewDetails
									.getCandidateEmail());
					Profile candidateProfile = null;
					if (!candidateProfiles.isEmpty()) {
						candidateProfile = candidateProfiles.get(0);
					}
					List<Round> rounds = interviewDetails.getRounds();
					if (rounds != null) {
						Date interviewDt_round1 = null;
						Date interviewDt_round2 = null;
						Date interviewDt_hr = null;
						for (Round round : rounds) {

							if (round.getRoundName()
									.equals("Technical Round 1")) {
								if (candidateProfile.getCreateDtm() != null
										&& round.getInterviewSchedule()
												.getInterviewDateTime() != null) {

									interviewDt_round1 = sdf.parse(round
											.getInterviewSchedule()
											.getInterviewDateTime());
									if (candidateProfile.getCreateDtm() != null) {
										round1_totalDiff = round1_totalDiff
												+ (interviewDt_round1.getTime() - candidateProfile
														.getCreateDtm()
														.getTime());
									}
								}

							}

							if (round.getRoundName()
									.equals("Technical Round 2")) {

								if (round.getInterviewSchedule()
										.getInterviewDateTime() != null) {

									interviewDt_round2 = sdf.parse(round
											.getInterviewSchedule()
											.getInterviewDateTime());
									if (interviewDt_round1 != null) {
										round2_totalDiff = round2_totalDiff
												+ (interviewDt_round2.getTime() - interviewDt_round1
														.getTime());
									}

								}

							}
							if (round.getRoundName().equals("Hr Round")) {

								if (round.getInterviewSchedule()
										.getInterviewDateTime() != null) {

									interviewDt_hr = sdf.parse(round
											.getInterviewSchedule()
											.getInterviewDateTime());

									if (interviewDt_round2 != null) {
										hrRound_totalDiff = hrRound_totalDiff
												+ (interviewDt_hr.getTime() - interviewDt_round2
														.getTime());
									}

								}

								Offer offer = offerRepository
										.getOffer(interviewDetails
												.getCandidateEmail());
								DateTime offer_Dt = null;
								if (offer != null) {
									if (offer.getStatus().equalsIgnoreCase(
											OfferState.APPROVED.name())
											|| offer.getStatus()
													.equalsIgnoreCase(
															OfferState.RELEASED
																	.name())
											|| offer.getStatus()
													.equalsIgnoreCase(
															OfferState.ACCEPTED
																	.name())
											|| offer.getStatus()
													.equalsIgnoreCase(
															OfferState.JOINED
																	.name())) {
										offer_Dt = offer.getCreatedDate();
										offered_totDiff = offered_totDiff
												+ (offer.getCreatedDate()
														.getMillis() - interviewDt_hr
														.getTime());

									}
									if (offer.getStatus().equalsIgnoreCase(
											OfferState.CLOSED.name())) {
										if (offer_Dt != null) {
											closed_totDiff = closed_totDiff
													+ (offer.getLastModifiedDate()
															.getMillis() - offer_Dt
															.getMillis());
										}

									}

								}
							}
						}
					}

				}

				if (round1_totalDiff != 0) {
					long round1_avgTime = round1_totalDiff
							/ listOfInteviewDetails.size();
					reportsVO
							.setAvgRound1Time(convertMilliSecondsToMinutes(round1_avgTime));
				} else {
					reportsVO.setAvgRound1Time("0 days 0 hours");
				}

				if (round2_totalDiff != 0) {
					long round2_avgTime = round2_totalDiff
							/ listOfInteviewDetails.size();

					reportsVO
							.setAvgRound2Time(convertMilliSecondsToMinutes(round2_avgTime));
				} else {
					reportsVO.setAvgRound2Time("0 days 0 hours");
				}
				if (hrRound_totalDiff != 0) {

					long hrRound_avgTime = hrRound_totalDiff
							/ listOfInteviewDetails.size();

					reportsVO
							.setAvgHRRoundTime(convertMilliSecondsToMinutes(hrRound_avgTime));
				} else {
					reportsVO.setAvgHRRoundTime("0 days 0 hours");
				}
				if (offered_totDiff != 0) {
					long offered_avgTime = offered_totDiff
							/ listOfInteviewDetails.size();

					reportsVO
							.setAvgTimeOffered(convertMilliSecondsToMinutes(offered_avgTime));
				} else {
					reportsVO.setAvgTimeOffered("0 days 0 hours");
				}
				if (closed_totDiff != 0) {
					long closed_avgTime = closed_totDiff
							/ listOfInteviewDetails.size();

					reportsVO
							.setAvgTimeClosed(convertMilliSecondsToMinutes(closed_avgTime));
				} else {
					reportsVO.setAvgTimeClosed("0 days 0 hours");
				}

				offers = offerService.getOffersByJobcode(position.getJobcode());
				for (OfferDTO offerDetails : offers) {
					if (offerDetails.getStatus().equalsIgnoreCase(
							OfferState.APPROVED.name())
							|| offerDetails.getStatus().equalsIgnoreCase(
									OfferState.RELEASED.name())
							|| offerDetails.getStatus().equalsIgnoreCase(
									OfferState.ACCEPTED.name())
							|| offerDetails.getStatus().equalsIgnoreCase(
									OfferState.JOINED.name())) {
						reportsVO.setOffered(reportsVO.getOffered() + 1);
					}
					if (offerDetails.getStatus().equalsIgnoreCase(
							OfferState.CLOSED.name())) {
						reportsVO.setClosed(reportsVO.getClosed() + 1);
					}
				}

				reportList.add(reportsVO);
			}
		}

		return reportList;
	}

	public String convertMilliSecondsToMinutes(long millis) {
		long seconds = millis / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		return days + " days " + hours % 24 + " hours ";
	}

}
