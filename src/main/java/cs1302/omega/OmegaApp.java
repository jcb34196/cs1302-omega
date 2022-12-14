package cs1302.omega;

import cs1302.game.DemoGame;
import cs1302.game.SpaceInvaders;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * the omega app triggers instance variable {@code SpaceInvaders} game.
 */
public class OmegaApp extends Application {

    private SpaceInvaders game;
    private Label notice;
    private ImageView banner;
    private Label instructions;
    private VBox root;
    private HBox button;
    private Button start;
    private Button quit;
    private Scene scene;

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // demonstrate how to load local asset using "file:resources/"
        ImageView titlePage = new ImageView("file:resources/readme-banner.png");
        titlePage.setPreserveRatio(true);
        titlePage.setFitWidth(640);
        button = new HBox();
        start = new Button("Start");
        quit = new Button("Quit");
        button.setStyle("-fx-background-color: black");
        button.getChildren().addAll(start, quit);
        start.setOnAction(event -> startGame());
        quit.setOnAction(event -> Platform.exit());


        // some labels to display information
        notice = new Label("Destroy all aliens! Dont let them reach you!!!");
        instructions
            = new Label("Move LEFT & RIGHT with arrow keys; press UP ARROW Key to shoot.");

        // demo game provided with the starter code
        game = new SpaceInvaders(640, 240);
        game.setStyle("-fx-background-color: black");
        game.setApplication(this);

        // setup scene
        root = new VBox(notice, instructions, game);
        scene = new Scene(root);

        // setup stage
        stage.setTitle("SPACE INVADERS!");
        stage.setScene(scene);
        stage.setMaxWidth(640);
        stage.setMinWidth(639);
        stage.setMaxHeight(416.5);
        stage.setMinHeight(410);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

        // play the game
        game.play();

    } // start

    /**
     * helper method to start game.
     */
    private void startGame() {
        root.getChildren().clear();
        game = new SpaceInvaders(640, 240);
        game.setApplication(this);
        game.setStyle("-fx-background-color: black");
        root.getChildren().addAll(notice, instructions, game);
        game.play();
    } // startGame

    /**
     * private method to be used in space invaders class
     * to display game end.
     */
    public void showGameOver() {
        ImageView gameOver = new ImageView("file:resources/sprites/Game_Over.jpg");
        gameOver.setPreserveRatio(false);
        gameOver.setFitWidth(640);
        gameOver.setFitHeight(360);
        root.getChildren().clear();
        start.setText("Play Again");
        root.getChildren().addAll(gameOver, button);
    } //showGameOver

    /**
     * private method to use in space invaders class
     * to show you won screen.
     */
    public void showYouWon() {
        ImageView youWon = new ImageView("file:resources/sprites/Win_Screen.png");
        youWon.setPreserveRatio(false);
        youWon.setFitWidth(640);
        youWon.setFitHeight(360);
        root.getChildren().clear();
        start.setText("Play Again");
        root.getChildren().addAll(youWon, button);

    } // showYouWon
} // OmegaApp
