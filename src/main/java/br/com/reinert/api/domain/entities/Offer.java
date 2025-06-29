package br.com.reinert.api.domain.entities;

import br.com.reinert.api.domain.enums.Occupation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "offers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Occupation occupation;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String street;

    private String number;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime hireDate;

    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "offer")
    private List<OfferCandidate> candidates;
}
