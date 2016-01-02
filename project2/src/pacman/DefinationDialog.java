package pacman;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class DefinationDialog {

    private Maze maze = null;
    private Stage stage;

    private GridPane gridPane;

    private ToggleGroup toggleGroup1;
    private ToggleGroup toggleGroup2;
    private ToggleGroup toggleGroup3;
    private ToggleGroup toggleGroup4;
    private int blinky = 1;
    private int pinky = 1;
    private int inky = 1;
    private int clyde = 1;

    public DefinationDialog(final Stage stg, Maze maze) {
        this.maze = maze;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("Customize");
        Group root = new Group();
        gridPane = new GridPane();
        gridPane.setPrefHeight(200);
        gridPane.setPrefWidth(300);
        gridPane.setHgap(20);
        gridPane.setVgap(30);

        createBlinky(0);
        createPinky(1);
        createInky(2);
        createClyde(3);

        Button btn = new Button("确定");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toggle1 = ((RadioButton) toggleGroup1.getSelectedToggle()).getText();
                String toggle2 = ((RadioButton) toggleGroup2.getSelectedToggle()).getText();
                String toggle3 = ((RadioButton) toggleGroup3.getSelectedToggle()).getText();
                String toggle4 = ((RadioButton) toggleGroup4.getSelectedToggle()).getText();
//                System.out.println("toggle1:" + toggle1 + ",toggle2:" + toggle2 + ",toggle3:" + toggle3 + ",toggle4:" + toggle4);
                double[] rates = new double[4];
                rates[0] = Double.parseDouble(toggle1);
                rates[1] = Double.parseDouble(toggle2);
                rates[2] = Double.parseDouble(toggle3);
                rates[3] = Double.parseDouble(toggle4);
                maze.definationGhosts(rates);
                stage.close();
            }
        });

        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {

                }
            }
        });

//        gridPane.setRowIndex(btn,4);
//        gridPane.setColumnIndex(btn,1);
        gridPane.add(btn, 1, 4);

        Scene scene = new Scene(root);
        root.getChildren().add(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    private void createClyde(int row) {
        Label labelClyde = new Label("Clyde speed:");
        toggleGroup4 = new ToggleGroup();
        RadioButton radioClyde1 = new RadioButton("0.5");
        radioClyde1.setToggleGroup(toggleGroup4);
        radioClyde1.setSelected(false);
        RadioButton radioClyde2 = new RadioButton("1");
        radioClyde2.setToggleGroup(toggleGroup4);
        radioClyde2.setSelected(true);
        RadioButton radioClyde3 = new RadioButton("2");
        radioClyde3.setToggleGroup(toggleGroup4);
        radioClyde3.setSelected(false);

        gridPane.setRowIndex(labelClyde, row);
        gridPane.setColumnIndex(labelClyde, 0);
        gridPane.setRowIndex(radioClyde1, row);
        gridPane.setColumnIndex(radioClyde1, 1);
        gridPane.setRowIndex(radioClyde2, row);
        gridPane.setColumnIndex(radioClyde2, 2);
        gridPane.setRowIndex(radioClyde3, row);
        gridPane.setColumnIndex(radioClyde3, 3);
        gridPane.getChildren().addAll(labelClyde, radioClyde1, radioClyde2, radioClyde3);
    }

    private void createInky(int row) {
        Label labelInky = new Label("Inky speed:");
        toggleGroup3 = new ToggleGroup();
        RadioButton radioInky1 = new RadioButton("0.5");
        radioInky1.setToggleGroup(toggleGroup3);
        radioInky1.setSelected(false);
        RadioButton radioInky2 = new RadioButton("1");
        radioInky2.setToggleGroup(toggleGroup3);
        radioInky2.setSelected(true);
        RadioButton radioInky3 = new RadioButton("2");
        radioInky3.setToggleGroup(toggleGroup3);
        radioInky3.setSelected(false);

        gridPane.setRowIndex(labelInky, row);
        gridPane.setColumnIndex(labelInky, 0);
        gridPane.setRowIndex(radioInky1, row);
        gridPane.setColumnIndex(radioInky1, 1);
        gridPane.setRowIndex(radioInky2, row);
        gridPane.setColumnIndex(radioInky2, 2);
        gridPane.setRowIndex(radioInky3, row);
        gridPane.setColumnIndex(radioInky3, 3);
        gridPane.getChildren().addAll(labelInky, radioInky1, radioInky2, radioInky3);
    }

    private void createPinky(int row) {
        Label labelPinky = new Label("Pinky speed:");
        toggleGroup2 = new ToggleGroup();
        RadioButton radioPinky1 = new RadioButton("0.5");
        radioPinky1.setToggleGroup(toggleGroup2);
        radioPinky1.setSelected(false);
        RadioButton radioPinky2 = new RadioButton("1");
        radioPinky2.setToggleGroup(toggleGroup2);
        radioPinky2.setSelected(true);
        RadioButton radioPinky3 = new RadioButton("2");
        radioPinky3.setToggleGroup(toggleGroup2);
        radioPinky3.setSelected(false);

        gridPane.setRowIndex(labelPinky, row);
        gridPane.setColumnIndex(labelPinky, 0);
        gridPane.setRowIndex(radioPinky1, row);
        gridPane.setColumnIndex(radioPinky1, 1);
        gridPane.setRowIndex(radioPinky2, row);
        gridPane.setColumnIndex(radioPinky2, 2);
        gridPane.setRowIndex(radioPinky3, row);
        gridPane.setColumnIndex(radioPinky3, 3);
        gridPane.getChildren().addAll(labelPinky, radioPinky1, radioPinky2, radioPinky3);
    }

    private void createBlinky(int row) {
        Label labelBlinky = new Label("Blinky speed:");
        toggleGroup1 = new ToggleGroup();
        RadioButton radioBlinky1 = new RadioButton("0.5");
        radioBlinky1.setToggleGroup(toggleGroup1);
        radioBlinky1.setSelected(false);
        RadioButton radioBlinky2 = new RadioButton("1");
        radioBlinky2.setToggleGroup(toggleGroup1);
        radioBlinky2.setSelected(true);
        RadioButton radioBlinky3 = new RadioButton("2");
        radioBlinky3.setToggleGroup(toggleGroup1);
        radioBlinky3.setSelected(false);

        gridPane.setRowIndex(labelBlinky, row);
        gridPane.setColumnIndex(labelBlinky, 0);
        gridPane.setRowIndex(radioBlinky1, row);
        gridPane.setColumnIndex(radioBlinky1, 1);
        gridPane.setRowIndex(radioBlinky2, row);
        gridPane.setColumnIndex(radioBlinky2, 2);
        gridPane.setRowIndex(radioBlinky3, row);
        gridPane.setColumnIndex(radioBlinky3, 3);
        gridPane.getChildren().addAll(labelBlinky, radioBlinky1, radioBlinky2, radioBlinky3);
    }
}
