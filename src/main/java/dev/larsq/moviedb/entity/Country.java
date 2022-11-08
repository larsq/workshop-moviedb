package dev.larsq.moviedb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
public class Country {
    @Id
    private String name;

    @ManyToMany(mappedBy = "countries")
    private Set<Narrative> filmsOrEpisodes;

    public String getName() {
        return name;
    }

    @Transient
    public String countryCode() {
        return Arrays.stream(Locale.getAvailableLocales()).filter(l -> l.getDisplayCountry(Locale.ENGLISH).equals(name)).map(Locale::getCountry).findFirst()
                .orElse(null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Narrative> getFilmsOrEpisodes() {
        return filmsOrEpisodes;
    }

    public void setFilmsOrEpisodes(Set<Narrative> filmsOrEpisodes) {
        this.filmsOrEpisodes = filmsOrEpisodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Country from(String name) {
        Country country = new Country();
        country.setName(name);

        return country;
    }

    @Override
    public String toString() {
        return "Country{name='%s', cc='%s'}".formatted(name, countryCode());
    }
}
