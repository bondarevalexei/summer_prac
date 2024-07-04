function validateRegisterForm() {
    let valid = true;

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const usernameError = document.getElementById('usernameError');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');

    usernameError.textContent = '';
    emailError.textContent = '';
    passwordError.textContent = '';

    if (!username) {
        usernameError.textContent = 'Username is required';
        valid = false;
    }
    if (!email) {
        emailError.textContent = 'Email is required';
        valid = false;
    } else if (!validateEmail(email)) {
        emailError.textContent = 'Email is invalid';
        valid = false;
    }
    if (!password) {
        passwordError.textContent = 'Password is required';
        valid = false;
    } else if (password.length < 6) {
        passwordError.textContent = 'Password must be at least 6 characters long';
        valid = false;
    }

    return valid;
}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function validateLoginForm() {
    let valid = true;

    // Получаем значения полей
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Получаем элементы для отображения ошибок
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');

    // Сброс сообщений об ошибках
    usernameError.textContent = '';
    passwordError.textContent = '';

    // Проверка длины имени пользователя
    if (username.length < 4) {
        usernameError.textContent = 'Username must be at least 4 characters long';
        valid = false;
    }

    // Проверка длины пароля
    if (password.length < 8) {
        passwordError.textContent = 'Password must be at least 8 characters long';
        valid = false;
    }

    return valid;
}