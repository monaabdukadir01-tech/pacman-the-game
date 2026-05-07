package dk.sdu.imada.oop26.view;

import dk.sdu.imada.oop26.model.Maze;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewHandler {

    private static final String TITLE_START = "Pac-Man – Start";
    private static final String TITLE_GAME = "Pac-Man";

    private final Stage stage;
    private final StartPageView startpageView;
    private final PacmanView pacmanView;

    public ViewHandler(Stage stage) {
        this.stage = stage;
        this.startpageView = new StartPageView(this);
        this.pacmanView = new PacmanView();
    }

    public void openStartScreen() {
        BorderPane region = startpageView.getUi();
        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;
        Scene scene = new Scene(region, width, height);
        stage.setTitle(TITLE_START);
        stage.setScene(scene);
        stage.show();
    }

    public void openPacmanScreen() {
        Pane pane = pacmanView.getPacmanScreenUi();
        pane.setStyle("-fx-background-color: black;");
        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;
        Scene scene = new Scene(pane, width, height);
        stage.setTitle(TITLE_GAME);
        stage.setScene(scene);
        stage.show();
        pane.requestFocus();
    }
}
