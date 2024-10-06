package serialization;

import java.util.List;

public class Order {
    private List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // Дефолтный конструктор
    public Order() {
    }

    // Конструктор для создания заказа
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
