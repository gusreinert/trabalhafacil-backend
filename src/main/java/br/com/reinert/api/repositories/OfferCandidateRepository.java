package br.com.reinert.api.repositories;

import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.OfferCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferCandidateRepository extends JpaRepository<OfferCandidate, UUID> {

    Optional<OfferCandidate> findByIdAndOffer(UUID id, Offer offer);

    Optional<OfferCandidate> findByOfferAndSelectedIsTrue(Offer offer);
}