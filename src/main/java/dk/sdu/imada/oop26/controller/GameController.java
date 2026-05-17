package dk.sdu.imada.oop26.controller;

import dk.sdu.imada.oop26.model.GameState;
import dk.sdu.imada.oop26.model.Ghost;
import dk.sdu.imada.oop26.model.Maze;
import dk.sdu.imada.oop26.model.Pacman;
import dk.sdu.imada.oop26.view.BackgroundMusic;
import dk.sdu.imada.oop26.view.PacmanView;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameController {

    private static final int COLLISION_DISTANCE = 20;

    private final Pacman pacman;
    private final Ghost redGhost;
    private final Ghost pinkGhost;
    private final Ghost blueGhost;
    private final Ghost orangeGhost;
    private final GameState gameState;
    private final PacmanView view;
    private final PacmanController pacmanController;
    private final BackgroundMusic backgroundMusic;

    public GameController(PacmanView view) {
        this.view = view;
        this.pacman = new Pacman();
        this.redGhost = new Ghost(28);
        this.pinkGhost = new Ghost(29);
        this.blueGhost = new Ghost(30);
        this.orangeGhost = new Ghost(31);
        this.gameState = new GameState();
        this.pacmanController = new PacmanController(pacman);
        this.backgroundMusic = new BackgroundMusic();

        view.setOnKeyPressed(e -> pacmanController.handleKey(e)); // Here we set the key event handler for the view to
                                                                  // control Pacman with the keyboard
    }

    public Pane startGame() {
        Pane pane = view.getPacmanScreenUi();
        pane.setStyle("-fx-background-color: black;");

        backgroundMusic.playBackgroundMusic();

        GraphicsContext gc = view.getGraphicsContext();

        new AnimationTimer() {
            public void handle(long now) {
                gameState.tick(now); // update POWER and IMMUNE timer

                if (!gameState.isGameOver()) {
                    // sæt scared på alle ghosts hvis state er POWER
                    boolean scared = gameState.getState().equals("POWER");
                    redGhost.setScared(scared);
                    pinkGhost.setScared(scared);
                    blueGhost.setScared(scared);
                    orangeGhost.setScared(scared);

                    pacman.update();
                    redGhost.update(pacman.getX(), pacman.getY());
                    pinkGhost.update(pacman.getX(), pacman.getY());
                    blueGhost.update(pacman.getX(), pacman.getY());
                    orangeGhost.update(pacman.getX(), pacman.getY());

                    checkPelletCollision(now);
                    checkGhostCollision(now);
                }
                view.render(gc, pacman, redGhost, pinkGhost, blueGhost, orangeGhost, gameState);
            }
        }.start();

        return pane;
    }

    private void checkPelletCollision(long now) {
        int row = (int) ((pacman.getY() + Pacman.SIZE / 2.0) / Maze.TILE_SIZE);
        int col = (int) ((pacman.getX() + Pacman.SIZE / 2.0) / Maze.TILE_SIZE);

        if (Maze.TILE_MAP[row].charAt(col) == ' ' && !gameState.isPelletEaten(row, col)) {
            gameState.eatPellet(row, col);
            gameState.addScore(10);

            boolean isPowerPellet = (row == 1 && col == 1) ||
                    (row == 1 && col == 58) ||
                    (row == 15 && col == 1) ||
                    (row == 15 && col == 58);

            boolean isCherry = (row == 1 && col == 30);

            if (isCherry) {
                gameState.addScore(500);
            } else if (isPowerPellet) {
                gameState.addScore(100);
                gameState.activatePower(now); // activate POWER state
            }
        }
    }

    private void checkGhostCollision(long now) {
        if (gameState.getState().equals("IMMUNE")) {
            return;
        }

        if (collision(redGhost) || collision(pinkGhost)
                || collision(blueGhost) || collision(orangeGhost)) {

            if (gameState.getState().equals("POWER")) {
                // POWER state – eat ghost and get point
                if (collision(redGhost)) {
                    redGhost.getEaten();
                    gameState.addScore(100);
                }
                if (collision(pinkGhost)) {
                    pinkGhost.getEaten();
                    gameState.addScore(100);
                }
                if (collision(blueGhost)) {
                    blueGhost.getEaten();
                    gameState.addScore(100);
                }
                if (collision(orangeGhost)) {
                    orangeGhost.getEaten();
                    gameState.addScore(100);
                }
            } else {
                // NORMAL state – mis a life and become IMMUNE for a short time
                gameState.loseLife(now); //
                if (!gameState.isGameOver()) {
                    resetPositions();
                }
            }
        }
    }

    private boolean collision(Ghost ghost) {
        if (ghost.isEaten()) {
            return false; // Eaten ghosts cannot collide with Pacman
        }
        double dx = ghost.getX() - pacman.getX();
        double dy = ghost.getY() - pacman.getY();
        return Math.sqrt(dx * dx + dy * dy) < COLLISION_DISTANCE;
    }

    private void resetPositions() {
        pacman.reset();
        pacman.setVelocityX(0);
        pacman.setVelocityY(0);

        redGhost.reset();
        redGhost.setRandomDirection();
        pinkGhost.reset();
        pinkGhost.setRandomDirection();
        blueGhost.reset();
        blueGhost.setRandomDirection();
        orangeGhost.reset();
        orangeGhost.setRandomDirection();
    }
}