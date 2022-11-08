package dev.larsq.moviedb.service;

import dev.larsq.moviedb.dto.MovieDto;
import dev.larsq.moviedb.dto.SearchRequestDto;
import dev.larsq.moviedb.dto.SearchResponseDto;
import dev.larsq.moviedb.entity.Narrative;
import dev.larsq.moviedb.exception.MovieNotFoundException;
import dev.larsq.moviedb.repository.MovieRepository;
import dev.larsq.moviedb.support.NarrativeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieDto findById(String id) {
        Narrative narrative = repository.findById(id);

        if (narrative == null) {
            throw new MovieNotFoundException();
        }

        return NarrativeConverter.toDto(narrative);
    }

    public SearchResponseDto search(SearchRequestDto part) {
        List<Narrative> result = repository.search(part.part());

        return new SearchResponseDto(result.stream().map(NarrativeConverter::toDto).toList());
    }
}
