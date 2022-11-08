package dev.larsq.moviedb.startup;

import dev.larsq.moviedb.entity.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Component
public class DatabaseInitialize {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitialize.class);
    private final EntityManager entitymanager;
    private final ResourceLoader loader;

    @Autowired
    DatabaseInitialize(EntityManager entitymanager, ResourceLoader loader) {
        this.entitymanager = entitymanager;
        this.loader = loader;
    }


    @EventListener
    @Transactional(REQUIRED)
    public void onApplicationStartup(ContextRefreshedEvent event) {
        var format = CSVFormat.DEFAULT.builder().setHeader("show_id", "type", "title", "director", "cast", "country", "date_added", "release_year", "rating", "duration", "listed_in", "description").build();

        LOGGER.info("{}", entitymanager);
        try (CSVParser parser = CSVParser.parse(loader.getResource("classpath:/netflix_titles.csv").getInputStream(), StandardCharsets.UTF_8, format)) {
            List<CSVRecord> records = parser.getRecords();

            Set<Actor> actors = records.stream().skip(1).flatMap(record -> Arrays.stream(record.get("cast").split(",")).map(String::trim).filter(trimmed -> !trimmed.isBlank()).map(Actor::create)).collect(Collectors.toUnmodifiableSet());
            LOGGER.info("actors: {}", actors.size());
            actors.forEach(entitymanager::persist);

            Set<Label> labels = records.stream().skip(1).flatMap(record -> Arrays.stream(record.get("listed_in").split(",")).map(String::trim).filter(trimmed -> !trimmed.isBlank()).map(Label::create)).collect(Collectors.toUnmodifiableSet());
            LOGGER.info("labels: {}", labels.size());
            labels.forEach(entitymanager::persist);

            Set<Country> countries = records.stream().skip(1).flatMap(record -> Arrays.stream(record.get("country").split(",")).map(String::trim).filter(trimmed -> !trimmed.isBlank()).map(Country::from)).collect(Collectors.toUnmodifiableSet());
            LOGGER.info("countries: {}", countries);
            countries.forEach(entitymanager::persist);

            records.stream().skip(1).forEach(this::createNarrative);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void createNarrative(CSVRecord record) {
        Set<Actor> actors = Arrays.stream(record.get("cast").split(",")).map(String::trim).filter(trimmed1 -> !trimmed1.isBlank()).collect(Collectors.toUnmodifiableSet()).stream().map(Actor::create).collect(Collectors.toUnmodifiableSet());
        Set<Label> labels = Arrays.stream(record.get("listed_in").split(",")).map(String::trim).filter(trimmed1 -> !trimmed1.isBlank()).collect(Collectors.toUnmodifiableSet()).stream().map(Label::create).collect(Collectors.toUnmodifiableSet());
        Set<Country> countries = Arrays.stream(record.get("country").split(",")).map(String::trim).filter(trimmed -> !trimmed.isBlank()).map(Country::from).collect(Collectors.toUnmodifiableSet());


        LocalDate date = record.get("date_added").isBlank() ? null :
                LocalDate.parse(record.get("date_added").trim(), DateTimeFormatter.ofPattern("MMMM d',' yyyy", Locale.ENGLISH));


        Narrative narrative = new Narrative();
        narrative.setId(record.get("show_id"));
        narrative.setType(Category.fromCode(record.get("type").trim()));
        narrative.setTitle(record.get("title"));
        narrative.setDirector(record.get("director"));
        narrative.setCast(actors.stream().map(entitymanager::merge).collect(Collectors.toUnmodifiableSet()));
        narrative.setCountries(countries.stream().map(entitymanager::merge).collect(Collectors.toUnmodifiableSet()));
        narrative.setDateAdded(date != null ? new Date(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);
        narrative.setReleaseYear(Integer.parseInt(record.get("release_year")));
        narrative.setRating(record.get("rating"));
        narrative.setDuration(record.get("duration"));
        narrative.setCategories(labels.stream().map(entitymanager::merge).collect(Collectors.toUnmodifiableSet()));
        narrative.setDescription(record.get("description"));

        //LOGGER.info("adding {}", narrative);

        entitymanager.persist(narrative);
    }

}
