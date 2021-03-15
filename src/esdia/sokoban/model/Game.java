package esdia.sokoban.model;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.sequences.Sequence;

public class Game {
    private Level currentLevel = null;
    private final LevelReader reader;

    public Game(LevelReader reader) {
        this.reader = reader;
    }

    public boolean nextLevel() {
        Level tmp = this.reader.read();
        if (tmp == null) {
            return false;
        }
        this.currentLevel = tmp;

        Configuration.instance().get_logger().info(
                "Loading level : " + tmp.name() + "\n" + tmp.toString()
        );

        return true;
    }

    public void closeReader() {
        Configuration.instance().get_logger().info(
                "Closing the level reader"
        );
        this.reader.close();
    }

    public boolean isComplete() {
        return this.currentLevel.isComplete();
    }

    public boolean loadNextLevel() {
        Configuration.instance().get_logger().info(
                "Loading next level"
        );
        boolean hasNextLevel = this.nextLevel();
        if (! hasNextLevel) {
            Configuration.instance().get_logger().info(
                    "No next level"
            );
        }

        return hasNextLevel;
    }

    public boolean skipLevel() {
        Configuration.instance().get_logger().info(
                "Skipping level"
        );
        return this.loadNextLevel();
    }

    public void applyMovements(Sequence<Movement> movements) {
        this.currentLevel.applyMovements(movements);
    }

    public Sequence<Movement> getMovements(Direction direction) {
        return this.currentLevel.getMovements(direction);
    }

    public Sequence<Movement> getMovementsByClick(int i, int j) {
        return this.currentLevel.getMovementByClick(i, j);
    }

    public String name() { return this.currentLevel.name(); }

    public boolean isEmpty(int i, int j) { return this.currentLevel.isEmpty(i, j); }
    public boolean isWall(int i, int j) { return this.currentLevel.isWall(i, j); }
    public boolean isPlayer(int i, int j) { return this.currentLevel.isPlayer(i, j); }
    public boolean isBox(int i, int j) { return this.currentLevel.isBox(i, j); }
    public boolean isGoal(int i, int j) { return this.currentLevel.isGoal(i, j); }
    public boolean isPlayerOnGoal(int i, int j) { return this.currentLevel.isPlayerOnGoal(i, j); }
    public boolean isBoxOnGoal(int i, int j) { return this.currentLevel.isBoxOnGoal(i, j); }

    public int lines() { return this.currentLevel.lines(); }
    public int columns() { return this.currentLevel.columns(); }
}
