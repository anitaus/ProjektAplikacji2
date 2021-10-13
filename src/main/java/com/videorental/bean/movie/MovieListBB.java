package com.videorental.bean.movie;

import com.videorental.dao.MovieDao;
import com.videorental.entity.Movie;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class MovieListBB {

    private static final String PAGE_MOVIE_EDIT = "editMovie?faces-redirect=true";
    private static final String PAGE_ADD_MOVIE = "addMovie?faces-redirect=true";

    @EJB
    MovieDao movieDao;

    @Inject
    FacesContext context;

    public List<Movie> getAll(){
        return movieDao.getAll();
    }
    public List<Movie> getByAvailability() {
        return movieDao.getByAvailability();
    }

    public String movieEdit(Movie movie){
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("movie", movie);
        return PAGE_MOVIE_EDIT;
    }

    public String addMovie(){
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        try {
            session.removeAttribute("movie");
        } catch (Exception e){
            e.printStackTrace();
        }
        return PAGE_ADD_MOVIE;
    }
}
