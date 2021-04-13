package engine.util;

import java.io.*;

public class ResourceLoader {

    public static FileReader loadResource(String fileName) {
        String path = getAbsoluPath(fileName);
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fr;
    }

    public static FileReader loadResource(String dir, String fileName) {
        return loadResource(dir + "\\" + fileName);
    }

    public static String loadShader(String source) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = new BufferedReader(loadResource("shader\\" + source));
        String line;
        try {
            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            shaderReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shaderSource.toString();
    }

    public static String getAbsoluPath(String path) {
        return System.getProperty("user.dir") + "\\src\\main\\resources\\" + path;
    }

}
