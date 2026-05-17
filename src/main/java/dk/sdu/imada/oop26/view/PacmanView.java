package dk.sdu.imada.oop26.view;

import dk.sdu.imada.oop26.model.GameState;
import dk.sdu.imada.oop26.model.Ghost;
import dk.sdu.imada.oop26.model.Maze;
import dk.sdu.imada.oop26.model.Pacman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

public class PacmanView {

    private static final int PELLET_OFFSET = 10;
    private static final int PELLET_SIZE = 4;
    private static final int POWER_PELLET_SIZE = 12;

    private static final String IMG_WALL = "/images/wall.png";
    private static final String IMG_PACMAN_UP = "/images/pacmanUp.png";
    private static final String IMG_PACMAN_DOWN = "/images/pacmanDown.png";
    private static final String IMG_PACMAN_LEFT = "/images/pacmanLeft.png";
    private static final String IMG_PACMAN_RIGHT = "/images/pacmanRight.png";
    private static final String IMG_RED_GHOST = "/images/redGhost.png";
    private static final String IMG_PINK_GHOST = "/images/pinkGhost.png";
    private static final String IMG_BLUE_GHOST = "/images/blueGhost.png";
    private static final String IMG_ORANGE_GHOST = "/images/orangeGhost.png";
    private static final String IMG_SCARED_GHOST = "/images/scaredGhost.png"; 
    private static final String IMG_CHERRY = "/images/cherry.png";

    private final Image imgUp = new Image(IMG_PACMAN_UP);
    private final Image imgDown = new Image(IMG_PACMAN_DOWN);
    private final Image imgLeft = new Image(IMG_PACMAN_LEFT);
    private final Image imgRight = new Image(IMG_PACMAN_RIGHT);

    private final Image imgRedGhost = new Image(IMG_RED_GHOST);
    private final Image imgPinkGhost = new Image(IMG_PINK_GHOST);
    private final Image imgBlueGhost = new Image(IMG_BLUE_GHOST);
    private final Image imgOrangeGhost = new Image(IMG_ORANGE_GHOST);
    private final Image imgScaredGhost = new Image(IMG_SCARED_GHOST); 
    private final Image imgCherry = new Image(IMG_CHERRY);

    private Consumer<KeyEvent> onKeyPressed;
    private Pane pane;

    public Pane getPacmanScreenUi() {
        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;

        Canvas canvas = new Canvas(width, height);

        pane = new Pane(canvas);
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(e -> {
            if (onKeyPressed != null)
                onKeyPressed.accept(e);
        });
        pane.requestFocus();

        return pane;
    }

    public void setOnKeyPressed(Consumer<KeyEvent> handler) {
        this.onKeyPressed = handler;
    }

    public GraphicsContext getGraphicsContext() {
        Canvas canvas = (Canvas) pane.getChildren().get(0);
        return canvas.getGraphicsContext2D();
    }

    public void render(GraphicsContext gc, Pacman pacman, Ghost redGhost, Ghost pinkGhost,
            Ghost blueGhost, Ghost orangeGhost, GameState gameState) {

        int width = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;
        gc.clearRect(0, 0, width, height);

        if (gameState.isGameOver()) {
            drawGameOver(gc, width, height);
            return;
        }

        Image wallImage = new Image(getClass().getResourceAsStream(IMG_WALL));
        drawMaze(gc, wallImage, gameState);
        drawGhosts(gc, redGhost, pinkGhost, blueGhost, orangeGhost, gameState); 
        drawPacman(gc, pacman);
        drawHud(gc, gameState);
    }

