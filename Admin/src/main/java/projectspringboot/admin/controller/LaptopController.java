package projectspringboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.Category;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.service.ICategoryService;
import projectspringboot.library.service.ILaptopService;
import projectspringboot.library.service.IStoreService;

import java.security.Principal;
import java.util.List;

@Controller
public class LaptopController {

    @Autowired
    private ILaptopService laptopService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IStoreService storeService;

    //Phan trang
    @GetMapping("/laptop/{pageNo}")
    public String pageLaptops(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Page<Laptop> laptopPage = laptopService.pageLaptops(pageNo);
        model.addAttribute("title", "Laptop page");
        model.addAttribute("size", laptopPage.getSize());
        model.addAttribute("totalPage", laptopPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("laptops", laptopPage);
        return "laptops/laptops";
    }

    //Tim kiem laptop
    @GetMapping("/search-result/{pageNo}")
    public String searchLaptop(@PathVariable("pageNo") int pageNo,
                               @RequestParam("keyword") String keyword,
                               Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Page<Laptop> laptops = laptopService.filterLaptop(keyword, pageNo);
        model.addAttribute("title", "Search page");
        model.addAttribute("laptops", laptops);
        model.addAttribute("size", laptops.getSize());
        model.addAttribute("totalPage", laptops.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "laptops/result-laptop";
    }

    @GetMapping("/add-laptop")
    public String displayAddLaptopPage(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("laptop", new Laptop());
        model.addAttribute("title", "Add new laptop page");
        return "laptops/addNew-laptop";
    }

    @PostMapping("/add-laptop")
    public String addNewLaptop(@Validated Laptop laptop,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        try{
            if(bindingResult.hasErrors()|| laptop.getImage().isEmpty()){
                if(laptop.getImage().isEmpty()){
                    bindingResult.rejectValue("image", "MultipartNotEmpty");
                }
                List<Category> categories = categoryService.findAll();
                model.addAttribute("categories", categories);
                model.addAttribute("laptops", laptop);
                return "redirect:/add-laptop";
            }

            String filename = storeService.storeFile(laptop.getImage());
            laptop.setFilename(filename);
            laptopService.save(laptop);
            redirectAttributes.addFlashAttribute("success", "Add new laptop success");
            return "redirect:/laptop/0";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add new laptop!!!");
            return "redirect:/laptop/0";
        }
    }

    @GetMapping("/update-laptop/{id}")
    public String displayUpdateLaptopPage(@PathVariable Long id, Model model){
        Laptop laptop = laptopService.findById(id);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("laptops", laptop);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Update laptop");
        return "laptops/edit-laptop";
    }

    @PostMapping("/update-laptop/{id}")
    public String updateLaptop( @Validated Laptop laptop,
                               Model model, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        try{

            if(bindingResult.hasErrors()){
                List<Category> categories = categoryService.findAll();
                model.addAttribute("categories", categories);
                model.addAttribute("laptops", laptop);
                return "laptops/edit-laptop";
            }
            laptopService.updateLaptop(laptop);
            redirectAttributes.addFlashAttribute("success", "Update successfully");
            return "redirect:/laptop/0";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!");
        }
        return "redirect:/laptop/0";
    }

    @RequestMapping(value = "/enable-laptop/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableLaptop(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            laptopService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully");
            return "redirect:/laptop/0";
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            redirectAttributes.addFlashAttribute("failed", "Failed to enable!!!");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error to server!!!");
        }
        return "redirect:/laptop/0";
    }
    @RequestMapping(value = "/delete-laptop/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteLaptop(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            laptopService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete successfully");
            return "redirect:/laptop/0";
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error to server!!!");
        }
        return "redirect:/laptop/0";
    }
}
