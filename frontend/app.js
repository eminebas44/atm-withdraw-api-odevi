const API_BASE_URL = 'http://localhost:7575/api';

document.addEventListener('DOMContentLoaded', () => {
    if (localStorage.getItem('jwtToken')) {
        showProtectedSection();
    }
});

function showProtectedSection() {
    document.getElementById('login-section').style.display = 'none';
    document.getElementById('protected-section').style.display = 'block';
    document.getElementById('card-display').textContent = "Kart: " + localStorage.getItem('cardNumber');
    getBalance();
}

function showLoginSection() {
    document.getElementById('login-section').style.display = 'block';
    document.getElementById('protected-section').style.display = 'none';
    document.getElementById('message').textContent = '';
}

async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const messageElement = document.getElementById('message');

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok && data.token) {
            localStorage.setItem('jwtToken', data.token);
            localStorage.setItem('cardNumber', username);
            showProtectedSection();
        } else {
            messageElement.textContent = 'Giriş Başarısız! Kart veya PIN hatalı.';
            messageElement.style.color = 'red';
        }
    } catch (error) {
        messageElement.textContent = 'Bağlantı Hatası: Sunucu çalışmıyor olabilir.';
    }
}

async function getBalance() {
    const token = localStorage.getItem('jwtToken');
    const cardNumber = localStorage.getItem('cardNumber');
    const balanceInfo = document.getElementById('balance-info');

    try {
        const response = await fetch(`${API_BASE_URL}/account/balance?cardNumber=${cardNumber}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        const data = await response.json();
        if (response.ok) {
            balanceInfo.textContent = `Hesap Bakiyeniz: ${data.balance} TL`;
        }
    } catch (error) {
        console.error('Bakiye hatası:', error);
    }
}

async function withdrawMoney() {
    const amount = document.getElementById('withdraw-amount').value;
    const token = localStorage.getItem('jwtToken');
    const cardNumber = localStorage.getItem('cardNumber');
    const msg = document.getElementById('withdraw-message');

    try {
        const response = await fetch(`${API_BASE_URL}/account/withdraw`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ cardNumber, amount: parseFloat(amount) })
        });
        const data = await response.json();
        if (response.ok) {
            msg.textContent = data.message;
            msg.style.color = "green";
            getBalance();
        } else {
            msg.textContent = data.message || "Hata oluştu.";
            msg.style.color = "red";
        }
    } catch (error) {
        msg.textContent = "İşlem sırasında bağlantı hatası.";
    }
}

async function depositMoney() {
    const token = localStorage.getItem('jwtToken');
    const cardNumber = localStorage.getItem('cardNumber');
    const msg = document.getElementById('deposit-message');


    const banknotes = {
        200: parseInt(document.getElementById('dep-200').value) || 0,
        100: parseInt(document.getElementById('dep-100').value) || 0,
        50: parseInt(document.getElementById('dep-50').value) || 0,
        20: parseInt(document.getElementById('dep-20').value) || 0,
        10: parseInt(document.getElementById('dep-10').value) || 0,
        5: parseInt(document.getElementById('dep-5').value) || 0
    };

    try {
        const response = await fetch(`${API_BASE_URL}/account/deposit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ cardNumber, banknotes })
        });
        const data = await response.json();
        if (response.ok) {
            msg.textContent = data.message;
            msg.style.color = "green";
            getBalance();
        } else {
            msg.textContent = data.message || "Para yatırılamadı.";
            msg.style.color = "red";
        }
    } catch (error) {
        msg.textContent = "Bağlantı hatası.";
    }
}

function logout() {
    localStorage.clear();
    showLoginSection();
}