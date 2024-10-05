package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.IngredientApiRequests;
import pens.OrderApiRequests;
import pens.UserApiRequests;
import serialization.Ingredient;
import serialization.Order;
import serialization.User;
import url.BaseUrlApi;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {


    UserApiRequests userApiRequests = new UserApiRequests();
    OrderApiRequests orderApiRequests = new OrderApiRequests();
    IngredientApiRequests ingredientApiRequests = new IngredientApiRequests();
    User user = new User("Andrey1!@yandex.ru", "Andrey", "12345Password");

    private String accessToken;
    private Ingredient ingredientList;
    private List<String> ingredients;
    private Order order;

    @Before
    public void setUp() {
        BaseUrlApi.setUp();
        ingredientList = ingredientApiRequests.getIngredientInfo();
        ingredients = new ArrayList<>();
        ingredients.add(ingredientList.getData().get(1).get_id());
        ingredients.add(ingredientList.getData().get(2).get_id());
        ingredients.add(ingredientList.getData().get(3).get_id());
        userApiRequests.createUser(user);
        accessToken = userApiRequests.loginUser(user).then().extract().path("accessToken");
        order = new Order(ingredients);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    public void testCreateOrderAuthUser(){
        orderApiRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void testCreateOrderNotAuthUser(){
        orderApiRequests.createOrderNotAuthUser(order)
                .then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов, авторизованный пользователь")
    public void testCreateOrderNoIngredients(){
        ingredients.clear();
        order = new Order(ingredients);
        orderApiRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов")
    public void testCreateOrderWithWrongHash(){
        ingredients.clear();
        ingredients.add("wrong");
        order = new Order(ingredients);
        orderApiRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat()
                .statusCode(500);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userApiRequests.deleteUser(accessToken);
        }
    }

}
