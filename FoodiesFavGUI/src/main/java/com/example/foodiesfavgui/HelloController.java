package com.example.foodiesfavgui; // Package declaration
import javafx.fxml.FXML; // Import for FXML annotations
import javafx.fxml.FXMLLoader; // Import for loading FXML files
import javafx.scene.Parent; // Import for creating the root node of the scene graph
import javafx.scene.Scene; // Import for creating the scene
import javafx.scene.control.*; // Import for JavaFX UI controls
import javafx.stage.Stage; // Import for creating a stage/window
import java.io.IOException; // Import for handling input/output exceptions
import java.util.ArrayList; // Import for managing array lists

import static com.example.foodiesfavgui.HelloApplication.*;

public class HelloController {
    @FXML
    private Label queue1lb1; // Label for the first customer in Queue 1
    @FXML
    private Label queue1lb2; // Label for the second customer in Queue 1
    @FXML
    private Label queue2lb1; // Label for the first customer in Queue 2
    @FXML
    private Label queue2lb2; // Label for the second customer in Queue 2
    @FXML
    private Label queue2lb3; // Label for the third customer in Queue 2
    @FXML
    private Label queue3lb1; // Label for the first customer in Queue 3
    @FXML
    private Label queue3lb2; // Label for the second customer in Queue 3
    @FXML
    private Label queue3lb3; // Label for the third customer in Queue 3
    @FXML
    private Label queue3lb4; // Label for the fourth customer in Queue 3
    @FXML
    private Label queue3lb5; // Label for the fifth customer in Queue 3
    @FXML
    private Label waitinglb1; // Label for the first customer in the waiting queue
    @FXML
    private Label waitinglb2; // Label for the second customer in the waiting queue
    @FXML
    private Label waitinglb3; // Label for the third customer in the waiting queue
    @FXML
    private Label waitinglb4; // Label for the fourth customer in the waiting queue
    @FXML
    private Label waitinglb5; // Label for the fifth customer in the waiting queue
    @FXML
    private Button closebtn; // Button to close the application
    @FXML
    private Button searchbtn; // Button to open the search customer view
    @FXML
    private Button quitbtn; // Button to quit the search customer view

    public void searchCustomer() throws IOException{
        // Get the current stage and close it
        Stage stage = (Stage) searchbtn.getScene().getWindow();
        stage.close();

        // Create a new stage for the search customer window
        Stage primaryStage = new Stage();

        // Load the FXML file for the search customer window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("search-customer.fxml"));
        Parent root = loader.load();

        // Set the title of the new stage
        primaryStage.setTitle("Search Customer");

        // Set the scene of the new stage with the loaded FXML file and specified dimensions
        primaryStage.setScene(new Scene(root, 800, 600));

        // Show the new stage
        primaryStage.show();
    }

    public void searchClose() throws IOException{
        // Get the current stage and close it
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();

        // Create a new stage for the start GUI window
        Stage startStage = new Stage();

        // Load the FXML file for the hello-view window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Get the controller of the hello-view window
        HelloController helloController = loader.getController();

        // Update the queue labels if needed
        helloController.updateQueueLabels();

        // Set the scene of the new stage with the loaded FXML file and specified dimensions
        Scene scene = new Scene(root, 850, 600);
        startStage.setScene(scene);

        // Set the title of the new stage
        startStage.setTitle("Start GUI");

        // Show the new stage
        startStage.show();
    }

    public void quitSearch(){
        // Get the current stage and close it
        Stage stage = (Stage) quitbtn.getScene().getWindow();
        stage.close();
    }

    public HelloController(){

    }

    void updateQueueLabels() {
        // Array of labels for queue1
        Label[] queue1Labels = {queue1lb1, queue1lb2};

        // Array of labels for queue2
        Label[] queue2Labels = {queue2lb1, queue2lb2, queue2lb3};

        // Array of labels for queue3
        Label[] queue3Labels = {queue3lb1, queue3lb2, queue3lb3, queue3lb4, queue3lb5};

        // Array of labels for waitingQueue
        Label[] waitingLabels = {waitinglb1, waitinglb2, waitinglb3, waitinglb4, waitinglb5};

        // Update labels for queue1 using queue1Labels
        updateLabels(queue1.getQueue(), queue1Labels);

        // Update labels for queue2 using queue2Labels
        updateLabels(queue2.getQueue(), queue2Labels);

        // Update labels for queue3 using queue3Labels
        updateLabels(queue3.getQueue(), queue3Labels);

        // Update labels for waitingQueue using waitingLabels
        updateLabels(waitingQueue.getWaitingQueue(), waitingLabels);

    }
    private void updateLabels(ArrayList<Customer> customers, Label[] labels) {
        for (int i = 0; i < labels.length; i++) {
            // Check if there is a customer at index i
            if (i < customers.size()) {
                // Get the customer at index i
                Customer customer = customers.get(i);
                // Check if the label at index i is not null
                if (labels[i] != null) {
                    // Set the text of the label to the customer's first name
                    labels[i].setText(customer.getFirstName());
                }
            } else {
                // No customer at index i, so set the label's text to an empty string
                if (labels[i] != null) {
                    labels[i].setText(" ");
                }
            }
        }
    }
    @FXML
    protected void viewQueueIncome(){
        // Print the income of each queue
        printIncomeOfEachQueue();

        // Print an empty line for formatting purposes
        System.out.println(" ");
    }

    @FXML
    protected void viewStockDetails(){
        // Show the stock details
        stockDetails();

        // Print an empty line for formatting purposes
        System.out.println(" ");
    }





}
