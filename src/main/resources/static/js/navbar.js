// Function to set active state
function setActiveState() {
  const links = document.querySelectorAll(".nav-links a");
  const logo = document.querySelector(".logo");
  const currentPage = window.location.pathname;

  // Remove active class from all links and logo
  links.forEach(link => link.classList.remove("active"));
  logo.classList.remove("active");

  // Find and set active class for current page
  const currentLink = Array.from(links).find(link => link.getAttribute('href') === currentPage);
  if (currentLink) {
    currentLink.classList.add("active");
  } else {
    logo.classList.add("active");
  }
}

// Set initial active state
setActiveState();

// Add click handlers
const links = document.querySelectorAll(".nav-links a");
const logo = document.querySelector(".logo");

links.forEach(link => {
  link.addEventListener("click", () => {
    links.forEach(l => l.classList.remove("active"));
    logo.classList.remove("active");
    link.classList.add("active");
  });
});

logo.addEventListener("click", () => {
  links.forEach(l => l.classList.remove("active"));
  logo.classList.add("active");
}); 