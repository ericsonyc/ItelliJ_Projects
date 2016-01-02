package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

import java.io.File;


public class DyingPacMan extends Arc {

    private final Timeline timeline;
    private Media dyingMedia;
    private MediaPlayer dyingMediaPlayer;

    public DyingPacMan(final Maze maze) {

        timeline = new Timeline();
        timeline.setCycleCount(1);
        String path = System.getProperty("user.dir");
        String path1 = new File(path + "\\src\\pacman\\music\\dyingpacman.mp3").toURI().toString();
        dyingMedia = new Media(path1);
        dyingMediaPlayer = new MediaPlayer(dyingMedia);
        dyingMediaPlayer.setCycleCount(2);
        dyingMediaPlayer.stop();

        KeyFrame kf1 =
                new KeyFrame(Duration.millis(600),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                maze.pacMan.setVisible(false);

                                for (Ghost g : maze.ghosts) {
                                    g.hide();
                                }
                                maze.backgroundMediaPlayer.pause();
                                DyingPacMan.this.setVisible(true);
                                dyingMediaPlayer.play();
                            }

                        },
                        new KeyValue(startAngleProperty(), 90),
                        new KeyValue(lengthProperty(), 360)
                );

        KeyFrame kf2 =
                new KeyFrame(Duration.millis(1500),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                setVisible(false);
//                                Label label=(Label) (maze.menuBar.getMenus().get(0).getGraphic());
//                                label.setText("开始(P)");
                                maze.startNewLife();
                                dyingMediaPlayer.stop();
                                maze.backgroundMediaPlayer.play();
                            }

                        },
                        new KeyValue(startAngleProperty(), 270),
                        new KeyValue(lengthProperty(), 0)
                );


        timeline.getKeyFrames().addAll(kf1, kf2);
    }

    public void pause() {
        timeline.pause();
    }

    public void start() {
        timeline.play();
    }

    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    public boolean isPaused() {
        return timeline.getStatus() == Animation.Status.PAUSED;
    }

    public void startAnimation(int x, int y) {

        setStartAngle(90);
        setCenterX(x);
        setCenterY(y);

        timeline.playFromStart();
    }

}
