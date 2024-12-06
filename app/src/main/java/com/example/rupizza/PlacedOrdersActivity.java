package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;


public class PlacedOrdersActivity extends AppCompatActivity {
    private Spinner spinnerOrderNum;

    private ListView listViewOrderPizzas;

    private EditText editTextOrderTot;

    private Button buttonCancelOrder;

    private ArrayList<Order> allOrders; // List of all orders

    private ArrayAdapter<String> spinnerAdapter; // Adapter for the Spinner
    private ArrayAdapter<Pizza> listAdapter; // Adapter for the ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placed_activity);

        // Initialize UI components
        spinnerOrderNum = findViewById(R.id.spinnerOrderNum);
        listViewOrderPizzas = findViewById(R.id.listViewOrderPizzas);
        editTextOrderTot = findViewById(R.id.editTextOrderTot);
        buttonCancelOrder = findViewById(R.id.buttonCancelOrder);

        // Get all orders from the manager
        allOrders = SharedData.getInstance().getManager().getOrders();

        // Populate the spinner with order numbers
        populateSpinner();

        listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );
        listViewOrderPizzas.setAdapter(listAdapter);

        if (!allOrders.isEmpty()) {
            loadOrderDetails(0); // Load the first order by default
        }

        // Handle spinner selection changes
        spinnerOrderNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Load the details of the selected order
                loadOrderDetails(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        buttonCancelOrder.setOnClickListener(v -> {
            int selectedPosition = spinnerOrderNum.getSelectedItemPosition(); // Get the selected order index
            if (selectedPosition != -1 && !allOrders.isEmpty()) {
                // Remove the selected order from the list
                allOrders.remove(selectedPosition);

                // Update the spinner to reflect the change
                populateSpinner();

                // Load the first remaining order, if any
                if (!allOrders.isEmpty()) {
                    loadOrderDetails(0); // Load the first order
                    spinnerOrderNum.setSelection(0); // Set the spinner to the first order
                } else {
                    // Clear the ListView and total if no orders remain
                    listAdapter.clear();
                    listAdapter.notifyDataSetChanged();
                    editTextOrderTot.setText(""); // Clear the total field

                    Toast.makeText(this, "No orders remaining.", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "Order cancelled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No order selected or no orders available.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void populateSpinner() {
        ArrayList<String> orderNumbers = new ArrayList<>();
        for (Order order : allOrders) {
            orderNumbers.add(String.valueOf(order.getNumber())); // Extract order numbers
        }

        // Set up the adapter for the Spinner
        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                orderNumbers
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrderNum.setAdapter(spinnerAdapter);
    }

    private void loadOrderDetails(int orderIndex) {
        // Get the selected order
        Order selectedOrder = allOrders.get(orderIndex);

        // Get the pizzas for the selected order
        ArrayList<Pizza> pizzas = selectedOrder.getPizzas();

        // Log the data for debugging
        Log.d("DEBUG", "Loading order: " + selectedOrder);
        Log.d("DEBUG", "Pizzas in order: " + pizzas);

        listAdapter.clear(); // Clear the current items in the adapter
        listAdapter.addAll(pizzas); // Add the pizzas from the selected order
        listAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the ListView

        // Update the total cost for the selected order
        double total = selectedOrder.calculateTotal(0.06625);
        editTextOrderTot.setText(String.format("$%.2f", total));
    }


}
