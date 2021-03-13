package esdia.sokoban.controller;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.model.Game;
import esdia.sokoban.view.EventListener;

import java.awt.event.KeyEvent;

public class GameController extends EventListener {

    public GameController(Game game) {
        super(game);
    }

    public void afterMove() {
        if (this.game.isComplete()) {
            this.loadNextLevel();
        }
        this.window.repaint();
    }

    @Override
    public void mouseClick(int x, int y) {
        Configuration.instance().get_logger().info(
                "Mouse click on (" + x + ", " + y + ")"
        );

        x = this.window.coordToIndexX(x);
        y = this.window.coordToIndexY(y);

        this.game.moveTo(x, y);

        this.afterMove();
    }

    @Override
    public void keyInput(int keyCode) {
        Configuration.instance().get_logger().info(
                "Key pressed : " + KeyEvent.getKeyText(keyCode)
        );

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> this.skipLevel();

            case KeyEvent.VK_F11 -> this.window.toggleFullscreen();

            case KeyEvent.VK_UP, KeyEvent.VK_Z -> this.game.moveUp();
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> this.game.moveDown();
            case KeyEvent.VK_LEFT, KeyEvent.VK_Q -> this.game.moveLeft();
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.game.moveRight();

            case KeyEvent.VK_ESCAPE -> this.window.shutdown();
        }

        this.afterMove();
    }
}
