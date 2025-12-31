# ATM & Finans Mikroservis Projesi

Bu proje, Java tabanlı bir ATM uygulaması ile entegre çalışan Python tabanlı bir Finans/MCP servisinden oluşmaktadır.

## Proje Bileşenleri ve Ödev Maddeleri
- **MCP Servisi:** `mcp_server.py` üzerinden Model Context Protocol sunucusu aktiftir.
- **Tool Fonksiyonu:** `doviz_cevirici` fonksiyonu ile AI modelleri için finansal hesaplama aracı sunulmuştur.
- **Public API & Requests:** Python `requests` kütüphanesi kullanılarak canlı döviz kurları çekilmektedir.
- **OpenAPI/Swagger:** Flask servisi OpenAPI standartlarına uygundur ve Swagger UI üzerinden test edilebilir.

## Kullanım
- Swagger Arayüzü: `http://localhost:5000/apidocs`
- Döviz Sorgulama: `http://localhost:5000/api/exchange/try`
- MCP Sunucusu: `python mcp_server.py`