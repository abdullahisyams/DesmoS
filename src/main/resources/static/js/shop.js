document.addEventListener('DOMContentLoaded', function() {
    // Product data (in a real application, this would come from a database)
    const products = [
        {
            id: 1,
            name: "Ducati Panigale V4",
            price: 24999,
            category: "sport",
            image: "/images/products/panigale-v4.jpg"
        },
        {
            id: 2,
            name: "Ducati Streetfighter V4",
            price: 22999,
            category: "naked",
            image: "/images/products/streetfighter-v4.jpg"
        },
        // Add more products as needed
    ];

    // Function to display products
    function displayProducts(productsToShow) {
        const productsContainer = document.querySelector('.products-container');
        if (!productsContainer) return;

        productsContainer.innerHTML = '';
        
        productsToShow.forEach(product => {
            const productCard = `
                <div class="product-card" data-category="${product.category}">
                    <img src="${product.image}" alt="${product.name}">
                    <h3>${product.name}</h3>
                    <p class="price">$${product.price.toLocaleString()}</p>
                    <button onclick="addToCart(${product.id})" class="add-to-cart-btn">Add to Cart</button>
                </div>
            `;
            productsContainer.innerHTML += productCard;
        });
    }

    // Function to filter products
    function filterProducts(category) {
        const filteredProducts = category === 'all' 
            ? products 
            : products.filter(product => product.category === category);
        displayProducts(filteredProducts);
    }

    // Add event listeners to filter buttons
    const filterButtons = document.querySelectorAll('.filter-btn');
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const category = button.dataset.category;
            filterProducts(category);
            
            // Update active button state
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
        });
    });

    // Initial display of all products
    displayProducts(products);
});

// Function to add product to cart
function addToCart(productId) {
    // Get existing cart from localStorage
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Add product to cart
    cart.push(productId);
    
    // Save updated cart
    localStorage.setItem('cart', JSON.stringify(cart));
    
    // Show success message
    alert('Product added to cart!');
} 