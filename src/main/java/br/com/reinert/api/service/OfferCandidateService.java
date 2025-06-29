package br.com.reinert.api.service;


import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.OfferCandidate;
import br.com.reinert.api.repositories.OfferCandidateRepository;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfferCandidateService {

    OfferCandidateRepository offerCandidateRepository;

    public void save(OfferCandidate offerCandidate) {
        offerCandidateRepository.save(offerCandidate);
    }

    public Optional<OfferCandidate> getByIdAndOffer(UUID id, Offer offer) {
        return offerCandidateRepository.findByIdAndOffer(id, offer);
    }

    public Optional<OfferCandidate> getByOfferAndSelectedIsTrue(Offer offer) {
        return offerCandidateRepository.findByOfferAndSelectedIsTrue(offer);
    }
}