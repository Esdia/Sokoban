package esdia.sokoban.controller;

import esdia.sokoban.model.Game;
import esdia.sokoban.model.Movement;
import esdia.sokoban.sequences.Iterator;
import esdia.sokoban.sequences.Sequence;
import esdia.sokoban.view.MainWindow;

public class TranslationAnimation extends Animation {
    private final Game game;

    private final Sequence<Movement> movements;

    /* The number of pixels the object moves each frame */
    private final int directionI;
    private final int directionJ;

    private double drawnFrames;
    private final double nbFrames;

    TranslationAnimation(MainWindow window, Game game, Sequence<Movement> movements) {
        super(window);

        this.game = game;

        this.movements = movements;

        Iterator<Movement> it = movements.iterator();

        if (!it.hasNext()) {
            throw new IllegalStateException("Cannot animate an empty movement sequence");
        }

        Movement movement = it.next();

        /* For now, an animation last 5 frames */
        this.nbFrames = 5;
        this.drawnFrames = 0;

        this.directionI = movement.getiDest() - movement.getiStart();
        this.directionJ = movement.getjDest() - movement.getjStart();

        this.window.setTranslatingObjects(this.movements);
    }

    @Override
    void prepareNextFrame() {
        if (this.drawnFrames == this.nbFrames) {
            return;
        }

        this.drawnFrames++;

        this.window.setAnimationOffset(
                directionI * (int) (drawnFrames / nbFrames * this.window.getImgSize()),
                directionJ * (int) (drawnFrames / nbFrames * this.window.getImgSize())
        );
    }

    @Override
    void afterComplete() {
        this.window.deleteTranslatingObjects();
        this.window.setAnimationOffset(0, 0);
        this.game.applyMovements(this.movements);
    }

    @Override
    boolean isComplete() {
        return this.drawnFrames == this.nbFrames;
    }

}
