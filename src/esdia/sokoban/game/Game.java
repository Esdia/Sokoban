package esdia.sokoban.game;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.ui.LevelUI;

public class Game {
    private Level currentLevel = null;
    private final LevelReader reader;

    private LevelUI levelUI = null;

    public Game(LevelReader reader) {
        this.reader = reader;
    }

    public void addLevelUi(LevelUI levelUI) {
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

    public void moveUp() {
        this.getCurrentLevel().moveUp();
        this.levelUI.repaint();
    }
    public void moveDown() {
        this.getCurrentLevel().moveDown();
        this.levelUI.repaint();
    }
    public void moveLeft() {
        this.getCurrentLevel().moveLeft();
        this.levelUI.repaint();
    }
    public void moveRight() {
        this.getCurrentLevel().moveRight();
        this.levelUI.repaint();
    }

    public void moveTo(int x, int y) {
        x = this.levelUI.coordToIndexX(x);
        y = this.levelUI.coordToIndexY(y);

        this.getCurrentLevel().moveClick(x, y);
        this.levelUI.repaint();
    }
}
