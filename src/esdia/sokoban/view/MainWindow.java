package esdia.sokoban.view;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.model.Direction;
import esdia.sokoban.model.Game;
import esdia.sokoban.model.Movement;
import esdia.sokoban.sequences.Sequence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainWindow implements Runnable {
    private final Game game;
    private final JFrame frame;
    private final LevelUI levelUI;
    private final EventListener controller;

    boolean maximized = false;

    private MainWindow(Game game, EventListener controller) {
        this.game = game;
        this.levelUI = new LevelUI(this.game);
        this.frame = new JFrame();
        this.controller = controller;
    }

    public void refreshFrameTitle() {
        this.frame.setTitle("Sokoban - Level " + this.game.name());
    }

    @Override
    public void run() {
        frame.add(this.levelUI);

        this.refreshFrameTitle();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.levelUI.addMouseListener(this.controller);
        this.frame.addKeyListener(this.controller);

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

    public int coordToIndexX(int x) {
        return this.levelUI.coordToIndexX(x);
    }

    public int coordToIndexY(int y) {
        return this.levelUI.coordToIndexY(y);
    }

    public int getImgSize() {
        return this.levelUI.getImgSize();
    }

    public void setAnimationOffset(int offsetI, int offsetJ) {
        this.levelUI.setAnimationOffset(offsetI, offsetJ);
    }

    public void setTranslatingObjects(Sequence<Movement> movements) {
        this.levelUI.setTranslatingObjects(movements);
    }

    public void deleteTranslatingObjects() {
        this.levelUI.deleteTranslatingObjects();
    }

    public void setFacingDirection(Direction direction) {
        this.levelUI.setFacingDirection(direction);
    }

    public void nextWalkingFrame() {
        this.levelUI.nextWalkingFrame();
    }

    public void repaint() {
        this.refreshFrameTitle();
        this.levelUI.repaint();
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

    public static void start(Game game, EventListener gameController) {
        MainWindow window = new MainWindow(game, gameController);
        gameController.setWindow(window);
        gameController.animationLoop();
        SwingUtilities.invokeLater(window);
    }
}
