package com.videorental.bean.customer;

import com.videorental.dao.CustomerDao;
import com.videorental.dao.UserDao;
import com.videorental.entity.Address;
import com.videorental.entity.Customer;
import com.videorental.entity.User;
import com.videorental.type.UserType;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Random;

@Named
@RequestScoped
public class CustomerEditBB {

    private static final String PAGE_CUSTOMER_LIST = "customerList?faces-redirect=true";
    private static final String PAGE_ERROR = "error?faces-redirect=true";

    private Customer customer = new Customer();
    private Address address = new Address();

    @EJB
    CustomerDao customerDao;

    @EJB
    UserDao userDao;

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public String save(Customer customer) {
        try {
            if (customer.getId() == null) {
                customer.setAddress(address);
                customerDao.create(customer);
            } else {
                customerDao.update(customer);
            }
            return PAGE_CUSTOMER_LIST;
        } catch (Exception e) {
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }

    public String delete(Customer customer){
        try {
            customerDao.delete(customer);
            return PAGE_CUSTOMER_LIST;
        } catch (Exception e){
            e.printStackTrace();
            return PAGE_ERROR;
        }
    }

    public String createUserFromCustomer(Customer customer){
        User user = new User();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setEmail(customer.getEmail());
        user.setUserType(UserType.USER_ROLE);
        user.setPassword(generateDefaultPassword());
        userDao.create(user);
        return PAGE_CUSTOMER_LIST;
    }

    private String generateDefaultPassword(){
        String pass = "";
        for (int i = 0; i < 5; i++) {
            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            pass = pass + c;
        }
        return pass;
    }
}
