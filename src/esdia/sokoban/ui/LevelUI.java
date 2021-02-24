package esdia.sokoban.ui;

import esdia.sokoban.game.Game;
import esdia.sokoban.game.Level;
import esdia.sokoban.global.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LevelUI extends JComponent {
    private final Game game;
    BufferedImage empty, wall, player, box, goal, boxOnGoal;

    private BufferedImage loadImg(String name) {
        InputStream in = LevelUI.class.getResourceAsStream("/images/" + name);
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            Configuration.instance().get_logger().severe("Cannot load image : " + name);
            System.exit(1);
        }

        return null;
    }

    private void loadImgs() {
        this.empty = this.loadImg("empty.png");
        this.wall = this.loadImg("wall.png");
        this.player = this.loadImg("player.png");
        this.box = this.loadImg("box.png");
        this.goal = this.loadImg("goal.png");
        this.boxOnGoal = this.loadImg("box_on_goal.png");
    }

    public LevelUI(Game game) {
        this.game = game;
        this.loadImgs();
    }

    private BufferedImage selectImage(Level l, int i, int j) {
        if (l.isEmpty(i, j)) return empty;
        if (l.isWall(i, j)) return wall;
        if (l.isPlayer(i, j) || l.isPlayerOnGoal(i, j)) return player;
        if (l.isBox(i, j)) return box;
        if (l.isGoal(i, j)) return goal;
        if (l.isBoxOnGoal(i, j)) return boxOnGoal;

        return null;
    }

    private int getImgSize(Level l) {
        return Math.min(getWidth() / l.columns(), getHeight() / l.lines());
    }

    private void fillWithGround(Graphics2D drawable) {
        int img_size = this.getImgSize(this.game.getCurrentLevel());

        int x_start = ((getWidth() / 2) - (img_size / 2)) % img_size - img_size;
        int x = x_start;
        int y = ((getHeight() / 2) - (img_size / 2)) % img_size - img_size;

        while (y < getHeight()) {
            while (x < getWidth()) {
                drawable.drawImage(this.empty, x, y, img_size, img_size, null);
                x += img_size;
            }
            x = x_start;
            y += img_size;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        drawable.clearRect(0, 0, width, height);

        this.fillWithGround(drawable);

        Level l = this.game.getCurrentLevel();

        int img_size = getImgSize(l);

        int x_start = width / 2 - (l.columns() * img_size / 2);
        int y_start = height / 2 - (l.lines() * img_size / 2);

        int x = x_start;
        int y = y_start;

        BufferedImage img;
        for (int i = 0; i < l.lines(); i++) {
            for (int j = 0; j < l.columns(); j++) {
                img = this.selectImage(l, i, j);
                if (img != null) {
                    drawable.drawImage(img, x, y, img_size, img_size, null);
                    x += img_size;
                }
            }
            x = x_start;
            y += img_size;
        }
    }
}
