package esdia.sokoban.ui;

import esdia.sokoban.global.Configuration;

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

        Configuration.instance().get_logger().info(
                "Mouse click on (" + x + ", " + y + ")"
        );

        boolean res = l.isNextToPlayer(x, y);
    }
}
