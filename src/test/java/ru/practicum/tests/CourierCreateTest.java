package ru.practicum.tests;

import ru.practicum.base.BaseTest;
import ru.practicum.client.CourierClient;
import ru.practicum.model.CourierRequest;
import ru.practicum.util.DataGenerator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

@DisplayName("Создание курьера")
class CourierCreateTest extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private Integer courierId;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    void cleanUp() {
        if (courierId != null) {
            courier.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может быть создан")
    void courierCanBeCreated() {
        CourierRequest body = DataGenerator.createValidCourierRequestBody();

        courier.create(body).statusCode(201)
                .body("ok", Matchers.equalTo(true));
        courierId = courier
                .login(DataGenerator.getCurrentCourierCredentials())
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    void cannotCreateDuplicateCourier() {
        CourierRequest body = DataGenerator.createValidCourierRequestBody();

        // первый раз
        courier.create(body).statusCode(201);
        courierId = courier
                .login(DataGenerator.getCurrentCourierCredentials())
                .extract()
                .path("id");

        // дубликат
        courier.create(body)
                .statusCode(409)
                .body("message", Matchers.equalTo("Этот логин уже используется"));
    }

    @ParameterizedTest(name = "[{index}] login={0}, password={1}, firstName={2}")
    @MethodSource("emptyFields")
    @DisplayName("Нельзя создать курьера без обязательного параметра")
    void requiredFieldsValidation(String login, String password, String firstName) {
        courier.create(new CourierRequest(login, password, firstName))
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));
    }

    private static Stream<Arguments> emptyFields() {
        return Stream.of(
                Arguments.of("", "pass", "Bob"),
                Arguments.of("login", "", "Bob")
        );
    }
}
