# 1. Aşama: Derleme (Build)
# Maven yüklü bir imaj kullanarak projeyi derliyoruz
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Testleri atlayarak paketliyoruz (Hız kazanmak için)
RUN mvn clean package -DskipTests

# 2. Aşama: Çalıştırma (Run)
# Sadece çalıştırılabilir JAR dosyasını alıp hafif bir imaja koyuyoruz
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
# İlk aşamada (build) oluşan jar dosyasını buraya kopyalıyoruz
COPY --from=build /app/target/*.jar app.jar
EXPOSE 7575
ENTRYPOINT ["java", "-jar", "app.jar"]