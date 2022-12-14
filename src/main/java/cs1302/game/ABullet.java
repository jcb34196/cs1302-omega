package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.scene.paint.Paint;

/**
 * This class creates a bullet to be fired out
 * of the player modle.
 */
public class ABullet extends Rectangle {

    private Rectangle bullet; // rectangle to represent bullet
    private Game game; // game object for bullet
    private double dx; // change in x per update
    private double dy; // change in y per update
    private Alien alien;

    /**
     * Constructs an {@code Bullet} object.
     * @param game parent game
     */
    public ABullet(Game game) {
        super(3, 8, Paint.valueOf("red")); // rectangle to represent bullet
        this.game = game;
        this.dx = 0; // each update, add position of player (to start). should be zero
        this.dy = 1.5; // each update, add position of player (to start). should incremint
    } // bullet

    /**
     * Update the position of the Bullet.
     * @param alien alien class object
     */
    public void update (Alien alien) {
        Bounds bulletBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        // if (bulletBounds.getMaxY() < gameBounds.getMaxY()) {
            // dy = 0; // if upper Y bound is hit, bullet will stop
        // } // if
        setX(alien.getX()); // set bullet to alien x
        setY(alien.getY()); // moves bullet alien  Y
    } // update

    /**
     * this method moves abullet along y axis.
     */
    public void shoot() {
        setY(getY() + dy);
    } // shoot
} // ABullet
