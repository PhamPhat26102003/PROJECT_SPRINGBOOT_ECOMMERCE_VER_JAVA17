package projectspringboot.customer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.Customer;
import projectspringboot.library.model.Order;
import projectspringboot.library.model.ShoppingCart;
import projectspringboot.library.service.ICustomerService;
import projectspringboot.library.service.IOrderService;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IOrderService orderService;
    @GetMapping("/checkout")
    public String displayMyAccountPage(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("customer", customer);
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("subTotal", shoppingCart.getTotalPrice());
        session.setAttribute("totalItem", shoppingCart.getTotalItem());
        model.addAttribute("title", "Checkout");
        return "checkout";
    }

    @GetMapping("/order")
    public String displayOrderPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orders = customer.getOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("title", "Order");
        return "order";
    }

    @PostMapping("/order-laptop")
    public String orderLaptop(Principal principal, Model model, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Order order =  orderService.saveOrder(shoppingCart);
        model.addAttribute("orders", order);
        session.removeAttribute("totalItem");
        return "redirect:/order";
    }

    @RequestMapping(value = "/accept-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String acceptOrder(Long id, Principal principal, RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        orderService.acceptOrder(id);
        redirectAttributes.addFlashAttribute("success", "Order accepted");
        return "redirect:/order";
    }

    @RequestMapping(value = "/cancel-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(Long id, Principal principal, RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        orderService.cancelOrder(id);
        redirectAttributes.addFlashAttribute("success", "Cancel order success");
        return "redirect:/order";
    }
}
