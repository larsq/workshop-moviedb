package dev.larsq.moviedb.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Narrative {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Category type;
    private String title;
    private String director;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "narrative_actor",
            joinColumns = @JoinColumn(name = "narrative_fk"),
            inverseJoinColumns = @JoinColumn(name = "actor_fk")
    )
    private Set<Actor> cast;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "narrative_country",
            joinColumns = @JoinColumn(name = "narrative_fk"),
            inverseJoinColumns = @JoinColumn(name = "country_fk")
    )
    private Set<Country> countries;

    private Date dateAdded;
    private Integer releaseYear;
    private String rating;
    private String duration;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "narrative_label",
            joinColumns = @JoinColumn(name = "narrative_fk"),
            inverseJoinColumns = @JoinColumn(name = "label_fk")
    )
    private Set<Label> categories;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getType() {
        return type;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Set<Actor> getCast() {
        return cast;
    }

    public void setCast(Set<Actor> cast) {
        this.cast = cast;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<Label> getCategories() {
        return categories;
    }

    public void setCategories(Set<Label> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Narrative{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", cast=" + cast +
                ", countries='" + countries + '\'' +
                ", dateAdded=" + dateAdded +
                ", releaseYear=" + releaseYear +
                ", rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                ", categories=" + categories +
                ", description='" + description + '\'' +
                '}';
    }
}
