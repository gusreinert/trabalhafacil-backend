package br.com.reinert.api.controller;

import br.com.reinert.api.domain.enums.Occupation;
import br.com.reinert.api.dto.MapResponseDTO;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/occupation")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OccupationController {

    @GetMapping("")
    public ResponseEntity<MapResponseDTO<String, String>> getAll() {
        var occupations = Arrays.stream(Occupation.values()).collect(Collectors.toMap(Enum::name, Occupation::getValue));
        return ResponseEntity.ok(new MapResponseDTO<>(occupations, occupations.size()));
    }
}