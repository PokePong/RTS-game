import engine.core.Engine;
import instance.RTSgame;

public class Launcher {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.setContext(new RTSgame());
        engine.start();
    }

}
