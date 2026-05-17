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
    private boolean eaten = false;
    private long eatenTime = 0;

    public Ghost(int startCol) {
        this.startX = startCol * Maze.TILE_SIZE;
        this.startY = GHOST_START_ROW * Maze.TILE_SIZE;
        this.x = startX;
        this.y = startY;
    }

    public void update(double pacmanX, double pacmanY) {
        // vent inde i boksen i 3 sekunder efter at være spist
        if (eaten) {
            if (System.nanoTime() - eatenTime >= 3_000_000_000L) {
                eaten = false;
                exited = false;
                startTime = -1;
            }
            return;
        }

        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }

        if (!exited && y <= 7 * Maze.TILE_SIZE) {
            exited = true;
        }

        if (!exited) {
            dirX = 0;
            dirY = -1;
        } else if (scared) {
            fleePacman(pacmanX, pacmanY); // løb væk hvis scared
        } else if ((System.currentTimeMillis() - startTime) / 1000.0 >= 10) {
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

    private void chasePacman(double pacmanX, double pacmanY) { // Move ghost towards Pacman
        int wantedDirX = 0;
        int wantedDirY = 0;

        double distanceX = Math.abs(pacmanX - x);
        double distanceY = Math.abs(pacmanY - y);

        if (distanceX > distanceY) {
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

    // bevæg ghost væk fra Pacman (modsat retning af chase)
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
            pickRandomDirection(); // vælg tilfældig retning hvis vej er blokeret
        }
    }

    private boolean isWall(double newX, double newY) {
        int tileX = (int) (newX / Maze.TILE_SIZE);
        int tileY = (int) (newY / Maze.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileY >= Maze.TILE_MAP.length || tileX >= Maze.TILE_MAP[tileY].length()) {
            return true;
        }

        // Hvis ghost er ude af boksen, må den ikke komme ind igen
        if (exited && tileY >= 7 && tileY <= 12 && tileX >= 26 && tileX <= 35) {
            return true;
        }

        char tile = Maze.TILE_MAP[tileY].charAt(tileX);

        // 'x' er væg, 'O' er power pellet og behandles også som væg for ghosts
        return tile == 'x' || tile == 'O';
    }

    // kaldes når Pacman spiser ghost i POWER state
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

    private void pickRandomDirection() {
        int random = (int) (Math.random() * 4);
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
        }
    }

    public void reset() {
        this.x = startX;
        this.y = startY;
        this.dirX = 0;
        this.dirY = -1;
        this.exited = false;
        this.startTime = -1;
        this.eaten = false;
        this.scared = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}