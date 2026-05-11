package dk.sdu.imada.oop26.view;

import dk.sdu.imada.oop26.controller.GameController;
import dk.sdu.imada.oop26.controller.StartPageController;
import dk.sdu.imada.oop26.model.Maze;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewHandler {

    private static final String TITLE_START = "Pac-Man Start";
    private static final String TITLE_GAME = "Pac-Man";

    private final Stage stage;
    private final StartPageView startPageView;

    public ViewHandler(Stage stage) {
        this.stage = stage;
        this.startPageView = new StartPageView();
        new StartPageController(startPageView, this);
    }

    public void openStartScreen() {
        BorderPane region = startPageView.getUi();
        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;
        Scene scene = new Scene(region, width, height);
        stage.setTitle(TITLE_START);
        stage.setScene(scene);
        stage.show();
    }

    public void openPacmanScreen() {
        PacmanView pacmanView = new PacmanView();
        GameController gameCtrl = new GameController(pacmanView);
        Pane pane = gameCtrl.startGame();
        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;
        Scene scene = new Scene(pane, width, height);
        stage.setTitle(TITLE_GAME);
        stage.setScene(scene);
        stage.show();
        pane.requestFocus();
    }
}
