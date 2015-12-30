package pacman;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.File;

public class Main extends Application {

    private Maze maze = null;
    private MenuBar menuBar;
    private Stage primeStage;
    int barHeight = 25;
    private Group root;
    private boolean pauseFlag = true;
    private boolean firstStart = true;


    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primeStage = primaryStage;
        primaryStage.setTitle("PacMan");//设置窗体标题

        root = new Group();

        double width = MazeData.calcGridX(MazeData.GRID_SIZE_X + 2) + 14;
        menuBar = getMenuBar(width, barHeight);
        double height = MazeData.calcGridY(MazeData.GRID_SIZE_Y + 4) + barHeight + 23;

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        final Scene scene = new Scene(root);

        root.getChildren().add(menuBar);

        maze = new Maze(menuBar, primaryStage);
        maze.setLayoutX(0);
        maze.setLayoutY(barHeight);
        root.getChildren().add(maze);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void pauseClick() {
//        menuBar.setFocusTraversable(false);
//        maze.setOuterFocus(true);
        Label labelPause = (Label) (menuBar.getMenus().get(0).getGraphic());
        if (firstStart && maze.waitForStart.get()) {
            firstStart = false;
            labelPause.setText("暂停(P)");
            maze.startNewGame();
            maze.waitForStart.setValue(false);
        } else {
            if (pauseFlag) {
                labelPause.setText("开始(P)");
                pauseFlag = !pauseFlag;
                maze.pauseGame();
            } else {
                labelPause.setText("暂停(P)");
                pauseFlag = !pauseFlag;
                maze.resumeGame();
            }
        }
    }

    private MenuBar getMenuBar(double width, double height) {
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(width);
        menuBar.setPrefHeight(height);
        Label labelPause = new Label("开始(P)");
        Label labelPlayAgain = new Label("重玩(N)");
        Label labelSave = new Label("存档(X)");
        Label labelRead = new Label("读档(R)");
        Label labelScore = new Label("排行榜(T)");
        Label labelHelp = new Label("帮助(H)");
        Label labelExit = new Label("退出(E)");

        labelPause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pauseClick();
            }
        });

        labelPause.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("Hello");
                if (event.getCode() == KeyCode.ENTER) {
                    pauseClick();
                } else {
                    maze.onKeyPressed(event);
                }
            }
        });

        labelPlayAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PlayAgainClick();
            }
        });

        labelPlayAgain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    PlayAgainClick();
                } else {
                    maze.onKeyPressed(event);
                }
            }
        });

        labelSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Menu Save");
                maze.pauseGame();
                if (maze.waitForStart.get()) {
                    SaveDialog dialog = new SaveDialog(primeStage, "请开始游戏后，再保存进度！！！");
                    return;
                }
                FileChooser chooser = new FileChooser();
                File file = chooser.showSaveDialog(primeStage);
                if (file != null)
                    MazeData.saveData(file.getAbsolutePath(), maze);
            }
        });

        labelRead.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maze.pauseGame();
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primeStage);
                if (file != null) {
                    MazeData.readData(file.getAbsolutePath(), maze);
                    file.delete();
                }
            }
        });

        labelScore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maze.getTopScores();
            }
        });

        labelHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HelpDialog help = new HelpDialog(primeStage);
            }
        });

        labelExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    primeStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Menu menuPause = new Menu();
        menuPause.setGraphic(labelPause);
        Menu menuPalyAgain = new Menu();
        menuPalyAgain.setGraphic(labelPlayAgain);
        Menu menuSave = new Menu();
        menuSave.setGraphic(labelSave);
        Menu menuRead = new Menu();
        menuRead.setGraphic(labelRead);
        Menu menuExit = new Menu();
        menuExit.setGraphic(labelExit);
        Menu menuHelp = new Menu();
        menuHelp.setGraphic(labelHelp);
        Menu menuScore = new Menu();
        menuScore.setGraphic(labelScore);
        menuBar.getMenus().addAll(menuPause, menuPalyAgain, menuSave, menuRead, menuScore, menuHelp, menuExit);
//        menuBar.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("filterevent");
//                maze.onKeyPressed(event);
//            }
//        });
        return menuBar;
    }

    private void PlayAgainClick() {
        if (maze.waitForStart.get()) {
            return;
        }
        Label labelPause = (Label) (menuBar.getMenus().get(0).getGraphic());
        labelPause.setText("开始(P)");
        firstStart = true;
        pauseFlag = true;
        maze.startNewGame();
        maze.gamePaused.set(false);
        maze.pauseGame();
        maze.waitForStart.set(true);
        maze.gamePaused.set(false);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
