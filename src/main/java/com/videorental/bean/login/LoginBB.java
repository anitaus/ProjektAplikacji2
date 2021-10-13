package com.videorental.bean.login;

import com.videorental.config.RemoteClient;
import com.videorental.dao.UserDao;
import com.videorental.entity.User;
import com.videorental.type.UserType;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBB implements Serializable {
    
    private static final String PAGE_INDEX = "index?faces-redirect=true";
    private static final String PAGE_LOGIN = "login?faces-redirect=true";
    private static final String PAGE_LOGIN_ERROR = "loginError?faces-redirect=true";

    private User user = new User();
    private Boolean admin;
    private String email;
    private String password;

    @Inject
    FacesContext context;

    @EJB
    UserDao userDao;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String validate(){
        context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        user = userDao.findByEmailAndPassword(email, password);

        if(user == null) {
            return PAGE_LOGIN_ERROR;
        }
        RemoteClient<User> remoteClient = new RemoteClient<User>();
        remoteClient.setDetails(user);
        remoteClient.getRoles().add(user.getUserType().toString());
        remoteClient.store(request);

        if(user.getUserType() == UserType.ADMIN_ROLE){
            admin = true;
        } else {
            admin = false;
        }
        return PAGE_INDEX;
    }

    public String logout(){
        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        user = null;
        admin = null;
        session.invalidate();
        return PAGE_LOGIN;
    }
}
