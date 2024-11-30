package com.example.rupizza;

/**
 * A class for creating New York-style pizzas.
 * Implements the PizzaFactory interface to produce New York Style Pizzas
 * The crust types for New York pizzas are Brooklyn, Thin, and Hand-Tossed.
 *
 * @author Divit Shetty (dps190)
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a Brooklyn-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Deluxe pizza with the specified size and Brooklyn crust.
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.BROOKLYN, size);
    }

    /**
     * Creates a BBQ Chicken pizza with a Thin-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a BBQ Chicken pizza with the specified size and Thin crust.
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.THIN, size);
    }

    /**
     * Creates a Meatzza pizza with a Hand-Tossed-style crust.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Meatzza pizza with the specified size and Hand-Tossed crust.
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.HAND_TOSSED, size);
    }

    /**
     * Creates a Build Your Own pizza with a Hand-Tossed-style crust.
     * The toppings for this pizza can be customized later.
     *
     * @param size the size of the pizza (Small, Medium, or Large).
     * @return a Build Your Own pizza with the specified size and Hand-Tossed crust.
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.HAND_TOSSED, size);
    }

    //Testbed main
    public static void main(String[] args) {
        PizzaFactory nyFactory = new NYPizza();

        // Create different pizzas
        Pizza deluxe = nyFactory.createDeluxe(Size.LARGE);
        Pizza bbqChicken = nyFactory.createBBQChicken(Size.MEDIUM);
        Pizza meatzza = nyFactory.createMeatzza(Size.SMALL);
        Pizza buildYourOwn = nyFactory.createBuildYourOwn(Size.LARGE);

        // Add toppings to Build Your Own
        buildYourOwn.addTopping(Topping.ONION);
        buildYourOwn.addTopping(Topping.BBQ_CHICKEN);

        // Print details
        System.out.println("Deluxe: " + deluxe + ", Price: $" + deluxe.price());
        System.out.println("BBQ Chicken: " + bbqChicken + ", Price: $" + bbqChicken.price());
        System.out.println("Meatzza: " + meatzza + ", Price: $" + meatzza.price());
        System.out.println("Build Your Own: " + buildYourOwn + ", Price: $" + buildYourOwn.price());
    }
}