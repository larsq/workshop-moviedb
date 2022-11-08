package dev.larsq.moviedb.repository;

import dev.larsq.moviedb.entity.Narrative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MovieRepository {
    private final EntityManager entitymanager;

    @Autowired
    public MovieRepository(EntityManager entitymanager) {
        this.entitymanager = entitymanager;
    }

    public Narrative findById(String id) {
        return entitymanager.find(Narrative.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Narrative> search(String part) {
        Query query = entitymanager.createNativeQuery("select * from narrative where lower(title) like :pattern", Narrative.class);

        return (List<Narrative>) query.setParameter("pattern", "%%%s%%".formatted(part.toLowerCase())).getResultList();
    }
}
