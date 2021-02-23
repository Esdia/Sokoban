package esdia.sokoban.global;

import esdia.sokoban.Sokoban;
import esdia.sokoban.sequences.LinkedList;
import esdia.sokoban.sequences.Sequence;
import esdia.sokoban.sequences.TableSequence;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private static Configuration config = null;
    private static Logger logger = null;

    private final Properties properties;

    private static void properties_from_file(InputStream in, Properties properties) {
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean user_properties_exist(String path) {
        File prop_file = new File(path);

        return prop_file.isFile();
    }

    private static void create_user_properties(String default_name, String user_path) {
        InputStream src = Sokoban.class.getResourceAsStream(default_name);
        Path dest = new File(user_path).toPath();

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties load_properties() {
        String default_prop = "/config/default.cfg";
        String prop_file = System.getProperty("user.home") + "/.sokoban";
        InputStream in;

        in = Configuration.class.getResourceAsStream(default_prop);
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

    private Configuration() {
        this.properties = load_properties();
    }

    public static Configuration instance() {
        if (config == null) {
            config = new Configuration();
        }

        return config;
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }

    @SuppressWarnings("unused")
    public String get(String key, String _default) {
        return this.properties.getProperty(key, _default);
    }

    @SuppressWarnings("unused")
    public <Type> Sequence<Type> new_sequence() {
        return switch (this.get("Sequence")) {
            case "Table" -> new TableSequence<>();
            case "LinkedList" -> new LinkedList<>();
            default -> throw new NoSuchElementException("Invalid sequence type");
        };
    }

    @SuppressWarnings("unused")
    public Logger get_logger() {
        if (logger == null) {
            logger = Logger.getLogger("Sokoban.Logger");
            logger.setLevel(Level.parse(this.get("LogLevel")));
        }
        return logger;
    }
}
