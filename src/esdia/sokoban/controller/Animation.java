package esdia.sokoban.controller;

import esdia.sokoban.view.MainWindow;

public abstract class Animation {
    protected final MainWindow window;

    Animation(MainWindow window) {
        this.window = window;
    }

    abstract void prepareNextFrame();

    abstract void afterComplete();

    abstract boolean isComplete();
}
