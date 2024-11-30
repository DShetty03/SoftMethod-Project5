package com.example.rupizza;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//class to handle other stuff with Order
// like tracking, cancelling, etc.

/**
 * Manages all orders in the RU Pizzeria system.
 * Provides functionality for creating, tracking, canceling, and exporting orders.
 * Orders are assigned unique order numbers automatically.
 *
 * @author Divit Shetty (dps190)
 */
public class OrderManager {
    private static final double TAX_RATE = 0.06625; // New Jersey sales tax as per writeup
    private static int nextOrderNumber = 1; // simple order number; can change to more complex later
    private final ArrayList<Order> orders; // List of all orders

    /**
     * Constructs an OrderManager to manage all orders.
     */
    public OrderManager() {
        orders = new ArrayList<>();
    }

    // Create a new order
    /**
     * Creates a new order and adds it to the list of orders.
     *
     * @return the newly created Order instance.
     */
    public Order createOrder() {
        Order newOrder = new Order(nextOrderNumber++);
        orders.add(newOrder);
        return newOrder;
    }

    // Cancel an order by giving the order number
    /**
     * Cancels an order by its unique order number.
     *
     * @param orderNumber the unique number of the order to be canceled.
     * @return true if the order was found and removed, false otherwise.
     */
    public boolean cancelOrder(int orderNumber) {
        return orders.removeIf(order -> order.getNumber() == orderNumber);
    }

    // Method to export all orders to a txt file
    /**
     * Exports all orders to a text file.
     * Each order includes its order number, list of pizzas, and the subtotal.
     *
     * @param filePath the file path where orders will be exported.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void exportOrders(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                writer.write(order.toString());
                writer.newLine();
            }
        }
    }

    // Get all current orders
    /**
     * Retrieves the list of all current orders.
     *
     * @return an ArrayList of all orders.
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    //testbed main
    public static void main(String[] args) {
        // Create instance of OrderManager
        OrderManager manager = new OrderManager();


        Order order1 = manager.createOrder();
        Order order2 = manager.createOrder();

        // Create pizza factories
        PizzaFactory chicagoFactory = new ChicagoPizza();
        PizzaFactory nyFactory = new NYPizza();

        // Add pizzas to orders
        order1.addPizza(chicagoFactory.createDeluxe(Size.LARGE));
        order1.addPizza(chicagoFactory.createBBQChicken(Size.MEDIUM));
        order1.addPizza(chicagoFactory.createBuildYourOwn(Size.SMALL));
        order1.getPizzas().get(2).addTopping(Topping.ONION); // Adding toppings to Build Your Own
        order1.getPizzas().get(2).addTopping(Topping.MUSHROOM);

        order2.addPizza(nyFactory.createMeatzza(Size.MEDIUM));
        order2.addPizza(nyFactory.createBuildYourOwn(Size.LARGE));
        order2.getPizzas().get(1).addTopping(Topping.PEPPERONI);
        order2.getPizzas().get(1).addTopping(Topping.GREEN_PEPPER);

        // Display all orders
        System.out.println("All Orders:");
        for (Order order : manager.getOrders()) {
            System.out.println(order);
        }

        // Cancel an order
        System.out.println("\nCanceling Order #" + order1.getNumber());
        manager.cancelOrder(order1.getNumber());

        // Display updated orders
        System.out.println("Remaining Orders:");
        for (Order order : manager.getOrders()) {
            System.out.println(order);
        }

        // Export orders to a file
        try {
            manager.exportOrders("orders.txt");
            System.out.println("\nOrders exported to 'orders.txt'.");
        } catch (IOException e) {
            System.err.println("Failed to export orders: " + e.getMessage());
        }
    }
}