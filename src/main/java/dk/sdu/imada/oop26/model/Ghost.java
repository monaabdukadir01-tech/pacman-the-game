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
    private int dirY = 0;

    public Ghost(int startCol) {
        this.startX = startCol * Maze.TILE_SIZE;
        this.startY = GHOST_START_ROW * Maze.TILE_SIZE;
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        double newX = x + dirX * speed;
        double newY = y + dirY * speed;

        if (!isWall(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            pickRandomDirection();
        }
    }

    private boolean isWall(double newX, double newY) {
        int tileX = (int) (newX / Maze.TILE_SIZE);
        int tileY = (int) (newY / Maze.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileY >= Maze.TILE_MAP.length || tileX >= Maze.TILE_MAP[tileY].length()) {
            return true; // Behandl out-of-bounds som væg
        }

        return Maze.TILE_MAP[tileY].charAt(tileX) == 'x';
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
        this.dirY = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
