package com.example.rupizza;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Activity for managing the NY-style pizza ordering interface.
 * Handles the selection of pizza type, size, crust, toppings, and order processing.
 *
 * @author Divit Shetty
 */
public class NYActivity extends AppCompatActivity {

    private Spinner typeNYSpinner, spinnerCrustNY;
    private ListView listViewAvailableToppingsNY, listViewSelectedToppingsNY;
    private ImageView imageNY;
    private RadioGroup sizeGroupNY;
    private RadioButton radioSmallNY, radioMediumNY, radioLargeNY;
    private EditText editTextCrustNY, editTextPriceNY;
    private Button buttonAddToOrderNY;

    private Order order;
    private PizzaFactory nyFactory = new NYPizza();
    private Pizza current;

    private ArrayAdapter<String> crustAdapter, availableToppingsAdapter, selectedToppingsAdapter;
    private ArrayList<Topping> availableToppings = new ArrayList<>();
    private ArrayList<Topping> selectedToppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_activity);

        // Initialize Views
        typeNYSpinner = findViewById(R.id.typeNYSpinner);
        spinnerCrustNY = findViewById(R.id.spinnerCrustNY);
        listViewAvailableToppingsNY = findViewById(R.id.listViewAvailableToppingsNY);
        listViewSelectedToppingsNY = findViewById(R.id.listViewSelectedToppingsNY);
        imageNY = findViewById(R.id.imageNY);
        sizeGroupNY = findViewById(R.id.radioGroupSizeNY);
        radioSmallNY = findViewById(R.id.radioSmallNY);
        radioMediumNY = findViewById(R.id.radioMediumNY);
        radioLargeNY = findViewById(R.id.radioLargeNY);
        editTextPriceNY = findViewById(R.id.editTextPriceNY);
        buttonAddToOrderNY = findViewById(R.id.buttonAddOrderNY);

        // Load shared data
        order = SharedData.getInstance().getOrder();

        // Load crust options first
        loadCrustOptions();

        // Load options for pizza types and toppings
        loadPizzaTypes();
        loadToppingOptions();

        // Handle size changes
        sizeGroupNY.setOnCheckedChangeListener((group, checkedId) -> updateSizePrice());

        // Handle add to order button
        buttonAddToOrderNY.setOnClickListener(v -> handleAddOrder());

        // Initialize default pizza
        initializeDefaultPizza();
    }

    private void initializeDefaultPizza() {
        radioSmallNY.setChecked(true);
        current = nyFactory.createBuildYourOwn(Size.SMALL);
        spinnerCrustNY.setSelection(0); // Set the default crust
        resetToppings();
        updateUI();
    }

    private void loadCrustOptions() {
        String[] crustOptions = {"Brooklyn", "Thin", "Hand_Tossed"};
        crustAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, crustOptions);
        crustAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrustNY.setAdapter(crustAdapter);

        spinnerCrustNY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCrust = (String) parent.getItemAtPosition(position);
                if (current != null) {
                    Crust crust = Crust.valueOf(selectedCrust.toUpperCase().replace(" ", "_"));
                    current.setCrust(crust);
                    updateUI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadPizzaTypes() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.pizza_types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeNYSpinner.setAdapter(adapter);

        typeNYSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);
                handleTypeChange(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadToppingOptions() {
        loadAvailableToppings();

        availableToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertToppingsToString(availableToppings));
        listViewAvailableToppingsNY.setAdapter(availableToppingsAdapter);

        selectedToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertToppingsToString(selectedToppings));
        listViewSelectedToppingsNY.setAdapter(selectedToppingsAdapter);

        listViewAvailableToppingsNY.setOnItemClickListener((parent, view, position, id) -> {
            Topping topping = availableToppings.get(position);
            availableToppings.remove(topping);
            selectedToppings.add(topping);
            current.addTopping(topping);
            updateToppingAdapters();
            updateUI();
        });

        listViewSelectedToppingsNY.setOnItemClickListener((parent, view, position, id) -> {
            Topping topping = selectedToppings.get(position);
            selectedToppings.remove(topping);
            availableToppings.add(topping);
            current.removeTopping(topping);
            updateToppingAdapters();
            updateUI();
        });
    }

    private void resetToppings() {
        availableToppings.clear();
        selectedToppings.clear();
        loadAvailableToppings();
        updateToppingAdapters();
    }

    private void loadAvailableToppings() {
        availableToppings.add(Topping.SAUSAGE);
        availableToppings.add(Topping.PEPPERONI);
        availableToppings.add(Topping.GREEN_PEPPER);
        availableToppings.add(Topping.ONION);
        availableToppings.add(Topping.MUSHROOM);
        availableToppings.add(Topping.BBQ_CHICKEN);
        availableToppings.add(Topping.PROVOLONE);
        availableToppings.add(Topping.CHEDDAR);
        availableToppings.add(Topping.BEEF);
        availableToppings.add(Topping.HAM);
    }

    private ArrayList<String> convertToppingsToString(ArrayList<Topping> toppings) {
        ArrayList<String> toppingStrings = new ArrayList<>();
        for (Topping topping : toppings) {
            toppingStrings.add(topping.name().replace("_", " ").toLowerCase());
        }
        return toppingStrings;
    }

    private void updateToppingAdapters() {
        availableToppingsAdapter.clear();
        availableToppingsAdapter.addAll(convertToppingsToString(availableToppings));
        availableToppingsAdapter.notifyDataSetChanged();

        selectedToppingsAdapter.clear();
        selectedToppingsAdapter.addAll(convertToppingsToString(selectedToppings));
        selectedToppingsAdapter.notifyDataSetChanged();
    }

    private void handleTypeChange(String type) {
        Size size = getSelectedSize();

        switch (type) {
            case "Deluxe":
                current = nyFactory.createDeluxe(size);
                resetToppings();
                break;
            case "BBQ Chicken":
                current = nyFactory.createBBQChicken(size);
                resetToppings();
                break;
            case "Meatzza":
                current = nyFactory.createMeatzza(size);
                resetToppings();
                break;
            default:
                current = nyFactory.createBuildYourOwn(size);
                resetToppings();
        }
        if (current != null) {
            String crustName = current.getCrust().name().replace("_", " ");
            int crustPosition = crustAdapter.getPosition(crustName);
            if (crustPosition >= 0) {
                spinnerCrustNY.setSelection(crustPosition);
            }
        }
        updateUI();
    }

    private Size getSelectedSize() {
        if (radioSmallNY.isChecked()) return Size.SMALL;
        if (radioMediumNY.isChecked()) return Size.MEDIUM;
        return Size.LARGE;
    }

    private void updateUI() {
        editTextPriceNY.setText(String.valueOf(current.price()));
    }

    private void handleAddOrder() {
        order.addPizza(current);
        Toast.makeText(this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
    }
    private void updateSizePrice() {
        if (current == null) {
            // If the pizza object is null, initialize a default pizza
            current = nyFactory.createBuildYourOwn(getSelectedSize());
        }
        // Update the pizza size
        current.setSize(getSelectedSize());
        // Update the price and UI
        updateUI();
    }
}