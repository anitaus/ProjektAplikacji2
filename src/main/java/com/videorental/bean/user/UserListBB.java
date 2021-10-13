package com.videorental.bean.user;

import com.videorental.dao.UserDao;
import com.videorental.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class UserListBB {

    private static final String PAGE_USER_EDIT = "editUser?faces-redirect=true";

    @EJB
    UserDao userDao;

    @Inject
    ExternalContext externalContext;

    public List<User> getAll() {
        return userDao.getAll();
    }

    public String userEdit(User user){
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.setAttribute("user", user);
        return PAGE_USER_EDIT;
    }

}