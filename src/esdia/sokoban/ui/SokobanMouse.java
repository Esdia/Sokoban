package esdia.sokoban.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SokobanMouse extends MouseAdapter {
    LevelUI l;

    public SokobanMouse(LevelUI l) {
        this.l = l;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        boolean res = l.isNextToPlayer(x, y);

        System.out.println("(" + x + ", " + y + ") Next to player ? " + res);
    }
}
