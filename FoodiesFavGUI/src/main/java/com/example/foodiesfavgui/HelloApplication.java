package com.example.foodiesfavgui;
// Import necessary JavaFX libraries
import javafx.application.Application; // Provides the necessary classes and methods for a JavaFX application
import javafx.fxml.FXMLLoader; // Loads JavaFX UI layouts from FXML files
import javafx.scene.Parent; // Represents the root node of the JavaFX scene graph
import javafx.scene.Scene; // Represents the container for all JavaFX content
import javafx.stage.Stage; // Represents the primary stage (window) of a JavaFX application

// Import necessary IO libraries
import java.io.IOException; // Handles input/output operations
import java.util.Collections; // Provides utility methods for working with collections
import java.util.Scanner; // Reads input from the user

// Import necessary classes for date and time manipulation
import java.io.FileWriter; // Provides the ability to write to a file
import java.io.File; // Represents a file on the file system

import java.time.LocalDateTime; // Represents a date and time without a time zone
import java.time.format.DateTimeFormatter; // Formats and parses dates and times

public class HelloApplication extends Application {
    private static final Scanner getInput = new Scanner(System.in); // Create a Scanner object to read user input
    private static int burgerStock = 50; // Initialize the initial stock of burgers

    // Variables to keep track of queue counts and total count
    private static int purchasedBurgersQueue1, purchasedBurgersQueue2, purchasedBurgersQueue3 = 0; // Initialize the number of burgers purchased in each queue
    private static int servedCount = 0, unservedCount = 0; // Initialize the count of served and unserved burgers
    private static int countOfQueue1, countOfQueue2, countOfQueue3, countOfWaitingQueue, totalCount = 0; // Initialize the count of burgers in each queue and total count
    private static int incomeOfQueue1, incomeOfQueue2, incomeOfQueue3, totalIncome; // Initialize the income earned from each queue and total income
    private static int addedBurgerCount = 0; // Initialize the count of added burgers

    static FoodQueue queue1 = new FoodQueue(2); // Create a new FoodQueue object with a maximum capacity of 2 for queue1
    public static FoodQueue queue2 = new FoodQueue(3); // Create a new FoodQueue object with a maximum capacity of 3 for queue2
    public static FoodQueue queue3 = new FoodQueue(5); // Create a new FoodQueue object with a maximum capacity of 5 for queue3
    static FoodQueue waitingQueue = new FoodQueue(); // Create a new FoodQueue object for the waiting queue

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
       // Create a new instance of FXMLLoader and load the FXML file "hello-view.fxml"

        Parent root = loader.load();
       // Load the FXML file and retrieve the root element as a Parent

        HelloController controller = loader.getController();
        // Get the controller associated with the loaded FXML file

        Scene scene = new Scene(root);
       // Create a new scene with the loaded root element

        primaryStage.setScene(scene);
       // Set the scene for the primary stage (window)

        primaryStage.show();
      // Display the primary stage (window)

