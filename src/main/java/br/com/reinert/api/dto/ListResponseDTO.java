package br.com.reinert.api.dto;

import java.util.List;

public record ListResponseDTO<T>(List<T> contents, long totalRecords) {};
