package com.example.rupizza;

import java.util.ArrayList;

/**
 * An abstract class representing a generic pizza.
 * Provides common properties such as crust type, size, and toppings,
 * and defines an abstract method for calculating the price of the pizza.
 * Includes helper methods to manage toppings.
 *
 * Extended by specific pizza classes Deluxe, BBQChicken, Meatzza, and BuildYourOwn.
 *
 * @author Divit Shetty (dps190)
 */
public abstract class Pizza {

    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    //Pizza constructor
    /**
     * Constructs a Pizza with the specified crust and size.
     * Initializes the list of toppings to an empty list.
     *
     * @param crust the crust type for the pizza.
     * @param size the size of the pizza (Small, Medium, or Large).
     */
    public Pizza (Crust crust, Size size){
        this.toppings = new ArrayList<>();
        this.crust = crust;
        this.size = size;
    }

    //Abstract method for price
    /**
     * Calculates the price of the pizza.
     * Must be implemented by subclasses.
     *
     * @return the price of the pizza as a double.
     */
    public abstract double price();

    /**
     * Gets the list of toppings on the pizza.
     *
     * @return an ArrayList of toppings.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Sets the list of toppings for the pizza.
     *
     * @param toppings the list of toppings to set.
     */
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Gets the crust type of the pizza.
     *
     * @return the crust type as a Crust enum.
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Sets the crust type for the pizza.
     *
     * @param crust the crust type to set.
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Gets the size of the pizza.
     *
     * @return the size of the pizza as a Size enum.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the pizza.
     *
     * @param size the size of the pizza to set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    //helper methods that could be used in BuildYourOwn
    //helper to add toppings
    /**
     * Adds a topping to the pizza.
     * Used in BuildYourOwn to customize the pizza.
     *
     * @param topping the topping to add.
     */
    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }

    //helper to remove toppings
    /**
     * Removes a topping from the pizza.
     * Used in BuildYourOwn to customize the pizza.
     *
     * @param topping the topping to remove.
     */
    public void removeTopping(Topping topping) {
        this.toppings.remove(topping);
    }

    /**
     * Provides a string representation of the pizza.
     * Includes crust type, size, and toppings.
     *
     * @return a string describing the pizza.
     */
    @Override
    public String toString() {
        return "Crust: " + crust + ", Size: " + size + ", Toppings: " + toppings;
    }

    // Testbed main method
    public static void main(String[] args) {
        //
        Pizza testPizza = new Pizza(Crust.HAND_TOSSED, Size.MEDIUM) {
            @Override
            public double price() {
                return 15.99; // Placeholder
            }
        };

        // Add some toppings
        testPizza.addTopping(Topping.SAUSAGE);
        testPizza.addTopping(Topping.PEPPERONI);
        testPizza.addTopping(Topping.MUSHROOM);

        // Display pizza details
        System.out.println("Pizza Details: " + testPizza);
        System.out.println("Price: $" + testPizza.price());

        // Remove a topping and display again
        testPizza.removeTopping(Topping.PEPPERONI);
        System.out.println("Updated Pizza Details: " + testPizza);
        System.out.println("Updated Price: $" + testPizza.price());
    }
}
