document.addEventListener('DOMContentLoaded', function() {
    // Show flash messages if they exist
    const flashMessages = document.querySelectorAll('.flash-message');
    flashMessages.forEach(message => {
        setTimeout(() => {
            message.style.opacity = '0';
            setTimeout(() => message.remove(), 300);
        }, 3000);
    });

    // Handle quantity form submissions
    const quantityForms = document.querySelectorAll('.quantity-form');
    quantityForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);
            const action = formData.get('action');
            const url = this.action;

            fetch(url, {
                method: 'POST',
                body: formData
            }).then(response => {
                if (response.ok) {
                    window.location.reload();
                }
            });
        });
    });

    // Handle remove item form submissions
    const removeForms = document.querySelectorAll('.remove-form');
    removeForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to remove this item?')) {
                const formData = new FormData(this);
                const url = this.action;

                fetch(url, {
                    method: 'POST',
                    body: formData
                }).then(response => {
                    if (response.ok) {
                        window.location.reload();
                    }
                });
            }
        });
    });

    // Handle clear cart form submission
    const clearCartForm = document.querySelector('.clear-cart-form');
    if (clearCartForm) {
        clearCartForm.addEventListener('submit', function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to clear your cart?')) {
                const formData = new FormData(this);
                const url = this.action;

                fetch(url, {
                    method: 'POST',
                    body: formData
                }).then(response => {
                    if (response.ok) {
                        window.location.reload();
                    }
                });
            }
        });
    }

    // Product data (same as in shop.js, in a real app this would come from a database)
    const products = [
        {
            id: 1,
            name: "Ducati Panigale V4",
            price: 24999,
            image: "/images/products/panigale-v4.jpg"
        },
        {
            id: 2,
            name: "Ducati Streetfighter V4",
            price: 22999,
            image: "/images/products/streetfighter-v4.jpg"
        }
    ];

    // Function to display cart items
    function displayCart() {
        const cartContainer = document.querySelector('.cart-items');
        const totalElement = document.querySelector('.cart-total');
        if (!cartContainer || !totalElement) return;

        // Get cart from localStorage
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        
        // Count items
        const itemCounts = cart.reduce((acc, id) => {
            acc[id] = (acc[id] || 0) + 1;
            return acc;
        }, {});

        // Clear cart container
        cartContainer.innerHTML = '';
        let total = 0;

        // Display each unique item
        Object.entries(itemCounts).forEach(([id, quantity]) => {
            const product = products.find(p => p.id === parseInt(id));
            if (!product) return;

            const itemTotal = product.price * quantity;
            total += itemTotal;

            const cartItem = `
                <div class="cart-item" data-id="${product.id}">
                    <img src="${product.image}" alt="${product.name}">
                    <div class="item-details">
                        <h3>${product.name}</h3>
                        <p class="price">$${product.price.toLocaleString()}</p>
                        <div class="quantity-controls">
                            <button onclick="updateQuantity(${product.id}, ${quantity - 1})">-</button>
                            <span>${quantity}</span>
                            <button onclick="updateQuantity(${product.id}, ${quantity + 1})">+</button>
                        </div>
                    </div>
                    <button onclick="removeFromCart(${product.id})" class="remove-btn">Remove</button>
                </div>
            `;
            cartContainer.innerHTML += cartItem;
        });

        // Update total
        totalElement.textContent = `Total: $${total.toLocaleString()}`;
    }

    // Function to update item quantity
    window.updateQuantity = function(productId, newQuantity) {
        if (newQuantity < 1) {
            removeFromCart(productId);
            return;
        }

        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        
        // Remove all instances of the product
        cart = cart.filter(id => id !== productId);
        
        // Add the new quantity
        for (let i = 0; i < newQuantity; i++) {
            cart.push(productId);
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        displayCart();
    };

    // Function to remove item from cart
    window.removeFromCart = function(productId) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        cart = cart.filter(id => id !== productId);
        localStorage.setItem('cart', JSON.stringify(cart));
        displayCart();
    };

    // Function to clear cart
    window.clearCart = function() {
        localStorage.removeItem('cart');
        displayCart();
    };

    // Initial display of cart
    displayCart();
}); 