        controller.updateQueueLabels();
     // Call the "updateQueueLabels()" method on the controller to update the queue labels in the GUI

    }

    public static void main(String[] args) {
        boolean run_again = true;
        System.out.println("""
                ---------------------------------------------------------------------------------------------------------------------------------------------------
                        
                                                       ----------          Foodies Fave Food Center           ----------
                        
                -------------------------------------------------------------------------------------------------------------------------------------------------------
                """);
        while (run_again) {
            System.out.println("""               
                    Please select an option:
                               
                    100 or VFQ: View all Queues
                    101 or VEQ: View all Empty Queues
                    102 or ACQ: Add customer to a Queue
                    103 or RCQ: Remove a unserved customer from a Queue
                    104 or PCQ: Remove a served customer
                    105 or VCS: View Customers Sorted in alphabetical order
                    106 or SPD: Store Program Data into file
                    107 or LPD: Load Program Data from file
                    108 or STK: View Remaining burgers Stock
                    109 or AFS: Add burgers to Stock
                    110 or IFQ: Income of each Queue
                    112 or GUI: Display the GUI
                    999 or EXT: Exit the Program
                                
                    ------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    """);

            System.out.print("Enter option: ");
            String user_choice = getInput.next();
            switch (user_choice) {
                case "100":
                case "VFQ":
                    printQueueStatus();
                    break;
                case "101":
                case "VEQ":
                    emptyQueues();
                    break;
                case "102":
                case "ACQ":
                    addCustomer();
                    break;
                case "103":
                case "RCQ":
                    removeUnservedCustomer();
                    break;
                case "104":
                case "PCQ":
                    removeServedCustomer();
                    break;
                case "105":
                case "VCS":
                    customerSort();
                    break;
                case "106":
                case "SPD":
                    saveToFile();
                    break;
                case "107":
                case "LPD":
                    loadData();
                    break;
                case "108":
                case "STK":
                    viewRemainingStock();
                    break;
                case "109":
                case "AFS":
                    addBurgersToStock();
                    break;
                case "110":
                case "IFQ":
                    printIncomeOfEachQueue();
                    break;
                case "112":
                case "GUI":
                    launch(args);
                    break;
                case "999":
                case "EXT":
                    run_again = false;
                    System.out.println(" ");
                    System.out.println("Program Ended.");
                    break;
                default:
                    System.out.println(" ");
                    System.out.println("Invalid Input.Please check the option menu and try again.");
            }
        }

    }

    /**
     * The method prints a visual representation of the queues.
     * Each queue is represented by an array.
     * showing occupied positions with 'O' and not occupied positions with 'X'.
     */
    private static void printQueueStatus() {
        System.out.println("*****************");
        System.out.println("*    Cashiers    *");
        System.out.println("*****************");
       // Print the header for the cashiers section

        int maxQueueSize = Math.max(queue1.getQueueCapacity(), Math.max(queue2.getQueueCapacity(), queue3.getQueueCapacity()));
      // Determine the maximum queue size among the three queues

        for (int i = 0; i < maxQueueSize; i++) {
            if (i < queue1.getQueueCapacity()) {
                int queueSize = queue1.getQueue().size();
                if (i < queueSize) {
                    System.out.print("  O ");  // Print "O" for a customer in queue1
                } else {
                    System.out.print("  X ");  // Print "X" for an empty spot in queue1
                }
            } else {
                System.out.print("    ");  // Print empty space if queue1 does not exist
            }

            if (i < queue2.getQueueCapacity()) {
                int queueSize = queue2.getQueue().size();
                if (i < queueSize) {
                    System.out.print("    O ");  // Print "O" for a customer in queue2
                } else {
                    System.out.print("    X ");  // Print "X" for an empty spot in queue2
                }
            } else {
                System.out.print("      ");  // Print empty space if queue2 does not exist
            }

            if (i < queue3.getQueueCapacity()) {
                int queueSize = queue3.getQueue().size();
                if (i < queueSize) {
                    System.out.print("    O ");  // Print "O" for a customer in queue3
                } else {
                    System.out.print("    X ");  // Print "X" for an empty spot in queue3
                }
            } else {
                System.out.print("     ");  // Print empty space if queue3 does not exist
            }

            System.out.println();
            // Move to the next line
        }
    }
    /**
     * This method is responsible for printing the indices of empty elements in three queues.
     * It iterates over each queue and checks if each element is null, indicating an empty spot.
     * If an element is null, it prints the corresponding index.
     * This method is used to display the positions that can be filled in each queue.
     */
    private static void emptyQueues() {
        System.out.println("---- Empty Queues ----");
        // Prints a header for the empty queues section

        System.out.print("Queue 1 - ");
        int queueSize1 = queue1.getQueue().size();
        for (int i = 0; i < queue1.getQueueCapacity(); i++) {
            if (i < queueSize1) {
                System.out.print("  ");  // Print "O" for a customer
            } else {
                System.out.print(i + 1 + " ");  // Print the index for an empty spot
            }
        }
        System.out.println(" ");

        System.out.print("Queue 2 - ");
        int queueSize2 = queue2.getQueue().size();
        for (int i = 0; i < queue2.getQueueCapacity(); i++) {
            if (i < queueSize2) {
                System.out.print("  ");  // Print "O" for a customer
            } else {
                System.out.print(i + 1 + " ");  // Print the index for an empty spot
            }
        }
        System.out.println(" ");

        System.out.print("Queue 3 - ");
        int queueSize3 = queue3.getQueue().size();
        for (int i = 0; i < queue3.getQueueCapacity(); i++) {
            if (i < queueSize3) {
                System.out.print("  ");  // Print "O" for a customer
            } else {
                System.out.print(i + 1 + " ");  // Print the index for an empty spot
            }
        }
        System.out.println(" ");
        System.out.println(" ");

    }

    /**
     * Validates the customer's first name.
     * Prompts the user to enter the first name and validates it to ensure it contains only alphabetic characters.
     * Capitalizes the first letter of the name before returning it.
     * @return The validated and capitalized first name.
     */
    private static String FirstNameValidation() {
        String firstName;

        while (true) {
            System.out.print("Enter customer's first name: ");
            firstName = getInput.next();

            if (!firstName.matches("[a-zA-Z]+")) {
                // Invalid input: contains non-alphabetic characters
                System.out.println("Invalid input. Enter a valid customer name with only alphabetic characters.");
                getInput.nextLine(); // Clear the invalid input from the scanner
                System.out.println();
            } else {
                // Valid input: capitalize the first letter of the customer name
                firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                break;
            }
        }

        return firstName;
    }

    /**
     * Validates the customer's second name.
     * Prompts the user to enter the second name and validates it to ensure it contains only alphabetic characters.
     * Capitalizes the first letter of the name before returning it.
     * @return The validated and capitalized second name.
     */
    private static String secondNameValidation() {
        String secondName;

        while (true) {
            System.out.print("Enter customer's second name: ");
            secondName = getInput.next();

            if (!secondName.matches("[a-zA-Z]+")) {
                // Invalid input: contains non-alphabetic characters
                System.out.println("Invalid input. Enter a valid customer name with only alphabetic characters.");
                getInput.nextLine(); // Clear the invalid input from the scanner
                System.out.println();
            } else {
                // Valid input: capitalize the first letter of the customer name
                secondName = secondName.substring(0, 1).toUpperCase() + secondName.substring(1);
                break;
            }
        }

        return secondName;
    }

    /**
     * Validates the number of burgers required by the customer.
     * Prompts the user to enter the number of burgers required and validates it to ensure it is a positive integer.
     * @return The validated number of burgers required.
     */
    private static int requiredBurgerCountValidation() {
        int burgersRequired;

        while (true) {
            System.out.print("Enter number of burgers required: ");

            try {
                burgersRequired = getInput.nextInt();
                if (burgersRequired > 0) {
                    // Valid input: positive integer
                    break;
                } else {
                    // Invalid input: non-positive number of burgers
                    System.out.println("Invalid input. Number of burgers must be greater than 0.");
                    System.out.println();
                }
            } catch (Exception e) {
                // Invalid input: non-integer value entered
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.println();
                getInput.next(); // Discard invalid input
            }
        }

        return burgersRequired;
    }
    /**
     * Adds a customer to the appropriate queue based on availability.
     * Prompts the user to enter the customer's first name, second name, and the number of burgers required.
     * Determines the shortest queue among the three queues and adds the customer to that queue.
     * If all queues are full, the customer is added to the waiting queue.
     */
    private static void addCustomer() {
        String firstName = FirstNameValidation(); // Get the validated and capitalized first name from user input
        String secondName = secondNameValidation(); // Get the validated and capitalized second name from user input
        int burgersRequired = requiredBurgerCountValidation(); // Get the validated number of burgers required from user input

        int lengthQueue1 = queue1.getQueue().size(); // Get the current length of queue1
        int lengthQueue2 = queue2.getQueue().size(); // Get the current length of queue2
        int lengthQueue3 = queue3.getQueue().size(); // Get the current length of queue3

        int minLength = Math.min(lengthQueue3, Math.min(lengthQueue2, lengthQueue1)); // Calculate the shortest queue length
        if (lengthQueue1 == queue1.getQueueCapacity() && lengthQueue2 == queue2.getQueueCapacity()
                && lengthQueue3 == queue3.getQueueCapacity()) {
            // All queues are full
            System.out.println("The queue is full!!");
            waitingQueue.addWaitingQueue(new Customer(firstName, secondName, burgersRequired));
            countOfWaitingQueue++;
            System.out.println("Customer added to the waiting queue.");
        } else if (lengthQueue1 < queue1.getQueueCapacity() && minLength == lengthQueue1) {
            // Queue 1 is the shortest
            queue1.addCustomer(new Customer(firstName, secondName, burgersRequired));
            countOfQueue1++;
            System.out.println("Customer added to Queue 1.");
        } else if (lengthQueue2 < queue2.getQueueCapacity() && minLength == lengthQueue2) {
            // Queue 2 is the shortest
            queue2.addCustomer(new Customer(firstName, secondName, burgersRequired));
            countOfQueue2++;
            System.out.println("Customer added to Queue 2.");
        } else {
            // Queue 3 is the shortest
            queue3.addCustomer(new Customer(firstName, secondName, burgersRequired));
            countOfQueue3++;
            System.out.println("Customer added to Queue 3.");
        }

        totalCount = countOfQueue1 + countOfQueue2 + countOfQueue3 + countOfWaitingQueue;
        // Update the total count of customers across all queues and waiting queue
    }
    /**
     * Validates the queue number entered by the user.
     * Prompts the user to enter the queue number and validates it to ensure it is an integer between 1 and 3 (inclusive).
     * @return The validated queue number.
     */
    private static int queueNumberValidation() {
        int queueNumber;

        while (true) {
            System.out.print("Enter the queue number: "); // Prompt the user to enter the queue number
            String input = getInput.next(); // Read the user's input as a string

            try {
                queueNumber = Integer.parseInt(input); // Convert the input string to an integer

                if (queueNumber >= 1 && queueNumber <= 3) {
                    // Valid input: queue number is between 1 and 3 (inclusive)
                    break;
                } else {
                    System.out.println();
                    System.out.println("Invalid queue number. Enter a number between 1 and 3.");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                // Invalid input: non-integer value entered
                System.out.println();
                System.out.println("Invalid input. Enter a valid integer for the queue number.");
                System.out.println();
            }
        }

        return queueNumber;
    }

    /**
     * Removes an unserved customer from the specified queue.
     * Prompts the user to enter the queue number and the customer's first name.
     * Removes the customer from the corresponding queue and increments the unserved count.
     */
    private static void removeUnservedCustomer() {
        System.out.println(" ");
        System.out.println("(1) - First Cashier\n(2) - Second Cashier\n(3) - Third Cashier");

        int queueNumber = queueNumberValidation(); // Get the validated queue number from user input
        String firstName = FirstNameValidation(); // Get the validated and capitalized first name from user input

        switch (queueNumber) {
            case 1:
                queue1.removeUnservedCustomer(firstName); // Remove the unserved customer from queue1
                unservedCount++;
                break;

            case 2:
                queue2.removeUnservedCustomer(firstName); // Remove the unserved customer from queue2
                unservedCount++;
                break;

            case 3:
                queue3.removeUnservedCustomer(firstName); // Remove the unserved customer from queue3
                unservedCount++;
                break;

            default:
                System.out.println("Invalid Input. Please enter a queue number between 1 to 3.");
        }
    }

    /**
     * Removes a served customer from the specified queue.
     * Prompts the user to enter the queue number.
     * Removes the served customer from the corresponding queue, updates stock and counts,
     * and adds the next customer from the waiting list to the appropriate queue if available.
     */
    private static void removeServedCustomer() {
        System.out.println("(1) - First Cashier\n(2) - Second Cashier\n(3) - Third Cashier");
        int queueNumber = queueNumberValidation(); // Get the validated queue number from user input

        FoodQueue selectedQueue; // Declare a variable to store the selected queue

        switch (queueNumber) {
            case 1:
                selectedQueue = queue1; // If the queueNumber is 1, assign queue1 to selectedQueue
                servedCount++; // Increment the servedCount
                break;
            case 2:
                selectedQueue = queue2; // If the queueNumber is 2, assign queue2 to selectedQueue
                servedCount++; // Increment the servedCount
                break;
            case 3:
                selectedQueue = queue3; // If the queueNumber is 3, assign queue3 to selectedQueue
                servedCount++; // Increment the servedCount
                break;
            default:
                System.out.println("Invalid Input. Please enter a queue number between 1 and 3.");
                // If the queueNumber is not 1, 2, or 3, print an error message
                return; // Exit the method
        }
        String result = selectedQueue.removeServedCustomer();
        // Remove the served customer from the selected queue and get the result message

        System.out.println(result);

        if (!waitingQueue.getWaitingQueue().isEmpty()) {
            // There are customers in the waiting queue
            Customer nextCustomer = waitingQueue.removeFirstFromWaitingQueue();
            // Remove the next customer from the waiting queue

            if (nextCustomer != null) {
                int minLength = Math.min(queue1.getQueue().size(), Math.min(queue2.getQueue().size(), queue3.getQueue().size()));
                // Determine the shortest queue length among the three queues

                if (minLength == queue1.getQueue().size() && queue1.getQueue().size() < queue1.getQueueCapacity()) {
                    // Add the next customer to queue1 if it is the shortest queue and has capacity
                    queue1.addCustomer(nextCustomer);
                    System.out.println("Next customer from the waiting list added to Queue 1.");
                } else if (minLength == queue2.getQueue().size() && queue2.getQueue().size() < queue2.getQueueCapacity()) {
                    // Add the next customer to queue2 if it is the shortest queue and has capacity
                    queue2.addCustomer(nextCustomer);
                    System.out.println("Next customer from the waiting list added to Queue 2.");
                } else {
                    // Add the next customer to queue3 if it is the shortest queue and has capacity
                    queue3.addCustomer(nextCustomer);
                    System.out.println("Next customer from the waiting list added to Queue 3.");
                }
            }
        }

        int requiredBurgers = selectedQueue.getBurgerStock();
        burgerStock += requiredBurgers;
        // Update the burger stock by adding the number of required burgers

        if (burgerStock == 10) {
            System.out.println("Warning: Stock Alert!! - 10 burgers remaining");
        }

        if (selectedQueue == queue1) {
            purchasedBurgersQueue1 -= requiredBurgers;
            // Update the count of purchased burgers in queue1 by subtracting the number of required burgers
        } else if (selectedQueue == queue2) {
            purchasedBurgersQueue2 -= requiredBurgers;
            // Update the count of purchased burgers in queue2 by subtracting the number of required burgers
        } else {
            purchasedBurgersQueue3 -= requiredBurgers;
            // Update the count of purchased burgers in queue3 by subtracting the number of required burgers
        }

        selectedQueue.getQueue().forEach(customer -> customer.setBurgersRequired(0));
        // Set the burgers required to 0 for all customers in the selected queue
    }

    /**
     * Sorts the customers in each queue alphabetically by their full names and displays the sorted queues.
     */
    private static void customerSort() {
        Collections.sort(queue1.getQueue()); // Sort the customers in queue1 alphabetically by their full names
        System.out.print("Queue 1 - ");
        System.out.println(String.join(", ", queue1.getQueue().stream()
                .map(Customer::getFullName) // Get the full names of the customers in queue1
                .toArray(String[]::new)));

        Collections.sort(queue2.getQueue()); // Sort the customers in queue2 alphabetically by their full names
        System.out.print("Queue 2 - ");
        System.out.println(String.join(", ", queue2.getQueue().stream()
                .map(Customer::getFullName) // Get the full names of the customers in queue2
                .toArray(String[]::new)));

        Collections.sort(queue3.getQueue()); // Sort the customers in queue3 alphabetically by their full names
        System.out.print("Queue 3 - ");
        System.out.println(String.join(", ", queue3.getQueue().stream()
                .map(Customer::getFullName) // Get the full names of the customers in queue3
                .toArray(String[]::new)));
    }
    /**
     * Displays the remaining stock of burgers.
     * Prints the remaining stock of burgers and adds a blank line for formatting purposes.
     */
    private static void viewRemainingStock() {
        System.out.println("Remaining Burger Stock: " + burgerStock);  // Print the remaining stock of burgers
        System.out.println(" ");                                      // Print a blank line for formatting purposes
    }

    /**
     * Adds burgers to the current stock.
     * Prompts the user to enter the number of burgers to add.
     * Updates the burger stock accordingly, considering the maximum stock capacity of 50.
     */
    private static void addBurgersToStock() {
        int burgerCount; // Variable to store the number of burgers to add

        while (true) {
            System.out.print("Enter how many burgers do you want to add to the current stock: ");
            // Prompt the user to enter the number of burgers

            if (getInput.hasNextInt()) {
                burgerCount = getInput.nextInt();
                // Read the user's input as the number of burgers to add

                if (burgerStock < 50) {
                    burgerStock = Math.min(burgerStock + burgerCount, 50);
                    // Update the burger stock, considering the maximum capacity of 50

                    addedBurgerCount += burgerCount;
                    // Increment the count of burgers added to the stock

                    System.out.println(addedBurgerCount);
                    // Print the total count of burgers added to the stock

                    System.out.println("Current Burger Stock - " + burgerStock);
                    // Display the current burger stock

                    System.out.println(" ");
                    // Print a blank line for formatting purposes
                } else {
                    System.out.println(" ");
                    // Print a blank line for formatting purposes

                    System.out.println("Sorry, the stock is full!!");
                    // Notify the user that the stock is already full

                    System.out.println(" ");
                    // Print a blank line for formatting purposes
                }

                break;
                // Exit the loop
            } else {
                System.out.println(" ");
                // Print a blank line for formatting purposes

                System.out.println("Invalid input. Enter a valid integer to add burgers to the current stock.");
                // Notify the user of an invalid input

                System.out.println(" ");
                // Print a blank line for formatting purposes

                getInput.next();
                // Clear the invalid input from the scanner
            }
        }
    }
    /**
     * Calculates and prints the income of each queue and the total income.
     * The income of each queue is calculated based on the purchased burgers and their price.
     */
    static void printIncomeOfEachQueue() {
        incomeOfQueue1 = purchasedBurgersQueue1 * 650;
        // Calculate the income of Queue 1 by multiplying the purchased burgers with the price

        System.out.println("Income of Queue 1 - Rs." + incomeOfQueue1);
        // Print the income of Queue 1

        incomeOfQueue2 = purchasedBurgersQueue2 * 650;
        // Calculate the income of Queue 2 by multiplying the purchased burgers with the price

        System.out.println("Income of Queue 2 - Rs." + incomeOfQueue2);
        // Print the income of Queue 2

        incomeOfQueue3 = purchasedBurgersQueue3 * 650;
        // Calculate the income of Queue 3 by multiplying the purchased burgers with the price

        System.out.println("Income of Queue 3 - Rs." + incomeOfQueue3);
        // Print the income of Queue 3

        totalIncome = incomeOfQueue1 + incomeOfQueue2 + incomeOfQueue3;
        // Calculate the total income by summing up the incomes of all queues

        System.out.println("Total income - Rs." + totalIncome);
        // Print the total income
    }

    /**
     * Saves the statistics and information of the Queue Management System to a file.
     * Writes the current date and time, along with various statistics and information, to a text file.
     */
    private static void saveToFile() {
        try {
            // Get the current date and time
            LocalDateTime myDateObj = LocalDateTime.now();

            // Define the date and time format
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            // Format the date and time
            String formattedDate = myDateObj.format(myFormatObj);

            // Create a FileWriter object to write to a file
            FileWriter myWriter = new FileWriter("Queue Management System Records.txt");

            // Append to an existing file or create a new file if it doesn't exist

            // Write the current date and time
            myWriter.write("Saved at " + formattedDate + "\n");

            // Write additional statistics and information
            myWriter.write("Total Number of Customers - " + totalCount + "\n");
            myWriter.write("Total No of Served Customers - " + servedCount + "\n");
            myWriter.write("Total No of Unserved Customers - " + unservedCount + "\n");
            myWriter.write("Total No of Customers in the waiting queue - " + countOfWaitingQueue + "\n");
            myWriter.write("Remaining burgers in the stock - " + burgerStock + "\n");
            myWriter.write("No of burgers added to the stock - " + addedBurgerCount + "\n");
            myWriter.write("Income of Queue 1 - Rs." + incomeOfQueue1 + "\n");
            myWriter.write("Income of Queue 2 - Rs." + incomeOfQueue2 + "\n");
            myWriter.write("Income of Queue 3 - Rs." + incomeOfQueue3 + "\n");
            myWriter.write("Total income - Rs." + totalIncome);

            // Close the FileWriter
            myWriter.close();

            // Print success message
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            // Print error message and stack trace in case of an error
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void loadData(){
        //https://www.w3schools.com/java/java_files_read.asp
        // Read the file and print its contents
        try {
            // Create a File object to represent the file
            File file = new File("Queue Management System Records.txt");

            // Create a Scanner object to read the file
            Scanner file_reader = new Scanner(file);
            StringBuilder fileContent = new StringBuilder(); // StringBuilder to store the file content

            // Iterate over each line in the file
            while (file_reader.hasNextLine()) {
                String text = file_reader.nextLine(); // Read the next line from the file
                fileContent.append(text).append("\n");

                String section = text.substring(0, 6); // Extract the section identifier from the line
                if (section.equals("Remain")) { // Check if the section is "Remain"
                    String section2 = text.substring(33, 35); // Extract the burger stock data from the line
                    burgerStock = Integer.parseInt(section2); // Convert the burger stock data to an integer
                }
            }
            // Close the file reader
            file_reader.close();

            System.out.println(fileContent);
            
            // Print a success message
            System.out.println("Queue Management Records are loaded from file.");
        } catch (IOException e) {
            // Print an error message and stack trace if an error occurs
            System.out.println("Error! " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays the details of the burger stock.
     * Prints the remaining burger stock and the number of burgers added to the stock.
     */
    //GUI
    static void stockDetails() {
        System.out.println("Remaining Burger Stock: " + burgerStock);
        // Print the remaining burger stock

        System.out.println("No of burgers added to the stock - " + addedBurgerCount + "\n");
        // Print the number of burgers added to the stock

    }


}
