package esdia.sokoban.ui;

import esdia.sokoban.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainWindow implements Runnable {
    Game game;
    JFrame frame;
    boolean maximized = false;

    private MainWindow(Game game) {
        this.game = game;
        this.frame = new JFrame();
    }

    @Override
    public void run() {
        frame.add(new LevelUI(this.game));

        frame.setTitle("Sokoban - Level " + this.game.getCurrentLevel().name());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getRootPane().registerKeyboardAction(actionEvent -> {
            if (game.nextLevel()) {
                frame.setTitle("Sokoban - Level " + this.game.getCurrentLevel().name());
                frame.repaint();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        frame.getRootPane().registerKeyboardAction(
                actionEvent -> toggleFullscreen(), KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        frame.setSize(1300, 1000);
        frame.setVisible(true);
    }

    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(this.frame);
            maximized = true;
        }
    }

    public static void start(Game game) {
        SwingUtilities.invokeLater(new MainWindow(game));
    }
}
