package br.com.reinert.api.repositories;

import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    List<Offer> findAllByEndDateIsNullOrderByCreatedDateDesc();

    List<Offer> findAllByUserAndEndDateIsNullOrderByCreatedDateDesc(User user);
}
