𝗠𝗶𝗰𝗿𝗼𝘀𝗲𝗿𝘃𝗶𝗰𝗲𝘀 𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝘄𝗶𝘁𝗵 𝗦𝗽𝗿𝗶𝗻𝗴 𝗕𝗼𝗼𝘁 𝟯 – 𝗛𝗼𝘁𝗲𝗹𝘀 & 𝗥𝗲𝘀𝗲𝗿𝘃𝗮𝘁𝗶𝗼𝗻 𝗦𝘆𝘀𝘁𝗲𝗺
This project implements a microservices-based architecture using Spring Boot 3, Java 21, and modern development practices.
It includes two business microservices (Hotels and Reservations), a Config Server, a Discovery Server, and an API Gateway enabling inter-service communication.

𝗠𝗶𝗰𝗿𝗼𝘀𝗲𝗿𝘃𝗶𝗰𝗲𝘀 𝗢𝘃𝗲𝗿𝘃𝗶𝗲𝘄 :
𝗛𝗼𝘁𝗲𝗹𝘀 𝗠𝗶𝗰𝗿𝗼𝘀𝗲𝗿𝘃𝗶𝗰𝗲
Manages hotel data and rooms.
Features:
• Create / get hotels
• The Room Controller exposes an endpoint that returns room details by ID, including the associated hotel information retrieved from the Hotels microservice.
• Uses its own dedicated database
• Exposes DTOs using 𝗝𝗮𝘃𝗮 𝗿𝗲𝗰𝗼𝗿𝗱𝘀
• Uses 𝗠𝗮𝗽𝗦𝘁𝗿𝘂𝗰𝘁 for entity mapping
• Uses Lombok for reducing boilerplate

𝗥𝗲𝘀𝗲𝗿𝘃𝗮𝘁𝗶𝗼𝗻 𝗠𝗶𝗰𝗿𝗼𝘀𝗲𝗿𝘃𝗶𝗰𝗲
Manages reservations and customers.
Features:
• Create / get reservations
• Customer management
• Communicates with hotels Microservice via API Gateway
• Uses its own database
• MapStruct for mapping
• DTOs defined as Java records
• Uses Lombok for reducing boilerplate

𝗔𝗣𝗜 𝗚𝗮𝘁𝗲𝘄𝗮𝘆
Acts as the single entry point for all external requests.
Responsibilities:
• Routes requests to Hotels or Reservation microservices
• Central point of communication between microservices

𝗗𝗶𝘀𝗰𝗼𝘃𝗲𝗿𝘆 𝗦𝗲𝗿𝘃𝗲𝗿 (𝗘𝘂𝗿𝗲𝗸𝗮 𝗦𝗲𝗿𝘃𝗲𝗿)
Responsible for service registration and discovery.
Hotels and Reservations register automatically
Gateway discovers services dynamically

𝗖𝗼𝗻𝗳𝗶𝗴 𝗦𝗲𝗿𝘃𝗲𝗿
Centralized configuration management.
Stores configuration files (YAML/Properties) in a Git repository
Each microservice retrieves its configuration on startup
Simplifies environment management (dev/test/prod)

𝗗𝗼𝗰𝗸𝗲𝗿 𝗖𝗼𝗺𝗽𝗼𝘀𝗲 𝗦𝗲𝘁𝘂𝗽
The docker-compose.yml configures:
• Two databases (one per microservice)
• Zipkin for distributed tracing
• Network shared by all microservices

𝗗𝗶𝘀𝘁𝗿𝗶𝗯𝘂𝘁𝗲𝗱 𝗧𝗿𝗮𝗰𝗶𝗻𝗴
All microservices integrate with 𝗭𝗶𝗽𝗸𝗶𝗻 via Micrometer Tracing.

This enables:
• Microservice call tracing
• Clear visualization of latency & dependencies
• Debugging multi-services workflows

📂 Project Structure
reserva-microservices
├── api-gateway/
├── config-service/
├── discovery-service/
├── hotel-service/
│    ├── client
│    ├── controller
│    ├── service
│    ├── repository
│    ├── entity
│    ├── dto (records)
│    ├── mapper (MapStruct)
│    └── application.yml
├── reservation-service/
│    ├── client
│    ├── controller
│    ├── service
│    ├──  repository
│    ├── entities
│    ├── dto (records)
│    ├── mapper (MapStruct)
│    └── application.yml
├── docker-compose.yml
└── README.md
𝗛𝗼𝘄 𝘁𝗼 𝗥𝘂𝗻 𝘁𝗵𝗲 𝗣𝗿𝗼𝗷𝗲𝗰𝘁
1. Start infrastructure (databases + zipkin)
2. docker compose up -d
3. Run Config Server, then Discovery Server
4. Start API Gateway and the two microservices

𝗜𝗻𝘁𝗲𝗿-𝗦𝗲𝗿𝘃𝗶𝗰𝗲 𝗖𝗼𝗺𝗺𝘂𝗻𝗶𝗰𝗮𝘁𝗶𝗼𝗻
The Reservation microservice communicates with the Hotels microservice via:
• API Gateway routes
• Service discovery (Eureka)
• 𝗙𝗲𝗶𝗴𝗻 𝗖𝗹𝗶𝗲𝗻𝘁

𝗦𝘂𝗺𝗺𝗮𝗿𝘆
This project demonstrates a complete, modern microservices architecture including:
• Spring Boot 3 & Java 21
• DTOs implemented with records
• MapStruct for clean mapping
• Lombok for reduced boilerplate
• API Gateway for routing
• Eureka Discovery Server for service registry
• Config Server for centralized configuration
• Docker Compose for databases and Zipkin
• Distributed tracing for observability

A fully scalable and production-ready microservice example.
