package com.videorental.bean.order;

import com.videorental.config.RemoteClient;
import com.videorental.dao.CustomerDao;
import com.videorental.dao.OrderDao;
import com.videorental.entity.Customer;
import com.videorental.entity.Order;
import com.videorental.entity.User;
import com.videorental.type.UserType;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class OrderListBB {

    @EJB
    OrderDao orderDao;

    @EJB
    CustomerDao customerDao;

    @Inject
    FacesContext context;

    public List<Order> getOrders(){
        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        RemoteClient<User> remoteClient = RemoteClient.load(session);
        User user = remoteClient.getDetails();
        if (user.getUserType() == UserType.USER_ROLE){
            return orderDao.getByCustomer(customerDao.findByEmail(user.getEmail()));
        } else {
            return orderDao.getAll();
        }
    }
}
