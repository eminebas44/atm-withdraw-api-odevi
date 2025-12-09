
const API_BASE_URL = 'http://localhost:7575/api';

document.addEventListener('DOMContentLoaded', () => {

    if (localStorage.getItem('jwtToken')) {
        showProtectedSection();
    }
});

function showProtectedSection() {

    document.getElementById('login-section').style.display = 'none';
    document.getElementById('protected-section').style.display = 'block';
 
    getBalance(); 
}

function showLoginSection() {

    document.getElementById('login-section').style.display = 'block';
    document.getElementById('protected-section').style.display = 'none';

    document.getElementById('message').textContent = '';
    document.getElementById('balance-info').textContent = '';
    document.getElementById('username').value = '';
    document.getElementById('password').value = '';
}

async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const messageElement = document.getElementById('message');

    messageElement.textContent = 'Giriş yapılıyor...';
    messageElement.style.color = 'blue';

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
            
            messageElement.textContent = 'Giriş Başarılı!';
            messageElement.style.color = 'green';
            

            setTimeout(showProtectedSection, 1000); 
        } else {
            messageElement.textContent = data.message || 'Giriş Başarısız. Kullanıcı adı/şifre hatalı.';
            messageElement.style.color = 'red';
            localStorage.removeItem('jwtToken');
        }
    } catch (error) {
        messageElement.textContent = 'Bağlantı Hatası: Backend sunucusuna ulaşılamadı.';
        messageElement.style.color = 'red';
        console.error('Login Error:', error);
    }
}

async function getBalance() {
    const token = localStorage.getItem('jwtToken');
    const cardNumber = localStorage.getItem('cardNumber');
    const balanceInfo = document.getElementById('balance-info');

    if (!token || !cardNumber) {
        balanceInfo.textContent = 'Oturum süresi doldu. Lütfen tekrar giriş yapın.';
        showLoginSection();
        return;
    }

    try {

        const response = await fetch(`${API_BASE_URL}/account/balance?cardNumber=${cardNumber}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const data = await response.json();

        if (response.ok) {
            balanceInfo.textContent = `Hesap Bakiyeniz: ${data.balance} TL`;
            balanceInfo.style.color = 'green';
            balanceInfo.style.fontWeight = 'bold';
        } else if (response.status === 401 || response.status === 403) {

            balanceInfo.textContent = 'Yetkisiz Erişim. Oturumunuz sona erdi.';
            balanceInfo.style.color = 'red';
            logout();
        } else {
            balanceInfo.textContent = data.message || 'Bakiye alınırken bir hata oluştu.';
            balanceInfo.style.color = 'orange';
        }
    } catch (error) {
        balanceInfo.textContent = 'Hata: API\'ye ulaşılamadı.';
        balanceInfo.style.color = 'red';
        console.error('Balance Error:', error);
    }
}

function logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('cardNumber');
    alert('Başarıyla çıkış yaptınız.');
    showLoginSection();
}