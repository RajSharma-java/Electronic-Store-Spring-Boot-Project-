# Electronic Store

## Overview
Electronic Store is a full-stack e-commerce application built using **Spring Boot**. It provides robust backend functionality, ensuring security, scalability, and seamless user experience. The project includes various modules like User, Product, Category, Role, Order, and Cart, making it a comprehensive solution for managing an online store.

---

## Features

### User Module
- User account creation, update, and deletion.
- Retrieve user details by ID or email.
- Secure authentication using **JWT**.

### Product Module
- Add, update, delete, and list products.
- Categorized product management for better organization.

### Category Module
- Manage product categories to streamline product listings.

### Cart Module
- Add/remove products from the cart.
- Calculate total price and display cart items dynamically.

### Order Module
- Create, view, and manage orders with real-time updates.

### Role Module
- Role-based access and permissions using **Spring Security**.

---

## Technologies Used
- **Backend**: Spring Boot
- **Security**: JWT Authentication, Spring Security
- **Database**: MySQL
- **API Documentation**: Swagger
- **Build Tool**: Maven

---

## Installation and Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/electronic-store.git
   ```

2. **Navigate to the Project Directory**
   ```bash
   cd electronic-store
   ```

3. **Set Up the Database**
   - Create a MySQL database named `electronic_store`.
   - Update the database connection properties in the `application.properties` file:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/electronic_store
     spring.datasource.username=yourUsername
     spring.datasource.password=yourPassword
     ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**
   - The API will be accessible at: `http://localhost:8080`
   - API documentation can be found at: `http://localhost:8080/swagger-ui/index.html`

---

## Future Enhancements
- **Unit Testing**: Implement unit tests for each controller.
- **Google Sign-In Integration**: Allow users to sign in using their Google accounts.
- **Dockerization**: Containerize the application for better deployment.

---

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Open a pull request.

---

## Acknowledgments
A special thanks to **Durgesh Sir** for his excellent course, which provided invaluable guidance and support throughout this project.

---
