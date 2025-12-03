package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Account;
import com.example.theaterproject.Services.AccountService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * The LoginViewController class is responsible for managing the login interface
 * and facilitating user authentication in the application.
 *
 * <p>
 * This class handles user actions such as signing in and creating a new account
 * while managing the transition to different views based on the user's role.
 * It interacts with the {@code AccountService} to validate credentials.
 * </p>
 */
public class LoginViewController {
    /**
     * Represents a text field in the login view for user input of the username.
     *
     * This field is linked to the FXML file associated with the LoginViewController
     * and is used to capture the username input provided by the user.
     */
    @FXML
    private TextField aUsernameTextField;
    /**
     * Represents the password input field on the login view. This field is used to
     * capture the user's password during the authentication process.
     * <p>
     * The password entered in this field is sent to the authentication logic
     * when the user clicks the "Sign In" button.
     * <p>
     * This field is part of the FXML file associated with the LoginViewController class,
     * and it is annotated with @FXML to indicate that it is injected at runtime
     * during FXML loading.
     */
    @FXML
    private PasswordField aPasswordField;
    /**
     * The accountService variable is an instance of the singleton {@link AccountService}
     * class. It provides centralized management of user accounts, including authentication,
     * client management, and access to manager-specific functionality.
     * <p>
     * This instance is used by the {@code LoginViewController} class for handling actions
     * such as user authentication and account initialization.
     */
    AccountService aAccountService = AccountService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    /**
     * Handles the click event for the "Sign In" button. Authenticates the user based on the entered
     * username and password. If the credentials match a manager account, the manager's dashboard is opened.
     * Otherwise, the main dashboard is shown. Closes the login window after successful navigation.
     *
     * @param pEvent the action event triggered by clicking the "Sign In" button
     */
    @FXML
    private void onSignInButtonClick(ActionEvent pEvent) {

        String username = aUsernameTextField.getText();
        String password = aPasswordField.getText();


        try {
            Account managerAccount = aAccountService.getManager();

            if (managerAccount.getUserName().equals(username) && managerAccount.getPassword().equals(password)) {
                aUIService.openNewWindow("editor-view", "Theater Dashboard", pEvent,900,700);
            } else {
                aUIService.openNewWindow("main-view", "Theater Dashboard", pEvent, 900, 700);
            }
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }


    /**
     * Handles the click event for the "New Account" button. Opens the "New Account" window
     * by loading the corresponding FXML file and displays it in a new stage. Closes the current
     * login window after successfully opening the "New Account" window.
     *
     * @param pEvent the action event triggered by clicking the "New Account" button
     */
    @FXML
    private void onNewAccountButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("new-account-view", "New Account", pEvent);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }

    }
}
