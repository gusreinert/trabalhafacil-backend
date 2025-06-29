package br.com.reinert.api.dto;

public record AuthResponseDTO(UserDTO user, String token) { }
