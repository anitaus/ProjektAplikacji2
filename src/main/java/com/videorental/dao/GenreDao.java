package com.videorental.dao;

import com.videorental.entity.Genre;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class GenreDao {
    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    EntityManager em;

    public List<Genre> getAll() {
        return em.createQuery("from Genre", Genre.class).getResultList();
    }

    public Genre findById(Long id) {
        return em.find(Genre.class, id);
    }

    public void create(Genre genre) {
        em.persist(genre);
    }

    public void update(Genre genre) {
        em.merge(genre);
    }

    public void delete(Genre genre) {
        em.remove(em.contains(genre) ? genre:em.merge(genre));
    }
}
