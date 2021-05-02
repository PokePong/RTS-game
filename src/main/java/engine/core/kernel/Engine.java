package engine.core.kernel;

import engine.core.Context;
import engine.core.Timer;
import engine.core.Window;
import org.lwjgl.glfw.GLFW;

/**
 * Engine founit l'objet principal du moteur de jeu.
 * Il s'occupe du lancement, de l'initialisation, de la boucle principale, de la mise en context et
 * de l'arrêt du programme.
 *
 * @author loic.besse
 * @version 1.0
 */
public class Engine {

    /**
     * Savoir si le moteur tourne.
     */
    private boolean running;

    /**
     * Objet permettant de gérer le temps.
     *
     * @see Timer
     */
    private Timer timer;

    /**
     * Objet gérant les composants du moteur.
     *
     * @see EngineSystem
     */
    private EngineSystem engineSystem;

    /**
     * Objet gérant la configuration du moteur.
     *
     * @see EngineConfig
     */
    private EngineConfig config;

    /**
     * Constucteur
     */
    public Engine() {
        this.timer = new Timer();
        this.engineSystem = new EngineSystem(this);
    }

    /**
     * Mise en context. Sans context, le moteur ne peut pas fonctionner.
     *
     * @param context Une classe enfant héritée de la classe Context
     * @see Context
     */
    public void setContext(Context context) {
        this.engineSystem.setContext(context);
    }

    /**
     * Permet de lancer le moteur.
     */
    public void start() {
        if (running)
            return;
        running = true;

        try {
            init();
            run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

    /**
     * Permet de stoper le moteur.
     */
    public void stop() {
        if (!running)
            return;
        running = false;
    }

    /**
     * Méthode d'initialisation du jeu.
     */
    private void init() {
        EngineConfig.init("config.properties");
        timer.mark();
        Window.create(config.getWindow_width(), config.getWindow_height(), config.getWindow_title() + " - " + config.getVersion());
        engineSystem.init();
    }

    /**
     * Boucle principale.
     */
    private void run() {
        double delta;
        double interval = 1d / config.getUps_cap();
        double accumulator = 0d;
        double frameCounter = 0d;

        int frames = 0;
        int ticks = 0;

        while (!Window.shouldClose()) {
            boolean render = false;

            delta = timer.getDelta();
            accumulator += delta;
            frameCounter += delta;

            while (accumulator >= interval) {
                update(interval);
                ticks++;

                accumulator -= interval;

                if (frameCounter >= 1) {
                    Window.addTitle("FPS: " + frames + " | Ticks: " + ticks);
                    frames = 0;
                    ticks = 0;
                    frameCounter = 0;
                }
            }
            // V-SYNC true
            GLFW.glfwSwapInterval(1);
            render();
            // V-SYNC false
            //sync();
            frames++;
        }
        stop();
    }

    /**
     * Méthode de synchronisation vis-à-vis des objectifs de FPS.
     */
    private void sync() {
        double slot = 1d / config.getFps_cap();
        double endTime = timer.getLastLoopTime() + slot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode d'update.
     * @param delta interval de temps
     */
    private void update(double delta) {
        engineSystem.update(delta);
    }

    /**
     * Méthode de rendu.
     */
    private void render() {
        engineSystem.render();
        Window.draw();
    }

    /**
     * Méthode de nettoyage.
     */
    private void cleanUp() {
        engineSystem.cleanUp();
        Window.destroy();
    }

    /**
     * Retourne la configuration
     * @return l'objet de la configuration EngineConfig
     */
    public EngineConfig getConfig() {
        return config;
    }
}
