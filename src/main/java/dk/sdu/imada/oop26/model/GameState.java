package dk.sdu.imada.oop26.model;

import java.util.Random;

public class GameState {

    private int score;
    private int lives;
    private boolean gameOver;
    private boolean[][] pelletsEaten;

    private int cherryRow = -1;
    private int cherryCol = -1;

    private String state = "NORMAL";
    private long powerTimer = 0;
    private long immuneTimer = 0;

    public GameState() {
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.pelletsEaten = new boolean[Maze.ROWS][Maze.COLS];
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getState() {
        return state;
    }

    public boolean[][] getPelletsEaten() {
        return pelletsEaten;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void loseLife(long nowNs) { // Called when Pacman collides with a ghost in NORMAL state
        this.lives--;
        if (this.lives <= 0) {
            this.gameOver = true;
        } else {
            state = "IMMUNE";
            immuneTimer = nowNs;
        }
    }

    public boolean isPelletEaten(int row, int col) {
        return pelletsEaten[row][col];
    }

    public void eatPellet(int row, int col) {
        pelletsEaten[row][col] = true;
    }

    public boolean isCherry(int row, int col) { // Check if the tile at (row, col) is the cherry and has not been eaten
        return row == cherryRow && col == cherryCol; // Check if the tile at (row, col) is the cherry and has not been
                                                     // eaten
    }

    public void eatCherry() { // Called when Pacman eats the cherry
        cherryRow = -1; // Move cherry off the board
        cherryCol = -1;
    }

    // Called Power state is activated to randomly place the cherry on an empty tile
    public void activatePower(long nowNs) {
        state = "POWER";
        powerTimer = nowNs;
    }

    // Called every tick to update the game state, such as checking if POWER or
    // IMMUNE state should end
    public void tick(long nowNs) {
        if (state.equals("POWER") && nowNs - powerTimer >= 15_000_000_000L) {
            state = "NORMAL";
        }
        if (state.equals("IMMUNE") && nowNs - immuneTimer >= 1_500_000_000L) {
            state = "NORMAL";
        }
    }

    // Called when POWER state is activated to randomly place the cherry on an empty
    // tile
    public int powerSecondsLeft(long nowNs) {
        long timeLeft = 15_000_000_000L - (nowNs - powerTimer);
        return (int) Math.ceil(timeLeft / 1_000_000_000.0);
    }
}