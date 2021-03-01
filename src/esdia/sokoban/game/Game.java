package esdia.sokoban.game;

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
}
