# LangsungMasak API Documentation

LangsungMasak is a meal subscription service that provides fresh ingredients and easy recipes, enabling customers to prepare healthy meals at home without extensive planning or shopping. Our service aims to save time and reduce the stress of cooking.

## Technology Stack

- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Containerization**: Docker

## Base URL

http://localhost:8080

## Authentication

All endpoints require authentication via a Bearer Token. You must include the `Authorization` header with the value `Bearer <token>` in your requests.

## User Management

### Create User (Admin)

- **Endpoint**: `/users`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "username": "alfin",
        "password": "123456aA=",
        "balance": 0
    }
    ```

### Top Up Balance (Admin)

- **Endpoint**: `/users/topup`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "userId": 2,
        "balance": 50000
    }
    ```

### Get All Users (Admin)

- **Endpoint**: `/users`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Query Parameters**:
  - `maxBalance`: Filter users with a balance less than or equal to this value.

### Get User (Admin)

- **Endpoint**: `/users/{id}`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Update User (Admin)

- **Endpoint**: `/users/{id}`
- **Method**: `PUT`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "balance": 50000
    }
    ```

### Delete User (Admin)

- **Endpoint**: `/users/{id}`
- **Method**: `DELETE`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "username": "alfin",
        "password": "123456",
        "balance": 10000,
        "role": "ROLE_USER"
    }
    ```

## Authentication

### Register

- **Endpoint**: `/auth/register`
- **Method**: `POST`
- **Body**:
    ```json
    {
        "username": "user1",
        "password": "123456aA=",
        "balance": 0
    }
    ```

### Login

- **Endpoint**: `/auth/login`
- **Method**: `POST`
- **Body**:
    ```json
    {
        "username": "admin",
        "password": "Admin123="
    }
    ```

### Get Profile

- **Endpoint**: `/auth/profile`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

## Item Management

### Create Item (Admin)

- **Endpoint**: `/items`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "name": "tahu",
        "qty": 20
    }
    ```

### Get All Items

- **Endpoint**: `/items`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Query Parameters**:
  - `minQty`: Minimum quantity of items to filter.
  - `maxQty`: Maximum quantity of items to filter.
  - `name`: Filter items by name.

### Get Item

- **Endpoint**: `/items/{id}`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Update Item (Admin)

- **Endpoint**: `/items/{id}`
- **Method**: `PUT`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "qty": 20
    }
    ```

### Delete Item (Admin)

- **Endpoint**: `/items/{id}`
- **Method**: `DELETE`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "username": "alfin",
        "password": "123456",
        "balance": 10000,
        "role": "ROLE_USER"
    }
    ```

## Recipe Management

### Create Recipe (Admin)

- **Endpoint**: `/recipes`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "name": "nasi goreng",
        "description": "nasi goreng enak dengan telor",
        "method": "tinggal digoreng",
        "price": 10000,
        "ingredients": [
            {
                "itemId": 1,
                "qty": 5
            }
        ]
    }
    ```

### Upload Recipe Image (Admin)

- **Endpoint**: `/recipes/{id}/upload`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    - Form-data: file
    - File: `/home/alfin/Pictures/tahu-isi.jpg`

### Get All Recipes (User)

- **Endpoint**: `/recipes`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Query Parameters**:
  - `name`: Filter recipes by name.
  - `price`: Filter recipes by price.

### Get Recipe

- **Endpoint**: `/recipes/{id}`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Update Recipe (Admin)

- **Endpoint**: `/recipes/{id}`
- **Method**: `PUT`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "description": "nasi goreng biasa"
    }
    ```

### Delete Recipe (Admin)

- **Endpoint**: `/recipes/{id}`
- **Method**: `DELETE`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "username": "alfin",
        "password": "123456",
        "balance": 10000,
        "role": "ROLE_USER"
    }
    ```

## MapBox Integration

### Get City Coordinates

- **Endpoint**: `/map/{city}`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Get Distance

- **Endpoint**: `/map/distance`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "destination": {
            "latitude": -6.177585,
            "longitude": 106.62991
        }
    }
    ```

### Get Price By Distance

- **Endpoint**: `/map/price`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "destination": {
            "latitude": -6.177585,
            "longitude": 106.62991
        }
    }
    ```

## Order Management

### Get All Orders (Admin)

- **Endpoint**: `/orders`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Get Order

- **Endpoint**: `/orders/{id}`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <token>`

### Create Order (User)

- **Endpoint**: `/orders`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <token>`
- **Body**:
    ```json
    {
        "destination": {
            "latitude": -6.177585,
            "longitude": 106.62991
        },
        "recipes": [
            {
                "recipeId": 1,
                "qty": 2
            }
        ]
    }
    ```

### Delete Order (Admin)

- **Endpoint**: `/orders/{id}`
- **Method**: `DELETE`
- **Headers**: 
  - `Authorization: Bearer <token>`
