package esdia.sokoban;

import esdia.sokoban.controller.GameController;
import esdia.sokoban.model.Game;
import esdia.sokoban.model.LevelReader;
import esdia.sokoban.view.MainWindow;

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

        InputStream in = get_input_stream(args[0]);
        if (in == null) {
            return;
        }
        Game game = new Game(new LevelReader(in));

        GameController controller = new GameController(game);

        if (game.nextLevel()) {
            MainWindow.start(game, controller);
        }
    }
}
