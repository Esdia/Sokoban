package esdia.sokoban.game;

public class Level {
    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int PLAYER = 2;
    private static final int BOX = 3;
    private static final int GOAL = 4;
    private static final int PLAYER_ON_GOAL = 5;
    private static final int BOX_ON_GOAL = 6;

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

    public int lines() { return this.l; }
    public int columns() { return this.c; }
    public String name() { return this.name; }

    public boolean isEmpty(int i, int j) { return this.grid[i][j] == EMPTY; }
    public boolean isWall(int i, int j) { return this.grid[i][j] == WALL; }
    public boolean isPlayer(int i, int j) { return this.grid[i][j] == PLAYER; }
    public boolean isBox(int i, int j) { return this.grid[i][j] == BOX; }
    public boolean isGoal(int i, int j) { return this.grid[i][j] == GOAL; }
    public boolean isPlayerOnGoal(int i, int j) { return this.grid[i][j] == PLAYER_ON_GOAL; }
    public boolean isBoxOnGoal(int i, int j) { return this.grid[i][j] == BOX_ON_GOAL; }

    public void print() {
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
