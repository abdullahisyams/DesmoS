package com.example.desmosecommerce.controller;

import com.example.desmosecommerce.entity.CartItem;
import com.example.desmosecommerce.entity.Product;
import com.example.desmosecommerce.entity.Order;
import com.example.desmosecommerce.service.CartService;
import com.example.desmosecommerce.service.ProductService;
import com.example.desmosecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cartcontroller")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public CartController(CartService cartService, ProductService productService, OrderService orderService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                          @RequestParam(defaultValue = "1") Integer quantity,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to add items to cart");
            return "redirect:/authcontroller/login";
        }

        Product product = productService.findById(productId);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Product not found");
            return "redirect:/maincontroller/shop";
        }

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartService.addItem(cartItem);

        redirectAttributes.addFlashAttribute("success", "Product added to cart");
        return "redirect:/cartcontroller/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/authcontroller/login";
        }

        List<CartItem> cartItems = cartService.getCartItems();
        double total = cartService.getTotal();
        double subtotal = total / (1 - cartService.getDiscountPercentage());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("discountPercentage", cartService.getDiscountPercentage());
        model.addAttribute("discountAmount", subtotal - total);
        return "cart";
    }

    @PostMapping("/remove/{itemId}")
    public String removeFromCart(@PathVariable Long itemId, HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to manage your cart");
            return "redirect:/authcontroller/login";
        }
        
        cartService.removeItem(itemId);
        redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        return "redirect:/cartcontroller/cart";
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam Long itemId,
                               @RequestParam int quantity,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to update cart");
            return "redirect:/authcontroller/login";
        }

        if (quantity <= 0) {
            cartService.removeItem(itemId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        } else {
            cartService.updateQuantity(itemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated");
        }
        return "redirect:/cartcontroller/cart";
    }

    @PostMapping("/apply-discount")
    public String applyDiscount(@RequestParam String code,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to apply discount");
            return "redirect:/authcontroller/login";
        }

        if (cartService.applyDiscount(code)) {
            redirectAttributes.addFlashAttribute("success", "Discount applied successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid discount code");
        }
        return "redirect:/cartcontroller/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to checkout");
            return "redirect:/authcontroller/login";
        }

        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cartcontroller/cart";
        }

        // Reduce stock for each item
        for (CartItem item : cartItems) {
            Product product = productService.findById(item.getProductId());
            if (product != null) {
                int newStock = product.getStock() - item.getQuantity();
                if (newStock >= 0) {
                    product.setStock(newStock);
                    productService.updateProduct(product);
                } else {
                    redirectAttributes.addFlashAttribute("error", "Not enough stock for " + product.getName());
                    return "redirect:/cartcontroller/cart";
                }
            }
        }

        // Instead of clearing the cart and finishing, redirect to payment selection
        session.setAttribute("checkoutTotal", cartService.getTotal());
        return "redirect:/cartcontroller/payment";
    }

    @GetMapping("/payment")
    public String paymentPage(HttpSession session, Model model) {
        Double total = (Double) session.getAttribute("checkoutTotal");
        if (total == null) {
            return "redirect:/cartcontroller/cart";
        }
        model.addAttribute("total", total);
        return "payment";
    }

    @PostMapping("/process-payment")
    public String processPayment(@RequestParam String paymentType, HttpSession session, RedirectAttributes redirectAttributes) {
        Double total = (Double) session.getAttribute("checkoutTotal");
        Long userId = (Long) session.getAttribute("userId");
        if (total == null || userId == null) {
            redirectAttributes.addFlashAttribute("error", "No checkout in progress");
            return "redirect:/cartcontroller/cart";
        }
        try {
            com.example.desmosecommerce.service.PaymentProviderFactory providerFactory =
                com.example.desmosecommerce.service.PaymentFactory.getProviderFactory(paymentType);
            String result = providerFactory.createPayment().pay(total);
            // Save the order
            Order order = new Order();
            order.setId(System.currentTimeMillis()); // Simple unique ID
            order.setOrderDate(LocalDateTime.now());
            order.setTotalAmount(total);
            order.setStatus("PAID");
            order.setDiscountCode(""); // Set if you have discount code logic
            order.setPaymentType(paymentType);
            order.setRefundStatus("NONE");
            orderService.save(order);
            // Clear the cart after payment
            cartService.clearCart();
            session.removeAttribute("checkoutTotal");
            redirectAttributes.addFlashAttribute("success", result);
            return "redirect:/maincontroller/shop";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cartcontroller/payment";
        }
    }
} 