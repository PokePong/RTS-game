package engine.util;

import engine.model.Mesh;
import engine.model.Vertex;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OBJLoader {

    public static Mesh loadMesh(String dir, String fileName) {
        long time = System.nanoTime();
        BufferedReader reader = new BufferedReader(ResourceLoader.loadResource("model\\" + dir, fileName));
        String line;

        Map<String, Integer> map = new HashMap<>();
        List<Vertex> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        List<Vector3f> positions = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        int count = 0;
        try {
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("#") || line.startsWith(" ") || line.isEmpty())
                    continue;
                String[] token = line.split(" ");
                switch (token[0]) {
                    case "v":
                        positions.add(new Vector3f(Float.parseFloat(token[1]), Float.parseFloat(token[2]),
                                Float.parseFloat(token[3])));
                        break;
                    case "vn":
                        normals.add(new Vector3f(Float.parseFloat(token[1]), Float.parseFloat(token[2]),
                                Float.parseFloat(token[3])));
                        break;
                    case "f":
                        if (token[1].contains("//")) {

                            for (int i = 1; i < 4; i++) {
                                int iVertex;
                                if (map.containsKey(token[i])) {
                                    iVertex = map.get(token[i]);
                                    indices.add(iVertex);
                                } else {
                                    String[] st = token[i].split("//");
                                    int[] index = {Integer.parseInt(st[0]) - 1, Integer.parseInt(st[1]) - 1};
                                    Vertex vertex = new Vertex(positions.get(index[0]));
                                    vertex.setNormal(normals.get(index[1]));
                                    iVertex = count;
                                    map.put(token[i], iVertex);
                                    vertices.add(vertex);
                                    indices.add(iVertex);
                                    count++;
                                }
                            }
                        } else {
                            for (int i = 1; i < 4; i++) {
                                int iVertex;
                                if (map.containsKey(token[i])) {
                                    iVertex = map.get(token[i]);
                                    indices.add(iVertex);
                                } else {
                                    int index = Integer.parseInt(token[i]) - 1;
                                    Vertex vertex = new Vertex(positions.get(index));
                                    iVertex = count;
                                    map.put(token[i], iVertex);
                                    vertices.add(vertex);
                                    indices.add(iVertex);
                                    count++;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(indices);
        Vertex[] verticesArray = new Vertex[vertices.size()];
        vertices.toArray(verticesArray);
        int[] indicesArray = indices.stream().mapToInt(i -> i).toArray();
        long delta = (System.nanoTime() - time) / 1000000;
        Debug.log("[OBJ] Loading " + fileName + ": " + delta + "ms");
        return new Mesh(verticesArray, indicesArray);
    }

}
