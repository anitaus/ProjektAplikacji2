package com.videorental.dao;

import com.videorental.entity.Movie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class MovieDao {

    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    EntityManager em;

    public List<Movie> getAll() {
        return em.createQuery("from Movie", Movie.class).getResultList();
    }

    public List<Movie> getByAvailability(){
        return em.createQuery("from Movie where isAvailable = true", Movie.class).getResultList();
    }

    public Movie findById(Long id) {

        return em.find(Movie.class, id);
    }

    public void create(Movie movie) {
        em.persist(movie);
    }

    public void update(Movie movie) {
        em.merge(movie);
    }

    public void delete(Movie movie) {
        em.remove(em.contains(movie) ? movie:em.merge(movie));
    }
}
