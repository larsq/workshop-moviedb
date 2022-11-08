package dev.larsq.moviedb.support;

import dev.larsq.moviedb.dto.MovieDto;
import dev.larsq.moviedb.dto.SearchRequestDto;
import dev.larsq.moviedb.dto.SearchResponseDto;
import dev.larsq.moviedb.model.MovieModel;
import dev.larsq.moviedb.model.SearchRequestModel;
import dev.larsq.moviedb.model.SearchResponseModel;

public class MovieAssembler {
    public static MovieModel toModel(MovieDto dto) {
        return new MovieModel(dto.id(), dto.type().getLabel(), dto.title(), dto.director(), dto.cast(), dto.country(), dto.dateAdded(), dto.releaseYear(), dto.rating(),
                dto.durationMinutes(), dto.numberOfSeasons(), dto.listedIn(), dto.description());
    }

    public static SearchResponseModel toModel(SearchResponseDto response) {
        return new SearchResponseModel(response.items().stream().map(MovieAssembler::toModel).toList());
    }

    public static SearchRequestDto toDto(SearchRequestModel request) {
        return new SearchRequestDto(request.part());
    }
}
