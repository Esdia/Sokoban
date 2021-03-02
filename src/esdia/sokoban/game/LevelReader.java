package esdia.sokoban.game;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LevelReader {
    Scanner scan;

    public LevelReader(InputStream in) {
        this.scan = new Scanner(in);
    }

    public Level read() {
        String line;

        Level l = new Level();

        int i;
        int j;

        do {
            try {
                line = scan.nextLine();

                if (!line.isEmpty()) {
                    if (line.charAt(0) == ';') {
                        i = 1;
                        while (line.charAt(i) == ' ') { i++; }
                        l.setName(line.substring(i));
                    } else {
                        i = l.lines();
                        l.resize_grid(i + 1, line.length());

                        for (j = 0; j < line.length(); j++) {
                            switch (line.charAt(j)) {
                                case ' ' -> l.emptySquare(i, j);
                                case '#' -> l.addWall(i, j);
                                case '@' -> l.addPlayer(i, j);
                                case '+' -> l.addPlayerOnGoal(i, j);
                                case '$' -> l.addBox(i, j);
                                case '*' -> l.addBoxOnGoal(i, j);
                                case '.' -> {
                                    l.addGoal(i, j);
                                    l.nbGoals++;
                                }
                                default -> { return null; }
                            }
                        }

                        while (j < l.columns()) {
                            l.addWall(i, j);
                            j++;
                        }
                    }
                }

            } catch (IllegalStateException | InputMismatchException e) {
                System.out.println("ERROR");
                e.printStackTrace();
                return null;
            } catch (NoSuchElementException e) {
                break;
            }
        } while (!line.isEmpty());

        if (l.lines() == 0 || l.columns() == 0) {
            return null;
        } else {
            return l;
        }
    }

    public void close() {
        this.scan.close();
    }
}
