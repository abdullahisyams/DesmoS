package com.example.desmosecommerce.controller;

import com.example.desmosecommerce.entity.Product;
import com.example.desmosecommerce.service.ProductService;
import com.example.desmosecommerce.service.SearchCriteria;
import com.example.desmosecommerce.service.NameCriteria;
import com.example.desmosecommerce.service.HighStockCriteria;
import com.example.desmosecommerce.service.LowStockCriteria;
import com.example.desmosecommerce.service.PriceAboveCriteria;
import com.example.desmosecommerce.service.PriceBelowCriteria;
import com.example.desmosecommerce.service.AndCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "shop";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) String price,
            Model model) {
        // Build composite criteria
        java.util.List<SearchCriteria> criteriaList = new java.util.ArrayList<>();
        if (query != null && !query.isEmpty()) {
            criteriaList.add(new NameCriteria(query));
        }
        if (stock != null) {
            if (stock.equals("high")) {
                criteriaList.add(new HighStockCriteria(5));
            } else if (stock.equals("low")) {
                criteriaList.add(new LowStockCriteria(5));
            }
        }
        if (price != null) {
            if (price.equals("above20k")) {
                criteriaList.add(new PriceAboveCriteria(20000));
            } else if (price.equals("below20k")) {
                criteriaList.add(new PriceBelowCriteria(20000));
            }
        }
        SearchCriteria criteria = criteriaList.isEmpty() ? p -> true :
            (criteriaList.size() == 1 ? criteriaList.get(0) : new AndCriteria(criteriaList.toArray(new SearchCriteria[0])));
        model.addAttribute("products", productService.searchByCriteria(criteria));
        return "shop";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product-details";
    }
} 