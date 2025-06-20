package ru.practicum.tests;

import ru.practicum.base.BaseTest;
import ru.practicum.client.OrderClient;
import ru.practicum.util.DataGenerator;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@DisplayName("Создание заказа")
class OrderCreateTest extends BaseTest {

    private final OrderClient order = new OrderClient();

    @ParameterizedTest(name = "Цвет: {0}")
    @MethodSource("colorCombinations")
    void orderColorVariants(String[] colors) {

        order.create(DataGenerator.createValidOrderBody(colors))
                .statusCode(201)
                .body("track", Matchers.notNullValue());
    }

    private static Stream<Arguments> colorCombinations() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{})
        );
    }
}
