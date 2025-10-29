1.Secure Bank ATM Project
2.Overview
3.This is a Spring Boot-based ATM (Automated Teller Machine) simulation project. It provides a secure banking system with account management, transaction processing, and user authentication. The application uses Spring Data JPA for database interactions, Thymeleaf for web templating, and MySQL for data persistence.

4.Features
5.User authentication with PIN verification
6.Account balance inquiry
7.Cash withdrawal with daily limits
8.Transaction history
9.Secure data handling with validation
10.Sample data loading for testing
Technologies Used
Java 17
Spring Boot 3.5.7
Spring Data JPA for ORM
Thymeleaf for server-side templating
MySQL as the database
Lombok for reducing boilerplate code
Maven for dependency management
Prerequisites
Java 17 or higher
Maven 3.6+
MySQL Server
Installation and Setup
Clone the repository:


git clone <repository-url>
cd atm-project
Configure the database:

Create a MySQL database named atm_db
Update src/main/resources/application.properties with your database credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/atm_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Build the project:


mvn clean install
Run the application:


mvn spring-boot:run
The application will start on http://localhost:8080

Usage
Access the ATM interface through your web browser
Use the sample accounts loaded by DataLoader:
Account 1: 1234567890123456 (PIN: 1234)
Account 2: 5940955036312345 (PIN: 1234)
Project Structure

src/
├── main/
│   ├── java/
│   │   └── com/example/atm/project/
│   │       ├── SecureBankAtmApplication.java  # Main application class
│   │       ├── DataLoader.java                # Loads sample data on startup
│   │       └── ...                            # Other classes (controllers, entities, etc.)
│   └── resources/
│       ├── application.properties             # Configuration properties
│       └── templates/                         # Thymeleaf templates
└── test/                                      # Test classes
API Endpoints
GET / - Home page
POST /login - User login
GET /account/{id} - Account details
POST /withdraw - Cash withdrawal
Other endpoints as implemented in controllers
Contributing
Fork the repository
Create a feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments
Spring Boot documentation
MySQL documentation
Thymeleaf documentation
