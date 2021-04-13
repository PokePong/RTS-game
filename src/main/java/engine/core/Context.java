package engine.core;

import engine.scene.Scenegraph;

/**
 * Context founit la structure et des méthodes permettant de mettre en place un jeu.
 * Une classe enfant devra héritée de Context afin de pouvoir configurer la classe Engine.
 *
 * @author loic.besse
 * @version 1.0
 */
public abstract class Context {

    /**
     * Objet regroupant tout les objets du jeu.
     *
     * @see Scenegraph
     */
    private Scenegraph scenegraph;

    /**
     * Constructeur.
     */
    protected Context() {
        this.scenegraph = new Scenegraph();
    }

    /**
     * Méthode d'initisation appelé dans EngineSystem
     */
    public void init() {
        __init__();
        scenegraph.init();
    }

    /**
     * Méthode d'update appelé dans EngineSystem
     *
     * @param delta intervalle de temps
     */
    public void update(double delta) {
        __update__(delta);
        scenegraph.update(delta);
    }

    /**
     * Méthode de nettage appelé dans EngineSystem
     */
    public void cleanUp() {
        scenegraph.cleanUp();
    }

    /**
     * Méthode d'abstraite d'initialisation permettant à l'utilisateur de créer des objet.
     * Appelée dans la méthode init
     */
    public abstract void __init__();

    /**
     * Méthode d'abstraite d'update permettant à l'utilisateur de mettre à jour ses objets.
     * Appelée dans la méthode update
     *
     * @param delta intervalle de temps
     */
    public abstract void __update__(double delta);

    /**
     * Retourne l'objet de scenegraph
     *
     * @return l'objet scenegraph
     */
    public Scenegraph getScenegraph() {
        return scenegraph;
    }

}
