package esdia.sokoban;

public class Level {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int PLAYER = 2;
    public static final int BOX = 3;
    public static final int GOAL = 4;
    public static final int PLAYER_ON_GOAL = 5;
    public static final int BOX_ON_GOAL = 6;

    private int[][] grid;
    private int l, c;

    private String name;

    public Level() {
        this.l = 0;
        this.c = 0;
        this.name = null;
        this.grid = new int[0][0];
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

    void emptySquare(int i, int j) { this.grid[i][j] = EMPTY; }
    void addWall(int i, int j) { this.grid[i][j] = WALL; }
    void addPlayer(int i, int j) { this.grid[i][j] = PLAYER; }
    void addBox(int i, int j) { this.grid[i][j] = BOX; }
    void addGoal(int i, int j) { this.grid[i][j] = GOAL; }
    void addPlayerOnGoal(int i, int j) { this.grid[i][j] = PLAYER_ON_GOAL; }
    void addBoxOnGoal(int i, int j) { this.grid[i][j] = BOX_ON_GOAL; }

    int lines() { return this.l; }
    int columns() { return this.c; }
    String name() { return this.name; }

    boolean isEmpty(int i, int j) { return this.grid[i][j] == EMPTY; }
    boolean isWall(int i, int j) { return this.grid[i][j] == WALL; }
    boolean isPlayer(int i, int j) { return this.grid[i][j] == PLAYER; }
    boolean isBox(int i, int j) { return this.grid[i][j] == BOX; }
    boolean isGoal(int i, int j) { return this.grid[i][j] == GOAL; }
    boolean isPlayerOnGoal(int i, int j) { return this.grid[i][j] == PLAYER_ON_GOAL; }
    boolean isBoxOnGoal(int i, int j) { return this.grid[i][j] == BOX_ON_GOAL; }

    void print() {
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
                System.out.print(c);
            }
            System.out.println();
        }
        if (name() != null) {
            System.out.println("; " + name());
        }
    }
}
