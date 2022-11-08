package dev.larsq.moviedb.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record MovieModel(String id,
                         String type,
                         String title,
                         String director,
                         List<String> cast,
                         List<String> country,
                         LocalDate dateAdded, @JsonProperty("release_year") Integer releaseYear, String rating,
                         @JsonProperty("duration_minutes") Integer durationMinutes,
                         @JsonProperty("duration_seasons") Integer numberOfSeasons,
                         @JsonProperty("listed_in") Set<String> listedIn,
                         String description) {
}
