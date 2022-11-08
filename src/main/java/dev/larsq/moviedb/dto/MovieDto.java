package dev.larsq.moviedb.dto;

import dev.larsq.moviedb.entity.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record MovieDto(String id,
                       Category type,
                       String title,
                       String director,
                       List<String> cast,
                       List<String> country,
                       LocalDate dateAdded,
                       Integer releaseYear,
                       String rating,
                       Integer durationMinutes,
                       Integer numberOfSeasons,
                       Set<String> listedIn,
                       String description) {
}
