package com.example.rupizza;

/**
 * A factory interface for creating different types of pizzas.
 * Defines methods for creating pizzas of specific types and sizes.
 * This interface is implemented by the classes ChicagoPizza and NYPizza
 *
 * @author Divit Shetty (dps190)
 */
public interface PizzaFactory {
    Pizza createDeluxe(Size size);
    Pizza createBBQChicken(Size size);
    Pizza createMeatzza(Size size);
    Pizza createBuildYourOwn(Size size);
}
