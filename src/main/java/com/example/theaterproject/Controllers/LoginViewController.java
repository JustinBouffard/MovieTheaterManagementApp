package com.example.theaterproject.Controllers;

import com.example.theaterproject.Models.Account;
import com.example.theaterproject.Services.AccountService;
import com.example.theaterproject.Services.UIService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController {
    @FXML
    private Button signInButton;

    @FXML
    private Button newAccountButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    AccountService accountService = AccountService.getInstance();
    private final UIService aUIService = UIService.getInstance();

    @FXML
    private void onSignInButtonClick(ActionEvent pEvent) {

        String username = usernameTextField.getText();
        String password = passwordField.getText();


        try {
            Account managerAccount = accountService.getManager();

            if (managerAccount.getUserName().equals(username) && managerAccount.getPassword().equals(password)) {
                aUIService.openNewWindow("editor-view", "Theater Dashboard", pEvent);
            } else {
                aUIService.openNewWindow("main-view", "Theater Dashboard", pEvent, 700, 400);
            }
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onNewAccountButtonClick(ActionEvent pEvent) {
        try {
            aUIService.openNewWindow("new-account-view", "New Account", pEvent);
        } catch (IOException e) {
            aUIService.showErrorAlert("Error", e.getMessage());
        }

    }
}
