FROM amazoncorretto:24-alpine3.22-jdk as build

RUN apk update && apk add maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src /app/src

RUN mvn clean package -DskipTests


FROM amazoncorretto:24-alpine3.22-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]