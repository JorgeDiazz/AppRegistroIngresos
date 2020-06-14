package com.jorgeandresdiaz.controldeingresos.fragments.model;

public class Transaction {

    private double amount;
    private boolean income;
    private String description;
    private String date;

    public Transaction(double amount, boolean income, String description, String date) {
        this.amount = amount;
        this.income = income;
        this.description = description;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
