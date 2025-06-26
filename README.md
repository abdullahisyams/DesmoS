# DesmoS
DesmoS - Ducati E-commerce System
DesmoS is a web-based e-commerce platform focused on Ducati motorcycles. Built using Java Spring Boot, it allows admins to manage product listings and supports essential e-commerce features (with customer-facing features in progress).

Tech Stack
Java 17
Spring Boot
MyBatis
Thymeleaf
MySQL
IntelliJ IDEA
Apache Tomcat (embedded in Spring Boot)

How to Run Locally
1. Prerequisites
Make sure the following are installed on your system:
Java 17+
IntelliJ IDEA
MySQL Server
Git

2. Clone the Repository
git clone https://github.com/yourusername/DesmoS.git
cd DesmoS

3. Set Up MySQL Database
Create a new database named desmosdb:
sql
CREATE DATABASE desmosdb;
Import the SQL schema if provided (e.g., schema.sql or data.sql file inside the project).

4. Configure application.properties
In src/main/resources/application.properties, update to your database credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/desmosdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

5. Open Project in IntelliJ
Open IntelliJ IDEA.
Click Open and select the root folder of the project.
IntelliJ will auto-detect it as a Maven or Gradle project and sync dependencies.

6. Run the Project
Locate the main class (usually in src/main/java/com/example/DesmoSApplication.java) and run it.
Spring Boot uses an embedded Tomcat server, so you don’t need to manually install Tomcat.

The app should be available at:
http://localhost:8080

Tips
Make sure port 8080 is not used by another application.
If using a different port or database name, update it in application.properties.
