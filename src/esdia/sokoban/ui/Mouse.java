package esdia.sokoban.ui;

import esdia.sokoban.global.Configuration;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    MainWindow window;

    public Mouse(MainWindow window) {
        this.window = window;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Configuration.instance().get_logger().info(
                "Mouse click on (" + x + ", " + y + ")"
        );

        this.window.game.moveTo(x, y);
    }
}
