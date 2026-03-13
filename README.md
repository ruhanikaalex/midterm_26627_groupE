# Online Quiz Management System

## Student Information
- **Name:** Ruhanika Alex
- **Student ID:** 26627
- **Assessment Date:** March 13, 2026

## Project Overview
This is a robust **Spring Boot** application developed as part of a Practical Assessment. The system manages quizzes, questions, users, and a deep hierarchical location system. It demonstrates advanced Spring Data JPA concepts, including complex relationships, custom queries, and pagination.

**[Click here to view the Entity Relationship Diagram (ERD)](ERD_DOC.md)**

## Assessment Criteria Fulfilled
This project meticulously implements the following requirements from the assessment rubric:

1.  **Entity Relationship Diagram (ERD)**: Built with 8 core tables: `User`, `Location`, `Quiz`, `QuizAttempt`, `Question`, `Answer`, `Notification`, and `Report`.
2.  **Location Management**: Full implementation of saving and hierarchical linking of locations (Province -> District -> Sector -> Cell -> Village).
3.  **Sorting & Pagination**: The `UserController` uses `Pageable` and `Sort` for efficient data retrieval.
4.  **Relationships**: 
    - **One-to-One**: `QuizAttempt` to `Report`.
    - **One-to-Many**: `Location` to `User`, `User` to `Quiz`.
    - **Many-to-Many**: `Quiz` to `Question` (using `quiz_questions` join table).
5.  **Repository Logic**:
    - Implementation of `existsByEmail()` and `existsByUsername()` in `UserRepository`.
    - **Hierarchical Retrieval**: A complex custom `@Query` in `LocationRepository` filters users across any level of the location hierarchy.

## Technologies Used
- **Backend**: Spring Boot 3.x, Spring Data JPA
- **Database**: PostgreSQL 17
- **Build Tool**: Maven
- **JSON Handling**: Jackson (with recursion guards)

## How to Run
1. Ensure PostgreSQL is running and the database `quizmanagementdb` exists (or as configured in `application.properties`).
2. Run the application using Maven:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
3. The server starts on **Port 8086**.

## API Highlights
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/locations` | Create a location (Hierarchy support) |
| POST | `/api/users/createUser` | Create a user linked to a Village |
| GET | `/api/users/by-province` | Retrieve users by Province Name or Code |
| GET | `/api/users` | List users with Pagination and Sorting |

---
**Note:** This project includes extensive documentation within the source code to explain the implementation logic for each assessment criterion.
