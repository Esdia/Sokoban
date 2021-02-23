package esdia.sokoban;

public class Game {
    private Level currentLevel = null;
    private final LevelReader reader;

    public Game(LevelReader reader) {
        this.reader = reader;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public boolean nextLevel() {
        this.currentLevel = this.reader.read();
        return this.currentLevel != null;
    }
}
