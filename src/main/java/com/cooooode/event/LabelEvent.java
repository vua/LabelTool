package com.cooooode.event;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LabelEvent {
    public static void hover(Node label) {
        label.setStyle("-fx-background-color: #BFEFFF;" +
                "-fx-border-color:#BFEFFF;" +
                "-fx-border-style: SOLID;");
        label.setOnMouseExited((event1) -> {
            label.setStyle("-fx-background-color: #fff;" +
                    "-fx-border-color:#F5F5F5;" +
                    "-fx-border-style: SOLID;");
        });

    }

    public static void clickLabel(Label label, TextField labelvalue) {
        label.setOnMouseClicked((event) -> {
            labelvalue.setText(label.getText().trim());
        });
    }

}