    private void drawMaze(GraphicsContext gc, Image wallImage, GameState gameState) {
        for (int r = 0; r < Maze.TILE_MAP.length; r++) {
            String row = Maze.TILE_MAP[r];
            for (int c = 0; c < row.length(); c++) {
                char tile = row.charAt(c);

                if (tile == 'x') {
                    gc.drawImage(wallImage,
                            c * Maze.TILE_SIZE, r * Maze.TILE_SIZE,
                            Maze.TILE_SIZE, Maze.TILE_SIZE);
                }

                if (tile == ' ' && !gameState.isPelletEaten(r, c)) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval(
                            c * Maze.TILE_SIZE + PELLET_OFFSET,
                            r * Maze.TILE_SIZE + PELLET_OFFSET,
                            PELLET_SIZE, PELLET_SIZE);
                }

                if (tile == ' ' && !gameState.isPelletEaten(r, c)) {
                    boolean isPowerPellet = (r == 1 && c == 1) ||
                            (r == 1 && c == 58) ||
                            (r == 15 && c == 1) ||
                            (r == 15 && c == 58);

                    if (isPowerPellet) {
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(
                                c * Maze.TILE_SIZE + 6,
                                r * Maze.TILE_SIZE + 6,
                                12, 12);
                    }

                    if (r == 1 && c == 30 && !gameState.isPelletEaten(r, c)) {
                        gc.drawImage(imgCherry,
                                c * Maze.TILE_SIZE,
                                r * Maze.TILE_SIZE,
                                Maze.TILE_SIZE, Maze.TILE_SIZE);
                    }
                }
            }
        }
    }

    // ← ÆNDRET – gameState tilføjet som parameter
    private void drawGhosts(GraphicsContext gc, Ghost red, Ghost pink, Ghost blue, Ghost orange, GameState gameState) {
        int s = Ghost.SIZE;

        // ← NY – vis scaredGhost billede hvis state er POWER, ellers normalt billede
        if (gameState.getState().equals("POWER")) {
            if (!red.isEaten())    gc.drawImage(imgScaredGhost, red.getX(),    red.getY(),    s, s);
            if (!pink.isEaten())   gc.drawImage(imgScaredGhost, pink.getX(),   pink.getY(),   s, s);
            if (!blue.isEaten())   gc.drawImage(imgScaredGhost, blue.getX(),   blue.getY(),   s, s);
            if (!orange.isEaten()) gc.drawImage(imgScaredGhost, orange.getX(), orange.getY(), s, s);
        } else {
            gc.drawImage(imgRedGhost,    red.getX(),    red.getY(),    s, s);
            gc.drawImage(imgPinkGhost,   pink.getX(),   pink.getY(),   s, s);
            gc.drawImage(imgBlueGhost,   blue.getX(),   blue.getY(),   s, s);
            gc.drawImage(imgOrangeGhost, orange.getX(), orange.getY(), s, s);
        }
    }

    private void drawPacman(GraphicsContext gc, Pacman pacman) {
        gc.drawImage(getPacmanImage(pacman), pacman.getX(), pacman.getY(),
                Pacman.SIZE, Pacman.SIZE);
    }

    private Image getPacmanImage(Pacman pacman) {
        if (pacman.getDirX() == 1)
            return imgRight;
        if (pacman.getDirX() == -1)
            return imgLeft;
        if (pacman.getDirY() == -1)
            return imgUp;
        if (pacman.getDirY() == 1)
            return imgDown;
        return imgRight;
    }

    private void drawHud(GraphicsContext gc, GameState gameState) {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText("Score: " + gameState.getScore(), 10, 20);
        if (gameState.getLives() > 0) {
            gc.fillText("Lives: " + gameState.getLives(), 10, 50);
        }

        // ← NY – vis POWER indikator
        if (gameState.getState().equals("POWER")) {
            gc.setFill(Color.CYAN);
            gc.fillText("POWER!", 10, 80);
        }

        // ← NY – vis IMMUNE indikator
        if (gameState.getState().equals("IMMUNE")) {
            gc.setFill(Color.WHITE);
            gc.fillText("IMMUNE", 10, 80);
        }
    }

    private void drawGameOver(GraphicsContext gc, int width, int height) {
        gc.setFill(Color.RED);
        gc.setFont(Font.font(30));
        gc.fillText("GAME OVER", width / 2.0 - 80, height / 2.0);
    }
}