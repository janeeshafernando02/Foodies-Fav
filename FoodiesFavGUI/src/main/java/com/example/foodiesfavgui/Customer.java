package com.example.foodiesfavgui; // Package declaration

public class Customer implements Comparable<Customer> {
    // Instance variables
    private final String firstName; // Customer's first name
    private final String secondName; // Customer's second name
    private int burgersRequired; // Number of burgers required by the customer

    // Constructor
    public Customer(String firstName, String secondName, int burgersRequired) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.burgersRequired = burgersRequired;
    }

    // Getter for firstName
    public String getFirstName() {
        return firstName;
    }

    // Getter for the full name (combination of firstName and secondName)
    public String getFullName() {
        return firstName + " " + secondName;
    }

    // Getter for burgersRequired
    public int getBurgersRequired() {
        return burgersRequired;
    }

    // Comparable interface method implementation for comparing customers based on their full names
    @Override
    public int compareTo(Customer other) {
        return this.getFullName().compareTo(other.getFullName());
    }

    // Setter for burgersRequired
    public void setBurgersRequired(int burgersRequired) {
        this.burgersRequired = burgersRequired;
    }
}
