package com.videorental.dao;

import com.videorental.entity.Address;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class AddressDao {

    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    EntityManager em;

    public List<Address> getAll() {
        return em.createQuery("from Address", Address.class).getResultList();
    }

    public Address findById(Long id) {
        return em.find(Address.class, id);
    }

    public void create(Address address) {
       em.persist(address);
    }

    public void update(Address address) {
        em.merge(address);
    }

    public void delete(Address address) {
        em.remove(em.contains(address) ? address:em.merge(address));
    }
}
