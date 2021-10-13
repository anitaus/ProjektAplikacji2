package com.videorental.bean.customer;

import com.videorental.dao.CustomerDao;
import com.videorental.entity.Customer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class CustomerListBB {

    private static final String PAGE_CUSTOMER_EDIT = "editCustomer?faces-redirect=true";

    @EJB
    CustomerDao customerDao;

    @Inject
    ExternalContext externalContext;

    public List<Customer> getAll(){
        return customerDao.getAll();
    }

    public String editCustomer(Customer customer){
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.setAttribute("customer", customer);
        return PAGE_CUSTOMER_EDIT;
    }
}
