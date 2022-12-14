package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Spirte for Aliens.
 */
public class Alien extends ImageView {

    private Game game; // game containing Alien sprite
    private double dx; // change in x direction per update
    private double dy; // change in y direction per update
    private ArrayList<Alien> alien;

    /**
     * Construct an {@code Alien} object.
     * @param game parent game
     */
    public Alien(Game game) {
        super("file:resources/sprites/Alien.png"); // Call parent Constuctor
        this.setPreserveRatio(true);
        this.setFitHeight(20);
        this.setFitWidth(20);
        this.setFitWidth(getImage().getWidth());
        this.game = game;
        this.dx = 1; // each update, add 2 to x
        this.dy = 5; // each update, add 10 to y
    } // Alien

    /**
     * Update position of the Alien.
     * @param alien object in arraylist
     */
    public void update(ArrayList<Alien> alien) {

        Bounds alienBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        /*
        if (alienBounds.getMaxX() > gameBounds.getMaxX()) {
            for (Alien var : alien) {
                var.turn();
            } // for
            //setY(getY() + dy);
        } else if (alienBounds.getMinX() < gameBounds.getMinX()) {
            for(Alien var : alien) {
                var.turn();
            } // for
            //setY(getY() + dy);
            */
        if (alienBounds.getMaxY() > gameBounds.getMaxY() - 10) { // -10
            for (Alien var : alien) {
                var.stopAlien();
            } // for
        } // if
        setX(getX() + dx); // moves alien
    } // update

    /**
     * this method determines if aliens can turn.
     * @return if can turn statment
     */
    public boolean canTurn() {
        Bounds alienBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        return (alienBounds.getMinX() < gameBounds.getMinX() ||
        alienBounds.getMaxX() > gameBounds.getMaxX());
    }

    /**
     * increment y by 10 and turn alien x direction.
     */
    public void turn() {
        setY(getY() + dy);
        dx *= - 1;
        this.setY(getY() + dy);
    } // changeDir

    /**
     * stop all aliens.
     */
    public void stopAlien() {
        dx *= 1;
        dy *= 1;
    } // stopAlien

} // Alien
