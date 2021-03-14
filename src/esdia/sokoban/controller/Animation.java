package esdia.sokoban.controller;

import esdia.sokoban.model.Game;
import esdia.sokoban.model.Movement;
import esdia.sokoban.sequences.Iterator;
import esdia.sokoban.sequences.Sequence;
import esdia.sokoban.view.LevelUI;
import esdia.sokoban.view.MainWindow;

public class Animation {
    Sequence<Movement> movements;
    Movement playerMovement;
    Movement boxMovement;

    /* The number of pixels the object moves each frame */
    int offsetI;
    int offsetJ;

    MainWindow window;
    Game game;

    double drawnFrames;
    double nbFrames;

    public Animation(MainWindow window, Game game, Sequence<Movement> movements) {
        this.window = window;
        this.game = game;

        this.movements = movements;

        Iterator<Movement> it = movements.iterator();

        if (!it.hasNext()) {
            throw new IllegalStateException("Cannot animate an empty movement sequence");
        }

        this.playerMovement = it.next();
        if (it.hasNext()) {
            this.boxMovement = this.playerMovement;
            this.playerMovement = it.next();
            this.window.getLevelUI().setMovingBox(this.boxMovement.getiStart(), this.boxMovement.getjStart());
        } else {
            this.boxMovement = null;
        }

        /* For now, an animation last 10 frames */
        this.nbFrames = 5;
        this.drawnFrames = 0;

        this.setOffsets();
    }

    private void setOffsets() {
        this.offsetI = this.playerMovement.getiDest() - this.playerMovement.getiStart();
        this.offsetJ = this.playerMovement.getjDest() - this.playerMovement.getjStart();
    }

    public void prepareNextFrame() {
        if (this.drawnFrames == this.nbFrames) {
            return;
        }

        this.drawnFrames++;

        LevelUI levelUI = this.window.getLevelUI();

        levelUI.setAnimationOffset(
                offsetI * (int) (drawnFrames / nbFrames * levelUI.getImgSize()),
                offsetJ * (int) (drawnFrames / nbFrames * levelUI.getImgSize())
        );
    }

    public void afterComplete() {
        LevelUI levelUI = this.window.getLevelUI();

        if (this.boxMovement != null) {
            levelUI.setMovingBox(-1, -1);
        }
        levelUI.setAnimationOffset(0, 0);
        this.game.applyMovements(this.movements);
    }

    public boolean isComplete() {
        return this.drawnFrames == this.nbFrames;
    }
}
