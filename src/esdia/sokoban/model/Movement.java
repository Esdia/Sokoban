package esdia.sokoban.model;

public class Movement {
    private final int iStart;
    private final int jStart;

    private final int iDest;
    private final int jDest;

    private final Direction direction;

    private final boolean isBox;

    Movement(int iStart, int jStart, Direction direction, boolean isBox) {
        this.direction = direction;
        this.iStart = iStart;
        this.jStart = jStart;

        int offsetI = 0;
        int offsetJ = 0;

        switch (this.direction) {
            case UP -> offsetI = -1;
            case DOWN -> offsetI = 1;
            case LEFT -> offsetJ = -1;
            case RIGHT -> offsetJ = 1;
        }

        this.iDest = this.iStart + offsetI;
        this.jDest = this.jStart + offsetJ;

        this.isBox = isBox;
    }

    public Movement(int iStart, int jStart, Direction direction) {
        this(iStart, jStart, direction, false);
    }

    public int getiStart() {
        return iStart;
    }

    public int getjStart() {
        return jStart;
    }

    public int getiDest() {
        return iDest;
    }

    public int getjDest() {
        return jDest;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isBox() {
        return isBox;
    }
}
