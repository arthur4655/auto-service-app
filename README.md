# Auto service app
## 📝Project description
This project provides basic functionality for auto service web applications such as creating and saving to db order
which will contain information about provided services, used products, mechanics, car and owner of this car
and description of order. Also order has status which could be changed. It can count total cost of order 
and salary to mechanics based on completed services.
## 🌐Technologies
* JDK 17
* Maven 4.0
* Spring and Spring Boot 3.0.1
* PostgreSQL 14.6
* OpenApi(Springdoc 2.0.2)
* Liquibase 4.17.2
* Docker 20.10.21
## 🚀Instructions for launching the project
1. Clone project from github.
2. Make sure you have installed docker on your PC
3. Build project with ```mvn clean package``` and run:
```bash
docker-compose up
```
4. You can read the documentation and test the endpoints at url: 
</br>`http://localhost:8080/swagger-ui/index.html` (local) 
</br>`http://localhost:6868/swagger-ui/index.html` (docker)
