package com.example.yogaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {
    private List<Food> foodList;
    private List<Food> foodListFull;
    private OnCaloriesChangeListener caloriesChangeListener;

    public interface OnCaloriesChangeListener {
        void onCaloriesChanged(int totalCalories);
    }

    public FoodAdapter(List<Food> foodList, OnCaloriesChangeListener listener) {
        this.foodList = new ArrayList<>(foodList);
        this.foodListFull = new ArrayList<>(foodList);
        this.caloriesChangeListener = listener;
        updateTotalCalories();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowfood, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvQuantity.setText(String.valueOf(food.getQuantity()));

        holder.btnIncrease.setOnClickListener(v -> {
            food.increaseQuantity();
            notifyItemChanged(position);
            updateTotalCalories();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (food.getQuantity() > 0) {
                food.setQuantity(food.getQuantity() - 1);
                notifyItemChanged(position);
                updateTotalCalories();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private void updateTotalCalories() {
        int totalCalories = 0;
        for (Food food : foodList) {
            totalCalories += food.getQuantity() * food.getCalories();
        }
        if (caloriesChangeListener != null) {
            caloriesChangeListener.onCaloriesChanged(totalCalories);
        }
    }

    // Thêm bộ lọc tìm kiếm
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    private final Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Food> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(foodListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Food item : foodListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodList.clear();
            foodList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvQuantity;
        Button btnIncrease, btnDecrease;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
