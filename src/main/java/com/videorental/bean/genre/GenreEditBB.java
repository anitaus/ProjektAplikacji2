package com.videorental.bean.genre;

import com.videorental.dao.GenreDao;
import com.videorental.entity.Genre;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class GenreEditBB {

    private static final String PAGE_ADD_MOVIE = "addMovie?faces-redirect=true";
    private static final String PAGE_EDIT_MOVIE = "editMovie?faces-redirect=true";
    private static final String PAGE_ERROR = "error?faces-redirect=true";

    private Genre genre = new Genre();

    @EJB
    GenreDao genreDao;

    @Inject
    FacesContext context;

    public Genre getGenre() {
        return genre;
    }

    public String save(Genre genre) {
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        try {
            genreDao.create(genre);
            if(session.getAttribute("movie") == null){
                return PAGE_ADD_MOVIE;
            } else {
                return PAGE_EDIT_MOVIE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }

    public String delete(Genre genre) {
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        genreDao.delete(genre);
        if (session.getAttribute("movie") == null){
            return PAGE_ADD_MOVIE;
        } else {
            return PAGE_EDIT_MOVIE;
        }
    }
}
