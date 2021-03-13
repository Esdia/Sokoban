package esdia.sokoban.view;

import esdia.sokoban.model.Game;
import esdia.sokoban.model.Level;
import esdia.sokoban.global.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LevelUI extends JComponent {
    private final Game game;

    private int imgSize;
    BufferedImage empty, wall, player, box, goal, boxOnGoal;

    private int topLeftX;
    private int topLeftY;

    public LevelUI(Game game) {
        this.game = game;
        this.loadImgs();
    }

    private BufferedImage loadImg(String name) {
        Configuration.instance().get_logger().info(
                "Logging image : " + name
        );
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
        int x_start = this.topLeftX % this.imgSize - this.imgSize;
        int x = x_start;
        int y = this.topLeftY % this.imgSize - this.imgSize;

        while (y < getHeight()) {
            while (x < getWidth()) {
                drawable.drawImage(this.empty, x, y, this.imgSize, this.imgSize, null);
                x += this.imgSize;
            }
            x = x_start;
            y += this.imgSize;
        }
    }

    public int coordToIndexX(int x) {
        return (x - this.topLeftX) / this.imgSize;
    }

    public int coordToIndexY(int y) {
        return (y - this.topLeftY) / this.imgSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        Configuration.instance().get_logger().info(
                "Repainting window"
        );
        Graphics2D drawable = (Graphics2D) g;
        Level l = this.game.getCurrentLevel();

        int width = getWidth();
        int height = getHeight();

        drawable.clearRect(0, 0, width, height);

        this.imgSize = getImgSize(l);


        int x_start = width / 2 - (l.columns() * imgSize / 2);
        int y_start = height / 2 - (l.lines() * imgSize / 2);

        int x = x_start;
        int y = y_start;

        this.topLeftX = x_start;
        this.topLeftY = y_start;

        this.fillWithGround(drawable);

        BufferedImage img;
        for (int i = 0; i < l.lines(); i++) {
            for (int j = 0; j < l.columns(); j++) {
                if (l.isPlayerOnGoal(i, j)) {
                    drawable.drawImage(goal, x, y, imgSize, imgSize, null);
                    drawable.drawImage(player, x, y, imgSize, imgSize, null);
                } else {
                    img = this.selectImage(l, i, j);
                    drawable.drawImage(img, x, y, imgSize, imgSize, null);
                }
                x += imgSize;
            }
            x = x_start;
            y += imgSize;
        }
    }
}
