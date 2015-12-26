package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main.fx created on 2008-12-20, 12:02:26 <br>
 * Main.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pac-Man");
        primaryStage.setWidth(MazeData.calcGridX(MazeData.GRID_SIZE_X + 2));
        primaryStage.setHeight(MazeData.calcGridY(MazeData.GRID_SIZE_Y + 4));
//    scene: Scene{
//            content: [ Maze {}
//            ]
//           }

        final Group root = new Group();
        final Scene scene = new Scene(root);
        root.getChildren().add(new Maze());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
