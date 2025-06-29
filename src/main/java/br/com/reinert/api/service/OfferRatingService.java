package br.com.reinert.api.service;

import br.com.reinert.api.domain.entities.OfferRating;
import br.com.reinert.api.repositories.OfferRatingRepository;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfferRatingService {

    OfferRatingRepository offerRatingRepository;

    public void save(OfferRating offerRating) {
        offerRatingRepository.save(offerRating);
    }
}