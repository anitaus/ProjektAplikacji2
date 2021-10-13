package com.videorental.dao;

import com.videorental.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class CustomerDao {

    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    EntityManager em;

    public List<Customer> getAll() {
        return em.createQuery("from Customer", Customer.class).getResultList();
    }
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public void create(Customer customer) {
        em.persist(customer);
    }

    public void update(Customer customer) {
        em.merge(customer);
    }

    public void delete(Customer customer) {
        em.remove(em.contains(customer) ? customer:em.merge(customer));
    }

    public Customer findByEmail(String email) {
        return em
                .createQuery("select c from Customer c where c.email = :email", Customer.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
