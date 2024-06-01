package projectspringboot.admin.controller;

import org.hibernate.internal.util.beans.BeanInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.Category;
import projectspringboot.library.service.ICategoryService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public String categories(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        else{
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("size", categories.size());
            model.addAttribute("title", "Manage category");
            model.addAttribute("categoryNew", new Category());
            return "category/category";
        }
    }

    @GetMapping("/add-category")
    public String displayAddNewCategoryPage(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Add category");
        return "category/addNew-category";
    }

    @PostMapping("/add-category")
    public String addNewCategory(@ModelAttribute("categoryNew") Category category, RedirectAttributes redirectAttributes){
        try{
                categoryService.save(category);
                redirectAttributes.addFlashAttribute("success", "Add category successfully!!");
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            dataIntegrityViolationException.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to add duplicate name!!");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server!1");
        }
        return "redirect:/categories";
    }

    @GetMapping("/update-category/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public String displayUpdateCategoryPage(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("title", "Update category");
        return "category/edit-category";
    }

    @PostMapping("/update-category/{id}")
    public String updateCategory(Category category,
                                 RedirectAttributes redirectAttributes,
                                 BindingResult bindingResult,
                                 Model model){
        try{
            if(bindingResult.hasErrors()){
                model.addAttribute("category", category);
            }
            categoryService.update((category));
            redirectAttributes.addFlashAttribute("success", "Update category successfully");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update!!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete category successfully");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable success");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to enable!!!");
        }
        return "redirect:/categories";
    }

}
