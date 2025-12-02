package com.example.theaterproject.Services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Service for common UI operations.
 * Provides reusable methods for window management, alerts, and FXML loading.
 */
public class UIService {

    private static UIService aInstance;
    private static final String aFxmlPathPrefix = "/com/example/theaterproject/";
    public static final int aDefaultScreenWidth = 480;
    public static final int aDefaultScreenHeight = 350;

    private UIService() {
    }

    /**
     * Gets the singleton instance of UIService.
     */
    public static UIService getInstance() {
        if (aInstance == null) {
            aInstance = new UIService();
        }
        return aInstance;
    }

    /**
     * Shows an error alert dialog.
     */
    public void showErrorAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.ERROR, pTitle, pMessage);
    }

    /**
     * Shows an information alert dialog.
     */
    public void showInfoAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.INFORMATION, pTitle, pMessage);
    }

    /**
     * Shows a warning alert dialog.
     */
    public void showWarningAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.WARNING, pTitle, pMessage);
    }

    /**
     * Shows an alert dialog of the specified type.
     */
    public void showAlert(Alert.AlertType pType, String pTitle, String pMessage) {
        Alert alert = new Alert(pType);
        alert.setTitle(pTitle);
        alert.setHeaderText(null);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

    /**
     * Closes the current window by extracting the stage from an ActionEvent source.
     */
    public void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens a new modal dialog window with the specified FXML file.
     * The dialog is modal and will block interaction with other windows until closed.
     * Uses the default screen size.
     */
    public FXMLLoader openModalDialog(String pFxmlFileName, String pTitle) throws IOException {
        return openModalDialog(pFxmlFileName, pTitle, aDefaultScreenWidth, aDefaultScreenHeight);
    }

    /**
     * Opens a new modal dialog window with the specified FXML file and custom dimensions.
     * The dialog is modal and will block interaction with other windows until closed.
     */
    public FXMLLoader openModalDialog(String pFxmlFileName, String pTitle, double pWidth, double pHeight) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(aFxmlPathPrefix + pFxmlFileName + ".fxml"));
        Parent root = loader.load();

        Stage modal = new Stage();
        modal.setScene(new Scene(root, pWidth, pHeight));
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle(pTitle);
        modal.showAndWait();

        return loader;
    }

    /**
     * Loads an FXML file and returns the FXMLLoader for accessing the controller.
     */
    public FXMLLoader loadFXML(String pFxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(aFxmlPathPrefix + pFxmlFileName + ".fxml"));
        loader.load();
        return loader;
    }

    /**
     * Opens a new window with the specified FXML file and closes the current window.
     * Uses the default screen size.
     */
    public void openNewWindow(String pFxmlFileName, String pTitle, ActionEvent pEvent) throws IOException {
        openNewWindow(pFxmlFileName, pTitle, pEvent, aDefaultScreenWidth, aDefaultScreenHeight);
    }

    /**
     * Opens a new window with the specified FXML file and closes the current window.
     * Uses custom dimensions.
     */
    public void openNewWindow(String pFxmlFileName, String pTitle, ActionEvent pEvent, double pWidth, double pHeight) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(aFxmlPathPrefix + pFxmlFileName + ".fxml"));
        javafx.scene.Parent root = loader.load();

        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.setTitle(pTitle);
        stage.setScene(new Scene(root, pWidth, pHeight));
        stage.show();

        closeWindow(pEvent);
    }
}