package pacman;

//import javafx.scene.CustomNode;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;


public class Maze extends Parent {

    public static final boolean DEBUG = false;

    // 吃掉ghosts的数量
    private int ghostEatenCount;

    public MenuBar menuBar;

    private Stage primaryStg;

    public BooleanProperty gamePaused;//游戏暂停标志

    //吃掉一个ghost显示的分数文本
    private static final ScoreText[] SCORE_TEXT = {
            new ScoreText("100", false),
            new ScoreText("100", false),
            new ScoreText("100", false),
            new ScoreText("100", false)
    };

    //pacman对象
    public PacMan pacMan;

    public final Ghost[] ghosts;//保存ghosts的数组

    private final DyingPacMan dyingPacMan;//pacman消失对象

    // pacman的图像
    private static final Image PACMAN_IMAGE = new Image(Maze.class.getResourceAsStream("images/left1.png"));

    // 保存第几关
    private final SimpleIntegerProperty level;

    // 增加功能，当分数大于10000分就加一条命
    private boolean addLifeFlag;

    //当前pacman的生命个数
    private final SimpleIntegerProperty livesCount;

    // 标志游戏是否才开始
    public BooleanProperty waitForStart;

    private final Group messageBox;

    // 标志pacman是否win
    private final BooleanProperty lastGameResult;

    // 游戏赢的文本
    private final Text gameResultText;

    private int flashingCount;

    private final Timeline flashingTimeline;

    private final Group group;

