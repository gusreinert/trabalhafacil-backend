package br.com.reinert.api.service;

import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.OfferCandidate;
import br.com.reinert.api.domain.entities.OfferRating;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.repositories.OfferRepository;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfferService {

    OfferRepository offerRepository;
    OfferRatingService offerRatingService;
    OfferCandidateService offerCandidateService;

    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    public List<Offer> getAll() {
        return offerRepository.findAllByEndDateIsNullOrderByCreatedDateDesc();
    }

    public List<Offer> getAllByUser(User user) {
        return offerRepository.findAllByUserAndEndDateIsNullOrderByCreatedDateDesc(user);
    }

    public Optional<Offer> getById(UUID id) {
        return offerRepository.findById(id);
    }

    public void delete(UUID id) {
        offerRepository.deleteById(id);
    }

    public void saveCandidate(OfferCandidate offerCandidate) {
        offerCandidateService.save(offerCandidate);
    }

    public Optional<OfferCandidate> getCandidateByIdAndOffer(UUID id, Offer offer) {
        return offerCandidateService.getByIdAndOffer(id, offer);
    }

    public Optional<OfferCandidate> getCandidateByOfferAndSelectedIsTrue(Offer offer ) {
        return offerCandidateService.getByOfferAndSelectedIsTrue(offer);
    }

    public void saveRating(OfferRating offerRating) {
        offerRatingService.save(offerRating);
    }
}