package com.worch.tests.constants;

public final class TestApiUrls {

    private TestApiUrls() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String API_BASE = "/api/v1";
    public static final String AUTH_BASE = API_BASE + "/auth";
    public static final String CHANNELS_BASE = API_BASE + "/channels";

    public static final String REGISTER = AUTH_BASE + "/register";
    public static final String LOGIN = AUTH_BASE + "/login";
    public static final String LOGOUT = AUTH_BASE + "/logout";

    public static final String REGISTRATION_SUCCESS = "Регистрация прошла успешно";
    public static final String LOGOUT_SUCCESS = "Вы успешно вышли из системы";

    public static final String INVALID_CREDENTIALS = "Неверные учетные данные";
    public static final String INVALID_TOKEN = "токен недействителен";
    public static final String LOGIN_EXISTS = "Логин уже занят";
    public static final String INVALID_EMAIL = "Некорректный email";
    
    public static final String CHANNEL_NOT_FOUND = "Канал с таким ID не найден";
    public static final String INVALID_UUID = "Некорректный UUID";
    public static final String AUTHENTICATION_REQUIRED = "Требуется аутентификация";
}
