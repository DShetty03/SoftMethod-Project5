package com.example.rupizza;

/**
 * Represents a BBQ Chicken pizza.
 * This pizza comes with the default toppings: BBQ chicken, green pepper, provolone, and cheddar.
 * Prices vary based only on the size of the pizza.
 *
 * Extends the abstract Pizza class and implements the price calculation specific to BBQ Chicken.
 *
 * @author Divit Shetty (dps190)
 */
public class BBQChicken extends Pizza {
    // Std prices for BBQ Chicken pizza
    private static final double SMALL_PRICE = 14.99;
    private static final double MEDIUM_PRICE = 16.99;
    private static final double LARGE_PRICE = 19.99;

    /**
     * Constructs a BBQ Chicken pizza with the specified crust and size.
     * Adds the default toppings for BBQ Chicken: BBQ chicken, green pepper, provolone, and cheddar.
     *
     * @param crust the crust type for the BBQ Chicken pizza.
     * @param size the size of the pizza (Small, Medium, or Large).
     */
    public BBQChicken(Crust crust, Size size) {
        super(crust, size);
        // fixed toppings for BBQ chicken
        this.addTopping(Topping.BBQ_CHICKEN);
        this.addTopping(Topping.GREEN_PEPPER);
        this.addTopping(Topping.PROVOLONE);
        this.addTopping(Topping.CHEDDAR);
    }

    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
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
     * Provides a string representation of the BBQ Chicken pizza.
     * Includes the type of pizza along with details such as crust, size, and toppings.
     *
     * @return a string describing the BBQ Chicken pizza.
     */
    @Override
    public String toString() {
        return "BBQChicken Pizza: " + super.toString();
    }
}
