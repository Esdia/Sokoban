package esdia.sokoban.controller;

import esdia.sokoban.view.MainWindow;

public class PlayerAnimation extends Animation {
    int counter;

    public PlayerAnimation(MainWindow window) {
        super(window);

        this.counter = 0;
    }

    @Override
    public void prepareNextFrame() {
        counter++;
        if (counter == 15) {
            counter = 0;
            this.window.getLevelUI().nextWalkingFrame();
        }
    }

    @Override
    public void afterComplete() {
        throw new IllegalStateException("This animation should never complete");
    }

    @Override
    public boolean isComplete() {
        // The player animation is never complete
        return false;
    }
}
