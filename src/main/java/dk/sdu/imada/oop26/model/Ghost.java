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

    public Ghost(int startCol) {
        this.startX = startCol * Maze.TILE_SIZE;
        this.startY = GHOST_START_ROW * Maze.TILE_SIZE;
        this.x = startX;
        this.y = startY;
    }

    public void update(double pacmanX, double pacmanY) {
        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }

        if (!exited && y <= 7 * Maze.TILE_SIZE) {
            exited = true;
        }

        if (!exited) {
            dirX = 0;
            dirY = -1;
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

    private void chasePacman(double pacmanX, double pacmanY) {
        // Beregn hvilken retning ghost skal gå mod Pacman
        int wantedDirX = 0;
        int wantedDirY = 0;

        // Tjek om Pacman er længere væk vandret eller lodret
        double distanceX = Math.abs(pacmanX - x);
        double distanceY = Math.abs(pacmanY - y);

        if (distanceX > distanceY) {
            // Pacman er længere væk vandret - gå til højre eller venstre
            if (pacmanX > x) {
                wantedDirX = 1;  // Pacman er til højre
            } else {
                wantedDirX = -1; // Pacman er til venstre
            }
            wantedDirY = 0;
        } else {
            // Pacman er længere væk lodret - gå op eller ned
            wantedDirX = 0;
            if (pacmanY > y) {
                wantedDirY = 1;  // Pacman er nedenfor
            } else {
                wantedDirY = -1; // Pacman er ovenfor
            }
        }

        // Tjek om den ønskede retning er fri for væg
        double nextX = x + wantedDirX * speed;
        double nextY = y + wantedDirY * speed;

        if (!isWall(nextX, nextY)) {
            // Vejen er fri - gå mod Pacman
            dirX = wantedDirX;
            dirY = wantedDirY;
        }
        // Hvis vejen er blokeret - behold nuværende retning
        // og lad pickRandomDirection() håndtere det når ghost rammer væggen
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
        this.dirY = -1;
        this.exited = false;
        this.startTime = -1;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}