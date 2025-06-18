package ru.practicum.tests;

import ru.practicum.base.BaseTest;
import ru.practicum.client.CourierClient;
import ru.practicum.model.CourierRequest;
import ru.practicum.util.DataGenerator;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@DisplayName("Логин курьера")
class CourierLoginTest extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private CourierRequest validCreds;
    private int courierId;

    @BeforeEach
    void createCourier() {
        validCreds = DataGenerator.createValidCourierRequestBody();
        courier.create(validCreds).statusCode(201);
        courierId = courier.login(validCreds)
                .extract()
                .path("id");
    }

    @AfterEach
    void deleteCourier() {
        courier.delete(courierId);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    void courierCanLogin() {
        courier.login(validCreds)
                .statusCode(200)
                .body("id", Matchers.equalTo(courierId));
    }

    @ParameterizedTest(name = "[{index}] login={0}, password={1}")
    @MethodSource("invalidCreds")
    @DisplayName("Невалидные или пустые поля")
    void invalidLogin(String login, String password, int expectedCode, String message) {
        courier.login(new CourierRequest(login, password))
                .statusCode(expectedCode)
                .body("message", Matchers.equalTo(message));
    }

    private static Stream<Arguments> invalidCreds() {
        return Stream.of(
                Arguments.of("", "pass", 400, "Недостаточно данных для входа"),
                Arguments.of("wrong", "pass", 404, "Учетная запись не найдена"),
                Arguments.of("login", "", 400, "Недостаточно данных для входа")
        );
    }
}
