# 🎯 QUIZ MICROSERVICES APP


> ⚡ Containerized, microservices-based **Quiz Application** built with Spring Boot, Spring Cloud, Eureka, and Docker.  
> Scalable, discoverable, and fun — because quizzes shouldn’t be monoliths!

---

## 🚀 Features & Highlights

✨ **Microservices:** Clean separation into independent deployable units  
🔍 **Service Discovery:** Eureka auto-manages where services live  
🛡️ **API Gateway:** Centralized secure routing via Spring Cloud Gateway  
🐳 **Dockerized:** Spin up your entire ecosystem with a single command  
🚀 **Hot Reload:** Ready for iterative local dev or full containerized deployment  

---

## 🧰 Tech Stack & Tools

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.x-lightgrey.svg)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-🐳-blue.svg)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-red.svg)](https://maven.apache.org/)

---

## ⚙️ Microservices Overview

| 🏗️ Service           | 🔎 Description                                   | 🚪 Port |
|-----------------------|-------------------------------------------------|---------|
| `service-registry`     | Eureka server for dynamic service discovery     | `8761`  |
| `api-gateway`          | Routes & secures traffic to backend services    | `8765`  |
| `quiz-service`         | Handles quiz orchestration & scoring logic      | `8090`  |
| `question-service`     | Stores & serves quiz questions                  | `8080`  |

---

## 🐳 Quick Start with Docker

### ✅ Prerequisites
- Docker & Docker Compose installed.

### 🚀 Spin it up!
```bash
git clone https://github.com/vedant-gore/quizapp-microservice.git
cd quizapp-microservice
docker-compose up --build

🔥 Architecture Diagram
     [ User ]
        |
        v
   [ API Gateway ]
      /       \
     v         v
[Quiz Service] [Question Service]
        |
        v
 [ Service Registry (Eureka) ]

