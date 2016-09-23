package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.view.ProfileDTO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ProfileConverter extends TwowayConverter<Profile, ProfileDTO> {

	@Override
	public ProfileDTO convertToDTO(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTO();
		try {
			BeanUtils.copyProperties(profileDTO, profile);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return profileDTO;
		}
		return profileDTO;
	}

	@Override
	public Profile convertToEntity(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		try {
			BeanUtils.copyProperties(profile, profileDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return profile;
		}
		return profile;
	}

	public List<ProfileDTO> convertToDTOs(List<Profile> profiles) {
		List<ProfileDTO> dtos = new ArrayList<>();
		profiles.stream()
				.forEach(profile -> dtos.add(convertToDTO(profile)));
		return dtos;
	}
}
