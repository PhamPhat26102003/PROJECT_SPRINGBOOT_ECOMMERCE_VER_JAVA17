package projectspringboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.dto.AccountAdminDto;
import projectspringboot.library.model.Admin;
import projectspringboot.library.model.Role;
import projectspringboot.library.repository.IRoleRepository;
import projectspringboot.library.service.IAdminService;

import java.security.Principal;
import java.util.List;

@Controller
public class ManageController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/manages")
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public String displayManagePage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Admin> admins = adminService.findAll();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("admins", admins);
        model.addAttribute("roles", roles);
        model.addAttribute("size", admins.size());
        model.addAttribute("title", "Manage");
        return "manage/manage";
    }

    @GetMapping("/add-account")
    public String displayAddNewAccountPage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("account", new AccountAdminDto());
        model.addAttribute("title", "Add new account");
        return "manage/addNew-account";
    }

    @PostMapping("/add-account")
    public String addNewAccount(@ModelAttribute("account") AccountAdminDto accountAdminDto,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("account", accountAdminDto);
                return "redirect:/add-account";
            }

            String username = accountAdminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                redirectAttributes.addFlashAttribute("failed", "Email has been exists!!!");
                model.addAttribute("account", accountAdminDto);
                return "redirect:/add-account";
            }

            if(accountAdminDto.getPassword().equals(accountAdminDto.getRepeatPass())){
                accountAdminDto.setPassword(passwordEncoder.encode(accountAdminDto.getPassword()));
                adminService.createAccount(accountAdminDto);
                model.addAttribute("account", accountAdminDto);
                redirectAttributes.addFlashAttribute("success", "Create new account success");
                return "redirect:/manages";
            }else{
                model.addAttribute("account", accountAdminDto);
                redirectAttributes.addFlashAttribute("failed", "Password is not the same!!!");
                return "redirect:/add-account";
            }
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server!!!");
            return "redirect:/add-account";
        }
    }
}
