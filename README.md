# 🌐 Web Scraping Platform – Desktop & Web Application

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-2C2255?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![SQL](https://img.shields.io/badge/SQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/)

> **Search. Collect. Store. Display.**  
> A web scraping platform built for **learning and academic purposes** using Java technologies.

---

## ✨ Features

- **Desktop Interface**: JavaFX-based GUI for user interaction
- **Web Scraping**: Search and extract data from multiple websites
- **Search Filters**: Scrape data based on user-defined criteria
- **Result Visualization**: Display scraping results in the UI
- **Text Export**: Save scraped data into a text file
- **Email Sending**: Send exported data via email
- **Database Storage**: Persist data in an SQL database
- **Web Display**: View stored data through a Spring Boot web app
- **Testing**: Unit tests for core functionalities

---

## 📖 Overview

This project is a **web scraping system implemented using two different applications and technologies**:

- **Scraper (JavaFX)** – Desktop application responsible for scraping, exporting, and storing data
- **WebApp (Spring Boot)** – Web application that displays stored scraping results

The project was developed to practice **desktop development**, **backend development**, **data persistence**, and **software testing** using Java.

---

## 🎯 Learning Goals

This project was built to practice:
- Java desktop development with **JavaFX**
- Web scraping techniques (e.g. Jsoup)
- Backend development with **Spring Boot**
- SQL database integration
- File handling and email sending
- Unit testing with **JUnit**
- Clean architecture and separation of concerns

---

## 🏗️ Project Architecture

```text
web-scraping/
├── scraper/            # JavaFX desktop scraping application
├── webapp/             # Spring Boot web application
└── README.md           # Global project documentation
```
🚀 Quick Start
📋 Prerequisites

    Java JDK 17+

    Maven or Gradle

    SQL Database (MySQL / PostgreSQL)

    Internet connection (for scraping)

1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/web-scraping.git
cd web-scraping
```
2️⃣ Scraper Application (JavaFX)
```bash
cd scraper
mvn clean install
mvn javafx:run
```
Features:

    Input search criteria

    Scrape multiple websites

    Display results

    Export results to a text file

    Send results by email

    Store results in SQL database

3️⃣ Web Application (Spring Boot)
```bash
cd ../webapp
mvn clean install
mvn spring-boot:run
```
Web app runs at:
👉 http://localhost:8080

Purpose:

    Retrieve data from database

    Display scraping results via web interface

🗄 Database Setup

    Create an SQL database manually

    Configure database credentials in application.properties

    Tables store scraped results and metadata

⚠️ Database setup is manual in this learning project.

### 🧪 Testing

Unit tests are implemented using JUnit.

mvn test

Test coverage focuses on:

    Scraping logic

    Data processing

    Database persistence

👤 User Workflow

1. Launch JavaFX scraper
2. Enter search criteria
3. Scrape multiple websites
4. View results in desktop UI
5. Export results to text file
6. Send results via email
7. Store results in SQL database
8. View results via web application

### 📄 User Guide (PDF)
A detailed user guide explaining how to use the JavaFX scraping application.

- **Location:** `scraper/guide.pdf`
- Covers UI usage, search criteria, export, and email features

### 📚 Javadoc
Complete **Javadoc** is provided for the Java source code.

- Generated from source comments
- Covers classes, methods, and application architecture
- Helps developers understand and maintain the code

**How to generate Javadoc:**
```bash
cd scraper
mvn javadoc:javadoc
```
    ⚠️ Educational Project Notice
    This project is intended for learning purposes and is not production-ready.

Current limitations:

    Scraping depends on website structure changes

    Limited error handling for network failures

    Manual database configuration

    Basic UI styling

    Limited test coverage in some modules
