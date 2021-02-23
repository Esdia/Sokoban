package esdia.sokoban;

import esdia.sokoban.game.Game;
import esdia.sokoban.game.LevelReader;
import esdia.sokoban.global.Configuration;
import esdia.sokoban.ui.MainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sokoban {
    static InputStream get_input_stream(String filename) {
        File f;
        InputStream in;

        try {
            f = new File(filename);
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR : cannot open '" + filename + "'");
            in = null;
        }

        return in;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR : Please specify a file with levels");
            return;
        }

        Configuration config = Configuration.instance();

        InputStream in = get_input_stream(args[0]);
        if (in == null) {
            return;
        }
        Game game = new Game(new LevelReader(in));

        if (game.nextLevel()) {
            MainWindow.start(game);
        }
    }
}
