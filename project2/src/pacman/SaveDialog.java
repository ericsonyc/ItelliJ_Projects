package pacman;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class SaveDialog {

    private TextArea area = null;
    private double width;
    private double height;

    public SaveDialog(final Stage stg) {
        width = 300;
        height = 100;
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("Save");
        Group root = new Group();
        AnchorPane anchorPane = new AnchorPane();
        area = new TextArea();
        area.setFont(new Font(30));
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefWidth(width);
        area.setPrefHeight(height);
        System.out.println(area.getWidth());
        System.out.println(area.getHeight());
        Scene scene = new Scene(root, width, height);
        area.setText("请开始游戏后，再保存进度！！！");
        anchorPane.getChildren().add(area);
        root.getChildren().add(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
