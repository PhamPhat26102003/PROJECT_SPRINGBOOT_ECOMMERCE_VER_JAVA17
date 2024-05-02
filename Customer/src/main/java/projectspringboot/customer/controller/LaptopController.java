package projectspringboot.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projectspringboot.library.dto.CategoryDto;
import projectspringboot.library.model.Category;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.service.ICategoryService;
import projectspringboot.library.service.ILaptopService;

import java.security.Principal;
import java.util.List;

@Controller
public class LaptopController {
    @Autowired
    private ILaptopService laptopService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/{id}")
    public String displayDetailLaptopPage(@PathVariable("id")Long id, Model model){
        Laptop laptop = laptopService.findById(id);
        Long categoryId = laptop.getCategory().getId();
        List<Laptop> relateProduct = laptopService.relateProduct(categoryId);
        model.addAttribute("relateProducts", relateProduct);
        model.addAttribute("laptop", laptop);
        model.addAttribute("title", "Detail");
        return "laptops/detail";
    }

    @GetMapping("/list-laptop")
    public String displayListLaptopPage(Model model, Principal principal, @Param("keyword") String keyword){
        List<Laptop> listLaptop = laptopService.findAll(keyword);
        model.addAttribute("laptops", listLaptop);
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "List laptop gaming");
        return "laptops/list-laptop";
    }

}
