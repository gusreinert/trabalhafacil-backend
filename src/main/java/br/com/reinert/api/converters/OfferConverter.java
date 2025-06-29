package br.com.reinert.api.converters;

import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.OfferCandidate;
import br.com.reinert.api.dto.OfferCandidateDTO;
import br.com.reinert.api.dto.OfferDTO;
import br.com.reinert.api.dto.OfferPreviewDTO;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@UtilityClass
public class OfferConverter {

    public OfferDTO toOfferDTO(Offer offer, Boolean children) {
        if (isNull(offer)) {
            return new OfferDTO();
        }

        return OfferDTO.builder()
                .id(String.valueOf(offer.getId()))
                .occupation(offer.getOccupation())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .postalCode(offer.getPostalCode())
                .street(offer.getStreet())
                .number(offer.getNumber())
                .district(offer.getDistrict())
                .city(offer.getCity())
                .state(offer.getState())
                .hireDate(offer.getHireDate())
                .endDate(offer.getEndDate())
                .user(UserConverter.toUserDTO(offer.getUser()))
                .candidates(toOfferCandidateListDTO(offer.getCandidates()))
                .build();

    }

    public OfferPreviewDTO toOfferPreviewDTO(Offer offer) {
        if (isNull(offer)) {
            return new OfferPreviewDTO();
        }

        return OfferPreviewDTO.builder()
                .id(String.valueOf(offer.getId()))
                .occupation(offer.getOccupation())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .user(UserConverter.toUserDTO(offer.getUser()))
                .candidates(offer.getCandidates().stream().map(candidate -> String.valueOf(candidate.getUser().getId())).collect(Collectors.toList()))
                .build();

    }

    public List<OfferCandidateDTO> toOfferCandidateListDTO(List<OfferCandidate> candidates) {
        if (candidates.isEmpty()) {
            return Collections.emptyList();
        }

        return candidates.stream().map(OfferConverter::toOfferCandidateDTO).collect(Collectors.toList());
    }

    public OfferCandidateDTO toOfferCandidateDTO(OfferCandidate candidate) {
        if (isNull(candidate)) {
            return new OfferCandidateDTO();
        }

        return OfferCandidateDTO.builder()
                .id(String.valueOf(candidate.getId()))
                .selected(candidate.getSelected())
                .user(UserConverter.toUserDTO(candidate.getUser()))
                .build();
    }
}


