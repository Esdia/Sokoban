package esdia.sokoban.controller;

import esdia.sokoban.view.MainWindow;

public abstract class Animation {
    MainWindow window;

    public Animation(MainWindow window) {
        this.window = window;
    }

    public abstract void prepareNextFrame();

    public abstract void afterComplete();

    public abstract boolean isComplete();
}
