package com.springMVC.demo.controllers;

import com.springMVC.demo.models.Category;
import com.springMVC.demo.models.Product;
import com.springMVC.demo.repositories.CategoryRepository;
import com.springMVC.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping(path="products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value="/getProductsByCategoryID/{categoryID}",method = RequestMethod.GET)
    public String getProductsByCategoryID(ModelMap modelMap, @PathVariable String categoryID){
        Iterable<Product> products = productRepository.findByCategoryID(categoryID);
        modelMap.addAttribute("products",products);
        return "productList";
    }

    //href="products/changeCategory/${product.getProductID()}
    @RequestMapping(value="/changeCategory/{productID}",method = RequestMethod.GET)
    public String changeCategory(ModelMap modelMap, @PathVariable String productID){
        Iterable<Category> categories = categoryRepository.findAll();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("product", productRepository.findById(productID).get());
        return "assign";
    }

    ///products/updateProduct/${product.getProductID()}
    @RequestMapping(value = "/updateProduct/{productID}", method = RequestMethod.POST)
    public String updateProduct(ModelMap modelMap,
                                @ModelAttribute("product") Product product,
                                @PathVariable String productID
    ) {
        if(productRepository.findById(productID).isPresent()) {
            Product foundProduct = productRepository
                    .findById(product.getProductID()).get();
            if(product.getProductName() != null) {
                foundProduct.setProductName(product.getProductName() );
            }
            if(product.getCategoryID() != null) {
                foundProduct.setCategoryID(product.getCategoryID());
            }
            if(product.getDescription() != null) {
                foundProduct.setDescription(product.getDescription());
            }
            if(product.getPrice() > 0) {
                foundProduct.setPrice(product.getPrice());
            }
            productRepository.save(foundProduct);
        }
        return "redirect:/products/getProductsByCategoryID/"+product.getCategoryID();
    }
}
