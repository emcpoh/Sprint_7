package ru.practicum.model;

public class CourierRequest {
    public String login;
    public String password;
    public String firstName;

    public CourierRequest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierRequest(String login, String password) {
        this(login, password, "");
    }
}