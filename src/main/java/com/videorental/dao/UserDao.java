package com.videorental.dao;

import com.videorental.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Stateless
public class UserDao {

    private static final String UNIT_NAME = "videorentalPU";

    @PersistenceContext(unitName = UNIT_NAME)
    EntityManager em;

    public void create(User user){
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) {
        em.remove(em.contains(user) ? user:em.merge(user));
    }

    public List<User> getAll(){
        return em.createQuery("from User", User.class).getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByEmail(String email){
        return em.createQuery("select c from User c where c.email = :email", User.class).setParameter("email", email).getSingleResult();
    }

    public User findByEmailAndPassword(String email, String password) {
        return em
                .createQuery("select c from User c where c.email = :email and c.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
}
