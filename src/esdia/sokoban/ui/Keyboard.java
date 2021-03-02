package esdia.sokoban.ui;

import esdia.sokoban.game.Level;
import esdia.sokoban.global.Configuration;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    MainWindow window;
    LevelUI levelUI;

    public Keyboard(MainWindow window, LevelUI levelUI) {
        this.window = window;
        this.levelUI = levelUI;
    }

    private void log(int code) {
        Configuration.instance().get_logger().info(
                "Key pressed : " + code
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.log(e.getKeyCode());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER -> {
                Configuration.instance().get_logger().info(
                        "Skipping level"
                );
                if (this.window.game.nextLevel()) {
                    this.window.frame.setTitle("Sokoban - Level " + this.window.game.getCurrentLevel().name());
                    this.window.frame.repaint();
                } else {
                    Configuration.instance().get_logger().info(
                            "No next level : shutting down"
                    );
                    this.window.shutdown();
                }
            }

            case KeyEvent.VK_F11 -> this.window.toggleFullscreen();

            case KeyEvent.VK_UP -> levelUI.moveUp();
            case KeyEvent.VK_DOWN -> levelUI.moveDown();
            case KeyEvent.VK_LEFT -> levelUI.moveLeft();
            case KeyEvent.VK_RIGHT -> levelUI.moveRight();
        }
    }
}
