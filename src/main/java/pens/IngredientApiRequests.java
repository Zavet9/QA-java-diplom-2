package pens;

import io.qameta.allure.Step;
import serialization.Ingredient;

import static io.restassured.RestAssured.given;

public class IngredientApiRequests {
    private static final String PENS_INGREDIENTS = "/api/ingredients";

    @Step("Получение информации об ингредиентах")
    public Ingredient getIngredientInfo(){
        return given()
                .header("Content-type", "application/json")
                .get(PENS_INGREDIENTS)
                .as(Ingredient.class);
    }
}
