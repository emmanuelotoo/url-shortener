# URL Shortener

A RESTful URL shortener service built with Spring Boot that allows you to create shortened URLs with optional custom aliases and expiry dates. The service tracks analytics like hit counts and last access times.


## How to Run

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Setup

1. **Clone the repository** (or navigate to the project directory)

2. **Set up the database:**
   ```sql
   CREATE DATABASE shortener;
   ```

3. **Configure environment variables:**
   
   Set the following environment variables for database credentials:
   ```cmd
   set DB_USERNAME=your_postgres_username
   set DB_PASSWORD=your_postgres_password
   ```

4. **Build the project:**
   ```cmd
   mvnw clean install
   ```

5. **Run the application:**
   ```cmd
   mvnw spring-boot:run
   ```

   Or run the JAR file directly:
   ```cmd
   java -jar target\url-shortener-0.0.1-SNAPSHOT.jar
   ```

The application will start on `http://localhost:8080`

## How to Use

### API Endpoints

#### 1. Create a Shortened URL

**Endpoint:** `POST /api/urls`

**Request Body:**
```json
{
  "url": "https://www.example.com/very/long/url/that/needs/shortening",
  "alias": "mylink",
  "expiryDays": 30
}
```

**Response:**
```json
{
  "id": 1,
  "code": "mylink",
  "targetUrl": "https://www.example.com/long-url",
  "expiresAt": "2025-11-28T12:00:00Z",
  "hits": 0,
  "lastAccessedAt": null,
  "createdAt": "2025-10-29T12:00:00Z"
}
```

#### 2. Get URL Information

**Endpoint:** `GET /api/urls/{code}`

**Example:**
```cmd
curl http://localhost:8080/api/urls/mylink
```

**Response:**
```json
{
  "id": 1,
  "code": "mylink",
  "targetUrl": "https://www.example.com/long-url",
  "expiresAt": "2025-11-28T12:00:00Z",
  "hits": 5,
  "lastAccessedAt": "2025-10-29T14:30:00Z",
  "createdAt": "2025-10-29T12:00:00Z"
}
```

#### 3. Redirect to Original URL

**Endpoint:** `GET /{code}`

Simply access the shortened URL in your browser or via HTTP client:

**Example:**
```
http://localhost:8080/mylink
```

This will redirect (HTTP 302) to the original URL and increment the hit counter.


