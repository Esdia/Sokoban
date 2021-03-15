package esdia.sokoban.model;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.sequences.Iterator;
import esdia.sokoban.sequences.Sequence;

class Level {
    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int PLAYER = 2;
    private static final int BOX = 3;
    private static final int GOAL = 4;
    private static final int PLAYER_ON_GOAL = 5;
    private static final int BOX_ON_GOAL = 6;

    // The player's coordinates
    private int playerI;
    private int playerJ;

    private int nbGoals;
    private int nbBoxesOnGoal;

    private int[][] grid;
    private int l, c;

    private String name;

    Level() {
        this.l = 0;
        this.c = 0;
        this.name = null;
        this.grid = new int[0][0];
        this.nbGoals = 0;
        this.nbBoxesOnGoal = 0;
    }

    void setNbGoals(int nbGoals) {
        this.nbGoals = nbGoals;
    }

    void resize_grid(int new_l, int new_c) {
        if(new_l < this.l) new_l = this.l;
        if(new_c < this.c) new_c = this.c;

        int[][] new_grid = new int[new_l][new_c];

        for (int i = 0; i < this.l; i++) {
            System.arraycopy(this.grid[i], 0, new_grid[i], 0, this.c);
        }

        this.l = new_l;
        this.c = new_c;
        this.grid = new_grid;
    }

    void setName(String s) { this.name = s; }

    private void setPlayerCoords(int i, int j) {
        this.playerI = i;
        this.playerJ = j;
    }

    void emptySquare(int i, int j) { this.grid[i][j] = EMPTY; }
    void addWall(int i, int j) { this.grid[i][j] = WALL; }
    void addPlayer(int i, int j) { this.grid[i][j] = PLAYER; this.setPlayerCoords(i, j); }
    void addBox(int i, int j) { this.grid[i][j] = BOX; }
    void addGoal(int i, int j) { this.grid[i][j] = GOAL; }
    void addPlayerOnGoal(int i, int j) { this.grid[i][j] = PLAYER_ON_GOAL; this.setPlayerCoords(i, j); }
    void addBoxOnGoal(int i, int j) { this.grid[i][j] = BOX_ON_GOAL; this.nbBoxesOnGoal++; }

    private void leaveSquare(int i, int j) {
        if (this.isBoxOnGoal(i, j)) {
            this.nbBoxesOnGoal--;
            this.addGoal(i, j);
        } else if (this.isPlayerOnGoal(i, j)) {
            this.addGoal(i, j);
        } else {
            this.emptySquare(i, j);
        }
    }

    public int lines() { return this.l; }
    public int columns() { return this.c; }
    public String name() { return this.name; }

    boolean isEmpty(int i, int j) { return this.grid[i][j] == EMPTY; }
    boolean isWall(int i, int j) { return this.grid[i][j] == WALL; }
    boolean isPlayer(int i, int j) { return this.grid[i][j] == PLAYER; }
    boolean isBox(int i, int j) { return this.grid[i][j] == BOX; }
    boolean isGoal(int i, int j) { return this.grid[i][j] == GOAL; }
    boolean isPlayerOnGoal(int i, int j) { return this.grid[i][j] == PLAYER_ON_GOAL; }
    boolean isBoxOnGoal(int i, int j) { return this.grid[i][j] == BOX_ON_GOAL; }

    boolean isComplete() {
        return this.nbBoxesOnGoal == this.nbGoals;
    }

    private boolean isNotInGrid(int i, int j) {
        return 0 > i || i >= this.lines() || 0 > j || j >= this.columns();
    }

    private boolean isNextToPlayer(int i, int j) {
        if (Math.abs(i - this.playerI) == 1 && j == this.playerJ) {
            return true;
        }

        return i == this.playerI && Math.abs(j - this.playerJ) == 1;
    }

