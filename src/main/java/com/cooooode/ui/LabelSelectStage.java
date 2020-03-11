package com.cooooode.ui;

import com.cooooode.event.ButtonEvent;
import com.cooooode.event.LabelEvent;
import com.cooooode.event.TextFieldEvent;
import com.cooooode.utils.UiUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LabelSelectStage {
    private LabelSelectStage() {
    }

    private static class LabelSelectStageInstance {
        private static final LabelSelectStage INSTANCE = new LabelSelectStage();
    }

    public static LabelSelectStage getInstance() {
        return LabelSelectStageInstance.INSTANCE;
    }

    public static void createLabelSelectStage(double xmax, double ymax) {

        Stage stage = new Stage();

        stage.setX(xmax + App.marginX + 160);
        stage.setY(ymax + App.marginY + 30);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setHeight(220);
        stage.setWidth(205);
        stage.setResizable(false);
        ScrollPane scrollPane = new ScrollPane();
        Label label = new Label("Label  ");

        TextField labelvalue = new TextField();
        labelvalue.setPrefWidth(100);
        GridPane gridPane = new GridPane();
        Button ok = new Button("Ok");
        ok.setOnMouseClicked(event -> {
            ButtonEvent.confirmLabel(labelvalue, stage);
        });
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5, 1, 5, 1));
        gridPane.add(label, 0, 0);
        gridPane.add(labelvalue, 1, 0);
        gridPane.add(ok, 2, 0);
        gridPane.setVgap(2);
        updateLabel(gridPane, labelvalue);
        scrollPane.setContent(gridPane);
        stage.setScene(new Scene(scrollPane));
        stage.setTitle("Label Setter");
        stage.getIcons().add(new Image("LabelTool.png"));
        stage.show();
        labelvalue.setOnKeyPressed((event) -> {
            TextFieldEvent.setLabel(event, labelvalue, stage);
        });
        stage.setOnCloseRequest(event -> {
            UiUtils.removeLastOne();
            UiUtils.repaint();
        });

    }

    public static void updateLabel(GridPane gridPane, TextField labelvalue) {
        int rowIdx = 0;
        for (String l : App.set) {
            rowIdx++;
            Label item = new Label("  " + l);
            item.setPrefWidth(170);
            item.setPrefHeight(20);
            item.setStyle("-fx-background-color: #fff;" +
                    "-fx-border-color:#F5F5F5;" +
                    "-fx-border-style: SOLID;"
            );
            item.setOnMouseEntered((event) -> {
                LabelEvent.hover(item);
                LabelEvent.clickLabel(item, labelvalue);
            });
            gridPane.add(item, 0, rowIdx, 3, 1);

        }
    }
}
