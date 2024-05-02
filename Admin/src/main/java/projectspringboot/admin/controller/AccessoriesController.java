package projectspringboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.Accessories;
import projectspringboot.library.model.Category;
import projectspringboot.library.service.IAccessoriesService;
import projectspringboot.library.service.ICategoryService;
import projectspringboot.library.service.IStoreService;

import java.security.Principal;
import java.util.List;

@Controller
public class AccessoriesController {
    @Autowired
    private IAccessoriesService accessoriesService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IStoreService storeService;

    @GetMapping("/accessories")
    public String displayAccessoriesPage(Model model, Principal principal, @Param("keyword") String keyword){
        if(principal == null){
            return "redirect:/login";
        }
        List<Accessories> accessoriesList = accessoriesService.findAll(keyword);
        model.addAttribute("accessories", accessoriesList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", accessoriesList.size());
        model.addAttribute("title", "Accessories page");
        return "accessories/accessories";
    }

    @GetMapping("/add-accessories")
    public String displayAddNewAccessoriesPage(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("accessory", new Accessories());
        model.addAttribute("title", "Add new accessories");
        return "accessories/addNew-accessory";
    }

    @PostMapping("/add-accessories")
    public String addNewAccessory(@Validated Accessories accessories,
                                  Model model,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        try{
            if(bindingResult.hasErrors() || accessories.getImage().isEmpty()){
                if(accessories.getImage().isEmpty()){
                    redirectAttributes.addFlashAttribute("image", "Image mustn't null!!");
                }
                List<Category> categories = categoryService.findAllByActivated();
                model.addAttribute("categories", categories);
                model.addAttribute("accessories", accessories);
                return "redirect:/add-accessories";
            }
            String filename = storeService.storeFile(accessories.getImage());
            accessories.setFilename(filename);
            accessoriesService.save(accessories);
            redirectAttributes.addFlashAttribute("success", "Add new accessories success");
            return "redirect:/accessories";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add new accessories!!!");
            return "redirect:/add-accessories";
        }
    }

    @RequestMapping(value = "/enable-accessories/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            accessoriesService.activatedById(id);
            redirectAttributes.addFlashAttribute("success", "Enable success");
            return "redirect:/accessories";
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            dataIntegrityViolationException.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to enable!!!");
            return "redirect:/accessories";
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error to server!!!");
            return "redirect:/accessories";
        }
    }

    @RequestMapping(value = "/delete-accessories/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            accessoriesService.deletedById(id);
            redirectAttributes.addFlashAttribute("success", "Delete success");
            return "redirect:/accessories";
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            dataIntegrityViolationException.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
            return "redirect:/accessories";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error to server!!!");
            return "redirect:/accessories";
        }
    }

    @GetMapping("/update-accessories/{id}")
    public String displayUpdateAccessoriesPage(@PathVariable("id") Long id, Model model){
        Accessories accessories = accessoriesService.findById(id);
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("accessories", accessories);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Update accessories");
        return "accessories/edit-accessory";
    }

    @PostMapping("/update-accessories/{id}")
    public String updateAccessory(@Validated Accessories accessories,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                List<Category> categories = categoryService.findAllByActivated();
                model.addAttribute("categories", categories);
                model.addAttribute("accessories", accessories);
            }
            accessoriesService.updateAccessories(accessories);
            redirectAttributes.addFlashAttribute("success", "Update success");
            return "redirect:/accessories";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!!");
            return "redirect:/update-accessories/{id}";
        }
    }

//    @GetMapping("/search-result")
//    public String searchAccessories(@RequestParam("keyword") String keyword, Model model){
//        List<Accessories> accessories = accessoriesService.searchAccessories(keyword);
//        model.addAttribute("title", "Result");
//        model.addAttribute("accessories", accessories);
//        return "accessories/accessories-result";
//    }
}
