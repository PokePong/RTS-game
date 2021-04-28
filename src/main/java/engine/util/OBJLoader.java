package engine.util;

import engine.math.vector.Vector3;
import engine.model.Mesh;
import engine.model.Vertex;

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

        List<Vector3> positions = new ArrayList<>();
        List<Vector3> normals = new ArrayList<>();

        int count = 0;
        boolean generateNormals = false;
        try {
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("#") || line.startsWith(" ") || line.isEmpty())
                    continue;
                String[] token = line.split(" ");
                switch (token[0]) {
                    case "v":
                        positions.add(new Vector3(Float.parseFloat(token[1]), Float.parseFloat(token[2]),
                                Float.parseFloat(token[3])));
                        break;
                    case "vn":
                        normals.add(new Vector3(Float.parseFloat(token[1]), Float.parseFloat(token[2]),
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
                        } else if (token[1].contains("/")) {
                            if(token[1].split("/").length == 2) {
                                generateNormals = true;
                                for (int i = 1; i < 4; i++) {
                                    int iVertex;

                                    if (map.containsKey(token[i])) {
                                        iVertex = map.get(token[i]);
                                        indices.add(iVertex);
                                    } else {
                                        String[] st = token[i].split("/");
                                        int[] index = {Integer.parseInt(st[0]) - 1, Integer.parseInt(st[1]) - 1};
                                        Vertex vertex = new Vertex(positions.get(index[0]));
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
                                        String[] st = token[i].split("/");
                                        int[] index = {Integer.parseInt(st[0]) - 1, Integer.parseInt(st[2]) - 1};
                                        Vertex vertex = new Vertex(positions.get(index[0]));
                                        iVertex = count;
                                        map.put(token[i], iVertex);
                                        vertices.add(vertex);
                                        indices.add(iVertex);
                                        count++;
                                    }
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

        if (generateNormals) {
            generateNormalsCW(verticesArray, indicesArray);
        }


        long delta = (System.nanoTime() - time) / 1000000;
        Debug.info("[OBJ] Loading " + fileName + ": " + delta + "ms");
        return new Mesh(verticesArray, indicesArray);
    }

    public static void generateNormalsCW(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            Vector3 v0 = vertices[indices[i]].getPosition();
            Vector3 v1 = vertices[indices[i + 1]].getPosition();
            Vector3 v2 = vertices[indices[i + 2]].getPosition();

            Vector3 normal = v1.sub(v0).cross(v2.sub(v0)).normalize();

            vertices[indices[i]].setNormal(vertices[indices[i]].getNormal().add(normal));
            vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
            vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; ++i) {
            vertices[i].setNormal(vertices[i].getNormal().negate().normalize());
        }
    }

    public static void generateNormalsCCW(Vertex[] vertices, int[] indices)
    {
        for ( int i = 0; i < indices.length; i += 3 )
        {
            Vector3 v0 = vertices[indices[i    ]].getPosition();
            Vector3 v1 = vertices[indices[i + 1]].getPosition();
            Vector3 v2 = vertices[indices[i + 2]].getPosition();

            Vector3 normal = v2.sub(v0).cross(v1.sub(v0)).normalize();

            vertices[indices[i	  ]].setNormal(vertices[indices[i    ]].getNormal().add(normal));
            vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
            vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
        }

        for ( int i = 0; i < vertices.length; ++i )
        {
            vertices[i].setNormal(vertices[i].getNormal().normalize());
        }
    }

}
