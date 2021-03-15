package esdia.sokoban.view;

import esdia.sokoban.model.Direction;
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
    BufferedImage empty, wall, box, goal, boxOnGoal;

    BufferedImage[][] player;

    private int topLeftX;
    private int topLeftY;


    /* Variables for the animation */
    int offsetI, offsetJ; // A box will always move along with the player, so we do not need two sets of offsets
    int movingBoxI, movingBoxJ;

    Direction currentFacingDirection;
    int currentWalkingFrame;

    public LevelUI(Game game) {
        this.game = game;
        this.loadImgs();
        this.setAnimationOffset(0, 0);
        this.setMovingBox(-1, -1);
        this.currentFacingDirection = Direction.DOWN;
        this.currentWalkingFrame = 0;
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
        this.box = this.loadImg("box.png");
        this.goal = this.loadImg("goal.png");
        this.boxOnGoal = this.loadImg("box_on_goal.png");

        player = new BufferedImage[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                player[i][j] = this.loadImg("player/player_" + i + "_" + j + ".png");
            }
        }
    }

    private int getImgSize(Level l) {
        return Math.min(getWidth() / l.columns(), getHeight() / l.lines());
    }

    public int coordToIndexX(int x) {
        return (x - this.topLeftX) / this.imgSize;
    }

    public int coordToIndexY(int y) {
        return (y - this.topLeftY) / this.imgSize;
    }

    public int getImgSize() {
        return this.imgSize;
    }

    public void setAnimationOffset(int offsetI, int offsetJ) {
        this.offsetI = offsetI;
        this.offsetJ = offsetJ;
    }

    public void setMovingBox(int movingBoxI, int movingBoxJ) {
        this.movingBoxI = movingBoxI;
        this.movingBoxJ = movingBoxJ;
    }

    public void setFacingDirection(Direction direction) {
        this.currentFacingDirection = direction;
    }

    public void nextWalkingFrame() {
        this.currentWalkingFrame = (this.currentWalkingFrame + 1) % 4;
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

    private void drawBackground(Graphics2D drawable) {
        int xStart = this.topLeftX;
        int yStart = this.topLeftY;

        int x = xStart;
        int y = yStart;

        Level l = this.game.getCurrentLevel();

        BufferedImage img;
        for (int i = 0; i < l.lines(); i++) {
            for (int j = 0; j < l.columns(); j++) {
                if (l.isEmpty(i, j)) {
                    x += this.imgSize;
                    continue;
                }

                img = null;
                if (l.isWall(i, j)) {
                    img = this.wall;
                } else if (l.isGoal(i, j) || l.isPlayerOnGoal(i, j)) {
                    img = this.goal;
                }

                if (img != null) {
                    drawable.drawImage(img, x, y, this.imgSize, this.imgSize, null);
                }

                x += this.imgSize;
            }
            x = xStart;
            y += imgSize;
        }
    }

    private void drawForeground(Graphics2D drawable) {
        int xStart = this.topLeftX;
        int yStart = this.topLeftY;

        int x = xStart;
        int y = yStart;

        Level l = this.game.getCurrentLevel();

        BufferedImage img;
        for (int i = 0; i < l.lines(); i++) {
            for (int j = 0; j < l.columns(); j++) {
                if (l.isEmpty(i, j)) {
                    x += this.imgSize;
                    continue;
                }

                img = null;
                if (l.isBox(i, j)) {
                    img = this.box;
                } else if (l.isBoxOnGoal(i, j)) {
                    img = this.boxOnGoal;
                } else if (l.isPlayer(i, j) || l.isPlayerOnGoal(i, j)) {
                    img = this.player[this.currentFacingDirection.ordinal()][this.currentWalkingFrame];
                    drawable.drawImage(img, x + offsetJ, y + offsetI, this.imgSize, this.imgSize, null);
                }

                if (l.isBox(i, j) || l.isBoxOnGoal(i, j)) {
                    if (i == this.movingBoxI && j == this.movingBoxJ) {
                        drawable.drawImage(img, x + offsetJ, y + offsetI, this.imgSize, this.imgSize, null);
                    } else {
                        drawable.drawImage(img, x, y, this.imgSize, this.imgSize, null);
                    }
                }
                x += this.imgSize;
            }
            x = xStart;
            y += imgSize;
        }
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

        this.imgSize = this.getImgSize(l);

        this.topLeftX = width / 2 - (l.columns() * imgSize / 2);
        this.topLeftY = height / 2 - (l.lines() * imgSize / 2);

        this.fillWithGround(drawable);
        this.drawBackground(drawable);
        this.drawForeground(drawable);
    }
}
