package ru.practicum.client;

import ru.practicum.model.OrderRequest;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String CREATE_PATH = "orders";
    private static final String GET_PATH = "orders";
    private static final String ACCEPT_PATH = "orders/accept/";


    @Step("Создать заказ")
    public ValidatableResponse create(OrderRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse list() {
        return given()
                .when()
                .get(GET_PATH)
                .then();
    }
}
