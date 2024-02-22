package com.example.foodiesfavgui; // Package declaration
import java.util.ArrayList; // Import the ArrayList class
import java.util.Iterator; // Import the Iterator class

public class FoodQueue {
    public ArrayList<Customer> queue; // ArrayList to store customers in the queue
    public ArrayList<Customer> waitingQueue; // ArrayList to store customers in the waiting queue
    public int queueCapacity; // Capacity of the queue
    public int burgerStock; // Remaining stock of burgers

    //Constructor
    public FoodQueue(int queueCapacity) {
        this.queue = new ArrayList<>(); // Initialize an empty ArrayList to store customers in the queue
        this.queueCapacity = queueCapacity; // Set the capacity of the queue
        this.burgerStock = 0; // Initialize burgerStock to 0 (no burgers in the queue initially)
    }

    public FoodQueue() {
        this.waitingQueue = new ArrayList<>(); // Initialize an empty ArrayList to store customers in the waiting queue
    }

    //getter
    public ArrayList<Customer> getQueue() {
        return queue; // Return the ArrayList of customers in the queue
    }

    public int getQueueCapacity() {
        return queueCapacity; // Return the capacity of the queue
    }

    public ArrayList<Customer> getWaitingQueue() {
        return waitingQueue; // Return the ArrayList of customers in the waiting queue
    }

    public void addCustomer(Customer c) {
        queue.add(c); // Add a customer to the queue
    }

    public String addWaitingQueue(Customer customer) {
        waitingQueue.add(customer); // Add a customer to the waiting queue
        return customer.getFullName() + " added to the waiting queue successfully."; // Return a success message with the customer's full name
    }
    public void removeUnservedCustomer(String name) {
        if (queue.isEmpty()) { // Check if the queue is empty
            System.out.println("The queue is empty."); // Print a message indicating the queue is empty
        } else {
            Iterator<Customer> iterator = queue.iterator(); // Create an iterator to iterate over the queue
            boolean customerFound = false; // Variable to track if the customer was found
            while (iterator.hasNext()) { // Iterate over each customer in the queue
                Customer customer = iterator.next(); // Get the next customer
                if (customer.getFirstName().equalsIgnoreCase(name)) { // Check if the customer's first name matches the input name
                    iterator.remove(); // Remove the customer from the queue
                    customerFound = true; // Set the customerFound flag to true
                    System.out.println("Customer is successfully removed from the queue."); // Print a success message
                    break; // Assuming there are no duplicate names in the queue, we can exit the loop after removing the customer.
                }
            }
            if (!customerFound) { // If the customer was not found in the queue
                System.out.println("Customer not found in the queue."); // Print a message indicating the customer was not found
            }
        }
    }
    public String removeServedCustomer() {
        if (queue.isEmpty()) { // Check if the queue is empty
            return "No customers in the queue."; // Return a message indicating there are no customers in the queue
        } else {
            Customer servedCustomer = queue.remove(0); // Remove the first customer from the queue and store the served customer
            int burgersRequired = servedCustomer.getBurgersRequired(); // Get the number of burgers required by the served customer
            reduceBurgerStock(burgersRequired); // Update the burger stock by reducing the number of burgers served
            return "Served customer " + servedCustomer.getFullName() + " removed from the queue."; // Return a message indicating the served customer was removed from the queue
        }
    }
    public Customer removeFirstFromWaitingQueue() {
        if (!waitingQueue.isEmpty()) { // Check if the waiting queue is not empty
            return waitingQueue.remove(0); // Remove and return the first customer from the waiting queue
        }
        return null; // Return null if the waiting queue is empty
    }
    private void reduceBurgerStock(int burgers) {
        burgerStock -= burgers; // Reduce the burger stock by the specified number of burgers
    }

    public int getBurgerStock() {
        return burgerStock; // Return the current burger stock
    }


}
