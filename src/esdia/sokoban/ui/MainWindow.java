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
        LevelUI l = new LevelUI(this.game);

        frame.add(l);
        l.addMouseListener(new SokobanMouse(l));

        frame.setTitle("Sokoban - Level " + this.game.getCurrentLevel().name());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(new Keyboard(this));

        frame.setSize(1280, 720);
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
