package com.example.rupizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    private Context context;
    private List<Topping> toppings;
    private OnToppingClickListener listener;

    public interface OnToppingClickListener {
        void onToppingClick(Topping topping);
    }

    public ToppingAdapter(Context context, List<Topping> toppings, OnToppingClickListener listener) {
        this.context = context;
        this.toppings = toppings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topping_item, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings.get(position);

        // Set the topping name
        holder.toppingName.setText(topping.name().replace("_", " "));

        // Set the topping image based on enum
        int imageResource = getImageResourceForTopping(topping);
        holder.toppingImage.setImageResource(imageResource);

        // Set the click listener
        holder.itemView.setOnClickListener(v -> listener.onToppingClick(topping));
    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    public static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingName;
        ImageView toppingImage;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            toppingName = itemView.findViewById(R.id.toppingName);
            toppingImage = itemView.findViewById(R.id.toppingImage);
        }
    }

    // Helper method to map Topping enum to drawable resource
    private int getImageResourceForTopping(Topping topping) {
        switch (topping) {
            case SAUSAGE:
                return R.drawable.sausage;
            case PEPPERONI:
                return R.drawable.pepperoni;
            case GREEN_PEPPER:
                return R.drawable.green_pepper;
            case ONION:
                return R.drawable.onion;
            case MUSHROOM:
                return R.drawable.mushroom;
            case BBQ_CHICKEN:
                return R.drawable.bbq_chicken;
            case PROVOLONE:
                return R.drawable.provolone;
            case CHEDDAR:
                return R.drawable.cheddar;
            case BEEF:
                return R.drawable.beef;
            case HAM:
                return R.drawable.ham;
            default:
                return R.drawable.ic_placeholder; // Placeholder for missing images
        }
    }
}