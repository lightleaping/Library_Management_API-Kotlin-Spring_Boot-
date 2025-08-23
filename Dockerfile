# ---------- 1) 빌드 스테이지: Gradle이 설치된 이미지로 JAR 빌드 ----------
FROM gradle:8.8-jdk17 AS build
WORKDIR /app

# 소스 복사
COPY . .

# Spring Boot fat jar 생성 (build/libs/*.jar)
RUN gradle clean bootJar --no-daemon

# ---------- 2) 런타임 스테이지: 가벼운 JRE로 실행 ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드 산출물만 복사 (이름은 app.jar로 통일)
COPY --from=build /app/build/libs/*.jar /app/app.jar

# 기본 프로필(mysql)과 포트
ENV SPRING_PROFILES_ACTIVE=mysql
EXPOSE 8080

# 실행 엔트리포인트
ENTRYPOINT ["java","-jar","/app/app.jar"]
