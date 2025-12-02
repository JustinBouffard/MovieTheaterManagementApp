package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Client;
import com.example.theaterproject.Services.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Controller responsible for handling the "Create New Account" view.
 * <p>
 * This controller validates user input (username, email, password, confirm password),
 * creates a new {@link Client} upon successful validation, registers it with the
 * {@link AccountService}, clears the form, provides user feedback via alerts, and
 * navigates to the main application view.
 * </p>
 *
 * <h2>Validation rules</h2>
 * <ul>
 *   <li>All fields are required.</li>
 *   <li>Email must match a basic pattern: local@domain.tld</li>
 *   <li>Passwords must match exactly.</li>
 *   <li>The username "manager" (case-insensitive) is reserved and cannot be used.</li>
 * </ul>
 *
 * <h2>Navigation</h2>
 * On successful account creation, this controller loads and shows
 * <code>/com/example/theaterproject/main-view.fxml</code> in a new {@link Stage}
 * and closes the current window.
 *
 * <p><strong>Threading note:</strong> All operations here are intended to run on
 * the JavaFX Application Thread.</p>
 *
 * @author pfelix7
 * @since 2025-12-02
 */
public class NewAccountViewController {

    /**
     * Text field bound to the username input in the FXML.
     */
    @FXML
    private TextField aUsernameField;

    /**
     * Text field bound to the email input in the FXML.
     */
    @FXML
    private TextField aEmailField;

    /**
     * Password field bound to the password input in the FXML.
     */
    @FXML
    private PasswordField aPasswordField;

    /**
     * Password field bound to the confirm password input in the FXML.
     */
    @FXML
    private PasswordField aConfirmPasswordField;

    /**
     * Simple email validation pattern that enforces the presence of
     * exactly one '@' and at least one dot after the '@'.
     * <p>Note: This is a basic format check and does not guarantee
     * the address actually exists.</p>
     */
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    /**
     * FXML event handler for the "Sign Up" button.
     * <p>
     * Delegates to {@link #handleSignUp(ActionEvent)} to perform
     * validations, account creation, and navigation.
     * </p>
     *
     * @param pEvent the originating action event from the UI
     */
    @FXML
    private void onSignUpButtonClick(ActionEvent pEvent) {
        handleSignUp(pEvent);
    }

    /**
     * Handles the full sign-up flow:
     * <ol>
     *   <li>Reads and trims user inputs from UI controls.</li>
     *   <li>Validates required fields, email format, password match, and reserved username.</li>
     *   <li>Constructs a {@link Client} and registers it with {@link AccountService}.</li>
     *   <li>Clears input fields and shows a success alert.</li>
     *   <li>Loads the main view and closes the current window.</li>
     * </ol>
     * Any validation failure results in an error alert and an early return.
     *
     * @param pEvent the action event that triggered sign-up (used to find the current window for closing)
     */
    private void handleSignUp(ActionEvent pEvent) {
        String username = aUsernameField.getText().trim();
        String email = aEmailField.getText().trim();
        String password = aPasswordField.getText().trim();
        String confirmPassword = aConfirmPasswordField.getText();

        // check if any field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incomplete field(s)", "Please fill in all the fields.");
            return;
        }

        // check if email is in valid format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        // make sure passwords match
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return;
        }

        // make sure username isn't manager
        if (username.equalsIgnoreCase("manager")) {
            showAlert(Alert.AlertType.ERROR, "Reserved Username", "Username cannot be " + username);
            return;
        }

        // create new client account
        try {
            Client newClient = new Client(username,password,email);

            // adding created client to AccountService list
            AccountService.getInstance().addClient(newClient);

            // clear the fields after account creation
            clearFields();

            showAlert(Alert.AlertType.INFORMATION, "Account Created", "Account has been successfully created.");

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid account information", e.getMessage());
        }

        // direct to main view after creation
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/theaterproject/main-view.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Theater Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // close login window
            Stage currentStage = (Stage) ((Node) pEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears all input fields in the sign-up form.
     * <p>
     * Helpful after successful account creation or when resetting the form.
     * </p>
     */
    private void clearFields() {
        aUsernameField.clear();
        aEmailField.clear();
        aPasswordField.clear();
        aConfirmPasswordField.clear();
    }

    /**
     * Shows a modal alert dialog to the user and waits for acknowledgement.
     *
     * @param type    the alert type (e.g., INFORMATION, WARNING, ERROR)
     * @param pTitle  the title text for the alert dialog
     * @param pMessage the content message to display to the user
     */
    private void showAlert(Alert.AlertType type,String pTitle, String pMessage) {
        Alert alert = new Alert(type);
        alert.setTitle(pTitle);
        alert.setHeaderText(null);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

}
