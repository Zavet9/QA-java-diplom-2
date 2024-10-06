package pens;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.User;

import static io.restassured.RestAssured.given;

public class UserApiRequests {

    private final static String PENS_AUTH = "/api/auth/";

    @Step("Создание пользователя")
    public Response createUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(PENS_AUTH +"register");
    }

    @Step("Авторизация пользователя")
    public Response loginUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(PENS_AUTH + "login");
    }

    @Step("Изменение данных пользователя")
    public Response changeDataUser(User user , String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .when()
                .body(user)
                .patch(PENS_AUTH + "user");
    }

    @Step("Получение информации о пользователе")
    public Response getDataUser(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .when()
                .get(PENS_AUTH + "user");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken){
        return given()
                .header("Authorization",accessToken)
                .when()
                .delete(PENS_AUTH + "user");
    }

}
