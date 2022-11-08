package dev.larsq.moviedb.dto;

import java.util.List;

public record SearchResponseDto(List<MovieDto> items) {
}
