package RUPizza;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Controller class for managing placed orders.
 * Handles viewing, removing, and exporting orders to a file.
 *
 * @author Richard Li (rl902)
 */
public class PlacedController implements Initializable {

    @FXML
    private TextField orderTot;

    @FXML
    private ComboBox<Integer> orderNum;

    @FXML
    private ListView<Pizza> pizzaList;

    private OrderManager orderman = SharedData.getInstance().getManager();

    private Order current = orderman.getOrders().get(0);

    /**
     * Handles changes in the selected order number from the ComboBox.
     * Updates the displayed pizzas and total cost for the selected order.
     */
    @FXML
    private void handleOrderChange() {
        Integer newOrderNum = orderNum.getValue();

        // Check if the selection is not null and valid
        if (newOrderNum != null && newOrderNum > 0) {
            // Retrieve the corresponding order
            Order newOrder = orderman.getOrders().get(newOrderNum - 1);
            current = newOrder;

            clear();
            loadOrder();
            updateCost();
        } else {
            // Handle the case where no valid selection is made
            System.out.println("No valid order selected");
            current = null;
            clear();
        }
    }

    /**
     * Handles removing the currently selected order from the list.
     * Updates the ComboBox and clears the ListView if the order list becomes empty.
     */
    @FXML
    private void handleRemove(){
        orderman.getOrders().remove(current);
        int selectedItem = orderNum.getValue();

        // Remove the selected item
        if (selectedItem != 0) { // Check if a valid item is selected
            orderNum.getItems().remove(Integer.valueOf(selectedItem)); // Remove by value, not index

            // Select the first item left in the list, if any
            if (!orderNum.getItems().isEmpty()) {
                orderNum.setValue(orderNum.getItems().get(0)); // Select the first item
            } else {
                orderNum.setValue(0); // Clear selection if the list is empty
            }

            System.out.println("Removed: " + selectedItem);
        }
    }

    /**
     * Opens a file chooser dialog to allow the user to select a file path for saving the exported orders.
     *
     * @return The selected file path, or null if no file was selected.
     */
    private String getFilePath() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File Destination");

        // Set file extension filters (optional, if you want .txt files)
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Show the save dialog and get the selected file
        File file = fileChooser.showSaveDialog(new Stage());

        // Check if a file was selected and return the file path
        return (file != null) ? file.getAbsolutePath() : null;
    }

    /**
     * Exports all placed orders to a file specified by the user using the file chooser.
     *
     * @throws IOException if an error occurs while writing to the file.
     */
    @FXML
    private void handleExport() throws IOException {
        String filePath = getFilePath();
        orderman.exportOrders(filePath);
    }

    /**
     * Loads all available order numbers into the ComboBox for selection.
     * Displays a message if there are no orders available.
     */
    private void loadOrderNums(){
        ArrayList<Order> orders = orderman.getOrders();
        int size = 0;
        for(Order order : orders){
            size++;
        }

        if (size==0){
            System.out.println("Please add an order");
            return;
        }

        for (int i = 1;i < size+1; i++){
            orderNum.getItems().add(i);
        }

        orderNum.setValue(1);
    }

    /**
     * Loads the pizzas for the currently selected order into the ListView.
     */
    private void loadOrder(){
        Order selectedOrder = current;
        for (Pizza pizza : selectedOrder.getPizzas()) {
            pizzaList.getItems().add(pizza);
        }
    }

    /**
     * Updates the total cost for the currently selected order and displays it in the TextField.
     */
    private void updateCost(){
        Double total = current.calculateTotal(.06625);
        orderTot.setText(String.format("%.2f", total));
    }

    /**
     * Clears the ListView displaying the pizzas in the current order.
     */
    private void clear(){
        pizzaList.getItems().clear();
    }

    /**
     * Initializes the controller. Loads the available orders into the ComboBox,
     * displays the pizzas for the first order, and updates the total cost.
     *
     * @param url            URL location of the FXML file.
     * @param resourceBundle Resource bundle for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrderNums();
        loadOrder();
        updateCost();
    }
}
