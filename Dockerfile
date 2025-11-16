# Java sürümü 21 JRE'ye yükseltildi. Bu, yerel olarak derlenen JAR dosyasıyla uyumluluk sağlar.
FROM eclipse-temurin:21-jre-alpine

# JAR dosyasının tam adı (Kontrol ettiğiniz çıktıya göre güncellendi)
ARG JAR_FILE=target/atm-withdraw-api-0.0.1-SNAPSHOT.jar

# Derlenen .jar dosyasını kapsayıcının içine app.jar adıyla kopyalar.
COPY ${JAR_FILE} app.jar

# Uygulamanın dinleyeceği portu belirtir.
EXPOSE 8080

# Kapsayıcı başladığında uygulamayı çalıştırır.
ENTRYPOINT ["java", "-jar", "/app.jar"]