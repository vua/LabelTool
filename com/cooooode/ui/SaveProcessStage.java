package com.cooooode.ui;

import com.cooooode.utils.UiUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SaveProcessStage {
    public static Stage createLabelSelectStage(int number,String ...args){
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.setTitle("Save Progress");
        Label label1=new Label("Template Style : "+args[0]);
        Label label2=new Label("Preservation Mode : "+args[1]);
        Label label3=new Label("File Format : "+args[2]);
        Label label4=new Label("Total Number Of Saved Labels : "+number);

        Label label5=new Label("Tip : The window will be automatically closed within 3 seconds after saving.");

        VBox vBox=new VBox();
        vBox.getChildren().addAll(label1, label2, label3,label4,label5);
        for(Node node:vBox.getChildren()){
            node.setStyle("-fx-padding: 2 5 0 5");
        }
        label5.setStyle("-fx-text-fill: #FF4040;" +
                "-fx-padding: 5");
        stage.setScene(new Scene(vBox));


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        UiUtils.center(false,stage);
        return stage;
    }
}
