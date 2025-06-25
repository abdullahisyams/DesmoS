document.querySelector(".buttonContainer2").addEventListener("click", function () {
    const audio = document.getElementById("soundCheck");
    audio.play();

    setTimeout(() => {
        audio.pause();
        audio.currentTime = 0; 
    }, 13000);
});

document.querySelector(".buttonContainer4").addEventListener("click", function () {
    const audio = document.getElementById("dryClutch");
    audio.play();

    setTimeout(() => {
        audio.pause();
        audio.currentTime = 0; 
    }, 10000);
});

// Wait until the DOM content is fully loaded before running the script
document.addEventListener('DOMContentLoaded', function () {
    let currentIndex = 0; 
    const slides = document.querySelectorAll('.carouselItem'); 
    const totalSlides = slides.length; 

    // Function to move the slide based on the direction
    function moveSlide(direction) {
        // Looping behavior: move to the next or previous slide
        if (direction <= 0) {
            currentIndex = (currentIndex + 1) % totalSlides; 
        } else if (direction > 0) {
            currentIndex = (currentIndex - 1 + totalSlides) % totalSlides; 
        }
        updateCarouselPosition(); 
    }

    // Function to update the carousel's position based on the current index
    function updateCarouselPosition() {
        const carouselContainer = document.querySelector('.productCarousels'); 
        // Calculate the offset in percentage to move between slides (100% per slide)
        const offset = 100 * -currentIndex;
        carouselContainer.style.transform = `translateX(${offset}%)`; 
    }

    // Event listeners for the previous and next buttons
    document.querySelector('.prevBtn').addEventListener('click', () => moveSlide(1));
    document.querySelector('.nextBtn').addEventListener('click', () => moveSlide(0));

    // Load navbar fragment
    fetch('/html/navbar.html')
      .then(response => response.text())
      .then(html => {
        document.getElementById('navbar-container').innerHTML = html;
        // Load navbar CSS
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = '/css/navbar.css';
        document.head.appendChild(link);
        // Load navbar JS
        const script = document.createElement('script');
        script.src = '/js/navbar.js';
        document.body.appendChild(script);
      });

    const link = document.createElement('link');
    link.href = '/css/navbar.css';
});

