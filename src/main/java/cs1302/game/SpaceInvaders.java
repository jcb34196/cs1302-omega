package cs1302.game;

import cs1302.game.Player;
import java.util.logging.Level;
//import java.lang.System;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import java.util.Date;
import java.util.Random;
import java.util.ArrayList;

/**
 * My recreation of the Arcade game SpaceInvaders. The player can
 * move the ship using left/right arrow keys and fire using spacebar.
 */
public class SpaceInvaders extends Game {

    private ArrayList<Alien> alien; // Alien game object
    private Bullet[] bullet = new Bullet[20]; // player bullet
    private ABullet[] aBullet = new ABullet[20]; // Alien bullet
    private Player player; // player game object
    private long startTime; // start time
    private long startTime2;
    private long  endTime; // end time incremented by 3 seconds.
    private Random rng;
    private int global;
    private int aliensDestroyed;

    /**
     * Constructs a {@code SpaceInvaders} object.
     * @param width scene width
     * @param height scene width
     */
    public SpaceInvaders(int width, int height) {
        super(width, height, 60); // call parent constructor
        this.player = new Player(this);
        alien = new ArrayList<Alien>();
        for (int i = 0; i < 32; i++) {
            alien.add( new Alien(this));
        } // for each
        for (int i = 0; i < bullet.length; i++) {
            bullet[i] = new Bullet(this, player);
        } // for each
        for (int i = 0; i < aBullet.length; i++) {
            aBullet[i] = new ABullet(this);
        } // for each
        this.player = new Player(this);
        this.startTime = System.currentTimeMillis(); // start time
        this.endTime = startTime + 2000; // end time incremented by 3 seconds.
        this.rng = new Random();
    } // SpaceInvaders

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        getChildren().add(player);
        player.setX(320);
        player.setY(300);
        int alienLoad = 0;
        double ypos = 0;
        for (int i = 0; i < 4; i++) {
            double xpos = 0;
            ypos += 40;
            for (int j = 0; j < 8; j++) {
                getChildren().add(alien.get(alienLoad));
                alien.get(alienLoad).setX(xpos += 40.0);
                alien.get(alienLoad).setY(ypos);
                alienLoad++;
            } // for
        } // for
        // set up bullet
        for (Bullet var : bullet) {
            getChildren().add(var);
            var.setX(player.getX());
            var.setY(player.getY());
            System.out.println(var);
            var.setVisible(false);
        } // for each
        // set up alien bullet
        for (ABullet var : aBullet) {
            getChildren().add(var);
            var.setX(-1000);
            var.setY(-1000);
            var.setVisible(false);
            startTime2 = System.currentTimeMillis() + 1000;
        } // for each
    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {
        Bounds playerBounds = player.getBoundsInParent();
        Bounds gameBounds = getGameBounds();
        startTime = System.currentTimeMillis();
        // update player position
        isKeyPressed(KeyCode.LEFT, () -> {
            if (playerBounds.getMinX() > gameBounds.getMinX()) {
                player.setX(player.getX() - 1.5);
            } // if
        }); // lambda KeyCode
        isKeyPressed(KeyCode.RIGHT, () -> {
            if (playerBounds.getMaxX() < gameBounds.getMaxX()) {
                player.setX(player.getX() + 1.5);
            } // if
        }); // lambda KeyCode
        // Bullet update
        if (startTime > startTime2) {
            startTime2 = System.currentTimeMillis() + 1000;
            isKeyPressed(KeyCode.UP,() -> {
                if (global == bullet.length) {
                    global = 0;
                } // if
                bullet[global].update();
                bullet[global].setX(player.getX() + 10);
                bullet[global].setY(player.getY());
                bullet[global].setVisible(true);
                global++;
            });
        } // if
        travelBullet();
        bulletCheck();
        //alien position update
        updateAlien();
        alienCheck();
        // Alien bullet update
        shootABullet();
        timedShoot();
        aBulletCheck();
        checkWon();
        checkLost();
        resetABulletVis();
        //System.out.println(aliensDestroyed);
    } // update

