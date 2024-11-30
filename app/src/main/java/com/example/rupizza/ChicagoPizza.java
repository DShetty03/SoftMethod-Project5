package com.example.rupizza;

/**
 * A class for creating Chicago-style pizzas.
 * Implements the PizzaFactory interface to produce Chicago-style pizzas.
 * The crust types for Chicago pizzas are Deep Dish, Pan, and Stuffed.
 *
 * @author Divit Shetty (dps190)
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a Deep Dish-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Deluxe pizza with the specified size and Deep Dish crust.
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.DEEP_DISH, size);
    }

    /**
     * Creates a BBQ Chicken pizza with a Pan-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a BBQ Chicken pizza with the specified size and Pan crust.
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.PAN, size);
    }

    /**
     * Creates a Meatzza pizza with a Stuffed-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Meatzza pizza with the specified size and Stuffed crust.
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.STUFFED, size);
    }

    /**
     * Creates a Build Your Own pizza with a Pan-style crust.
     * The toppings for this pizza can be customized later.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Build Your Own pizza with the specified size and Pan crust.
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.PAN, size);
    }

    //testbed main
    public static void main(String[] args) {
        PizzaFactory chicagoFactory = new ChicagoPizza();

        // Create different pizzas
        Pizza deluxe = chicagoFactory.createDeluxe(Size.LARGE);
        Pizza bbqChicken = chicagoFactory.createBBQChicken(Size.MEDIUM);
        Pizza meatzza = chicagoFactory.createMeatzza(Size.SMALL);
        Pizza buildYourOwn = chicagoFactory.createBuildYourOwn(Size.LARGE);

        // Add toppings to Build Your Own
        buildYourOwn.addTopping(Topping.ONION);
        buildYourOwn.addTopping(Topping.MUSHROOM);

        // Print details
        System.out.println("Deluxe: " + deluxe + ", Price: $" + deluxe.price());
        System.out.println("BBQ Chicken: " + bbqChicken + ", Price: $" + bbqChicken.price());
        System.out.println("Meatzza: " + meatzza + ", Price: $" + meatzza.price());
        System.out.println("Build Your Own: " + buildYourOwn + ", Price: $" + buildYourOwn.price());
    }
}
