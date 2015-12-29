package pacman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class ScoreDialog extends Dialog {

    private ListView<String> listView = null;
    private double width;
    private double height;

    public ScoreDialog(final Stage stg) {
        width = 300;
        height = 200;
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("Top Scores");
        Group root = new Group();
        AnchorPane anchorPane = new AnchorPane();
        listView = new ListView<String>();
        listView.setPrefWidth(width);
        listView.setPrefHeight(height);
        listView.setEditable(false);
        List<String> list = getList();
        ObservableList<String> items = FXCollections.observableArrayList(list);
        listView.setItems(items);
        Scene scene = new Scene(root, width, height);
        anchorPane.getChildren().add(listView);
        root.getChildren().add(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    private List<String> getList() {
        List<String> list = new ArrayList<String>();
        for (int i = MazeData.queue.size() - 1; i >= 0; i--) {
            TopScore top = MazeData.queue.get(i);
            if (top.getScore() > 0)
                list.add("姓名：" + top.getName() + "   得分：" + top.getScore());
            else
                break;
        }
        return list;
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