    /**
     * method to reset bullets.
     */
    public void resetABulletVis() {
        for (ABullet var : aBullet) {
            if (var.isVisible() == true && var.getY() >= 380) {
                var.setVisible(false);
            } // if
        } // for
    } // resetABulletVis

    /**
     * check and see if game won.
     */
    private void checkWon() {
        if (aliensDestroyed == 32) {
            System.out.println("game won");
            app.showYouWon();
            stop();
        } // if
    } // checkWon

    /**
     * check see if lost.
     */
    private void checkLost() {
        for (Alien var : alien) {
            if (var.isVisible() && var.getBoundsInParent().getMaxY() >=
                player.getBoundsInParent().getMinY()) {
                app.showGameOver();
                //System.out.println("game1 lost");
                stop();
            } // if
        } // for
        if (player.isVisible() != true) {
            app.showGameOver();
            //System.out.println("game2 lost");
            stop();
        } // if
    } // checkLost

    /**
     * private method to time shoot.
     */
    private void timedShoot() {
        for (ABullet var : aBullet) {
            if (var.isVisible() != true) {
                if (startTime > endTime) {
                    var.update(randAlien());
                    var.shoot();
                    var.setVisible(true);
                    endTime += 500;
                } // if
            } // if
            startTime = System.currentTimeMillis();
        } // for
    } // timedShoot

    /**
     * rng generator.
     * @return random alien index of arraylist
     */
    private Alien randAlien() {
        int x = rng.nextInt(32);
        while (!alien.get(x).isVisible() && aliensDestroyed < alien.size()) {
            x = rng.nextInt(32);
        } //  while
        return alien.get(x);
    } // randAlien

    /**
     * private helper method to track each indiviusal bullet from
     * the array, help save space in main game loop.
     */
    private void travelBullet() {
        for (Bullet var : bullet) {
            if (var.isVisible() == true) {
                var.travel();
            } // if
        } // for
    } // shootBullet

    /**
     * helper method to check if each bullet connects to alien.
     */
    private void bulletCheck() {
        for (Bullet var : bullet) {
            for (Alien ble  : alien) {
                if (var.isVisible() == true && ble.isVisible() == true) {
                    if (var.getBoundsInParent().intersects(ble.getBoundsInParent())) {
                        var.setVisible(false);
                        ble.setVisible(false);
                        aliensDestroyed++;
                    } // if
                } // if
            } // for
        } // for
    } // bulletCheck

    /**
     * helper method to shoot each alien bullet.
     */
    private void shootABullet() {
        for (ABullet var : aBullet) {
            if (var.isVisible() == true) {
                var.shoot();
            } // if
        } // for
    } // shootABullet

    /**
     * private helper method to  check alien bullet collision.
     */
    private void aBulletCheck() {
        for (ABullet var: aBullet) {
            if (var.isVisible() && player.getBoundsInParent().intersects(var.getBoundsInParent())) {
                player.setVisible(false);
                //System.out.println("abulletCheck");
                //System.out.println(var.getBoundsInParent());
                //System.out.println(player.getBoundsInParent());
                stop();
            } // if
        } // for
    } // aBulletCheck

    /**
     * private helper method to move all aliens as a continues
     * block down the game screen.
     */
    private void updateAlien() {
        for (Alien var : alien) {
            var.update(alien);
        } // for
        for (Alien var: alien) {
            if (var.canTurn()) {
                for (Alien al: alien) {
                    al.turn();
                }
                break;
            }
        }
    } // updateAlien

    /**
     * private helper method to check alien bounds.
     */
    private void alienCheck() {
        for (Alien var : alien) {
            if (var.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (var.isVisible() == true) {
                    player.setVisible(false);
                    //System.out.println("AlienCheck");
                    stop();
                } // if
            } // if
        } // for
    } // alienCheck

} // SpaceInvaders
