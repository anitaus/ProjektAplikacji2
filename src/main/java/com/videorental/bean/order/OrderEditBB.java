package com.videorental.bean.order;

import com.videorental.dao.CustomerDao;
import com.videorental.dao.MovieDao;
import com.videorental.dao.OrderDao;
import com.videorental.entity.Customer;
import com.videorental.entity.Movie;
import com.videorental.entity.Order;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Named
@RequestScoped
public class OrderEditBB {

    private static final String PAGE_MOVIE_ORDER_LIST = "orderList?faces-redirect=true";

    private Order order = new Order();

    @EJB
    OrderDao orderDao;

    @EJB
    MovieDao movieDao;

    @EJB
    CustomerDao customerDao;

    public Order getOrder() {
        return order;
    }

    public String save(Order order, Long movieId, Long customerId){
        Movie movie = movieDao.findById(movieId);
        Customer customer = customerDao.findById(customerId);
        order.setPurchaseDate(LocalDate.now());
        order.setMovie(movie);
        order.setCustomer(customer);
        order.setTotalAmount(calculateTotalAmount(order.getPurchaseDate(), order.getReturnDate(), order.getMovie().getPrice()));
        order.setClosed(false);
        orderDao.create(order);
        updateMovieAvailable(order.getMovie(), false);
        return PAGE_MOVIE_ORDER_LIST;
    }

    public String delete(Order order){
        updateMovieAvailable(order.getMovie(), true);
        orderDao.delete(order);
        return PAGE_MOVIE_ORDER_LIST;
    }

    public String closeOrder(Order order){
        order.setClosed(true);
        orderDao.update(order);
        updateMovieAvailable(order.getMovie(), true);
        return PAGE_MOVIE_ORDER_LIST;
    }

    private void updateMovieAvailable(Movie movie, Boolean bool) {
        movie.setAvailable(bool);
        movieDao.update(movie);
    }

    private BigDecimal calculateTotalAmount(LocalDate purchaseDate, LocalDate returnDate, BigDecimal price){
        Period period = Period.between(purchaseDate, returnDate);
        return price.multiply(BigDecimal.valueOf(period.getDays()));
    }
}
