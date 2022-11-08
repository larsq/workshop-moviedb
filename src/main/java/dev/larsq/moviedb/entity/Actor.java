package dev.larsq.moviedb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Actor {
    @Id
    private String name;

    @ManyToMany(mappedBy = "cast")
    private Set<Narrative> filmsOrEpisodes;

    public String getName() {
        return name;
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
        Actor actor = (Actor) o;
        return name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Actor create(String name) {
        Actor actor = new Actor();
        actor.setName(name);

        return actor;
    }
}
