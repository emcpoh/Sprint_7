package ru.practicum.tests;

import ru.practicum.base.BaseTest;
import ru.practicum.client.OrderClient;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Получение заказа")
class OrderListTest extends BaseTest {

    private final OrderClient order = new OrderClient();

    @Test
    void listContainsOrders() {
        order.list()
                .statusCode(200)
                .body("orders", Matchers.not(Matchers.empty()));
    }
}