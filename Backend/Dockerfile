FROM openjdk:latest 

WORKDIR /Backend
COPY  .mvn/ .mvn 
COPY mvnw pom.xml ./   

COPY src ./src 
EXPOSE 8181 

CMD ["./mvnw","spring-boot:run","-Dspring-boot.run.profile=mysql"]