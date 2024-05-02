package projectspringboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectspringboot.library.model.Category;
import projectspringboot.library.service.ICategoryService;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
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

    @RequestMapping(value = "/findById" , method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String updateCategory(Category category, RedirectAttributes redirectAttributes){
        try{
            categoryService.update((category));
            redirectAttributes.addFlashAttribute("success", "Update category successfully");
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            dataIntegrityViolationException.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update because duplicate name!!!");
        }
        catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Error server!!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete category successfully");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete!!!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes redirectAttributes){
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
