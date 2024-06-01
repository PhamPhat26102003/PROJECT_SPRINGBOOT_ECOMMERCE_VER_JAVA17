package projectspringboot.library.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.*;
import projectspringboot.library.repository.*;
import projectspringboot.library.service.ILaptopService;
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
    @Autowired
    private ILaptopRepository laptopRepository;

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

    //Order và cập nhật số lượng hàng trong kho, số lươn hàng trong kho sẽ giảm
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
            Laptop laptop = laptopRepository.getById(item.getLaptop().getId());
            if(laptop.getCurrentQuantity() < item.getQuantity()){
                throw new RuntimeException("Not enough quantity in stock!!!");
            }
            laptop.setCurrentQuantity(laptop.getCurrentQuantity() - item.getQuantity());
            laptopRepository.save(laptop);
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
        shoppingCartRepository.save(shoppingCart);
        orderRepository.save(order);
        return order;
    }

    //Complete order thay đổi trangj thái của đơn hàng
    @Transactional
    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING...");
        return orderRepository.save(order);
    }

    //Cancel order hủy order và trả về số lợng hàng ban đầu
    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.getById(id);
        for(OrderDetail item : order.getOrderDetailList()){
            Laptop laptop = item.getLaptop();
            laptop.setCurrentQuantity(laptop.getCurrentQuantity() + item.getQuantity());
            laptopRepository.save(laptop);
        }
        orderRepository.delete(order);
    }

    //Lấy số lượng hàng
    @Override
    public int checkQuantity(Long id) {
        Laptop laptop = laptopRepository.getById(id);
        return laptop.getCurrentQuantity();
    }
}
