package dev.larsq.moviedb.resource;

import dev.larsq.moviedb.dto.MovieDto;
import dev.larsq.moviedb.dto.SearchResponseDto;
import dev.larsq.moviedb.model.MovieModel;
import dev.larsq.moviedb.model.SearchRequestModel;
import dev.larsq.moviedb.model.SearchResponseModel;
import dev.larsq.moviedb.service.MovieService;
import dev.larsq.moviedb.support.MovieAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieEndpoint {
    private final MovieService service;

    @Autowired
    public MovieEndpoint(MovieService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public MovieModel byId(@PathVariable("id") String id) {
        MovieDto dto = service.findById(id);

        return MovieAssembler.toModel(dto);
    }

    @CrossOrigin
    @PostMapping("/")
    public SearchResponseModel search(@RequestBody SearchRequestModel request) {
        SearchResponseDto response = service.search(MovieAssembler.toDto(request));
        return MovieAssembler.toModel(response);
    }
}
