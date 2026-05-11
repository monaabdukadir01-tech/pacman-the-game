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

    private Button enter_button;
    private BorderPane root;

    public StartPageView() {
        enter_button = new Button("Start Game");
        root = buildUi();
    }

    private BorderPane buildUi() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));

        String pacman_thumbnail2 = "/images/pacman-thumbnail2.png";
        Image pacman_thumbnail = new Image(getClass().getResourceAsStream(pacman_thumbnail2));
        try {
            if (pacman_thumbnail.isError()) {
                System.err.println("Billede indlæst med fejl!");
            }
        } catch (Exception e) {
            System.err.println("Kunne ikke indlæse billede: " + e.getMessage());
        }

        BackgroundSize bsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(pacman_thumbnail,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bsize);
        pane.setBackground(new Background(backgroundImage));

        Label welcome_label = new Label("Welcome To Pac-Man");
        BorderPane.setAlignment(welcome_label, Pos.CENTER);
        welcome_label.setStyle("-fx-font-size: 40; -fx-text-fill: white;");
        pane.setTop(welcome_label);

        BorderPane.setAlignment(enter_button, Pos.BOTTOM_CENTER);
        pane.setBottom(enter_button);

        return pane;
    }

    public BorderPane getUi() {
        return root;
    }

    public void setStartButtonAction(EventHandler<ActionEvent> handler) {
        enter_button.setOnAction(handler);
    }
}
