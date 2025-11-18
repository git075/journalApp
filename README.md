📝 Journal App — Secure Personal Journal with Spring Boot, JWT & Redis

A production-grade Spring Boot backend application that allows users to securely manage their personal journal entries.
It features JWT authentication, Redis caching, MongoDB storage, login rate limiting, and Weather API integration, following clean architecture and real-world backend standards.

🚀 Features

🔐 1. JWT Authentication + Spring Security

Login & registration with encrypted passwords.

Stateless session management using JWT.

Role-based authorization (USER, ADMIN).

Custom authentication provider support.

🗒 2. Journal CRUD

Create, read, update, delete journal entries.

Each entry includes:

Date & timestamp

Mood

Content

Weather information (auto-fetched)

🌦 3. Weather API Integration

Fetches weather details dynamically.

Uses Redis caching to avoid repeated API calls.

Improves latency & reduces external API cost.

⚡ 4. Redis Caching + Rate Limiting

Two Redis services:

a) Login Attempt Tracking

Prevents brute-force attacks:

Stores failed attempts:
login_attempts:{username} = count

Blocks user for 15 minutes after max attempts.

Automatically resets on successful login.

b) Generic Redis Cache

Stores weather API responses in JSON format.

Uses RedisTemplate<String, String> with string serializers.

Converts objects to JSON using ObjectMapper.

🗄 5. MongoDB Integration

Stores user data & journal entries.

Fast, scalable, cloud-ready NoSQL database.

Uses Spring Data MongoDB repositories.


🛠 Tech Stack
Backend

Java 17

Spring Boot

Spring Security (JWT)

Spring Data MongoDB

Spring Data Redis

Database

MongoDB Atlas / Local MongoDB

Caching

Redis (Lettuce client)

Other Tools

Maven

Docker

Postman

Git/GitHub