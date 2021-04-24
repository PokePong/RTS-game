package engine.gl;

public interface VBO {

    public void render();

    public void cleanUp();

    public int getIndicesCount();

    public int getVaoId();

}
