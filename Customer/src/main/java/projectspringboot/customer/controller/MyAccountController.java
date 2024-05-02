package projectspringboot.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.City;
import projectspringboot.library.model.Customer;
import projectspringboot.library.service.ICityService;
import projectspringboot.library.service.ICustomerService;

import java.security.Principal;
import java.util.List;

@Controller
public class MyAccountController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICityService cityService;
    @GetMapping("/account")
    public String displayMyAccountPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<City> cities = cityService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("title", "My Account");
        return "account";
    }

    @RequestMapping(value="/update-info-account", method = {RequestMethod.GET, RequestMethod.PUT})
    public String UpdateInfoMyAccount(Model model,
                                      RedirectAttributes redirectAttributes,
                                      Principal principal,
                                      @ModelAttribute("customer") Customer customer){
        try{

            if(principal == null){
                return "redirect:/login";
            }
            Customer updateCustomer = customerService.update(customer);
            redirectAttributes.addFlashAttribute("customer", updateCustomer);
            redirectAttributes.addFlashAttribute("success", "Update account success");
            return "redirect:/account";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "Failed to update");
            return "redirect:/account";
        }
    }
}
