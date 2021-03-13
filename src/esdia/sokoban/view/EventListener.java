package esdia.sokoban.view;

import esdia.sokoban.model.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class EventListener implements MouseListener, KeyListener {
    protected MainWindow window;
    protected Game game;

    public EventListener(Game game) {
        this.game = game;
    }

    public void setWindow(MainWindow window) {
        this.window = window;
    }

    protected void loadNextLevel() {
        if (! this.game.loadNextLevel()) {
            this.window.shutdown();
        }
    }

    protected void skipLevel() {
        if (! this.game.skipLevel()) {
            this.window.shutdown();
        }
    }

    public abstract void mouseClick(int x, int y);

    public abstract void keyInput(int keyCode);

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        this.mouseClick(x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keyInput(e.getKeyCode());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
