<p align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-17+-F05032?style=for-the-badge&logo=java&logoColor=white" alt="Java 17+"/>
  <img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jwt&logoColor=white" alt="JWT"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16+-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Maven-build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
</p>


<h1 align="center">Online Shop REST API</h1>

<p align="center">
  <strong>A clean, modern and secure backend for an e-commerce platform</strong><br>
  Authentication · Shopping Cart · Orders · Products · Categories · Admin features
</p>

<p align="center">
  <a href="#✨-key-features">Key Features</a> •
  <a href="#-technologies">Technologies</a> •
  <a href="#-api-endpoints">API Endpoints</a> •
  <a href="#-quick-start">Quick Start</a> •
  <a href="#-project-structure">Structure</a>
</p>

<br>

## ✨ Key Features

- JWT-based authentication & role-based access (USER / ADMIN)
- Full shopping cart functionality
- Order creation & history
- Nested categories (parent ↔ children)
- Product CRUD (restricted to ADMIN)
- Stock quantity validation & insufficient stock protection
- Clean DTOs, custom exceptions & global exception handling
- Input validation with meaningful error messages
- Stateless authentication (no sessions)
- Ready for Swagger/OpenAPI documentation

## 🛠 Technologies

- **Spring Boot 3.x**
- **Spring Security** + **JWT**
- **Spring Data JPA** / Hibernate
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Bean Validation** (Jakarta)
- **BCrypt** password hashing
- Global **@RestControllerAdvice** error handling

## 📡 Main API Endpoints

| Method  | Endpoint                              | Description                           | Access          |
|---------|---------------------------------------|---------------------------------------|-----------------|
| POST    | `/api/v1/auth/register`              | Register new user                     | Public          |
| POST    | `/api/v1/auth/login`                 | Login & receive JWT                   | Public          |
| ——————— | ————————————————————————————————————— | ————————————————————————————————————— | ——————————————— |
| GET     | `/api/v1/products`                   | List all products                     | Authenticated   |
| GET     | `/api/v1/products/{id}`              | Get single product                    | Authenticated   |
| POST    | `/api/v1/products`                   | Create product                        | ADMIN           |
| PUT     | `/api/v1/products/{id}`              | Update product                        | ADMIN           |
| DELETE  | `/api/v1/products/{id}`              | Delete product                        | ADMIN           |
| ——————— | ————————————————————————————————————— | ————————————————————————————————————— | ——————————————— |
| POST    | `/api/v1/cart/{quantity}/{productId}`| Add item to cart                      | Authenticated   |
| GET     | `/api/v1/cart`                       | View cart contents                    | Authenticated   |
| PUT     | `/api/v1/cart/{quantity}/{productId}`| Update item quantity                  | Authenticated   |
| DELETE  | `/api/v1/cart/{productId}`           | Remove item from cart                 | Authenticated   |
| ——————— | ————————————————————————————————————— | ————————————————————————————————————— | ——————————————— |
| POST    | `/api/v1/order`                      | Create order from cart                | Authenticated   |
| GET     | `/api/v1/order`                      | Get user’s order history              | Authenticated   |
| GET     | `/api/v1/order/{order_id}`           | Get order details                     | Authenticated   |
| ——————— | ————————————————————————————————————— | ————————————————————————————————————— | ——————————————— |
| GET     | `/api/v1/categories`                 | List all categories                   | Public          |
| POST    | `/api/v1/categories`                 | Create category (with parent support) | ADMIN           |
