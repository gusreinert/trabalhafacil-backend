package br.com.reinert.api.repositories;

import br.com.reinert.api.domain.entities.OfferRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRatingRepository extends JpaRepository<OfferRating, UUID> {
}