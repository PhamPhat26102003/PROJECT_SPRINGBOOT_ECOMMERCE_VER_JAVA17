package projectspringboot.customer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import projectspringboot.library.model.Customer;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.model.ShoppingCart;
import projectspringboot.library.repository.ILaptopRepository;
import projectspringboot.library.service.ICustomerService;
import projectspringboot.library.service.ILaptopService;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private ILaptopService laptopService;
    @Autowired
    private ILaptopRepository laptopRepository;
    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(@PageableDefault(size = 8)Pageable pageable,
                           Model model,
                           Principal principal,
                           HttpSession session){
        Page<Laptop> laptops = laptopRepository.findAll(pageable);
        model.addAttribute("laptops", laptops);
        model.addAttribute("title", "Laptop Gaming");
        if (principal != null){
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName() + ' ' + customer.getLastName());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            session.setAttribute("totalItem", shoppingCart.getTotalItem());
        }else{
            session.removeAttribute("username");
        }
        return "index";
    }
}
