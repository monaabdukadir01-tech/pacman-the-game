package dk.sdu.imada.oop26.model;

public class Ghost {

    public static final int SIZE = 24;
    private static final int GHOST_START_ROW = 9;

    private double x;
    private double y;
    private final double startX;
    private final double startY;

    private double speed = 1.5;
    private int dirX = 0;
    private int dirY = -1;

    private long startTime = -1;
    private boolean exited = false;

    private boolean scared = false;
<<<<<<< HEAD
    private boolean eaten = false;
=======
    private boolean eaten  = false;
>>>>>>> main
    private long eatenTime = 0;

    public Ghost(int startCol) {
        this.startX = startCol * Maze.TILE_SIZE;
        this.startY = GHOST_START_ROW * Maze.TILE_SIZE;
        this.x = startX;
        this.y = startY;
    }

    public void update(double pacmanX, double pacmanY) {
<<<<<<< HEAD
        // stay in ghost box for 10 seconds before chasing Pacman, and cannot be eaten
        // during this time
        if (eaten) {
            if (System.nanoTime() - eatenTime >= 3_000_000_000L) {
                eaten = false;
                exited = false;
=======
        // Vent inde i boksen i 3 sekunder efter at være spist
        if (eaten) {
            if (System.nanoTime() - eatenTime >= 3_000_000_000L) {
                eaten     = false;
                exited    = false;
>>>>>>> main
                startTime = -1;
            }
            return;
        }

        if (startTime == -1) { // Start timer when ghost starts moving
            startTime = System.currentTimeMillis();
        }

        if (!exited && y <= 7 * Maze.TILE_SIZE) { // Check if ghost has exited the box (passed the exit row)
            exited = true;
        }

        if (!exited) {
            // Bevæg ud af boksen
            dirX = 0;
            dirY = -1;
        } else if (scared) {
<<<<<<< HEAD
            fleePacman(pacmanX, pacmanY); // Flee from Pacman if scared
        } else if ((System.currentTimeMillis() - startTime) / 1000.0 >= 10) { // After 10 seconds, start chasing Pacman
=======
            // Kun skift retning hvis ghost rammer en væg
            double newX = x + dirX * speed;
            double newY = y + dirY * speed;
            if (isWall(newX, newY)) {
                fleePacman(pacmanX, pacmanY);
            }
        } else if ((System.currentTimeMillis() - startTime) / 1000.0 >= 10) {
>>>>>>> main
            chasePacman(pacmanX, pacmanY);
        }

        double newX = x + dirX * speed;
        double newY = y + dirY * speed;

        if (!isWall(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            pickRandomDirection();
        }
    }

<<<<<<< HEAD
    private void chasePacman(double pacmanX, double pacmanY) { // Move ghost towards Pacman
=======
    // Bevæger ghost mod Pacman
    private void chasePacman(double pacmanX, double pacmanY) {
>>>>>>> main
        int wantedDirX = 0;
        int wantedDirY = 0;

        double distanceX = Math.abs(pacmanX - x);
        double distanceY = Math.abs(pacmanY - y);

        if (distanceX > distanceY) { // Prioritize horizontal movement if Pacman is farther away horizontally
            if (pacmanX > x) {
                wantedDirX = 1;
            } else {
                wantedDirX = -1;
            }
            wantedDirY = 0;
        } else {
            wantedDirX = 0;
            if (pacmanY > y) {
                wantedDirY = 1;
            } else {
                wantedDirY = -1;
            }
        }

        double nextX = x + wantedDirX * speed;
        double nextY = y + wantedDirY * speed;

        if (!isWall(nextX, nextY)) {
            dirX = wantedDirX;
            dirY = wantedDirY;
        }
    }

<<<<<<< HEAD
    // Move ghost away from Pacman when scared
    private void fleePacman(double pacmanX, double pacmanY) {
        int wantedDirX = 0;
        int wantedDirY = 0;

        double distanceX = Math.abs(pacmanX - x);
        double distanceY = Math.abs(pacmanY - y);

        if (distanceX > distanceY) {
            if (pacmanX > x) {
                wantedDirX = -1;
            } else {
                wantedDirX = 1;
            }
            wantedDirY = 0;
        } else {
            wantedDirX = 0;
            if (pacmanY > y) {
                wantedDirY = -1;
            } else {
                wantedDirY = 1;
            }
        }

        double nextX = x + wantedDirX * speed;
        double nextY = y + wantedDirY * speed;

        if (!isWall(nextX, nextY)) {
            dirX = wantedDirX;
            dirY = wantedDirY;
        } else {
            pickRandomDirection(); // If the desired direction is blocked, pick a random direction to flee
        }
=======
    // Bevæger ghost væk fra Pacman og finder altid en fri retning
    private void fleePacman(double pacmanX, double pacmanY) {
        if (!isWall(x + speed, y))  { dirX =  1; dirY =  0; }
        else if (!isWall(x - speed, y))  { dirX = -1; dirY =  0; }
        else if (!isWall(x, y - speed))  { dirX =  0; dirY = -1; }
        else if (!isWall(x, y + speed))  { dirX =  0; dirY =  1; }
>>>>>>> main
    }

    private boolean isWall(double newX, double newY) {
        int tileX = (int) (newX / Maze.TILE_SIZE);
        int tileY = (int) (newY / Maze.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileY >= Maze.TILE_MAP.length || tileX >= Maze.TILE_MAP[tileY].length()) {
            return true;
        }

        // Allow ghosts to pass through the exit area of the ghost box without treating
        // it as a wall
        if (exited && tileY >= 7 && tileY <= 12 && tileX >= 26 && tileX <= 35) {
            return true;
        }

        char tile = Maze.TILE_MAP[tileY].charAt(tileX);

<<<<<<< HEAD
        return tile == 'x' || tile == 'O'; // Treat 'O' as a wall for ghosts to prevent them from getting stuck in the
                                           // box
    }

    // Called when Pacman eats a ghost in POWER state
=======
        return tile == 'x' || tile == 'O';
    }

    // Kaldes når Pacman spiser ghost i POWER state
>>>>>>> main
    public void getEaten() {
        eaten = true;
        eatenTime = System.nanoTime();
        x = startX;
        y = startY;
        dirX = 0;
        dirY = -1;
    }

    public boolean isEaten() {
        return eaten;
    }

    public boolean isScared() {
        return scared;
    }

    public void setScared(boolean scared) {
        this.scared = scared;
    }

    public void setRandomDirection() {
        pickRandomDirection();
    }

    private void pickRandomDirection() { // Pick a random direction to move when hitting a wall
        int random = (int) (Math.random() * 4);
<<<<<<< HEAD
        switch (random) {
            case 0 -> {
                dirX = 1;
                dirY = 0;
            }
            case 1 -> {
                dirX = -1;
                dirY = 0;
            }
            case 2 -> {
                dirX = 0;
                dirY = -1;
            }
            default -> {
                dirX = 0;
                dirY = 1;
            }
=======

        if (random == 0) { dirX =  1; dirY =  0; }
        if (random == 1) { dirX = -1; dirY =  0; }
        if (random == 2) { dirX =  0; dirY = -1; }
        if (random == 3) { dirX =  0; dirY =  1; }

        // Hvis den valgte retning er en væg, prøv ned i stedet
        if (isWall(x + dirX * speed, y + dirY * speed)) {
            dirX = 0;
            dirY = 1;
>>>>>>> main
        }
    }

    public void reset() {
        this.x = startX;
        this.y = startY;
        this.dirX = 0;
        this.dirY = -1;
        this.exited = false;
        this.startTime = -1;
<<<<<<< HEAD
        this.eaten = false;
        this.scared = false;
=======
        this.eaten     = false;
        this.scared    = false;
>>>>>>> main
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}