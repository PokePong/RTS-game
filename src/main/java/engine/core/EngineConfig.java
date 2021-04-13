package engine.core;

import engine.util.ResourceLoader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * EngineConfig founit des variables présentent dans le fichier de configuration.
 * Ce fichier nommé config.properties est accessible depuis le dosser resources
 *
 * @author loic.besse
 * @version 1.0
 */
public class EngineConfig {

    private static String fileName;
    private static Properties properties;

    private static String version;

    private static int window_width;
    private static int window_height;
    private static String window_title;

    private static int fps_cap;
    private static int ups_cap;

    private static float z_near;
    private static float z_far;
    private static int fov;

    public static void init(String fileName) {
        properties = new Properties();
        try {
            FileReader fr = ResourceLoader.loadResource(fileName);
            properties.load(fr);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        version = properties.getProperty("version");

        window_width = Integer.parseInt(properties.getProperty("window_width"));
        window_height = Integer.parseInt(properties.getProperty("window_height"));
        window_title = properties.getProperty("window_title");

        fps_cap = Integer.parseInt(properties.getProperty("fps_cap"));
        ups_cap = Integer.parseInt(properties.getProperty("ups_cap"));

        z_near = Float.parseFloat(properties.getProperty("z_near"));
        z_far = Float.parseFloat(properties.getProperty("z_far"));
        fov = Integer.parseInt(properties.getProperty("fov"));
    }

    public static String getFileName() {
        return fileName;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getVersion() {
        return version;
    }

    public static int getWindow_width() {
        return window_width;
    }

    public static int getWindow_height() {
        return window_height;
    }

    public static String getWindow_title() {
        return window_title;
    }

    public static int getFps_cap() {
        return fps_cap;
    }

    public static int getUps_cap() {
        return ups_cap;
    }

    public static float getZ_near() {
        return z_near;
    }

    public static float getZ_far() {
        return z_far;
    }

    public static int getFov() {
        return fov;
    }

}