    public Maze(MenuBar menuBar, Stage primaryStg) {
        this.menuBar = menuBar;
        this.primaryStg = primaryStg;
        this.setFocused(true);//设置焦点

        gamePaused = new SimpleBooleanProperty(false);//游戏暂停标志位，开始为false

        pacMan = new PacMan(this, 15, 24);//初始化pacman

        /**
         * 下面声明了4个ghosts
         */
        final Ghost ghostBlinky = new Ghost(
                new Image(getClass().getResourceAsStream("images/ghostred1.png")),
                new Image(getClass().getResourceAsStream("images/ghostred2.png")),
                this,
                pacMan,
                15,
                14,
                0,
                -1,
                1);

        final Ghost ghostPinky = new Ghost(
                new Image(getClass().getResourceAsStream("images/ghostpink1.png")),
                new Image(getClass().getResourceAsStream("images/ghostpink2.png")),
                this,
                pacMan,
                14,
                15,
                1,
                0,
                10);

        final Ghost ghostInky = new Ghost(
                new Image(getClass().getResourceAsStream("images/ghostcyan1.png")),
                new Image(getClass().getResourceAsStream("images/ghostcyan2.png")),
                this,
                pacMan,
                12,
                15,
                1,
                0,
                20);

        final Ghost ghostClyde = new Ghost(
                new Image(getClass().getResourceAsStream("images/ghostorange1.png")),
                new Image(getClass().getResourceAsStream("images/ghostorange2.png")),
                this,
                pacMan,
                16,
                15,
                1,
                0,
                30);

        ghosts = new Ghost[]{ghostBlinky, ghostPinky, ghostInky, ghostClyde};

        dyingPacMan = new DyingPacMan(this);
        dyingPacMan.setCenterX(0);
        dyingPacMan.setCenterY(0);
        dyingPacMan.setRadiusX(13);
        dyingPacMan.setRadiusY(13);
        dyingPacMan.setStartAngle(90);
        dyingPacMan.setLength(360);
        dyingPacMan.setType(ArcType.ROUND);
        dyingPacMan.setFill(Color.YELLOW);
        dyingPacMan.setVisible(false);

        livesCount = new SimpleIntegerProperty(2);

        // 显示pacman还有多少生命剩余的图片

        final ImageView livesImage1 = new ImageView(PACMAN_IMAGE);
        livesImage1.setX(MazeData.calcGridX(18));
        livesImage1.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage1.visibleProperty().bind(livesCount.greaterThan(0));
        livesImage1.setCache(true);
        final ImageView livesImage2 = new ImageView(PACMAN_IMAGE);
        livesImage2.setX(MazeData.calcGridX(16));
        livesImage2.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage2.visibleProperty().bind(livesCount.greaterThan(1));
        livesImage2.setCache(true);
        final ImageView livesImage3 = new ImageView(PACMAN_IMAGE);
        livesImage3.setX(MazeData.calcGridX(14));
        livesImage3.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage3.visibleProperty().bind(livesCount.greaterThan(2));
        livesImage3.setCache(true);
        final ImageView[] livesImage = new ImageView[]{livesImage1, livesImage2, livesImage3};


        level = new SimpleIntegerProperty(1);
        addLifeFlag = true;
        waitForStart = new SimpleBooleanProperty(true);

        messageBox = new Group();
        final Rectangle rectMessage = new Rectangle(MazeData.calcGridX(5),
                MazeData.calcGridYFloat(17.5f),
                MazeData.GRID_GAP * 19,
                MazeData.GRID_GAP * 5);
        rectMessage.setStroke(Color.RED);
        rectMessage.setStrokeWidth(5);
        rectMessage.setFill(Color.CYAN);
        rectMessage.setOpacity(0.75);
        rectMessage.setArcWidth(25);
        rectMessage.setArcHeight(25);

        final StringBinding messageBinding = new StringBinding() {

            {
                super.bind(gamePaused);
            }

            @Override
            protected String computeValue() {
                if (gamePaused.get()) {
                    return "   按 P 开始游戏！ ";
                } else {
                    return "   按任意键开始游戏！ ";
                }
            }
        };

        final Text textMessage = new Text(MazeData.calcGridX(6),
                MazeData.calcGridYFloat(20.5f),
                "    按任意键开始游戏！ ");
        textMessage.textProperty().bind(messageBinding);
        textMessage.setFont(new Font(18));
        textMessage.setFill(Color.RED);
        messageBox.getChildren().add(rectMessage);
        messageBox.getChildren().add(textMessage);

        lastGameResult = new SimpleBooleanProperty(false);

        final StringBinding lastGameResultBinding = new StringBinding() {

            {
                super.bind(lastGameResult);
            }

            @Override
            protected String computeValue() {
                if (lastGameResult.get()) {
                    return "  YOU WIN ";
                } else {
                    return "GAME OVER   ";
                }
            }
        };

        gameResultText = new Text(MazeData.calcGridX(11),
                MazeData.calcGridY(11) + 8,
                " YOU WIN ");
        gameResultText.textProperty().bind(lastGameResultBinding);
        gameResultText.setFont(new Font(20));
        gameResultText.setFill(Color.RED);
        gameResultText.setVisible(false);

        flashingCount = 0;

        flashingTimeline = new Timeline();
        flashingTimeline.setCycleCount(5);
        final KeyFrame kf = new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gameResultText.setVisible(!gameResultText.isVisible());
                if (gameResultText.isVisible()) {
                    Label label = (Label) menuBar.getMenus().get(0).getGraphic();
                    label.setText("开始(P)");
                }
                if (++flashingCount == 5) {
                    messageBox.setVisible(true);
                    waitForStart.set(true);
                }
            }

        });
        flashingTimeline.getKeyFrames().add(kf);

        group = new Group();

        // 设置背景矩形
        final Rectangle blackBackground = new Rectangle(0, 0,
                MazeData.calcGridX(MazeData.GRID_SIZE_X + 2),
                MazeData.calcGridY(MazeData.GRID_SIZE_Y + 3));
        blackBackground.setFill(Color.BLACK);
        blackBackground.setCache(true);
        group.getChildren().add(blackBackground);

        // 墙壁矩形
        group.getChildren().add(new WallRectangle(0, 0, MazeData.GRID_SIZE_X, MazeData.GRID_SIZE_Y));

        group.getChildren().add(new WallRectangle(14, -0.5f, 15, 4));
        group.getChildren().add(new WallBlackRectangle(13.8f, -1, 15.3f, 0));

        group.getChildren().add(new WallRectangle(2, 2, 5, 4)); // upper-left rectangle
        group.getChildren().add(new WallRectangle(7, 2, 12, 4)); // upper 2nd-from-left rectangle
        group.getChildren().add(new WallRectangle(17, 2, 22, 4)); // upper 2nd-from-right rectangle
        group.getChildren().add(new WallRectangle(24, 2, 27, 4)); // upper-right rectangle
        group.getChildren().add(new WallRectangle(2, 6, 5, 7)); // left-side 2nd from top rectangle

        group.getChildren().add(new WallRectangle(14, 6, 15, 10));
        group.getChildren().add(new WallRectangle(10, 6, 19, 7));
        group.getChildren().add(new WallBlackLine(14.05f, 7, 14.95f, 7));

        group.getChildren().add(new WallRectangle(7.5f, 9, 12, 10));
        group.getChildren().add(new WallRectangle(7, 6, 8, 13));
        group.getChildren().add(new WallBlackLine(8, 9, 8, 10));

        group.getChildren().add(new WallRectangle(17, 9, 21.5f, 10));
        group.getChildren().add(new WallRectangle(21, 6, 22, 13));
        group.getChildren().add(new WallBlackLine(21, 9, 21, 10));

        group.getChildren().add(new WallRectangle(24, 6, 27, 7)); // right-side 2nd from top rectangle

        group.getChildren().add(new WallRectangle(10, 12, 19, 17));
        group.getChildren().add(new WallRectangle(10.5f, 12.5f, 18.5f, 16.5f));
        final Rectangle cageRect = new Rectangle(MazeData.calcGridX(13),
                MazeData.calcGridY(12),
                MazeData.GRID_GAP * 3,
                MazeData.GRID_GAP / 2);
        cageRect.setStroke(Color.GREY);
        cageRect.setFill(Color.GREY);
        cageRect.setCache(true);
        group.getChildren().add(cageRect);

        group.getChildren().add(new WallRectangle(7, 15, 8, 20));

        group.getChildren().add(new WallRectangle(21, 15, 22, 20));

        group.getChildren().add(new WallRectangle(14, 19, 15, 23));
        group.getChildren().add(new WallRectangle(10, 19, 19, 20));
        group.getChildren().add(new WallBlackLine(14.05f, 20, 14.95f, 20));

        group.getChildren().add(new WallRectangle(4, 22, 5, 26));
        group.getChildren().add(new WallRectangle(2, 22, 5, 23));
        group.getChildren().add(new WallBlackRectangle(4, 22.05f, 5, 23.2f));

        group.getChildren().add(new WallRectangle(7, 22, 12, 23)); // left lower horizontal rectangle

        group.getChildren().add(new WallRectangle(24, 22, 25, 26));
        group.getChildren().add(new WallRectangle(24, 22, 27, 23));
        group.getChildren().add(new WallBlackRectangle(24, 22.05f, 25, 23.2f));

        group.getChildren().add(new WallRectangle(17, 22, 22, 23)); // right lower horizontal rectangle

        group.getChildren().add(new WallRectangle(-1, 25, 2, 26)); // left horizontal wall
        group.getChildren().add(new WallRectangle(27, 25, MazeData.GRID_SIZE_X + 1, 26)); // right horizontal wall

        // left bottom upside-down T
        group.getChildren().add(new WallRectangle(7, 25, 8, 29));
        group.getChildren().add(new WallRectangle(2, 28, 12, 29));
        group.getChildren().add(new WallBlackLine(7.05f, 28, 7.95f, 28));

        // lower middle T
        group.getChildren().add(new WallRectangle(14, 25, 15, 29));
        group.getChildren().add(new WallRectangle(10, 25, 19, 26));
        group.getChildren().add(new WallBlackLine(14.05f, 26, 14.95f, 26));

        // right bottom upside-down T
        group.getChildren().add(new WallRectangle(21, 25, 22, 29));
        group.getChildren().add(new WallRectangle(17, 28, 27, 29));
        group.getChildren().add(new WallBlackLine(21.05f, 28, 21.95f, 28));

        // Outer border of outside wall
        final Rectangle outerWall = new Rectangle(MazeData.calcGridXFloat(-0.5f),
                MazeData.calcGridYFloat(-0.5f),
                (MazeData.GRID_SIZE_X + 1) * MazeData.GRID_GAP,
                (MazeData.GRID_SIZE_Y + 1) * MazeData.GRID_GAP);
        outerWall.setStrokeWidth(MazeData.GRID_STROKE);
        outerWall.setStroke(Color.BLUE);
        outerWall.setFill(null);
        outerWall.setArcWidth(12);
        outerWall.setArcHeight(12);
        outerWall.setCache(true);
        group.getChildren().add(outerWall);

        group.getChildren().add(new WallRectangle(-1, 9, 5, 13)); // outer wall above left side door
        group.getChildren().add(new WallRectangle(-1, 9.5f, 4.5f, 12.5f)); // inner wall above left side door
        group.getChildren().add(new WallRectangle(-1, 15, 5, 20)); // outer wall below left side wall
        group.getChildren().add(new WallRectangle(-1, 15.5f, 4.5f, 19.5f)); // inner wall below left side door wall

        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 9, MazeData.GRID_SIZE_X + 1, 13)); // outer wall above right side door
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 9.5f, MazeData.GRID_SIZE_X + 1, 12.5f)); // inner wall above right side door
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 15, MazeData.GRID_SIZE_X + 1, 20)); // outer wall below right side wall
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 15.5f, MazeData.GRID_SIZE_X + 1, 19.5f)); // inner wall below right side door wall

        group.getChildren().add(new WallBlackRectangle(-2, 8, -0.5f, MazeData.GRID_SIZE_Y)); // black-out left garbage outside the wall
        group.getChildren().add(new WallBlackRectangle(-0.5f, 8, 0, 9.5f)); // black-out horizontal line inside outer-left wall above side door
        group.getChildren().add(new WallBlackRectangle(-0.5f, 19.5f, 0, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-left wall below side door

        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X + 0.5f, 8, MazeData.GRID_SIZE_X + 2, MazeData.GRID_SIZE_Y)); // black-out garbage on right side of outside wall
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 8, MazeData.GRID_SIZE_X + 0.5f, 9.5f)); // black-out horizontal line inside outer-right wall above side door
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 19.5f, MazeData.GRID_SIZE_X + 0.5f, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-right wall below side door

        // black-out outer walls inside both side doors
        group.getChildren().add(new WallBlackRectangle(-1, 13, 1, 15)); // left
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X - 1, 13, MazeData.GRID_SIZE_X + 1, 15)); // right

        // Add back 4 blue wall segments that were deleted
        group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 9, -0.5f, 9.5f));
        group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 19.5f, -0.5f, 20));
        group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 9, MazeData.GRID_SIZE_X + 0.5f, 9.5f));
        group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 19.5f, MazeData.GRID_SIZE_X + 0.5f, 20));

        final Text textScore = new Text(MazeData.calcGridX(0),
                MazeData.calcGridY(MazeData.GRID_SIZE_Y + 2),
                "分数: " + pacMan.score);
        textScore.textProperty().bind(pacMan.score.asString("分数: %1d  "));
        textScore.setFont(new Font(20));
        textScore.setFill(Color.YELLOW);
        textScore.setCache(true);
        group.getChildren().add(textScore);

        group.getChildren().addAll(SCORE_TEXT);
        group.getChildren().add(dyingPacMan);

        final Text textLife = new Text(MazeData.calcGridX(10), MazeData.calcGridY(MazeData.GRID_SIZE_Y + 2), "生命值：");
        textLife.textProperty().bind(livesCount.asString("生命值："));
        textLife.setFont(new Font(20));
        textLife.setFill(Color.YELLOW);
        textLife.setCache(true);
        group.getChildren().add(textLife);

        group.getChildren().addAll(livesImage);
        group.getChildren().add(gameResultText);

        final Text textLevel = new Text(MazeData.calcGridX(22),
                MazeData.calcGridY(MazeData.GRID_SIZE_Y + 2),
                "级别: " + level);
        textLevel.textProperty().bind(level.asString("级别: %1d "));
        textLevel.setFont(new Font(20));
        textLevel.setFill(Color.YELLOW);
        textLevel.setCache(true);
        group.getChildren().add(textLevel);
        group.setFocusTraversable(true);
        group.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                onKeyPressed(ke);
            }
        });

        putDots();

        group.getChildren().add(pacMan);

        group.getChildren().addAll(ghosts);

        group.getChildren().add(new WallBlackRectangle(-2, 13, -0.5f, 15));
        group.getChildren().add(new WallBlackRectangle(29.5f, 13, 31, 15));

        group.getChildren().add(messageBox);


        getChildren().add(group);

        if (DEBUG) {
            MazeData.printData();
            MazeData.printDots();
        }
    }

    public String saveMaze() {
        StringBuilder sb = new StringBuilder();
        for (Ghost ghost : ghosts) {
            sb.append(ghost.preseveStatus() + '\n');
        }
        sb.append(pacMan.preservePacman() + "\n");
        sb.append(level.get() + "\n");
        sb.append(livesCount.get());
        return sb.toString();
    }

    public void setMaze(String filename) {
        try {
            this.startNewGame();
            this.pauseGame();
            BufferedReader br = new BufferedReader(new FileReader(filename));
            for (Ghost ghost : ghosts) {
                String[] value = br.readLine().split(",");
                ghost.setStatus(value);
                ghost.start();
            }
            String[] value = br.readLine().split(",");
            pacMan.setPacmanStatus(value);
            pacMan.setKeyboardBuffer(-1);
            pacMan.start();
            messageBox.setVisible(true);
            String str = br.readLine().trim();
            level.set(Integer.parseInt(str));
            str = br.readLine().trim();
            livesCount.set(Integer.parseInt(str));
            for (int i = 0; i <= MazeData.GRID_SIZE_Y; i++) {
                value = br.readLine().split(",");
                for (int j = 0; j <= MazeData.GRID_SIZE_X; j++) {
                    if (null != MazeData.DOT_POINTERS[j][i]) {
                        ((Dot) MazeData.DOT_POINTERS[j][i]).setVisible(Boolean.parseBoolean(value[j]));
                    } else {

                    }
                }
            }
            br.close();
            this.waitForStart.set(false);
            this.pauseGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putDots() {
        putDotHorizontally(2, 12, 1);
        putDotHorizontally(17, 27, 1);

        putDotHorizontally(2, 27, 5);

        putDotHorizontally(2, 5, 8);
        putDotHorizontally(24, 27, 8);

        putDotHorizontally(10, 13, 8);
        putDotHorizontally(16, 19, 8);

        putDotHorizontally(2, 12, 21);
        putDotHorizontally(17, 27, 21);

        putDotHorizontally(2, 2, 24);
        putDotHorizontally(27, 27, 24);

        putDotHorizontally(7, 12, 24);
        putDotHorizontally(17, 22, 24);

        putDotHorizontally(2, 5, 27);
        putDotHorizontally(24, 27, 27);

        putDotHorizontally(10, 12, 27);
        putDotHorizontally(17, 19, 27);

        putDotHorizontally(2, 27, 30); // bottom row


        putDotVertically(1, 1, 8);
        putDotVertically(28, 1, 8);

        putDotVertically(1, 21, 24);
        putDotVertically(28, 21, 24);

        putDotVertically(1, 27, 30);
        putDotVertically(28, 27, 30);

        putDotVertically(3, 24, 27);
        putDotVertically(26, 24, 27);

        putDotVertically(6, 1, 27);
        putDotVertically(23, 1, 27);

        putDotVertically(9, 5, 8);
        putDotVertically(20, 5, 8);

        putDotVertically(9, 24, 27);
        putDotVertically(20, 24, 27);

        putDotVertically(13, 1, 4);
        putDotVertically(16, 1, 4);

        putDotVertically(13, 21, 24);
        putDotVertically(16, 21, 24);

        putDotVertically(13, 27, 30);
        putDotVertically(16, 27, 30);
    }


    public void setOuterFocus(boolean flag) {
        this.setFocused(flag);
    }

    public void onKeyPressed(KeyEvent e) {

        if (e.getCode() == KeyCode.E) {
            primaryStg.close();
        }

        if (e.getCode() == KeyCode.N) {
            Label labelPause = (Label) (menuBar.getMenus().get(0).getGraphic());
            labelPause.setText("开始(P)");
            if (waitForStart.get())
                return;
            startNewGame();
            pauseGame();
            waitForStart.set(true);
            gamePaused.set(false);
            return;
        }

        if (e.getCode() == KeyCode.X) {
            if (waitForStart.get()) {
                SaveDialog dialog = new SaveDialog(primaryStg);
                return;
            }
            pauseGame();
            FileChooser chooser = new FileChooser();
            File file = chooser.showSaveDialog(primaryStg);
            if (file != null)
                MazeData.saveData(file.getAbsolutePath(), this);
            pauseGame();
        }

        if (e.getCode() == KeyCode.R) {
            pauseGame();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStg);
            if (file != null) {
                MazeData.readData(file.getAbsolutePath(), this);
//                file.delete();
            }
        }

        // wait for the player's keyboard input to start the game
        if (waitForStart.get()) {
            waitForStart.set(false);
            startNewGame();
            Label label = (Label) (menuBar.getMenus().get(0).getGraphic());
            label.setText("暂停(P)");
            return;
        }

        if (e.getCode() == KeyCode.P) {
            if (gamePaused.get()) {
                Label label = (Label) (menuBar.getMenus().get(0).getGraphic());
                label.setText("暂停(P)");
                resumeGame();
            } else {
                Label label = (Label) (menuBar.getMenus().get(0).getGraphic());
                label.setText("开始(P)");
                pauseGame();
            }

            return;
        }

        if (gamePaused.get()) {
            return;
        }

        if (e.getCode() == KeyCode.DOWN) {
            pacMan.setKeyboardBuffer(MovingObject.MOVE_DOWN);
        } else if (e.getCode() == KeyCode.UP) {
            pacMan.setKeyboardBuffer(MovingObject.MOVE_UP);
        } else if (e.getCode() == KeyCode.RIGHT) {
            pacMan.setKeyboardBuffer(MovingObject.MOVE_RIGHT);
        } else if (e.getCode() == KeyCode.LEFT) {
            pacMan.setKeyboardBuffer(MovingObject.MOVE_LEFT);
        }

    }


    // create a Dot GUI object
    public final Dot createDot(int x1, int y1, int type) {
        Dot d = new Dot(MazeData.calcGridX(x1), MazeData.calcGridY(y1), type);

        if (type == MazeData.BIG_DOT) {
            d.playTimeline();

            d.shouldStopAnimation.bind(gamePaused.or(waitForStart)); // patweb
        }

        // set the dot type in data model
        MazeData.setData(x1, y1, type);

        // set dot reference
        MazeData.setDot(x1, y1, d);

        return d;
    }

    // put dots into the maze as a horizontal line
    public final void putDotHorizontally(int x1, int x2, int y) {

        Dot dot;
        for (int x = x1; x <= x2; x++) {
            if (MazeData.getData(x, y) == MazeData.EMPTY) {
                int dotType;

                if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
                    dotType = MazeData.BIG_DOT;
                } else {
                    dotType = MazeData.NORMAL_DOT;
                }

                dot = createDot(x, y, dotType);

                // insert dots into group
                group.getChildren().add(dot);
            } else {
                if (DEBUG) {
                    System.out.println("!! WARNING: Trying to place horizontal dots at occupied position (" + x + ", " + y + ")");
                }
            }
        }
    }

    // put dots into the maze as a vertical line
    public final void putDotVertically(int x, int y1, int y2) {
        Dot dot;
        for (int y = y1; y <= y2; y++) {
            if (MazeData.getData(x, y) == MazeData.EMPTY) {
                int dotType;

                if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
                    dotType = MazeData.BIG_DOT;
                } else {
                    dotType = MazeData.NORMAL_DOT;
                }

                dot = createDot(x, y, dotType);
                group.getChildren().add(dot);
            } else {
                if (DEBUG) {
                    System.out.println("!! WARNING: Trying to place vertical   dots at occupied position (" + x + ", " + y + ")");
                }
            }
        }
    }


    public void makeGhostsHollow() {

        ghostEatenCount = 0;

        for (Ghost g : ghosts) {
            g.changeToHollowGhost();
        }
    }


    // determine if pacman meets a ghost
    public boolean hasMet(Ghost g) {

        int distanceThreshold = 22;

        int x1 = g.imageX.get();
        int x2 = pacMan.imageX.get();

        int diffX = Math.abs(x1 - x2);

        if (diffX >= distanceThreshold) {
            return false;
        }

        int y1 = g.imageY.get();
        int y2 = pacMan.imageY.get();
        int diffY = Math.abs(y1 - y2);

        if (diffY >= distanceThreshold) {
            return false;
        }

        // calculate the distance to see if pacman touches the ghost
        if (diffY * diffY + diffX * diffX <= distanceThreshold * distanceThreshold) {
            return true;
        }

        return false;
    }

    public void pacManMeetsGhosts() {

        for (Ghost g : ghosts) {
            if (hasMet(g)) {
                if (g.isHollow) {
                    pacManEatsGhost(g);
                } else {
                    for (Ghost ghost : ghosts) {
                        ghost.stop();
                    }
                    pacMan.stop();

                    dyingPacMan.startAnimation(pacMan.imageX.get(), pacMan.imageY.get());
                    break;
                }
            }
        }
    }

    public void pacManEatsGhost(Ghost g) {

        ghostEatenCount++;

        int multiplier = 1;
        for (int i = 1; i <= ghostEatenCount; i++) {
            multiplier += multiplier;
        }

        pacMan.score.set(pacMan.score.get() + multiplier * 100);
        if (addLifeFlag && (pacMan.score.get() >= 10000)) {
            addLife();
        }

        ScoreText st = SCORE_TEXT[ghostEatenCount - 1];
        st.setX(g.imageX.get() - 10);
        st.setY(g.imageY.get());

        g.stop();
        g.resetStatus();
        g.trapCounter = -10;

        st.showText();

    }

    public void resumeGame() {

        if (!gamePaused.get()) {
            return;
        }

        messageBox.setVisible(false);

        for (Ghost g : ghosts) {
            if (g.isPaused()) {
                g.start();
            }
        }

        if (pacMan.isPaused()) {
            pacMan.start();
        }

        if (dyingPacMan.isPaused()) {
            dyingPacMan.start();
        }

        if (flashingTimeline.getStatus() == Animation.Status.PAUSED) {
            flashingTimeline.play();
        }

        gamePaused.set(false);

    }

    public void pauseGame() {

        if (waitForStart.get() || gamePaused.get()) {
            return;
        }

        System.out.println("pause Game");
        messageBox.setVisible(true);

        for (Ghost g : ghosts) {
            if (g.isRunning()) {
                g.pause();
            }
        }
        System.out.println("pause Game ghosts");

        if (pacMan.isRunning()) {
            pacMan.pause();
        }

        if (dyingPacMan.isRunning()) {
            dyingPacMan.pause();
        }

        if (flashingTimeline.getStatus() == Animation.Status.RUNNING) {
            flashingTimeline.pause();
        }
        gamePaused.set(true);
    }


    // reset status and start a new game
    public void startNewGame() {

        messageBox.setVisible(false);
        pacMan.resetStatus();

        gameResultText.setVisible(false);

        if (!lastGameResult.get()) {
            level.set(1);
            addLifeFlag = true;
            pacMan.score.set(0);
            pacMan.dotEatenCount = 0;

            livesCount.set(2);
        } else {
            lastGameResult.set(false);
            level.set(level.get() + 1);
        }

        for (int x = 1; x <= MazeData.GRID_SIZE_X; x++) {
            for (int y = 1; y <= MazeData.GRID_SIZE_Y; y++) {
                Dot dot = (Dot) MazeData.getDot(x, y);

                if ((dot != null) && !dot.isVisible()) {
                    dot.setVisible(true);
                }
            }
        }
        for (Ghost g : ghosts) {
            g.resetStatus();
        }

    }

    // reset status and start a new level
    public void startNewLevel() {

        lastGameResult.set(true);

        pacMan.hide();
        pacMan.dotEatenCount = 0;

        for (Ghost g : ghosts) {
            g.hide();
        }

        flashingCount = 0;
        flashingTimeline.playFromStart();
    }

    // reset status and start a new life
    public void startNewLife() {
//        Label label=(Label)menuBar.getMenus().get(0).getGraphic();
//        label.setText("暂停(P)");
        // reduce a life of Pac-Man
        if (livesCount.get() > 0) {
            livesCount.set(livesCount.get() - 1);
        } else {
            lastGameResult.set(false);
            flashingCount = 0;
            flashingTimeline.playFromStart();
            return;
        }

        pacMan.resetStatus();

        for (Ghost g : ghosts) {
            g.resetStatus();
        }
    }

    public void addLife() {

        if (addLifeFlag) {
            livesCount.set(livesCount.get() + 1);
            addLifeFlag = false;
        }
    }

}
