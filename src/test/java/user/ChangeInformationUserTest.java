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

public class ChangeInformationUserTest {

    User user = new User("Andrey1!@yandex.ru", "Andrey", "12345Password");
    UserApiRequests userApiRequests = new UserApiRequests();
    private String accessToken;
    private String updateEmail = "Andrey!@yandex.ru";
    private String updateName = "Andrey1";

    @Before
    public void setUp(){
        BaseUrlApi.setUp();
        userApiRequests.createUser(user);
        accessToken = userApiRequests.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение email авторизованного пользователя")
    @Description("Позитивный тест изменения email авторизованного пользователя")
    public void testUpdateEmailAuthUser(){
        user.setEmail(updateEmail);
        userApiRequests.changeDataUser(user,accessToken)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Изменение имени авторизованного пользователя")
    @Description("Позитивный тест изменение имени авторизованного пользователя")
    public void checkUpdateNameAuthUser(){
        user.setName(updateName);
        userApiRequests.changeDataUser(user,accessToken)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Изменение email неавторизованного пользователя")
    @Description("Проверка корректности поведение АС при попытке изменить email неавторизованного пользователя")
    public void checkUpdateEmailNotAuthUser(){
        accessToken="";
        user.setEmail(updateEmail);
        userApiRequests.changeDataUser(user,accessToken)
                .then().assertThat()
                .body("success", equalTo(false)).
                and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    @Description("Проверка корректности поведение АС при попытке изменить имя неавторизованного пользователя")
    public void checkUpdateNameNotAuthUser() {
        accessToken="";
        user.setName(updateName);
        userApiRequests.changeDataUser(user, accessToken)
                .then().assertThat()
                .body("success", equalTo(false)).
                and()
                .statusCode(401);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userApiRequests.deleteUser(accessToken);
        }
    }
}
