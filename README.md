### MICROSERVICES SPRING - ANGULAR


## Tecnologías
- Backend: Spring Boot 3.5.7, Java 17+
- Frontend: Angular 9, TypeScript, RxJS
- Base de datos: PostgreSQL / MySQL / MongoDB
- Mensajería/eventos: Kafka / RabbitMQ (Pendiente)
- Contenedores: Docker, Docker Compose
- Gestión de dependencias: Maven / npm

## Arquitectura de Microservicios
1. Gateway Service → Entrada principal para las solicitudes, manejo de rutas y seguridad.
2. Discovery-service → Servicio de descubrimiento (Eureka)
3. Auth Service → Autenticación y autorización de usuarios (JWT) (Pendiente).
4. Curso-service → Microservicio de gestión de cursos (mysql)
5. Examen-service → Microservicio de gestión de exámenes (mysql)
6. Respuesta-service → Microservicio de gestión de respuestas (mongodb)
7. Usuario-service → Microservicio de gestión de usuarios (postgresql)
8. Commons-alumnos, Commons-examenes, Commons-services → Librerías compartidas
9. Notification Service → Manejo de notificaciones por email o eventos (Pendiente).


## Instalación y Ejecución
Prerequisitos:
- Java 17+
- Node.js 12.15.0
- Angular CLI
- Docker y Docker Compose
- PostgreSQL / MongoDB instalado

### Backend (Spring Boot):
1. Clonar el repositorio
2. Construir el proyecto:
   mvn clean install
3. Ejecutar el microservicio:
   mvn spring-boot:run

Frontend (Angular):
1. Ir al frontend
2. Instalar dependencias:
   npm install
3. Ejecutar la aplicación:
   ng serve --open

Con Docker: 
1. Levantar las base de datos. Mongo db se debe tener instalado en la máquina.
```
docker compose up -d
```


## Referencias
- https://start.spring.io/
- https://spring.io/microservices
- https://angular.io/docs
- https://material.angular.dev/
- https://sweetalert2.github.io/
- https://docs.docker.com/
- https://kafka.apache.org/documentation/
- https://devops.datenkollektiv.de/banner.txt/index.html
- https://getbootstrap.com/docs/4.6/getting-started/introduction/


## Licencia
MIT License
