package com.example.rupizza;

/**
 * Represents a Build Your Own pizza.
 * This pizza allows the customer to select their own toppings.
 * Prices vary based on the size of the pizza and the number of toppings that are added.
 * Extends the abstract Pizza class and implements the price calculation specific to Build Your Own.
 * The base price is determined by the size of the pizza, and a fixed cost is added for each topping.
 *
 * @author Divit Shetty (dps190)
 */
public class BuildYourOwn extends Pizza {
    // Base prices for Build Your Own pizza (before toppings)
    private static final double SMALL_PRICE = 8.99;
    private static final double MEDIUM_PRICE = 10.99;
    private static final double LARGE_PRICE = 12.99;

    // Cost per topping
    private static final double TOPPING_COST = 1.69;


    /**
     * Constructs a Build Your Own pizza with the specified crust and size.
     * Toppings can be added or removed after creation.
     *
     * @param crust the crust type for the Build Your Own pizza.
     * @param size the size of the pizza (Small, Medium, or Large).
     */
    public BuildYourOwn(Crust crust, Size size) {
        super(crust, size);
    }

    /**
     * Calculates the price of the Build Your Own pizza based on its size and number of toppings.
     *
     * @return the total price of the pizza as a double.
     */
    @Override
    public double price() {
        double basePrice;
        switch (this.getSize()) {
            case SMALL: basePrice = SMALL_PRICE; break;
            case MEDIUM: basePrice = MEDIUM_PRICE; break;
            case LARGE: basePrice = LARGE_PRICE; break;
            default: basePrice = 0.0; // Fallback
        }
        // Add cost for all the toppings
        return basePrice + (this.getToppings().size() * TOPPING_COST);
    }

    /**
     * Provides a string representation of the Build Your Own pizza.
     * Includes the type of pizza along with details such as crust, size, and toppings.
     *
     * @return a string describing the Build Your Own pizza.
     */
    @Override
    public String toString() {
        return "Build Your Own Pizza: " + super.toString();
    }
}