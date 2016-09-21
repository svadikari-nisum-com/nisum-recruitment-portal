package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.view.OfferDTO;

@Component
@Slf4j
public class OfferConverter extends TwowayConverter<Offer, OfferDTO> {

	@Override
	public OfferDTO convertToDTO(Offer offer) {
		OfferDTO offerDTO = new OfferDTO();
		try {
			BeanUtils.copyProperties(offerDTO, offer);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return offerDTO;
		}
		return offerDTO;
	}

	@Override
	public Offer convertToEntity(OfferDTO offerDTO) {
        Offer offer = new Offer();
		try {
			BeanUtils.copyProperties(offer, offerDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage(),e);
			return offer;
		}
		return offer;
	}

	public List<OfferDTO> convertToDTOs(List<Offer> offers) {
		List<OfferDTO> dtos = new ArrayList<>();
		offers.stream()
				.forEach(offer -> dtos.add(convertToDTO(offer)));
		return dtos;
	}

}
