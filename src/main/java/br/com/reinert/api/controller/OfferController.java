package br.com.reinert.api.controller;

import br.com.reinert.api.converters.OfferConverter;
import br.com.reinert.api.domain.entities.Offer;
import br.com.reinert.api.domain.entities.OfferCandidate;
import br.com.reinert.api.domain.entities.OfferRating;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.domain.enums.Role;
import br.com.reinert.api.dto.*;
import br.com.reinert.api.infra.security.TokenService;
import br.com.reinert.api.service.NotificationService;
import br.com.reinert.api.service.OfferService;
import br.com.reinert.api.service.UserService;
import br.com.reinert.api.utils.AuthUtils;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfferController {

    UserService userService;
    OfferService offerService;
    TokenService tokenService;
    NotificationService notificationService;

    @PostMapping("")
    public ResponseEntity<Void> create(@RequestHeader("Authorization") String authHeader, @RequestBody OfferDTO body) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            createOffer(user, body);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<Void> update(@PathVariable String offerId, @RequestBody OfferDTO body) {
        Optional<Offer> offerOpt = offerService.getById(UUID.fromString(offerId));

        if (offerOpt.isPresent()) {
            Offer offer = offerOpt.get();
            updateOffer(offer, body);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("")
    public ResponseEntity<ListResponseDTO<OfferPreviewDTO>> getAll(@RequestHeader("Authorization") String authHeader) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Offer> offers = user.getRole() == Role.WORKER ? offerService.getAll() : offerService.getAllByUser(user);

            return ResponseEntity.ok(new ListResponseDTO<>(offers.stream().map(OfferConverter::toOfferPreviewDTO).collect(Collectors.toList()), offers.size()));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferDTO> getById(@RequestHeader("Authorization") String authHeader, @PathVariable String offerId) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<Offer> offerOpt = offerService.getById(UUID.fromString(offerId));
            return offerOpt.map(offer -> ResponseEntity.ok(OfferConverter.toOfferDTO(offer, (user.getRole() == Role.USER)))).orElseGet(() -> ResponseEntity.ok(new OfferDTO()));
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        offerService.delete(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{offerId}/candidates")
    public ResponseEntity<Void> createCandidate(@RequestHeader("Authorization") String authHeader, @PathVariable String offerId) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            if (user.getRole() != Role.WORKER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            Optional<Offer> offerOpt = offerService.getById(UUID.fromString(offerId));
            if (offerOpt.isPresent()) {
                var offer = offerOpt.get();
                createOfferCandidate(user, offer);
                notificationService.sendToUser(offer.getUser(), "Novidades!", String.format("%s se aplicou para %s.", user.getName(), offer.getTitle()));

                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{offerId}/candidates/{candidateId}")
    public ResponseEntity<Void> updateCandidate(@PathVariable String offerId, @PathVariable String candidateId, @RequestBody OfferCandidateDTO body) {
        Optional<Offer> offerOpt = offerService.getById(UUID.fromString(offerId));
        if (offerOpt.isPresent()) {
            var offer = offerOpt.get();
            Optional<OfferCandidate> offerCandidateOpt = offerService.getCandidateByIdAndOffer(UUID.fromString(candidateId), offer);
            offerCandidateOpt.ifPresent(offerCandidate -> updateOfferCandidate(offerCandidate, body));

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{offerId}/ratings")
    public ResponseEntity<Void> createRating(@RequestHeader("Authorization") String authHeader, @PathVariable String offerId, @RequestBody OfferRatingDTO body) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User raterUser = userOpt.get();

            Optional<Offer> offerOpt = offerService.getById(UUID.fromString(offerId));
            offerOpt.ifPresent(offer -> {
                Optional<OfferCandidate> ratedCandidate = offerService.getCandidateByOfferAndSelectedIsTrue(offer);
                ratedCandidate.ifPresent(offerCandidate -> createOfferRating(raterUser, offerCandidate.getUser(), offer, body));
            });

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    private void createOffer(User user, OfferDTO offerDTO) {
        var offer = new Offer();
        offer.setOccupation(offerDTO.getOccupation());
        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setPostalCode(offerDTO.getPostalCode());
        offer.setStreet(offerDTO.getStreet());
        offer.setNumber(offerDTO.getNumber());
        offer.setDistrict(offerDTO.getDistrict());
        offer.setCity(offerDTO.getCity());
        offer.setState(offerDTO.getState());
        offer.setUser(user);
        offerService.save(offer);
    }

    private void  updateOffer(Offer offer, OfferDTO offerDTO) {
        offer.setHireDate(offerDTO.getHireDate());
        offer.setEndDate(offerDTO.getEndDate());
        offerService.save(offer);
    }

    private void createOfferCandidate(User user, Offer offer) {
        var offerCandidate = new OfferCandidate();
        offerCandidate.setUser(user);
        offerCandidate.setOffer(offer);
        offerService.saveCandidate(offerCandidate);
    }

    private void updateOfferCandidate(OfferCandidate offerCandidate, OfferCandidateDTO offerCandidateDTO) {
        offerCandidate.setSelected(offerCandidateDTO.getSelected());
        offerService.saveCandidate(offerCandidate);
    }

    private void createOfferRating(User raterUser, User ratedUser, Offer offer, OfferRatingDTO offerRatingDTO) {
        var offerRating = new OfferRating();
        offerRating.setRating(offerRatingDTO.getRating());
        offerRating.setComment(offerRatingDTO.getComment());
        offerRating.setRaterUser(raterUser);
        offerRating.setRatedUser(ratedUser);
        offerRating.setOffer(offer);
        offerService.saveRating(offerRating);
    }
}
