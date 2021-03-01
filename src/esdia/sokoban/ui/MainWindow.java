package esdia.sokoban.ui;

import esdia.sokoban.game.Game;
import esdia.sokoban.global.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

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
        LevelUI l = new LevelUI(this.game);

        frame.add(l);
        l.addMouseListener(new Mouse(l));

        frame.setTitle("Sokoban - Level " + this.game.getCurrentLevel().name());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(new Keyboard(this));

        frame.setSize(1280, 720);
        frame.setVisible(true);
    }

    public void toggleFullscreen() {
        Configuration.instance().get_logger().info(
                "Toggling fullscreen"
        );

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

    public void shutdown() {
        this.game.closeReader();

        Configuration.instance().get_logger().info(
                "Closing the window"
        );
        this.frame.dispatchEvent(
                new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING)
        );
    }

    public static void start(Game game) {
        SwingUtilities.invokeLater(new MainWindow(game));
    }
}
