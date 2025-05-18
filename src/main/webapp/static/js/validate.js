function validatePasswords() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorText = document.getElementById("passwordError");

    if (password !== confirmPassword) {
        errorText.style.display = "block";
        return false;
    }

    errorText.style.display = "none";
    return true;
}
