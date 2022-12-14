package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * Player sprit {@code Player} object.
 */
public class Player extends ImageView {

    private Game game; // game object containing player
    private double dx; // change in x position per update
    private double dy; // change in y position per update

    /**
     * constructs an {@code Player} object.
     * @param game parent game
     */
    public Player(Game game) {
        super("file:resources/sprites/Ship.png"); // call parent constructor
        this.setPreserveRatio(true);
        this.setFitHeight(20);
        this.setFitWidth(20);
        this.setFitWidth(getImage().getWidth());
        this.game = game;
        this.dx = 1; // change in x per update
        this.dy = 0; // change in y per update
    } // Player

} // Player
