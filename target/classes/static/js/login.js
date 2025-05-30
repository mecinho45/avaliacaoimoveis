document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
loginForm.addEventListener('submit', async (e) => {
    const submitBtn = loginForm.querySelector('button[type="submit"]');
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Entrando...';

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            // Validar antes do submit
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            if (!username || !password) {
                e.preventDefault();
                alert('Por favor, preencha todos os campos');
            }
submitBtn.disabled = false;
    submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Entrar';
});

        });
    }
});