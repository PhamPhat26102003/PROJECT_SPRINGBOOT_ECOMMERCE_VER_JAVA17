package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.*;
import projectspringboot.library.repository.*;
import projectspringboot.library.service.IOrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<Order> findAll(String username) {
        Customer customer = customerRepository.findByUsername(username);
        List<Order> orders = customer.getOrders();
        return orders;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderStatus("PENDING...");
        order.setDateOrder(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for(CartItem item : shoppingCart.getCartItem()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setLaptop(item.getLaptop());
            orderDetail.setUnitPrice(item.getLaptop().getCostPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(item);
        }
        order.setOrderDetailList(orderDetailList);
        shoppingCart.setCartItem(new HashSet<>());
        shoppingCart.setTotalPrice(0);
        shoppingCart.setTotalPrice(0);
        shoppingCartRepository.save(shoppingCart);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING...");
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
