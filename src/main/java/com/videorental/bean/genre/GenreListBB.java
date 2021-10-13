package com.videorental.bean.genre;

import com.videorental.dao.GenreDao;
import com.videorental.entity.Genre;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GenreListBB {

    @EJB
    GenreDao genreDao;

    public List<Genre> getAll(){
        return genreDao.getAll();
    }
}
