package projectspringboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectspringboot.library.service.IStoreService;

@RestController
@RequestMapping("/assets")
public class AssetsController {
    @Autowired
    private IStoreService storeService;

    @GetMapping("/{filename:.+}")
    public Resource getResource(@PathVariable("filename") String filename){
        return storeService.loadAsResource(filename);
    }
}
