package esdia.sokoban;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Properties;

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

    static void properties_from_file(InputStream in, Properties properties) {
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean user_properties_exist(String path) {
        File prop_file = new File(path);

        return prop_file.isFile();
    }

    static void create_user_properties(String default_name, String user_path) {
        InputStream src = Sokoban.class.getResourceAsStream(default_name);
        Path dest = new File(user_path).toPath();

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Properties load_properties() {
        String default_prop = "default.cfg";
        String prop_file = System.getProperty("user.home") + "/.sokoban";
        InputStream in;

        in = Sokoban.class.getResourceAsStream(default_prop);
        Properties default_properties = new Properties();
        properties_from_file(in, default_properties);

        if (!user_properties_exist(prop_file)) {
            create_user_properties(default_prop, prop_file);
            return default_properties;
        }

        Properties properties = new Properties(default_properties);
        try {
            in = new FileInputStream(prop_file);
            properties_from_file(in, properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("ERROR : Please specify a file with levels");
            return;
        }

        Properties properties = load_properties();

        InputStream in = get_input_stream(args[0]);
        if (in == null) {
            return;
        }

        ArrayList<Level> levels = load_levels(in);

        for (Level level : levels) {
            level.print();
            System.out.println();
        }

        System.out.println(properties.getProperty("Sequence"));
    }
}
