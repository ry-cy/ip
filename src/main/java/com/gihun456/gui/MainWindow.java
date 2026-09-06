package com.gihun456.gui;

import com.gihun456.Gihun456;
import com.gihun456.ui.UiMessages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gihun456 gihun;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/GihunUser.png"));
    private Image gihunImage = new Image(this.getClass().getResourceAsStream("/images/GihunBot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getGihunBannerDialog(UiMessages.BANNER, gihunImage),
                DialogBox.getGihunDialog(UiMessages.GREETING, gihunImage)
        );
    }

    /**
     * Injects the Gihun456 instance. 
     */
    public void setGihun(Gihun456 g) {
        gihun = g;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = gihun.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGihunDialog(response, gihunImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
