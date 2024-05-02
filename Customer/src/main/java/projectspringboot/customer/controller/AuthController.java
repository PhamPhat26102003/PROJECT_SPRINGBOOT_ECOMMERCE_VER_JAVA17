package projectspringboot.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.dto.CustomerDto;
import projectspringboot.library.model.City;
import projectspringboot.library.model.Customer;
import projectspringboot.library.repository.ICityRepository;
import projectspringboot.library.service.ICityService;
import projectspringboot.library.service.ICustomerService;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ICityService cityService;

    @GetMapping("/register")
    public String registerPage(Model model){
        List<City> cities = cityService.findAll();
        model.addAttribute("cities", cities);
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/customer-new")
    public String addNewCustomer(@ModelAttribute("customerDto")CustomerDto customerDto,
                                 Model model,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        try{
            List<City> cities = cityService.findAll();
            if(bindingResult.hasErrors()){
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                return "register";
            }

            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if(customer != null){
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                model.addAttribute("username", "Username has been existed !!!");
                return "register";
            }

            if(customerDto.getPassword().equals(customerDto.getRepeatPass())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                model.addAttribute("success", "Register success");
                redirectAttributes.addFlashAttribute("success", "Register success");
                return "redirect:/register";
            }else{
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("cities", cities);
                model.addAttribute("password", "Password is not the same!!");
                return "register";
            }
        }catch(Exception e){
            model.addAttribute("error", "Server error!!!");
            return "register";
        }
    }
}
