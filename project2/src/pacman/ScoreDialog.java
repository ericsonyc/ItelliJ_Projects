package pacman;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class ScoreDialog {

    private String filename = "./about.txt";
    private TextArea area = null;
    private double width;
    private double height;

    public ScoreDialog(final Stage stg) {
        width = 300;
        height = 200;
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("About");
        Group root = new Group();
        AnchorPane anchorPane = new AnchorPane();
        area = new TextArea();
//        area.setPrefColumnCount(30);
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefWidth(width);
        area.setPrefHeight(height);
        System.out.println(area.getWidth());
        System.out.println(area.getHeight());
        Scene scene = new Scene(root, width, height);
        area.setText(getHelp(filename));
        anchorPane.getChildren().add(area);
        root.getChildren().add(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    private String getHelp(String filename) {
        File file = new File(filename);
        String result = "";
        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder("");
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp + "\n");
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result = "还没有帮助文件，请联系开发者添加！";
        }
        return result;
    }
}
