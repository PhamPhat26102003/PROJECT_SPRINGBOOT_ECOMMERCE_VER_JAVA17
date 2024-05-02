package projectspringboot.library.service;

import org.w3c.dom.stylesheets.LinkStyle;
import projectspringboot.library.model.Order;
import projectspringboot.library.model.ShoppingCart;

import java.util.List;

public interface IOrderService {
    List<Order> findAll(String username);
    List<Order> findAllOrders();
    Order saveOrder(ShoppingCart shoppingCart);
    Order acceptOrder(Long id);
    void cancelOrder(Long id);
}
