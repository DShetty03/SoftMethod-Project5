package com.example.rupizza;

import java.util.ArrayList;

/**
 * Represents a single order
 * Each order has a unique order number and a list of pizzas.
 * This class provides methods to add, remove, and calculate totals for pizzas in the order.
 *
 * @author Divit Shetty (dps190)
 */

public class Order {
    private int number; // Unique order number
    private ArrayList<Pizza> pizzas; // List of pizzas in the order


    /**
     * Constructs an Order with a unique order number.
     *
     * @param number the unique order number for this order.
     */
    public Order(int number) {
        this.number = number; // Order number must be passed explicitly
        this.pizzas = new ArrayList<>();
    }

    // Method to add a pizza to the order
    /**
     * Adds a pizza to the order.
     *
     * @param pizza the pizza to add to the order.
     */
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    // Method to remove a pizza from the order
    /**
     * Removes a pizza from the order.
     *
     * @param pizza the pizza to remove from the order.
     */
    public void removePizza(Pizza pizza) {
        this.pizzas.remove(pizza);
    }

    // Method to calculate subtotal
    /**
     * Calculates the subtotal of all pizzas in the order.
     *
     * @return the subtotal of the order as a double.
     */
    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        return subtotal;
    }

    // Calculate sales tax
    // sales tax given in OrderManager
    /**
     * Calculates the sales tax for the order based on the given tax rate.
     *
     * @param taxRate the sales tax rate as a decimal (e.g., 0.06625 for 6.625%).
     * @return the calculated sales tax as a double.
     */
    public double calculateTax(double taxRate) {
        return calculateSubtotal() * taxRate;
    }

    // Calculate order total
    /**
     * Calculates the total cost of the order, including sales tax.
     *
     * @param taxRate the sales tax rate as a decimal (0.06625 for 6.625%) which is stated in other class.
     * @return the total cost of the order as a double.
     */
    public double calculateTotal(double taxRate) {
        return calculateSubtotal() + calculateTax(taxRate);
    }


    /**
     * Gets the unique order number for this order.
     *
     * @return the order number as an integer.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the list of pizzas in this order.
     *
     * @return the list of pizzas as an ArrayList.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Provides a string representation of the order, including order details,
     * pizzas, and the subtotal.
     *
     * @return a string representation of the order.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Number: ").append(number).append("\n");
        for (Pizza pizza : pizzas) {
            sb.append(pizza).append(", Price: $").append(String.format("%.2f", pizza.price())).append("\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", calculateSubtotal())).append("\n");
        return sb.toString();
    }
}