package br.com.reinert.api.dto;

import br.com.reinert.api.domain.enums.Occupation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferPreviewDTO {

    private String id;
    private Occupation occupation;
    private String title;
    private String description;
    private UserDTO user;
    private List<String> candidates;
}
