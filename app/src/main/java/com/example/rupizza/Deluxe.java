package com.example.rupizza;

/**
 * Represents a Deluxe pizza.
 * This pizza comes with default toppings sausage, pepperoni, green pepper, onion, and mushroom.
 * Prices vary based on the size of the pizza.
 * Extends the abstract Pizza class and implements the price calculation specific to Deluxe.
 * @author Divit Shetty (dps190)
 */
public class Deluxe extends Pizza{

    //Base prices for deluxe pizzas (no toppings)
    private static final double SMALL_PRICE = 16.99;
    private static final double MEDIUM_PRICE = 18.99;
    private static final double LARGE_PRICE = 20.99;

    /**
     * Constructs a Deluxe pizza with the specified crust and size.
     * Adds the default toppings for Deluxe: sausage, pepperoni, green pepper, onion, and mushroom.
     *
     * @param crust the crust type for the Deluxe pizza.
     * @param size the size of the pizza (Small, Medium, or Large).
     */
    public Deluxe(Crust crust, Size size){
        super(crust,size);
        // add fixed deluxe toppings
        this.addTopping(Topping.SAUSAGE);
        this.addTopping(Topping.PEPPERONI);
        this.addTopping(Topping.GREEN_PEPPER);
        this.addTopping(Topping.ONION);
        this.addTopping(Topping.MUSHROOM);
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
     *
     * @return the price of the pizza as a double.
     */
    @Override
    public double price(){
        switch (this.getSize()){
            case SMALL: return SMALL_PRICE;
            case MEDIUM: return MEDIUM_PRICE;
            case LARGE: return LARGE_PRICE;
            default: return 0.0;
        }
    }

    //Maybe remove this
    // toString to indicate the type of pizza
    /**
     * Provides a string representation of the Deluxe pizza.
     * Includes the type of pizza along with details such as crust, size, and toppings.
     *
     * @return a string describing the Deluxe pizza.
     */
    @Override
    public String toString() {
        return "Deluxe Pizza: " + super.toString();
    }
}
