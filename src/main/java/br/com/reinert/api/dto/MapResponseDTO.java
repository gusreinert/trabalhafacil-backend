package br.com.reinert.api.dto;

import java.util.Map;

public record MapResponseDTO<T, R>(Map<T, R> contents, long totalRecords) { }
