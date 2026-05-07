package dk.sdu.imada.oop26.view;

import dk.sdu.imada.oop26.controller.PacmanController;
import dk.sdu.imada.oop26.model.*;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PacmanView {

    private static final int PELLET_OFFSET = 10;
    private static final int PELLET_SIZE   = 4;
    private static final int COLLISION_DISTANCE = 20;

    private static final String IMG_WALL         = "/images/wall.png";
    private static final String IMG_PACMAN_UP    = "/images/pacmanUp.png";
    private static final String IMG_PACMAN_DOWN  = "/images/pacmanDown.png";
    private static final String IMG_PACMAN_LEFT  = "/images/pacmanLeft.png";
    private static final String IMG_PACMAN_RIGHT = "/images/pacmanRight.png";
    private static final String IMG_RED_GHOST    = "/images/redGhost.png";
    private static final String IMG_PINK_GHOST   = "/images/pinkGhost.png";
    private static final String IMG_BLUE_GHOST   = "/images/blueGhost.png";
    private static final String IMG_ORANGE_GHOST = "/images/orangeGhost.png";

    private final Pacman pacman;
    private final Ghost redGhost;
    private final Ghost pinkGhost;
    private final Ghost blueGhost;
    private final Ghost orangeGhost;
    private final GameState gameState;
    private final PacmanController controller;
    private final BackgroundMusic backgroundMusic;

    // Pacman images loaded in view
    private final Image imgUp    = new Image(IMG_PACMAN_UP);
    private final Image imgDown  = new Image(IMG_PACMAN_DOWN);
    private final Image imgLeft  = new Image(IMG_PACMAN_LEFT);
    private final Image imgRight = new Image(IMG_PACMAN_RIGHT);

    private final Image imgRedGhost    = new Image(IMG_RED_GHOST);
    private final Image imgPinkGhost   = new Image(IMG_PINK_GHOST);
    private final Image imgBlueGhost   = new Image(IMG_BLUE_GHOST);
    private final Image imgOrangeGhost = new Image(IMG_ORANGE_GHOST);

    public PacmanView() {
        this.pacman      = new Pacman();
        this.redGhost    = new Ghost(28);
        this.pinkGhost   = new Ghost(29);
        this.blueGhost   = new Ghost(30);
        this.orangeGhost = new Ghost(31);
        this.gameState   = new GameState();
        this.controller  = new PacmanController(pacman);
        this.backgroundMusic = new BackgroundMusic();
    }

    public Pane getPacmanScreenUi() {
        int width  = Maze.COLS * Maze.TILE_SIZE;
        int height = Maze.ROWS * Maze.TILE_SIZE;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane pane = new Pane(canvas);
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(e -> controller.handleKey(e));
        pane.requestFocus();

        Image wallImage = new Image(getClass().getResourceAsStream(IMG_WALL));

        backgroundMusic.playBackgroundMusic();

        new AnimationTimer() {
            public void handle(long now) {
                gc.clearRect(0, 0, width, height);

                if (gameState.isGameOver()) {
                    drawGameOver(gc, width, height);
                    return;
                }

                pacman.update();
                redGhost.update();
                pinkGhost.update();
                blueGhost.update();
                orangeGhost.update();

                checkPelletCollision();
                checkGhostCollision();

                drawMaze(gc, wallImage);
                drawGhosts(gc);
                drawPacman(gc);
                drawHud(gc);
            }
        }.start();

        return pane;
    }

    // ── Drawing helpers ──────────────────────────────────────────────

    private void drawMaze(GraphicsContext gc, Image wallImage) {
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
            }
        }
    }

    private void drawGhosts(GraphicsContext gc) {
        int s = Ghost.SIZE;
        gc.drawImage(imgRedGhost,    redGhost.getX(),    redGhost.getY(),    s, s);
        gc.drawImage(imgPinkGhost,   pinkGhost.getX(),   pinkGhost.getY(),   s, s);
        gc.drawImage(imgBlueGhost,   blueGhost.getX(),   blueGhost.getY(),   s, s);
        gc.drawImage(imgOrangeGhost, orangeGhost.getX(), orangeGhost.getY(), s, s);
    }

    private void drawPacman(GraphicsContext gc) {
        gc.drawImage(getPacmanImage(), pacman.getX(), pacman.getY(),
                Pacman.SIZE, Pacman.SIZE);
    }

    private Image getPacmanImage() {
        if (pacman.getDirX() ==  1) return imgRight;
        if (pacman.getDirX() == -1) return imgLeft;
        if (pacman.getDirY() == -1) return imgUp;
        if (pacman.getDirY() ==  1) return imgDown;
        return imgRight;
    }

    private void drawHud(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText("Score: " + gameState.getScore(), 10, 20);
        if (gameState.getLives() > 0) {
            gc.fillText("Lives: " + gameState.getLives(), 10, 50);
        }
    }

    private void drawGameOver(GraphicsContext gc, int width, int height) {
        gc.setFill(Color.RED);
        gc.setFont(Font.font(30));
        gc.fillText("GAME OVER", width / 2.0 - 80, height / 2.0);
    }

    // ── Collision logic ───────────────────────────────────────────────

    private void checkPelletCollision() {
        int row = (int) ((pacman.getY() + Pacman.SIZE / 2.0) / Maze.TILE_SIZE);
        int col = (int) ((pacman.getX() + Pacman.SIZE / 2.0) / Maze.TILE_SIZE);

        if (Maze.TILE_MAP[row].charAt(col) == ' ' && !gameState.isPelletEaten(row, col)) {
            gameState.eatPellet(row, col);
            gameState.addScore(10);
        }
    }

    private void checkGhostCollision() {
        if (collision(redGhost) || collision(pinkGhost)
                || collision(blueGhost) || collision(orangeGhost)) {
            gameState.loseLife();
            if (!gameState.isGameOver()) {
                resetPositions();
            }
        }
    }

    private boolean collision(Ghost ghost) {
        double dx = ghost.getX() - pacman.getX();
        double dy = ghost.getY() - pacman.getY();
        return Math.sqrt(dx * dx + dy * dy) < COLLISION_DISTANCE;
    }

    private void resetPositions() {
        pacman.reset();
        pacman.setVelocityX(0);
        pacman.setVelocityY(0);

        redGhost.reset();    redGhost.setRandomDirection();
        pinkGhost.reset();   pinkGhost.setRandomDirection();
        blueGhost.reset();   blueGhost.setRandomDirection();
        orangeGhost.reset(); orangeGhost.setRandomDirection();
    }
}
