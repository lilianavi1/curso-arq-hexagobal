package com.example.hexagonal.application.rest;

import com.example.hexagonal.domain.model.Order;
import com.example.hexagonal.domain.model.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@QuarkusTest
class OrderResourceTest {

    @Test
    void createOrder() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201)
                    .body("status", is("PENDING"));
    }

    @Test
    void addItemToOrder() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201);

        OrderItem item = new OrderItem("product1", 2, new BigDecimal("50.0"));
        given()
                .contentType("application/json")
                .body(item)
                .when().post("/orders/{orderId}/items")
                .then()
                    .statusCode(200);
    }

    @Test
    void updateOrderStatus() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
                .contentType("application/json")
                .body(order)
                .when().post("/orders")
                .then()
                    .statusCode(201);

        given()
                .contentType("application/json")
                .body("CONFIRMED")
                .when().post("/orders/{orderId}/status")
                .then()
                    .statusCode(200)
                    .body("status", is("CONFIRMED"));
    }

}