package com.videorental.bean.movie;

import com.videorental.dao.GenreDao;
import com.videorental.dao.MovieDao;
import com.videorental.entity.Genre;
import com.videorental.entity.Movie;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MovieEditBB {

    private static final String PAGE_MOVIE_LIST = "movieList?faces-redirect=true";
    private static final String PAGE_ERROR = "error?faces-redirect=true";

    private Movie movie = new Movie();
    private Genre genre = new Genre();

    @EJB
    MovieDao movieDao;

    @EJB
    GenreDao genreDao;

    @Inject
    FacesContext context;

    public Movie getMovie() {
        return movie;
    }

    public Genre getGenre() {
        return genre;
    }

    public String save(Movie movie, Genre genre){
        try {
            movie.setGenre(genreDao.findById(genre.getId()));
            movie.setAvailable(true);
            if(movie.getId() == null){
                movieDao.create(movie);
            } else {
                movieDao.update(movie);
            }
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.removeAttribute("movie");
            return PAGE_MOVIE_LIST;
        } catch (Exception e) {
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }

    public String delete(Movie movie){
        movieDao.delete(movie);
        return PAGE_MOVIE_LIST;
    }
}
