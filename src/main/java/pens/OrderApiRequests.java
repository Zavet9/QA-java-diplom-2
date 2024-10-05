package pens;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.Order;

import static io.restassured.RestAssured.given;

public class OrderApiRequests {
    private final static String PENS_ORDER ="/api/orders";

    @Step("Создание заказа авторизованным пользователем")
    public Response createOrderAuthUser(Order order, String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body(order)
                .when()
                .post(PENS_ORDER);
    }

    @Step("Создание заказа неавторизованным пользователем")
    public Response createOrderNotAuthUser(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(PENS_ORDER);
    }

    @Step("Получение заказов авторизованным пользователем")
    public Response getOrdersAuthUser(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .get(PENS_ORDER);
    }

    @Step("Получение заказов неавторизованным пользователем")
    public Response getOrdersNotAuthUser(){
        return given()
                .header("Content-type", "application/json")
                .get(PENS_ORDER);
    }

}
