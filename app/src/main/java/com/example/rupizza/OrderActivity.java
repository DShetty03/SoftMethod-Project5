package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {
    private EditText editTextOrderNum;

    private ListView listViewPizzas;

    private EditText editTextSubTot;

    private EditText editTextTax;

    private EditText editTextTot;

    private Button buttonRemPizza;

    private Button buttonClearOrder;

    private Button buttonPlaceOrder;

    private ArrayAdapter<Pizza> adapter;
    private ArrayList<Pizza> pizzas;
    private int number;
    private Double sub;
    private Double taxTot;
    private Double total;
    private int selectedItemPosition = -1; // To track selected item position


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        editTextOrderNum = findViewById(R.id.editTextOrderNum);
        listViewPizzas = findViewById(R.id.listViewPizzas);
        editTextSubTot = findViewById(R.id.editTextSubTot);
        editTextTax = findViewById(R.id.editTextTax);
        editTextTot = findViewById(R.id.editTextTot);
        buttonRemPizza = findViewById(R.id.buttonRemPizza);
        buttonClearOrder = findViewById(R.id.buttonClearOrder);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        pizzas = new ArrayList<>(SharedData.getInstance().getOrder().getPizzas());

        number = SharedData.getInstance().getOrder().getNumber();
        editTextOrderNum.setText(Integer.toString(number));
        updateMoney();

        // Create a simple ArrayAdapter to display pizza names
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout for each item in the list
                pizzas
        );
        listViewPizzas.setAdapter(adapter); // Set the adapter to the ListView

        // Highlight selected item
        listViewPizzas.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;

            // Highlight the selected item
            for (int i = 0; i < listViewPizzas.getChildCount(); i++) {
                if (i == position) {
                    listViewPizzas.getChildAt(i).setBackgroundColor(Color.LTGRAY); // Highlight color
                } else {
                    listViewPizzas.getChildAt(i).setBackgroundColor(Color.TRANSPARENT); // Reset other items
                }
            }
        });

        // Remove selected item when button is clicked
        buttonRemPizza.setOnClickListener(v -> {
            if (selectedItemPosition != -1) {
                pizzas.remove(selectedItemPosition); // Remove from the list
                adapter.notifyDataSetChanged(); // Update the ListView

                SharedData.getInstance().getOrder().getPizzas().remove(selectedItemPosition);

                updateMoney();

                selectedItemPosition = -1;
            } else {
                Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
            }
        });

        buttonClearOrder.setOnClickListener(v -> {
            // Clear all items from the data source
            pizzas.clear();
            adapter.notifyDataSetChanged();

            SharedData.getInstance().getOrder().getPizzas().clear();

            updateMoney();

            Toast.makeText(this, "All items cleared!", Toast.LENGTH_SHORT).show();
        });

        buttonPlaceOrder.setOnClickListener(v -> {
            handlePlace();
        });

    }

    /**
     * Updates the subtotal, tax, and total cost based on the current pizzas in the order.
     */
    private void updateMoney() {
        sub = SharedData.getInstance().getOrder().calculateSubtotal();
        taxTot = SharedData.getInstance().getOrder().calculateTax(.06625);
        total = SharedData.getInstance().getOrder().calculateTotal(.06625);

        editTextSubTot.setText(Double.toString(Math.round(sub * 100.0) / 100.0));
        editTextTax.setText(Double.toString(Math.round(taxTot * 100.0) / 100.0));
        editTextTot.setText(Double.toString(Math.round(total * 100.0) / 100.0));
    }

    private void handlePlace() {
        // Create a new order
        Order newOrder = SharedData.getInstance().getManager().createOrder();
        SharedData.getInstance().setOrder(newOrder);

        Log.d("DEBUG", "OrderManager now has " + SharedData.getInstance().getManager().getOrders().size() + " orders.");

        // Update the order number and prices
        number = SharedData.getInstance().getOrder().getNumber();

        Log.d("DEBUG", "Last order was " + SharedData.getInstance().getManager().getOrders().get(number-2).toString() + ".");

        editTextOrderNum.setText(Integer.toString(number));
        updateMoney();

        Log.d("DEBUG", "we should be on the new order now " + SharedData.getInstance().getManager().getOrders().get(number-1).toString() + ".");

        // Get the new pizzas list and update the adapter
        pizzas = new ArrayList<>(SharedData.getInstance().getOrder().getPizzas());

        // Clear the adapter and set the new list
        adapter.clear(); // Clear existing data in the adapter
        adapter.addAll(pizzas); // Add the new pizzas list
        adapter.notifyDataSetChanged(); // Notify the adapter of changes

        selectedItemPosition = -1;

        Log.d("DEBUG", "check OrderManager now has " + SharedData.getInstance().getManager().getOrders().size() + " orders.");
        Log.d("DEBUG", "check Last order was " + SharedData.getInstance().getManager().getOrders().get(number-2).toString() + ".");


        Toast.makeText(this, "New order placed!", Toast.LENGTH_SHORT).show();
    }


}
