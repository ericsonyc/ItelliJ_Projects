package pacman;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class NameDialog {

    private TextField field = null;
    private Button okbtn = null;
    private double width;
    private double height;
    public static BooleanProperty property = new SimpleBooleanProperty(false);
    public static String text = "";
    private Maze maze = null;
    private Stage stage;

    public NameDialog(final Stage stg, Maze maze) {
        width = 350;
        height = 80;
        this.maze = maze;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("Name:Press Enter Confirmed");
        Group root = new Group();
        AnchorPane anchorPane = new AnchorPane();
        okbtn = new Button("确定");
        field = new TextField();
        field.setFont(new Font(30));
        field.setPrefWidth(width);
        field.setPrefHeight(height);
        property.set(false);
        MyEvent myEvent = new MyEvent();
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    maze.handleEvent(field.getText());
                    stage.close();
                }
            }
        });
        okbtn.setOnMouseClicked(myEvent);
        Scene scene = new Scene(root, width, height + 20);
        anchorPane.getChildren().add(field);
        okbtn.setLayoutX(0);
        okbtn.setLayoutY(height);
        anchorPane.getChildren().add(okbtn);
        root.getChildren().add(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    class MyEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            maze.handleEvent(field.getText());
            stage.close();
        }
    }
}
