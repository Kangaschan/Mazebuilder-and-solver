import java.util.Random;

public class Tile {
    private final int FLOOR2 =20;
    private final int FLOOR3 = 30;
    private final int TILE =0;
    private final int PATH_TILE = -1;
    private final int LEDDER = 1;
    private final int ELEVETOR = 2;
    private final int BED = 3;
    private final int EXIT = 4;
    private final int LAB = 5;
    private final int MED = 6;
    int tiletype;
    int length;
    boolean lWall;
    boolean rWall;
    boolean tWall;
    boolean bWall;

    public Tile(){
        lWall = true;
        rWall = true;
        tWall = true;
        bWall = true;
        tiletype = TILE;
        length = -5;
    }

}
