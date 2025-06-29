package br.com.reinert.api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "offer_ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double rating;

    private String comment;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @OneToOne
    @JoinColumn(name = "rater_user_id", nullable = false)
    private User raterUser;

    @OneToOne
    @JoinColumn(name = "rated_user_id", nullable = false)
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;
}
