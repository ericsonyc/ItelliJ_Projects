package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Duration;

public abstract class MovingObject extends Parent {//移动物体的父类

    // animation frames total and movement distance
    protected static final int ANIMATION_STEP = 4;
    protected static final int MOVE_SPEED = MazeData.GRID_GAP / ANIMATION_STEP;

    protected static final int MOVING = 1;
    protected static final int STOPPED = 0;

    protected static final int MOVE_LEFT = 0;
    protected static final int MOVE_UP = 1;
    protected static final int MOVE_RIGHT = 2;
    protected static final int MOVE_DOWN = 3;

    protected Maze maze;
    protected int state;

    protected IntegerProperty currentImage;
    protected Image[] images;//保存pacman的图片，不同方向
    protected ObjectBinding imageBinding;
    protected int moveCounter;

    protected int x;//当前x坐标
    protected int y;//当前y坐标

    public IntegerProperty imageX;//设置移动物体图片x大小
    public IntegerProperty imageY;//设置移动物体图片y大小

    protected int xDirection;//x方向
    protected int yDirection;//y方向

    public Timeline timeline;

    public MovingObject() {
        currentImage = new SimpleIntegerProperty(0);

        imageBinding = new ObjectBinding() {

            {
                super.bind(currentImage);
            }

            @Override
            protected Image computeValue() {
                return images[currentImage.get()];
            }
        };

        moveCounter = 0;
        xDirection = 0;
        yDirection = 0;
        timeline = createTimeline();
    }

    public abstract void moveOneStep();

    private Timeline createTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                moveOneStep();
            }

        });
        timeline.getKeyFrames().add(kf);

        return timeline;
    }

    public void stop() {
        timeline.stop();
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

}
