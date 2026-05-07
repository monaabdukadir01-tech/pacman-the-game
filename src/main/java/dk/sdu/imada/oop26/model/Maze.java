package dk.sdu.imada.oop26.model;

public class Maze {

    public static final int TILE_SIZE = 24;
    public static final int COLS = 60;
    public static final int ROWS = 17;

    public static final String[] TILE_MAP = {
        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
        "x                                                          x",
        "x     x       xxxxx       xxxxxxxx      xxxxx         x    x",
        "xxxx                                                    xxxx",
        "x00x     x       x   xxx            xxx   xx   xx       x00x",
        "xxxx     x       x                       x   x    x     xxxx",
        "x        x       x   x                x  x   x    x        x",
        "x   xxx  x  x    x   x  xxxxOOOOxxxx  x  x   x    x  xxx   x",
        "O   xxx  x  x    x   x  xOOOOOOOOOOx  x  x   x    x  xxx   O",
        "x        x  x    x   x   xxxxxxxxxx   x  x        x        x",
        "x        x       x   x                x  x   xx   x        x",
        "xxxx      x     x    x                x  x   xx   x     xxxx",
        "x00x        x x      x   xxxxxxxxxx   x  x        x     x00x",
        "xxxx         x               x                          xxxx",
        "x                            x                             x",
        "x         xxxxx            x O x            xxxxx          x",
        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
    };

    public static boolean isWall(int row, int col) {
        return TILE_MAP[row].charAt(col) == 'x';
    }
}
