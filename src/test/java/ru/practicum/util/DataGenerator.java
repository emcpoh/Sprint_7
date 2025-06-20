package ru.practicum.util;

import ru.practicum.model.CourierRequest;
import ru.practicum.model.OrderRequest;

import java.time.LocalDate;
import java.util.UUID;

public final class DataGenerator {

    private static final int LEN = 10;
    public static String login;
    public static String password;
    public static String firstName;


    private DataGenerator() {}

    private static String rnd() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, LEN);
    }

    public static String login() {
        login = "l_" + rnd();
        return login;
    }

    public static String password() {
        password = "p_" + rnd();
        return password;
    }

    public static String firstName() {
        firstName = "f_" + rnd();
        return firstName;
    }

    public static CourierRequest getCurrentCourierCredentials() {
        return new CourierRequest(login, password);
    }

    public static CourierRequest createValidCourierRequestBody() {
        login = login();
        password = password();
        firstName = firstName();
        return new CourierRequest(login, password, firstName);
    }

    public static CourierRequest createValidCourierRequestBodyWithoutFirstName() {
        login = login();
        password = password();
        return new CourierRequest(login, password);
    }

    public static OrderRequest createValidOrderBody(String... colors) {
        return new OrderRequest(
                "Ivan",
                "Petrov",
                "ул. Ленина, 1",
                "4",
                "+7 900 123-45-67",
                5,
                LocalDate.now().plusDays(1).toString(),
                "Звоните за час",
                colors
        );
    }
}
