package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

/**
 * This class creates a bullet to be fired out
 * of the player modle.
 */
public class Bullet extends Rectangle  {

    private Rectangle bullet; // rectangle to represent bullet
    private Game game; // game object for bullet
    private double dx; // change in x per update
    private double dy; // change in y per update
    private Player player;

    /**
     * Constructs an {@code Bullet} object.
     * @param game parent game
     * @param player player class
     */
    public Bullet(Game game, Player player) {
        // this.bullet = new Rectangle(90, 10); // rectangle to represent bullet
        super(3, 8, Paint.valueOf("white"));
        this.player = player;
        this.game = game;
        this.dx = 0; // each update, add position of player (to start). should be zero
        this.dy = 1.5; // each update, add position of player (to start). should incremint
    } // bullet

    /**
     * Update the position of the Bullet.
     */
    public void update() {
        Bounds bulletBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        // if (bulletBounds.getMaxY() > gameBounds.getMaxY()) {
            //  dy = 0; // if upper Y bound is hit, bullet will stop
            // }
        this.setX(player.getX()); // set bullet x
        this.setY(player.getY()); // moves bullet up Y axis
    } // update

    /**
     * this method moves bullet along y axis.
     */
    public void travel() {
        setY(getY() - dy);
    } // travel
} // Bullet
