package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by ericson on 2015/12/27 0027.
 */
public class ModalDialog {
    Button btn;
    public ModalDialog(final Stage stg){
        btn=new Button();
        final Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stg);
        stage.setTitle("Top Stage With Modality");
        Group root=new Group();
        Scene scene=new Scene(root,300,250, Color.LIGHTGREEN);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("OK");
        root.getChildren().add(btn);
        stage.setScene(scene);
        stage.show();
    }
}
