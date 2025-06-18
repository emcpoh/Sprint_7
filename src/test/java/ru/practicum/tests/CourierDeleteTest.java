package ru.practicum.tests;

import ru.practicum.base.BaseTest;
import ru.practicum.client.CourierClient;
import ru.practicum.model.CourierRequest;
import ru.practicum.util.DataGenerator;

import org.junit.jupiter.api.*;
import org.hamcrest.Matchers;

@DisplayName("Удаление курьера")
class CourierDeleteTest extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private Integer courierId;

    @BeforeEach
    void setUp() {
        CourierRequest body = DataGenerator.createValidCourierRequestBody();
        courier.create(body).statusCode(201);
        courierId = courier.login(DataGenerator.getCurrentCourierCredentials())
                .extract()
                .path("id");
    }

    @AfterEach
    void cleanUp() {
        if (courierId != null) {
            courier.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно удалить")
    void courierCanBeDeleted() {
        courier.delete(courierId)
                .statusCode(200)
                .body("ok", Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Нельзя удалить курьера без передачи параметра id")
    void deleteCourierMissingId() {
        courier.delete()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Нельзя удалить несуществующего курьера")
    void deleteCourierWhichNotExist() {
        courier.delete(courierId);
        courier.delete(courierId)
                .statusCode(404)
                .body("message", Matchers.equalTo("Курьера с таким id нет."));
    }
}
