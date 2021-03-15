package esdia.sokoban.controller;

import esdia.sokoban.view.MainWindow;

public class PlayerAnimation extends Animation {
    private int counter;

    PlayerAnimation(MainWindow window) {
        super(window);

        this.counter = 0;
    }

    @Override
    void prepareNextFrame() {
        this.counter++;
        if (this.counter == 15) {
            this.counter = 0;
            this.window.nextWalkingFrame();
        }
    }

    @Override
    void afterComplete() {
        throw new IllegalStateException("This animation should never complete");
    }

    @Override
    boolean isComplete() {
        // The player animation is never complete
        return false;
    }
}
