package ru.practicum.client;

import ru.practicum.model.CourierRequest;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String CREATE_PATH = "courier";
    private static final String LOGIN_PATH = "courier/login";
    private static final String DELETE_PATH = "courier/{id}";
    private static final String DELETE_PATH_WITHOUT_ID = "courier/";

    @Step("Создать курьера")
    public ValidatableResponse create(CourierRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(CourierRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Удалить курьера id={id}")
    public ValidatableResponse delete(Integer id) {
        return given()
                .when()
                .delete(DELETE_PATH, id)
                .then();
    }

    @Step("Удалить курьера без id")
    public ValidatableResponse delete() {
        return given()
                .when()
                .delete(DELETE_PATH_WITHOUT_ID)
                .then();
    }
}