    /* Given a destination tile, this method returns
     * the direction the player has to move to reach it.
     * This method requires the player to be right next to the (i, j) tile :
     *
     * XOX
     * OPO
     * XOX
     *
     * If P represents the tile the player is on, this method returns a valid
     * direction if and only if (i, j) is a tile marked by an 'O'
     */
    private Direction getMovingDirection(int i, int j) {
        if (!this.isNextToPlayer(i, j)) {
            return Direction.UNDEFINED;
        }

        if (i == this.playerI + 1) {
            return Direction.DOWN;
        } else if (i == this.playerI - 1) {
            return Direction.UP;
        } else if (j == this.playerJ + 1) {
            return Direction.RIGHT;
        } else if (j == this.playerJ - 1) {
            return Direction.LEFT;
        }

        /* We should never get there because if isNextToPlayer returns true,
         * we should be entering one if.
         */
        return Direction.UNDEFINED;
    }

    private boolean canMoveBoxTo(int i, int j) {
        if (this.isNotInGrid(i, j)) {
            return false;
        }

        return this.isEmpty(i, j) || this.isGoal(i, j);

    }

    private boolean canMoveTo(int i, int j) {
        if (this.isNotInGrid(i, j)) {
            return false;
        }

        return this.isEmpty(i, j) || this.isGoal(i, j) || this.isBox(i, j) || this.isBoxOnGoal(i, j);
    }

    /* Applies a movement that we know legal */
    private void moveBox(Movement movement) {
        if (this.isGoal(movement.getiDest(), movement.getjDest())) {
            this.addBoxOnGoal(movement.getiDest(), movement.getjDest());
        } else {
            this.addBox(movement.getiDest(), movement.getjDest());
        }

        this.leaveSquare(movement.getiStart(), movement.getjStart());
    }

    /* Applies a movement that we know legal */
    private void movePlayer(Movement movement) {
        if (this.isGoal(movement.getiDest(), movement.getjDest())) {
            this.addPlayerOnGoal(movement.getiDest(), movement.getjDest());
        } else {
            this.addPlayer(movement.getiDest(), movement.getjDest());
        }

        this.leaveSquare(movement.getiStart(), movement.getjStart());
    }

    void applyMovement(Movement movement) {
        if (movement.isBox()) {
            this.moveBox(movement);
        } else {
            this.movePlayer(movement);
        }
    }

    /*
    * WARNING this method does not check whether the movements are legal or not. It supposes they are
    * movements[0] = player, movements[1] = box (if it exists)
    */
    void applyMovements(Sequence<Movement> movements) {
        Iterator<Movement> it = movements.iterator();
        while (it.hasNext()) {
            this.applyMovement(it.next());
        }
    }

    private Movement getBoxMovement(int iBox, int jBox, Direction direction) {
        Movement movement = new Movement(iBox, jBox, direction, true);

        if (!this.canMoveBoxTo(movement.getiDest(), movement.getjDest())) {
            return null;
        }

        return movement;
    }

    private Movement getPlayerMovement(Direction direction) {
        Movement movement = new Movement(this.playerI, this.playerJ, direction);

        if (!this.canMoveTo(movement.getiDest(), movement.getjDest())) {
            return null;
        }

        return movement;
    }

    Sequence<Movement> getMovements(Direction direction) {
        Sequence<Movement> movements = Configuration.instance().new_sequence();

        if (direction == Direction.UNDEFINED) {
            return movements;
        }

        Movement playerMovement = this.getPlayerMovement(direction);

        if (playerMovement == null) {
            return movements;
        }

        if (this.isBox(playerMovement.getiDest(), playerMovement.getjDest()) || this.isBoxOnGoal(playerMovement.getiDest(), playerMovement.getjDest())) {
            Movement boxMovement = this.getBoxMovement(playerMovement.getiDest(), playerMovement.getjDest(), direction);
            if (boxMovement == null) {
                return movements;
            }

            movements.insertHead(boxMovement);
        }

        movements.insertTail(playerMovement);

        return movements;
    }

    Sequence<Movement> getMovementByClick(int i, int j) {
        return this.getMovements(this.getMovingDirection(i, j));
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        char c;

        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                switch (this.grid[i][j]) {
                    case WALL -> c = '#';
                    case PLAYER -> c = '@';
                    case BOX -> c = '$';
                    case GOAL -> c = '.';
                    case PLAYER_ON_GOAL -> c = '+';
                    case BOX_ON_GOAL -> c = '*';
                    default -> c = ' ';
                }
                s.append(c);
            }
            s.append('\n');
        }

        return s.toString();
    }

    @SuppressWarnings("unused")
    public void print() {
        System.out.println(this.toString());
    }
}
