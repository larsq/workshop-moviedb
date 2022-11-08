package dev.larsq.moviedb.support;

import dev.larsq.moviedb.dto.MovieDto;
import dev.larsq.moviedb.entity.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NarrativeConverter {
    public static MovieDto toDto(Narrative narrative) {
        return new MovieDto(
                narrative.getId(),
                narrative.getType(),
                narrative.getTitle(),
                narrative.getDirector(),
                narrative.getCast().stream().map(Actor::getName).toList(),
                narrative.getCountries().stream().map(Country::countryCode).toList(),
                narrative.getDateAdded() != null ? narrative.getDateAdded().toLocalDate() : null,
                narrative.getReleaseYear(),
                narrative.getRating(),
                narrative.getType() == Category.MOVIE ? extractNumber(narrative.getDuration()) : 0,
                narrative.getType() == Category.TV_SHOW ? extractNumber(narrative.getDuration()) : 0,
                narrative.getCategories().stream().map(Label::getLabel).collect(Collectors.toUnmodifiableSet()),
                narrative.getDescription());

    }

    private static Integer extractNumber(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }

        Matcher matcher = Pattern.compile("(\\d+).*").matcher(str);
        return matcher.matches() ? Integer.parseInt(matcher.group(1)) : 0;
    }
}
