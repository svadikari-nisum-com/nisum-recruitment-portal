package com.nisum.employee.ref.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Rating;
import com.nisum.employee.ref.domain.Round;
import com.nisum.employee.ref.view.InterviewDetailsDTO;
import com.nisum.employee.ref.view.RatingDTO;
import com.nisum.employee.ref.view.RoundDTO;


@Slf4j
@Component
public class InterviewDetailsConverter extends TwowayConverter<InterviewDetails, InterviewDetailsDTO> {

	@Override
	public InterviewDetailsDTO convertToDTO(InterviewDetails interviewDetails) {
		InterviewDetailsDTO interviewDetailsDTO = new InterviewDetailsDTO();
		try {
			BeanUtils.copyProperties(interviewDetailsDTO, interviewDetails);
			List<RoundDTO> roundDTOs = new ArrayList<>();
			interviewDetails.getRounds().stream().forEach(round -> {
				RoundDTO roundDTO = new RoundDTO();
				try {
					BeanUtils.copyProperties(roundDTO, round);
					
					List<RatingDTO> ratingDTOs = new ArrayList<>();
					round.getInterviewFeedback().getRateSkills().stream().forEach(rating -> {
						RatingDTO ratingDTO = new RatingDTO();
							try {
								BeanUtils.copyProperties(ratingDTO, rating);
								ratingDTOs.add(ratingDTO);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
					});
					roundDTOs.add(roundDTO);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			});
			interviewDetailsDTO.setRounds(roundDTOs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return interviewDetailsDTO;
		}
			return interviewDetailsDTO;
	}

	@Override
	public InterviewDetails convertToEntity(InterviewDetailsDTO interviewDetailsDTO) {
		InterviewDetails interviewDetails = new InterviewDetails();
		try {
			BeanUtils.copyProperties(interviewDetails, interviewDetailsDTO);
			List<Round> rounds = new ArrayList<>();
			interviewDetailsDTO.getRounds().stream().forEach(roundDTO -> {
				Round round = new Round();
				try {
					BeanUtils.copyProperties(round, roundDTO);
					List<Rating> ratings = new ArrayList<>();
					round.getInterviewFeedback().getRateSkills().stream().forEach(ratingDTO -> {
						Rating rating = new Rating();
							try {
								BeanUtils.copyProperties(rating, ratingDTO);
								ratings.add(rating);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
					});
					rounds.add(round);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			});
			interviewDetails.setRounds(rounds);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return interviewDetails;
		}
			return interviewDetails;
	}

	public List<InterviewDetailsDTO> convertToDTOs(List<InterviewDetails> interviewDetail) {
		List<InterviewDetailsDTO> dtos = new ArrayList<>();
		interviewDetail.stream()
				.forEach(interviewDetails -> dtos.add(convertToDTO(interviewDetails)));
		return dtos;
	}
}

