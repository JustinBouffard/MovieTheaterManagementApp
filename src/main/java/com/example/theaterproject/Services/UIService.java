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

    private static final String FXML_PATH_PREFIX = "/com/example/theaterproject/";
    public static final int DEFAULT_SCREEN_WIDTH = 480;
    public static final int DEFAULT_SCREEN_HEIGHT = 350;

    /**
     * Shows an error alert dialog.
     */
    public static void showErrorAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.ERROR, pTitle, pMessage);
    }

    /**
     * Shows an information alert dialog.
     */
    public static void showInfoAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.INFORMATION, pTitle, pMessage);
    }

    /**
     * Shows a warning alert dialog.
     */
    public static void showWarningAlert(String pTitle, String pMessage) {
        showAlert(Alert.AlertType.WARNING, pTitle, pMessage);
    }

    /**
     * Shows an alert dialog of the specified type.
     */
    public static void showAlert(Alert.AlertType pType, String pTitle, String pMessage) {
        Alert alert = new Alert(pType);
        alert.setTitle(pTitle);
        alert.setHeaderText(null);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

    /**
     * Closes the current window by extracting the stage from an ActionEvent source.
     */
    public static void closeWindow(ActionEvent pEvent) {
        Node source = (Node) pEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens a new window with the specified FXML file as a modal dialog.
     * The current window will be closed after opening the new one.
     * Uses the default screen size.
     */
    public static void openModalWindow(String pFxmlFileName, String pTitle, ActionEvent pEvent) throws IOException {
        openModalWindow(pFxmlFileName, pTitle, pEvent, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
    }

    /**
     * Opens a new window with the specified FXML file as a modal dialog with custom dimensions.
     * The current window will be closed after opening the new one.
     */
    public static void openModalWindow(String pFxmlFileName, String pTitle, ActionEvent pEvent, double pWidth, double pHeight) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(FXML_PATH_PREFIX + pFxmlFileName + ".fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle(pTitle);
        newStage.setScene(new Scene(root, pWidth, pHeight));
        newStage.show();

        closeWindow(pEvent);
    }

    /**
     * Opens a new modal dialog window with the specified FXML file.
     * The dialog is modal and will block interaction with other windows until closed.
     * Uses the default screen size.
     */
    public static FXMLLoader openModalDialog(String pFxmlFileName, String pTitle) throws IOException {
        return openModalDialog(pFxmlFileName, pTitle, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
    }

    /**
     * Opens a new modal dialog window with the specified FXML file and custom dimensions.
     * The dialog is modal and will block interaction with other windows until closed.
     */
    public static FXMLLoader openModalDialog(String pFxmlFileName, String pTitle, double pWidth, double pHeight) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(FXML_PATH_PREFIX + pFxmlFileName + ".fxml"));
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
    public static FXMLLoader loadFXML(String pFxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(UIService.class.getResource(FXML_PATH_PREFIX + pFxmlFileName + ".fxml"));
        loader.load();
        return loader;
    }
}
