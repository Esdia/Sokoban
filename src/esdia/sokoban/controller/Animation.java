package esdia.sokoban.controller;

import esdia.sokoban.model.Game;
import esdia.sokoban.view.MainWindow;

public abstract class Animation {
    MainWindow window;
    Game game;

    public Animation(MainWindow window, Game game) {
        this.window = window;
        this.game = game;
    }

    public abstract void prepareNextFrame();

    public abstract void afterComplete();

    public abstract boolean isComplete();
}
