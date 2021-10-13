package com.videorental.bean.user;

import com.videorental.dao.UserDao;
import com.videorental.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class UserEditBB {

    private static final String PAGE_USER_LIST = "userList?faces-redirect=true";
    private static final String PAGE_ERROR = "error?faces-redirect=true";

    private User user = new User();

    @EJB
    UserDao userDao;

    @Inject
    FacesContext context;

    public User getUser() {
        return user;
    }

    public String save(User user){
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        try {
            if (user.getId() == null){
                userDao.create(user);
            } else {
                userDao.update(user);
            }
            session.removeAttribute("movie");
            return PAGE_USER_LIST;
        } catch (Exception e){
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }

    public String delete(User user){
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        try {
            userDao.delete(user);
            session.removeAttribute("movie");
            return PAGE_USER_LIST;
        } catch (Exception e){
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }
}
