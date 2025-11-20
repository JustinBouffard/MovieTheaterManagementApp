package com.example.theaterproject.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class mainViewController {
    @FXML
    private GridPane movieGridPane;
    /**
     * Keeps track of the number of cards added to the grid pane.
     */
    private int cardCount = 0;
    /**
     * Represents the number of cards displayed in each row of the composite enclosure grid.
     * This constant is used to properly arrange the layout of cards in the user interface.
     */
    private final int cardPerRow = 3;

    private VBox createStyledCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.TOP_CENTER); // Center content inside the card
        card.setPrefWidth(240);            // Consistent width
        card.setPrefHeight(120);           // Optional: consistent height
        card.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(10), Insets.EMPTY)));
        card.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        card.setEffect(new DropShadow(5, Color.gray(0.3)));
        return card;
    }

    /**
     * Defines a CSS style for buttons to standardize their visual appearance across the application.
     * The style string includes properties such as background color, border settings, text fill color,
     * font weight, padding, and a drop shadow effect. These properties enhance the visual consistency
     * and usability of the buttons in the graphical user interface.
     */
    private static final String BUTTON_STYLE = """
                -fx-background-color: #dddddd;
                -fx-border-color: #444;
                -fx-border-width: 1;
                -fx-border-radius: 6;
                -fx-text-fill: #222;
                -fx-font-weight: bold;
                -fx-background-radius: 6;
                -fx-padding: 6 12;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 2, 0, 0, 1);
            """;

    /**
     * Adds a new enclosure card to the composite enclosure grid pane. The card includes
     * the enclosure's name and a button to view the enclosure details.
     *
     * @param enclosure the enclosure object to use for creating the card. It provides
     *                  the name of the enclosure and is used to open the corresponding
     *                  enclosure window when the "View" button is clicked.
     */
    public void addMovieCard

    {
        VBox card = createStyledCard();

        Label nameLabel = new Label(.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        nameLabel.setAlignment(Pos.CENTER);

        //Button viewButton = new Button("View");
        //viewButton.setStyle(BUTTON_STYLE);
        //viewButton.setOnAction(e -> openEnclosureWindow(enclosure));

        card.getChildren().addAll(nameLabel);

        int col = cardCount % cardPerRow;
        int rowIndex = cardCount / cardPerRow;
        //compositeEnclosureGridPane.add(card, col, rowIndex);
        GridPane.setMargin(card, new Insets(10));
        GridPane.setHalignment(card, HPos.CENTER);
        GridPane.setValignment(card, VPos.TOP);

        cardCount++;
    }
    }
