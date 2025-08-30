package com.worch.tests.util;

import com.worch.model.dto.request.LoginRequest;
import com.worch.model.dto.request.RegisterRequest;

public class TestUserBuilder {
    private String phone = "+79991234567";
    private String login = "testuser" + System.currentTimeMillis();
    private String password = "password123";
    private String email = "test@example.com";
    private String firstName = "Иван";
    private String lastName = "Иванов";

    public TestUserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public TestUserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public TestUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequest buildRegisterRequest() {
        return new RegisterRequest(phone, login, password, email, firstName, lastName);
    }

    public LoginRequest buildLoginRequest() {
        return new LoginRequest(login, password);
    }
}
