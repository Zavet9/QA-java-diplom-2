package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.UserApiRequests;
import serialization.User;
import url.BaseUrlApi;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegistrationUserTest {

    User user = new User("Andrey1!@yandex.ru", "Andrey", "12345Password");
    UserApiRequests userApiRequests = new UserApiRequests();
    private String accessToken;

    @Before
    public void setUp(){
        BaseUrlApi.setUp();
    }

    @Test
    @DisplayName("Регистрация пользователя позитивный тест")
    public void testCreateUser(){
        userApiRequests.createUser(user)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Регистрация пользователя с данными ранее зарегистрированного пользователя")
    public void testCreateDuplicateUser(){
        userApiRequests.createUser(user);
        userApiRequests.createUser(user)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Регистрация пользователя без email")
    public void testCreateUserWithoutEmail(){
        user.setEmail("");
        userApiRequests.createUser(user)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    public void testCreateUserWithoutPassword(){
        user.setPassword("");
        userApiRequests.createUser(user)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);

    }

    @Test
    @DisplayName("Регистрация пользователя без имени")
    public void testCreateUserWithoutName(){
        user.setName("");
        userApiRequests.createUser(user)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @After
    public void deleteUser(){
        accessToken = userApiRequests.loginUser(user).then().extract().path("accessToken");
        if (accessToken!=null) {
            userApiRequests.deleteUser(accessToken);
        }
    }

}
