package projectspringboot.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectspringboot.library.model.Customer;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.model.ShoppingCart;
import projectspringboot.library.service.IAccessoriesService;
import projectspringboot.library.service.ICustomerService;
import projectspringboot.library.service.ILaptopService;
import projectspringboot.library.service.IShoppingCartService;

import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ILaptopService laptopService;
    @Autowired
    private IAccessoriesService accessoriesService;

    @GetMapping("/cart")
    public String displayCartPage(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("cart", shoppingCart);
        if(shoppingCart == null){
            model.addAttribute("check", "No item in cart!!");
        }
        session.setAttribute("totalItem", shoppingCart.getTotalItem());
        model.addAttribute("subTotal", shoppingCart.getTotalPrice());
        model.addAttribute("title", "Cart");
        return "cart";
    }

    @PostMapping("/add-item-to-cart")
    public String addItemToCart(Model model,
                                Principal principal,
                                HttpServletRequest request,
                                @RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity){
        if(principal == null){
            return "redirect:/login";
        }
        Laptop laptop = laptopService.findById(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(laptop, quantity, customer);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long id,
                             Model model,
                             Principal principal){
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Laptop laptop = laptopService.findById(id);
            ShoppingCart shoppingCart = shoppingCartService.updateItemInCart(laptop, quantity, customer);

            model.addAttribute("cart", shoppingCart.getTotalItem());
            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteCart(@RequestParam("id") Long id,
                             Model model,
                             Principal principal){
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Laptop laptop = laptopService.findById(id);
            ShoppingCart shoppingCart = shoppingCartService.deleteItemInCart(laptop, customer);
            model.addAttribute("cart", shoppingCart);
            return "redirect:/cart";
        }
    }
}
