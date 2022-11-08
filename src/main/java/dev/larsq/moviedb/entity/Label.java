package dev.larsq.moviedb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Label {
    @Id
    private String label;

    @ManyToMany(mappedBy = "categories")
    private Set<Narrative> filmsOrEpisodes;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label1 = (Label) o;
        return label.equals(label1.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    public static Label create(String name) {
        Label label = new Label();
        label.setLabel(name);

        return label;
    }
}
