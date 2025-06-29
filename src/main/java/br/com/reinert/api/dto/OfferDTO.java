package br.com.reinert.api.dto;

import br.com.reinert.api.domain.enums.Occupation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {

    private String id;
    private Occupation occupation;
    private String title;
    private String description;
    private String postalCode;
    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private LocalDateTime hireDate;
    private LocalDateTime endDate;
    private UserDTO user;
    private List<OfferCandidateDTO> candidates;
}

