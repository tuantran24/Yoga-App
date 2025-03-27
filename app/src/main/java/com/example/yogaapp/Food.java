package com.example.yogaapp;

public class Food {
    private String name;
    private int quantity;
    private int calories;

    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
        this.quantity = 0;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
    public void increaseQuantity() {
        this.quantity++;
    }
    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}
