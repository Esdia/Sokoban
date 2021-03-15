package esdia.sokoban.controller;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.model.Direction;
import esdia.sokoban.model.Game;
import esdia.sokoban.model.Movement;
import esdia.sokoban.sequences.Iterator;
import esdia.sokoban.sequences.Sequence;
import esdia.sokoban.view.EventListener;
import esdia.sokoban.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameController extends EventListener {
    private final Sequence<Animation> animations;

    private boolean isMoving;
    private boolean isAnimated;

    public GameController(Game game) {
        super(game);
        this.isMoving = false;

        this.animations = Configuration.instance().new_sequence();
        this.isAnimated = false;
    }

    @Override
    public void setWindow(MainWindow window) {
        super.setWindow(window);

        this.animations.insertHead(new PlayerAnimation(window));
    }

    @Override
    public void animationLoop() {
        this.isAnimated = true;
        ActionListener actionListener = actionEvent -> {
            if (!isAnimated) return;
            Iterator<Animation> it = animations.iterator();
            Animation anim;
            while (it.hasNext()) {
                anim = it.next();
                if (anim.isComplete()) {
                    if (anim instanceof TranslationAnimation) {
                        this.endOfMovingAnimation(anim);
                    } else {
                        anim.afterComplete();
                    }
                    it.delete();
                } else {
                    anim.prepareNextFrame();
                }
            }
            window.repaint();
        };

        Timer timer = new Timer(16, actionListener);
        timer.setRepeats(true);

        timer.start();
    }

    private void toggleAnimations() {
        this.isAnimated = !this.isAnimated;
    }

    private void endOfMovingAnimation(Animation animation) {
        animation.afterComplete();
        this.isMoving = false;
        this.afterMove();
    }

    private void afterMove() {
        if (this.game.isComplete()) {
            this.loadNextLevel();
        }
        this.window.repaint();
    }

    private void animate(Sequence<Movement> movements) {
        if (!movements.isEmpty()) {
            this.isMoving = true;
            Animation animation = new TranslationAnimation(this.window, this.game, movements);
            this.animations.insertHead(animation);
        }
    }

    private void applyMovements(Sequence<Movement> movements) {
        if (!movements.isEmpty() && !this.isMoving) {
            this.window.setFacingDirection(
                    movements.iterator().next().getDirection()
            );
        }

        if (this.isAnimated) {
            if (! this.isMoving) {
                this.animate(movements);
            }
        } else {
            this.game.applyMovements(movements);
            this.afterMove();
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        Configuration.instance().get_logger().info(
                "Mouse click on (" + x + ", " + y + ")"
        );
        if (this.isAnimated && this.isMoving) return;

        x = this.window.coordToIndexX(x);
        y = this.window.coordToIndexY(y);

        int i = y;
        int j = x;

        Sequence<Movement> movements = this.game.getMovementsByClick(i, j);

        this.applyMovements(movements);
    }

    private void move(Direction direction) {
        Sequence<Movement> movements = this.game.getMovements(direction);

        this.applyMovements(movements);
    }

    @Override
    public void keyInput(int keyCode) {
        Configuration.instance().get_logger().info(
                "Key pressed : " + KeyEvent.getKeyText(keyCode)
        );

        switch (keyCode) {
            case KeyEvent.VK_ENTER -> this.skipLevel();

            case KeyEvent.VK_F11 -> this.window.toggleFullscreen();

            case KeyEvent.VK_UP, KeyEvent.VK_Z -> this.move(Direction.UP);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> this.move(Direction.DOWN);
            case KeyEvent.VK_LEFT, KeyEvent.VK_Q -> this.move(Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.move(Direction.RIGHT);

            case KeyEvent.VK_P -> this.toggleAnimations();

            case KeyEvent.VK_ESCAPE -> this.window.shutdown();
        }
    }
}
