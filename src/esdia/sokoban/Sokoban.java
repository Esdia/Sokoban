package esdia.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Sokoban {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR : Please specify a file with levels");
            return;
        }

        File f;
        InputStream in;

        try {
            f = new File(args[0]);
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR : cannot open '" + args[0] + "'");
            return;
        }

        LevelReader lr = new LevelReader(in);
        ArrayList<Level> levels = new ArrayList<>();

        Level l = lr.read();
        while (l != null) {
            levels.add(l);
            l = lr.read();
        }

        lr.close();

        for (Level level : levels) {
            level.print();
            System.out.println();
        }
    }
}
