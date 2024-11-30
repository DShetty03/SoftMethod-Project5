package com.example.rupizza;

/**
 * Represents a Meatzza pizza.
 * This pizza comes with the fixed toppings sausage, pepperoni, beef, and ham.
 * Prices vary based on the size of the pizza.
 * Extends the abstract Pizza class and implements the price calculation specific to Meatzza.
 *
 * @author Divit
 */
public class Meatzza extends Pizza {
    // Std prices for Meatzza pizza
    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;


    /**
     * Constructs a Meatzza pizza with the specified crust and size.
     * Adds the default toppings for Meatzza: sausage, pepperoni, beef, and ham.
     *
     * @param crust the crust type for the Meatzza pizza.
     * @param size the size of the pizza (Small, Medium, or Large).
     */
    public Meatzza(Crust crust, Size size) {
        super(crust, size);
        // Default toppings
        this.addTopping(Topping.SAUSAGE);
        this.addTopping(Topping.PEPPERONI);
        this.addTopping(Topping.BEEF);
        this.addTopping(Topping.HAM);
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
     *
     * @return the price of the pizza as a double.
     */
    @Override
    public double price() {
        switch (this.getSize()) {
            case SMALL: return SMALL_PRICE;
            case MEDIUM: return MEDIUM_PRICE;
            case LARGE: return LARGE_PRICE;
            default: return 0.0;
        }
    }

    //Maybe remove this
    // toString to indicate the type of pizza
    /**
     * Provides a string representation of the Meatzza pizza.
     * Includes the type of pizza along with details such as crust, size, and toppings.
     *
     * @return a string describing the Meatzza pizza.
     */
    @Override
    public String toString() {
        return "Meatzza: " + super.toString();
    }
}
