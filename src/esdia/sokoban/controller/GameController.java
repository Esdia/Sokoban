package esdia.sokoban.controller;

import esdia.sokoban.global.Configuration;
import esdia.sokoban.model.Direction;
import esdia.sokoban.model.Game;
import esdia.sokoban.model.Movement;
import esdia.sokoban.sequences.Iterator;
import esdia.sokoban.sequences.Sequence;
import esdia.sokoban.view.EventListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameController extends EventListener {
    Sequence<Animation> animations;

    boolean isMoving;
    boolean isAnimated;

    public GameController(Game game) {
        super(game);
        this.isMoving = false;

        this.animations = Configuration.instance().new_sequence();
        this.isAnimated = false;
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
                    this.endOfMovingAnimation(anim);
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

    public void toggleAnimations() {
        this.isAnimated = !this.isAnimated;
    }

    public void endOfMovingAnimation(Animation animation) {
        animation.afterComplete();
        this.isMoving = false;
        this.afterMove();
    }

    public void afterMove() {
        if (this.game.isComplete()) {
            this.loadNextLevel();
        }
        this.window.repaint();
    }

    public void animate(Sequence<Movement> movements) {
        if (!movements.isEmpty()) {
            this.isMoving = true;
            Animation animation = new TranslationAnimation(this.window, this.game, movements);
            this.animations.insertHead(animation);
        }
    }

    public void applyMovements(Sequence<Movement> movements) {
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

    public void move(Direction direction) {
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
