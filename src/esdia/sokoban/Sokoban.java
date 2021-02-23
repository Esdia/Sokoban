package esdia.sokoban;

import esdia.sokoban.global.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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

    static ArrayList<Level> load_levels(InputStream in) {
        LevelReader lr = new LevelReader(in);
        ArrayList<Level> levels = new ArrayList<>();

        Level l = lr.read();
        while (l != null) {
            levels.add(l);
            l = lr.read();
        }

        lr.close();

        return levels;
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

        ArrayList<Level> levels = load_levels(in);

        for (Level level : levels) {
            level.print();
            System.out.println();
        }
    }
}
