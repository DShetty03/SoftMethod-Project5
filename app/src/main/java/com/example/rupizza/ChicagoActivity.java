package com.example.rupizza;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Activity for managing the Chicago-style pizza ordering interface.
 * Handles the selection of pizza type, size, crust, toppings, and order processing.
 *
 * @author Divit Shetty
 */
public class ChicagoActivity extends AppCompatActivity {

    private Spinner typeChicagoSpinner, spinnerCrustChicago;
    private ListView listViewAvailableToppingsChicago, listViewSelectedToppingsChicago;
    private ImageView imageChicago;
    private RadioGroup sizeGroupChicago;
    private RadioButton radioSmallChicago, radioMediumChicago, radioLargeChicago;
    private EditText editTextPriceChicago;
    private Button buttonAddToOrderChicago;

    private Order order;
    private PizzaFactory chicagoFactory = new ChicagoPizza();
    private Pizza current;

    private ArrayAdapter<String> crustAdapter, availableToppingsAdapter, selectedToppingsAdapter;
    private ArrayList<Topping> availableToppings = new ArrayList<>();
    private ArrayList<Topping> selectedToppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chicago_activity);

        // Initialize Views
        typeChicagoSpinner = findViewById(R.id.typeChicagoSpinner);
        spinnerCrustChicago = findViewById(R.id.spinnerCrustChicago);
        listViewAvailableToppingsChicago = findViewById(R.id.listViewAvailableToppingsChicago);
        listViewSelectedToppingsChicago = findViewById(R.id.listViewSelectedToppingsChicago);
        imageChicago = findViewById(R.id.imageChicago);
        sizeGroupChicago = findViewById(R.id.radioGroupSizeChicago);
        radioSmallChicago = findViewById(R.id.radioSmallChicago);
        radioMediumChicago = findViewById(R.id.radioMediumChicago);
        radioLargeChicago = findViewById(R.id.radioLargeChicago);
        editTextPriceChicago = findViewById(R.id.editTextPriceChicago);
        buttonAddToOrderChicago = findViewById(R.id.buttonAddOrderChicago);

        // Load shared data
        order = SharedData.getInstance().getOrder();

        // Load crust options first
        loadCrustOptions();

        // Load options for pizza types and toppings
        loadPizzaTypes();
        loadToppingOptions();

        // Handle size changes
        sizeGroupChicago.setOnCheckedChangeListener((group, checkedId) -> updateSizePrice());

        // Handle add to order button
        buttonAddToOrderChicago.setOnClickListener(v -> handleAddOrder());

        // Initialize default pizza
        initializeDefaultPizza();
    }

    private void initializeDefaultPizza() {
        radioSmallChicago.setChecked(true);
        current = chicagoFactory.createBuildYourOwn(Size.SMALL);
        spinnerCrustChicago.setSelection(0); // Set the default crust
        resetToppings();
        updateUI();
    }

    private void loadCrustOptions() {
        String[] crustOptions = {"Deep_Dish", "Stuffed", "Pan"};
        crustAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, crustOptions);
        crustAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrustChicago.setAdapter(crustAdapter);

        spinnerCrustChicago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        typeChicagoSpinner.setAdapter(adapter);

        typeChicagoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        listViewAvailableToppingsChicago.setAdapter(availableToppingsAdapter);

        selectedToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertToppingsToString(selectedToppings));
        listViewSelectedToppingsChicago.setAdapter(selectedToppingsAdapter);

        listViewAvailableToppingsChicago.setOnItemClickListener((parent, view, position, id) -> {
            Topping topping = availableToppings.get(position);
            availableToppings.remove(topping);
            selectedToppings.add(topping);
            current.addTopping(topping);
            updateToppingAdapters();
            updateUI();
        });

        listViewSelectedToppingsChicago.setOnItemClickListener((parent, view, position, id) -> {
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
                current = chicagoFactory.createDeluxe(size);
                resetToppings();
                break;
            case "BBQ Chicken":
                current = chicagoFactory.createBBQChicken(size);
                resetToppings();
                break;
            case "Meatzza":
                current = chicagoFactory.createMeatzza(size);
                resetToppings();
                break;
            default:
                current = chicagoFactory.createBuildYourOwn(size);
                resetToppings();
        }
        if (current != null) {
            String crustName = current.getCrust().name().replace("_", " ");
            int crustPosition = crustAdapter.getPosition(crustName);
            if (crustPosition >= 0) {
                spinnerCrustChicago.setSelection(crustPosition);
            }
        }
        updateUI();
    }

    private Size getSelectedSize() {
        if (radioSmallChicago.isChecked()) return Size.SMALL;
        if (radioMediumChicago.isChecked()) return Size.MEDIUM;
        return Size.LARGE;
    }

    private void updateUI() {
        editTextPriceChicago.setText(String.valueOf(current.price()));
    }

    private void handleAddOrder() {
        order.addPizza(current);
        Toast.makeText(this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
    }

    private void updateSizePrice() {
        if (current == null) {
            current = chicagoFactory.createBuildYourOwn(getSelectedSize());
        }
        current.setSize(getSelectedSize());
        updateUI();
    }
}