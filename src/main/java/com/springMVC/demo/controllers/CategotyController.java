package com.springMVC.demo.controllers;

import com.springMVC.demo.models.Category;
import com.springMVC.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="categories")
public class CategotyController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value="",method = RequestMethod.GET)
    public String getAllCategories(ModelMap modelMap) {
        Iterable<Category> categories = categoryRepository.findAll();
        modelMap.addAttribute("categories", categories);
        return "category";
    }
}
