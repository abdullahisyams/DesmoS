document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.querySelector('.register-form');
    
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Get form values
            const fullname = document.getElementById('fullname').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm-password').value;

            // Reset previous error messages
            clearErrors();

            // Validate form
            let isValid = true;

            // Validate full name
            if (!fullname.trim()) {
                showError('fullname', 'Full name is required');
                isValid = false;
            }

            // Validate email
            if (!email.trim()) {
                showError('email', 'Email is required');
                isValid = false;
            } else if (!isValidEmail(email)) {
                showError('email', 'Please enter a valid email address');
                isValid = false;
            }

            // Validate password
            if (!password) {
                showError('password', 'Password is required');
                isValid = false;
            } else if (password.length < 8) {
                showError('password', 'Password must be at least 8 characters long');
                isValid = false;
            }

            // Validate confirm password
            if (!confirmPassword) {
                showError('confirm-password', 'Please confirm your password');
                isValid = false;
            } else if (password !== confirmPassword) {
                showError('confirm-password', 'Passwords do not match');
                isValid = false;
            }

            // If form is valid, submit it
            if (isValid) {
                // In a real application, you would send this data to your server
                console.log('Form submitted:', { fullname, email, password });
                
                // Show success message
                showSuccess('Registration successful! Redirecting to login...');
                
                // Redirect to login page after a short delay
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
            }
        });
    }

    // Helper function to validate email format
    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    // Helper function to show error message
    function showError(fieldId, message) {
        const field = document.getElementById(fieldId);
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;
        field.parentNode.appendChild(errorDiv);
        field.classList.add('error');
    }

    // Helper function to clear all error messages
    function clearErrors() {
        // Remove all error messages
        document.querySelectorAll('.error-message').forEach(error => error.remove());
        // Remove error class from all fields
        document.querySelectorAll('.error').forEach(field => field.classList.remove('error'));
    }

    // Helper function to show success message
    function showSuccess(message) {
        const successDiv = document.createElement('div');
        successDiv.className = 'success-message';
        successDiv.textContent = message;
        registerForm.insertBefore(successDiv, registerForm.firstChild);
        
        // Remove success message after 3 seconds
        setTimeout(() => {
            successDiv.remove();
        }, 3000);
    }
}); 