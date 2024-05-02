package projectspringboot.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projectspringboot.library.model.Accessories;
import projectspringboot.library.service.IAccessoriesService;
import projectspringboot.library.service.ICategoryService;

import java.util.List;

@Controller
public class AccessoriesController {

    @Autowired
    private IAccessoriesService accessoriesService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list-accessory")
    public String displayListAccessoriesPage(Model model, @Param("keyword") String keyword){
        List<Accessories> accessories = accessoriesService.findAll(keyword);
        model.addAttribute("accessories", accessories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "List Accessories");
        return "accessories/list-accessory";
    }

    @GetMapping("/accessories/{id}")
    public String displayDetailAccessory(@PathVariable("id") Long id, Model model){
        Accessories accessories = accessoriesService.findById(id);
        Long categoryId = accessories.getCategory().getId();
        List<Accessories> relatedProduct = accessoriesService.relatedProduct(categoryId);
        model.addAttribute("relatedProducts", relatedProduct);
        model.addAttribute("accessory", accessories);
        model.addAttribute("title", "Detail");
        return "accessories/detail";
    }

}
