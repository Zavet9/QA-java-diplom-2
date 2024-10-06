package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.UserApiRequests;
import serialization.User;
import url.BaseUrlApi;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    User user = new User("Andrey1!@yandex.ru", "Andrey", "12345Password");
    UserApiRequests userApiRequests = new UserApiRequests();
    private String accessToken;
    @Before
    public void setUp(){
        BaseUrlApi.setUp();
        userApiRequests.createUser(user);
        accessToken = userApiRequests.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя позитивный тест")
    @Description("Авторизация пользователя позитивный тест")
    public void testLoginUser(){
        userApiRequests.loginUser(user)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация с невалидным email")
    public void testLoginUserIncorrectEmail(){
        user.setEmail("ios@yandex");
        userApiRequests.loginUser(user)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Авторизация с невалидным паролем")
    public void testLoginUserIncorrectPassword(){
        user.setPassword("000");
        userApiRequests.loginUser(user)
                .then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userApiRequests.deleteUser(accessToken);
        }
    }
}
