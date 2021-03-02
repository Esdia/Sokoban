package esdia.sokoban.game;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.ui.LevelUI;
import esdia.sokoban.ui.MainWindow;

public class Game {
    private Level currentLevel = null;
    private final LevelReader reader;

    private MainWindow window = null;
    private LevelUI levelUI = null;

    public Game(LevelReader reader) {
        this.reader = reader;
    }

    public void setupUI(MainWindow window, LevelUI levelUI) {
        this.window = window;
        this.levelUI = levelUI;
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

    public void loadNextLevel() {
        if (this.nextLevel()) {
            this.window.getFrame().setTitle("Sokoban - Level " + this.getCurrentLevel().name());
        } else {
            Configuration.instance().get_logger().info(
                    "No next level : shutting down"
            );
            this.window.shutdown();
        }
    }

    public void skipLevel() {
        Configuration.instance().get_logger().info(
                "Skipping level"
        );
        this.loadNextLevel();
        this.levelUI.repaint();
    }

    public void checkAfter() {
        if (this.getCurrentLevel().isComplete()) {
            this.loadNextLevel();
        }
        this.levelUI.repaint();
    }

    public void moveUp() {
        this.getCurrentLevel().moveUp();
        this.checkAfter();
    }
    public void moveDown() {
        this.getCurrentLevel().moveDown();
        this.checkAfter();
    }
    public void moveLeft() {
        this.getCurrentLevel().moveLeft();
        this.checkAfter();
    }
    public void moveRight() {
        this.getCurrentLevel().moveRight();
        this.checkAfter();
    }

    public void moveTo(int x, int y) {
        x = this.levelUI.coordToIndexX(x);
        y = this.levelUI.coordToIndexY(y);

        this.getCurrentLevel().moveClick(x, y);
        this.checkAfter();
    }
}
