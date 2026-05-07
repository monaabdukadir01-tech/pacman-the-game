package dk.sdu.imada.oop26.model;

public class Pacman {

    public static final int SIZE = 24;

    private static final int START_COL = 29;
    private static final int START_ROW = 15;

    private double x;
    private double y;
    private final double startX;
    private final double startY;

    private int speed = 3;
    private int dirX = 0;
    private int dirY = 0;

    public Pacman() {
        this.startX = START_COL * Maze.TILE_SIZE;
        this.startY = START_ROW * Maze.TILE_SIZE;
        this.x = startX;
        this.y = startY;
    }

    public void reset() {
        this.x = startX;
        this.y = startY;
        this.dirX = 0;
        this.dirY = 0;
    }

    public void moveLeft()  { dirX = -1; dirY =  0; }
    public void moveRight() { dirX =  1; dirY =  0; }
    public void moveUp()    { dirX =  0; dirY = -1; }
    public void moveDown()  { dirX =  0; dirY =  1; }

    public void setVelocityX(int dirX) { this.dirX = dirX; }
    public void setVelocityY(int dirY) { this.dirY = dirY; }

    public void update() {
        double newX = x + dirX * speed;
        double newY = y + dirY * speed;

        if (canMoveTo(newX, y)) x = newX;
        if (canMoveTo(x, newY)) y = newY;
    }

    private boolean canMoveTo(double newX, double newY) {
        int tileSize = Maze.TILE_SIZE;

        int topLeftRow     = (int) (newY / tileSize);
        int topLeftCol     = (int) (newX / tileSize);
        int bottomRightRow = (int) ((newY + SIZE - 1) / tileSize);
        int bottomRightCol = (int) ((newX + SIZE - 1) / tileSize);

        boolean topLeftIsWall     = Maze.TILE_MAP[topLeftRow].charAt(topLeftCol) == 'x';
        boolean bottomRightIsWall = Maze.TILE_MAP[bottomRightRow].charAt(bottomRightCol) == 'x';

        return !topLeftIsWall && !bottomRightIsWall;
    }

    public int getDirX() { return dirX; }
    public int getDirY() { return dirY; }
    public double getX() { return x; }
    public double getY() { return y; }
}
