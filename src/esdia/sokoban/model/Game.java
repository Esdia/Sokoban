package esdia.sokoban.model;

import esdia.sokoban.global.Configuration;

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
        return this.getCurrentLevel().isComplete();
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

    public void moveUp() {
        this.getCurrentLevel().moveUp();
    }
    public void moveDown() {
        this.getCurrentLevel().moveDown();
    }
    public void moveLeft() {
        this.getCurrentLevel().moveLeft();
    }
    public void moveRight() {
        this.getCurrentLevel().moveRight();
    }

    public void moveTo(int x, int y) {
        this.getCurrentLevel().moveClick(x, y);
    }
}
