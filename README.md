# order-inventory-microservices-assignment

A Spring Boot microservices application with Order Service and Inventory Service using H2 database.

## Project Setup

## Project Setup

### Prerequisites

### Prerequisites- Java 17

- Java 17- Gradle 8.14

- Gradle 8.14

### Building the Project

### Building the Project```bash

```bash./gradlew clean build

./gradlew clean build```

```

### Running Services

### Running Services

**Inventory Service** (Port: 8081)

**Inventory Service** (Port: 8081)```bash

```bash./gradlew :inventory-service:bootRun

./gradlew :inventory-service:bootRun```

```Access H2 Console: http://localhost:8081/h2-console

- H2 Console: http://localhost:8081/h2-console

- Database: jdbc:h2:mem:inventorydb**Order Service** (Port: 8080)

```bash

**Order Service** (Port: 8080)./gradlew :order-service:bootRun

```bash```

./gradlew :order-service:bootRun

```## API Documentation

- H2 Console: http://localhost:8080/h2-console

- Database: jdbc:h2:mem:orderdb### Inventory Service



### Database Schema**Get Inventory Batches**

- `GET /inventory/{productId}`

**Inventory Service**- Returns list of batches for a product

- Products (id, name)

- Batches (id, product_id, quantity, expiry_date, batch_number)**Update Inventory**

- Sample Data: 3 products (A, B, C) with 2 batches for product A- `POST /inventory/update`

- Request Body:

**Order Service**  ```json

- Orders (id, productId, quantity, status, orderDate)  {

    "productId": 1,

## API Documentation    "quantity": 5

  }

### Inventory Service Endpoints  ```



**Get Inventory Batches by Product**### Order Service

```

GET /inventory/{productId}**Place Order**

```- `POST /order`

- Response: List of batches for a product- Request Body:

- Example: GET http://localhost:8081/inventory/1  ```json

  {

**Update Inventory**    "productId": 1,

```    "quantity": 5

POST /inventory/update  }

Content-Type: application/json  ```

- Returns: Order with status COMPLETED or FAILED

{

  "productId": 1,**Get Order**

  "quantity": 10- `GET /order/{id}`

}- Returns: Order details by ID

```

## Testing

### Order Service Endpoints

### Unit Tests (JUnit 5 + Mockito)

**Place Order**```bash

```./gradlew test

POST /order```

Content-Type: application/json

### Integration Tests (@SpringBootTest + H2)

{- Tests use in-memory H2 database

  "productId": 1,- All component tests included in test suite

  "quantity": 5

}### Postman Collection

```- Import Postman collections from:

- Response: Order object with status COMPLETED or FAILED  - `order-service/src/test/resources/postman/`

  - `inventory-service/src/test/resources/postman/`

**Get Order by ID**

```### Running Specific Service Tests

GET /order/{id}```bash

```./gradlew :order-service:test

- Response: Order details./gradlew :inventory-service:test

- Example: GET http://localhost:8080/order/1```

## Testing

### Run All Tests
```bash
./gradlew test
```

### Run Service-Specific Tests
```bash
./gradlew :order-service:test
./gradlew :inventory-service:test
```

### Test Coverage

**Unit Tests** (JUnit 5 + Mockito)
- OrderServiceImplTest - Service logic with mocked dependencies
- InventoryServiceImplTest - Service logic with mocked dependencies

**Integration Tests** (@SpringBootTest + H2)
- OrderControllerTest - REST endpoints and database integration
- InventoryControllerTest - REST endpoints and database integration

**Postman Collections**
- Import from: `order-service/postman/order-inventory-collection.postman_collection.json`
- Includes: All Order and Inventory endpoints ready to test

### Test Database
- Tests use in-memory H2 database
- No external database required
- Automatic cleanup between test runs
