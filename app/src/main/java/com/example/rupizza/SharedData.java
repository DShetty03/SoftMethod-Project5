package com.example.rupizza;


/**
 * Singleton class that provides shared access to the current order and order manager.
 * This class is used to manage the currently active order and all placed orders across
 * different controllers in the application.
 *
 * @author Richard Li (rl902)
 */
public class SharedData {
    // Singleton instance of SharedData
    private static SharedData instance = new SharedData();

    // Manages all placed orders
    private OrderManager manager = new OrderManager();

    // The current active order
    private Order order = manager.createOrder();

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Ensures that this class follows the Singleton design pattern.
     */
    private SharedData() { }

    /**
     * Provides access to the single instance of SharedData.
     *
     * @return The Singleton instance of SharedData.
     */
    public static SharedData getInstance() {
        return instance;
    }

    /**
     * Gets the current active order.
     *
     * @return The current Order object.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Gets the OrderManager that manages all placed orders.
     *
     * @return The OrderManager object.
     */
    public OrderManager getManager() {
        return manager;
    }

    /**
     * Sets a new active order.
     *
     * @param order The new Order object to set as the current active order.
     */
    public void setOrder(Order order) {
        this.order = order;
    }
}