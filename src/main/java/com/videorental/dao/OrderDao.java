package com.videorental.dao;

import com.videorental.entity.Customer;
import com.videorental.entity.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class OrderDao {

    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager em;

    public List<Order> getAll() {
        return em.createQuery("from Order", Order.class).getResultList();
    }

    public List<Order> getByCustomer(Customer customer){
        return em
                .createQuery("select c from Order c where c.customer = :customer", Order.class)
                .setParameter("customer", customer)
                .getResultList();
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public void delete(Order order) {
        em.remove(em.contains(order) ? order :em.merge(order));
    }

    public void update(Order order) {
        em.merge(order);
    }

    public void create(Order order) {
        em.persist(order);
    }
}
