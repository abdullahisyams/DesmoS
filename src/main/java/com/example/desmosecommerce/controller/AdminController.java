package com.example.desmosecommerce.controller;

import com.example.desmosecommerce.entity.Product;
import com.example.desmosecommerce.service.ProductService;
import com.example.desmosecommerce.entity.Order;
import com.example.desmosecommerce.service.OrderService;
import com.example.desmosecommerce.service.PaymentFactory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public AdminController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("products", productService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String addProductForm(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("product", new Product());
        return "admin/edit-product";
    }

    @PostMapping("/products/add")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            HttpSession session) {
        
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }

        try {
            // Create upload directory if it doesn't exist
            Path uploadDir = Paths.get(uploadPath).toAbsolutePath();
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Generate unique filename
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);

            // Save the file
            Files.copy(image.getInputStream(), filePath);

            // Create and save the product
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setDescription(description);
            product.setImageUrl(filename); // Store just the filename

            productService.processEntity(product, "create");
            return "redirect:/admin/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/products?error=Failed to upload image";
        }
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("product", productService.findById(id));
        return "admin/edit-product";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "currentImageUrl", required = false) String currentImageUrl,
            HttpSession session) {
        
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }

        try {
            // Get the existing product
            Product existingProduct = productService.findById(id);
            if (existingProduct == null) {
                return "redirect:/admin/products?error=Product not found";
            }

            // Update the product fields
            existingProduct.setName(name);
            existingProduct.setPrice(price);
            existingProduct.setStock(stock);
            existingProduct.setDescription(description);

            if (image != null && !image.isEmpty()) {
                // Create upload directory if it doesn't exist
                Path uploadDir = Paths.get(uploadPath).toAbsolutePath();
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                // Generate unique filename
                String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = uploadDir.resolve(filename);

                // Save the file
                Files.copy(image.getInputStream(), filePath);

                // Update the image URL
                existingProduct.setImageUrl(filename); // Store just the filename
            }
            // If no new image is uploaded, keep the existing image URL
            // No need to set it explicitly as it's already set in the existing product

            productService.processEntity(existingProduct, "update");
            return "redirect:/admin/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/products?error=Failed to upload image";
        }
    }

    @GetMapping("/products/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("product", productService.findById(id));
        return "admin/view-product";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        productService.processEntity(productService.findById(id), "delete");
        return "redirect:/admin/products";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @PostMapping("/orders/refund")
    public String refundOrder(@RequestParam Long orderId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/authcontroller/admin/login";
        }
        Order order = orderService.findById(orderId);
        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Order not found");
            return "redirect:/admin/orders";
        }
        if (order.getRefundStatus() != null && order.getRefundStatus().equals("REFUNDED")) {
            redirectAttributes.addFlashAttribute("error", "Order already refunded");
            return "redirect:/admin/orders";
        }
        // Use Abstract Factory to process refund
        try {
            String paymentType = order.getPaymentType();
            com.example.desmosecommerce.service.PaymentProviderFactory providerFactory =
                PaymentFactory.getProviderFactory(paymentType);
            String result = providerFactory.createRefund().refund(order.getTotalAmount());
            orderService.updateRefundStatus(orderId, "REFUNDED");
            redirectAttributes.addFlashAttribute("success", result);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Refund failed: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
    }
}
