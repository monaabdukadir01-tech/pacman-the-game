package dk.sdu.imada.oop26.model;

public class GameState {

    private int score;
    private int lives;
    private boolean gameOver;
    private boolean[][] pelletsEaten;

    public GameState() {
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.pelletsEaten = new boolean[Maze.ROWS][Maze.COLS];
    }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return gameOver; }
    public boolean[][] getPelletsEaten() { return pelletsEaten; }

    public void addScore(int points) { this.score += points; }

    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            this.gameOver = true;
        }
    }

    public boolean isPelletEaten(int row, int col) {
        return pelletsEaten[row][col];
    }

    public void eatPellet(int row, int col) {
        pelletsEaten[row][col] = true;
    }
}
