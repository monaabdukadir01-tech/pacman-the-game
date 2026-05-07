package dk.sdu.imada.oop26.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class StartPageView {

    private static final String THUMBNAIL_PATH = "/images/pacman-thumbnail2.png";
    private static final String WELCOME_TEXT = "Welcome To Pac-Man";
    private static final String BUTTON_TEXT = "Start Game";
    private static final double PADDING = 20;
    private static final double FONT_SIZE = 40;

    private final Button startButton;

    public StartPageView() {
        this.startButton = new Button(BUTTON_TEXT);
    }

    public BorderPane getUi() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(PADDING));
        root.setBackground(buildBackground());

        Label welcomeLabel = new Label(WELCOME_TEXT);
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        welcomeLabel.setStyle("-fx-font-size: " + FONT_SIZE + "; -fx-text-fill: white;");
        root.setTop(welcomeLabel);

        BorderPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        root.setBottom(startButton);

        return root;
    }

    public void setStartButtonAction(EventHandler<ActionEvent> handler) {
        startButton.setOnAction(handler);
    }

    private Background buildBackground() {
        Image image = new Image(getClass().getResourceAsStream(THUMBNAIL_PATH));
        if (image.isError()) {
            System.err.println("Kunne ikke indlæse baggrundsbillede: " + THUMBNAIL_PATH);
        }
        BackgroundSize size = new BackgroundSize(
                BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        BackgroundImage bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                size);
        return new Background(bgImage);
    }

}
