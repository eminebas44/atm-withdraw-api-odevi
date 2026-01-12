# ATM & Finans Mikroservis Projesi

Bu proje, Java (Spring Boot) tabanlı bir **ATM API** uygulaması ile entegre çalışan Python tabanlı bir **Finans/MCP servisinden** oluşmaktadır. Sistem, kullanıcıların bankacılık işlemlerini güvenli bir şekilde yapmasını ve AI modelleri için finansal araçlar sunulmasını sağlar.

## 🚀 Proje Bileşenleri ve Ödev Maddeleri
- **Docker Desteği:** Proje, `Dockerfile` ve `docker-compose.yml` kullanılarak tek komutla ayağa kaldırılabilir.
- **Veritabanı:** Kullanıcı verileri ve bakiye yönetimi için **PostgreSQL** kullanılmaktadır.
- **Güvenlik (JWT):** Hassas işlemler (bakiye sorgulama, para çekme) JWT/Bearer token ile korunmaktadır.
- **OpenAPI/Swagger:** Sistemde hem Java backend hem de Python servisi için Swagger dokümantasyonu mevcuttur.
- **MCP Servisi:** `mcp_server.py` üzerinden Model Context Protocol sunucusu aktiftir.
- **Tool Fonksiyonu:** `doviz_cevirici` fonksiyonu ile finansal hesaplama aracı sunulur.

## 📊 Sistem Akış Diyagramı (Mermaid.js)
Aşağıdaki diyagram, bir kullanıcının sisteme giriş yapıp para çekme sürecini (Sequence Diagram) göstermektedir:

```mermaid
sequenceDiagram
    participant User as Kullanıcı
    participant Auth as Auth Controller
    participant ATM as ATM Controller
    participant DB as PostgreSQL

    User->>Auth: Login Talebi (Kart No & PIN)
    Auth->>DB: Kullanıcı Bilgilerini Doğrula
    DB-->>Auth: Onay
    Auth-->>User: JWT Token Döner
    
    Note over User, ATM: Sonraki istekler Header'da Token ile yapılır
    
    User->>ATM: Para Çekme Talebi (JWT + Miktar)
    ATM->>DB: Bakiye Kontrolü ve Güncelleme
    DB-->>ATM: İşlem Başarılı
    ATM-->>User: Yeni Bakiye Bilgisi (HTTP 200 OK)