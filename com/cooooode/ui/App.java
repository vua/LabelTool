package com.cooooode.ui;

import com.cooooode.event.ButtonEvent;
import com.cooooode.event.CanvasEvent;
import com.cooooode.event.LabelEvent;
import com.cooooode.utils.UiUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class App extends Application {
    public static String path = "";
    public static String save_path = "src/resource/out";
    public final static String init_image_path = "src/resource/LabelTool.png";
    public final static String init_label_path = "src/resource/label.txt";
    public static FileInputStream is = null;
    public static double marginX, marginY;
    public static LinkedHashSet<String> set = new LinkedHashSet<>();
    public static List<String> paths = new ArrayList<>();
    public static List<String> allimagepaths = new ArrayList<>();
    public static List<List<String>> labels = new ArrayList<>();
    public static List<List<double[]>> locs = new ArrayList<>();
    public static List<Double> ratios = new ArrayList<>();
    public static List<Integer> widths = new ArrayList<>();
    public static List<Integer> heights = new ArrayList<>();
    public static List<List<String>> final5_8 = new ArrayList<>();
    public static List<List<String>> final4 = new ArrayList<>();
    public static List<List<int[]>> final0_3 = new ArrayList<>();
    public static String format = "pascal";
    public static String method = "alone";
    public static String type = ".xml";
    public static int index = 0;

    static {
        System.out.println("加载Label和Format配置文件");
        try {
            UiUtils.loadLabel(init_label_path);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static GraphicsContext ctx;
    public static VBox vBox_right_top;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LabelTool (Version 2.0)");
        primaryStage.setResizable(false);
        BorderPane pane = new BorderPane();
        VBox vBox_left = new VBox();

        vBox_left.setPrefWidth(150);
        Button open_dir = new Button("Open Folder");
        open_dir.setPrefHeight(20);
        open_dir.setPrefWidth(150);

        Button next = new Button("next");
        next.setPrefWidth(75);
        Button prev = new Button("prev");
        prev.setPrefWidth(75);
        next.setOnMouseClicked(event -> {
            ButtonEvent.nextImage();
        });
        prev.setOnMouseClicked(event -> {
            ButtonEvent.prevImage();
        });
        HBox hBox_page = new HBox();

        hBox_page.getChildren().addAll(prev, next);
        Label label_format = new Label("Template Style");

        label_format.setStyle("-fx-background-color: #000;" +
                "-fx-text-fill:#fff;" +
                "-fx-pref-width: 190;" +
                "-fx-alignment: CENTER;" +
                "-fx-padding: 3 0 3 0"
        );
        ToggleGroup group1 = new ToggleGroup();
        VBox vBox1 = new VBox();
        RadioButton pascal = new RadioButton("PASCAL VOC");
        pascal.setUserData("pascal");

        RadioButton coco = new RadioButton("COCO");
        coco.setUserData("coco");
        RadioButton diy = new RadioButton("Custom Format");
        diy.setUserData("diy");
        pascal.setToggleGroup(group1);
        pascal.setSelected(true);
        pascal.requestFocus();
        coco.setToggleGroup(group1);
        diy.setToggleGroup(group1);
        vBox1.getChildren().addAll(pascal, coco, diy);
        for (Node node : vBox1.getChildren()) {
            node.setStyle("-fx-alignment: center;" +
                    "-fx-pref-width: 130;" +
                    "-fx-padding: 2 0 2 0");
        }
        group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group1.getSelectedToggle() != null) {
                    format = group1.getSelectedToggle().getUserData().toString();
                }
            }
        });
        Label label_method = new Label("Preservation Mode");
        ToggleGroup group2 = new ToggleGroup();
        VBox vBox2 = new VBox();
        RadioButton alone = new RadioButton("Separate Preservation");
        RadioButton unify = new RadioButton("Unified preservation");
        alone.setSelected(true);
        alone.requestFocus();
        alone.setUserData("alone");
        unify.setUserData("unify");
        alone.setToggleGroup(group2);
        unify.setToggleGroup(group2);
        group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group2.getSelectedToggle() != null) {
                    method = group2.getSelectedToggle().getUserData().toString();
                }
            }
        });
        vBox2.getChildren().addAll(alone, unify);
        for (Node node : vBox2.getChildren()) {
            node.setStyle("-fx-alignment: center;" +
                    "-fx-pref-width: 130;" +
                    "-fx-padding: 2 0 2 0");
        }
        VBox vBox3 = new VBox();
        ToggleGroup group3 = new ToggleGroup();


        RadioButton xml = new RadioButton(".xml");
        RadioButton txt = new RadioButton(".txt");
        xml.setUserData(".xml");
        txt.setUserData(".txt");
        xml.setSelected(true);
        xml.requestFocus();
        xml.setToggleGroup(group3);
        txt.setToggleGroup(group3);
        group3.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group3.getSelectedToggle() != null) {
                    type = group3.getSelectedToggle().getUserData().toString();
                }
            }
        });

        vBox3.getChildren().addAll(xml, txt);
        Label label_type = new Label("File Format");
        label_type.setStyle("-fx-background-color: #000;" +
                "-fx-text-fill:#fff;" +
                "-fx-pref-width: 190;" +
                "-fx-alignment: CENTER;" +
                "-fx-padding: 3 0 3 0"
        );
        for (Node node : vBox3.getChildren()) {
            node.setStyle("-fx-alignment: center;" +
                    "-fx-pref-width: 130;" +
                    "-fx-padding: 2 0 2 0");
        }
        label_method.setStyle("-fx-background-color: #000;" +
                "-fx-text-fill:#fff;" +
                "-fx-pref-width: 190;" +
                "-fx-alignment: CENTER;" +
                "-fx-padding: 3 0 3 0"
        );
        Button save_dir = new Button("Save Path");
        save_dir.setPrefHeight(20);
        save_dir.setPrefWidth(150);

        Button save = new Button("Save");
        Button back = new Button("Back");
        save.setPrefHeight(20);
        save.setPrefWidth(150);
        back.setOnMouseClicked(event->{
            try {
                ButtonEvent.goBack();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        back.setPrefHeight(20);
        back.setPrefWidth(150);
        save_dir.setOnMouseClicked(event -> {
            ButtonEvent.selectSaveDir();
        });
        save.setOnMouseClicked(event -> {
            ButtonEvent.save2LabelFile(format, method, type);
        });
        TextArea tip = new TextArea("[Available Variables]\n" +
                "   $0 : xmin\n" +
                "   $1 : ymin\n" +
                "   $2 : xmax\n" +
                "   $3 : ymax\n" +
                "   $4 : label\n" +
                "   $5 : file name\n" +
                "   $6 : absolute path\n" +
                "   $7 : image width\n" +
                "   $8 : image height\n" +
                "   $9 : line separator\n\n" +
                "[Repeat Symbol]\n" +
                "Format : /*repeat content*/\n" +
                "eg:xmin ymin xmax ymax filename label\n" +
                "   /*$0 $1 $2 $3 $5 $4 $9*/");
        tip.setPrefHeight(300);
        tip.setStyle("-fx-padding: 5");
        vBox_left.getChildren().addAll(open_dir, hBox_page,back,
                label_format, vBox1, label_method, vBox2, label_type, vBox3,
                save_dir, save, tip);
        pane.setLeft(vBox_left);

        /*
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        */
        Canvas canvas = new Canvas(600, 600);
        ctx = canvas.getGraphicsContext2D();
        UiUtils.openImage(init_image_path);
        pane.setCenter(canvas);
        VBox vBox_right = new VBox();
        vBox_right.setPrefWidth(200);
        ScrollPane scrollPane_top = new ScrollPane();
        vBox_right_top = new VBox();

        scrollPane_top.setPrefViewportHeight(200);
        updateLabel();
        scrollPane_top.setContent(vBox_right_top);

        VBox vBox_files = new VBox();
        ScrollPane scrollPane_bottom = new ScrollPane();
        scrollPane_bottom.setPrefViewportHeight(350);

        Label label_list = new Label("Folder Workspace");
        label_list.setStyle("-fx-background-color: #000;" +
                "-fx-text-fill:#fff;" +
                "-fx-pref-width: 190;" +
                "-fx-alignment: CENTER;" +
                "-fx-padding: 3 0 3 0"
        );
        VBox vBox_right_bottom = new VBox();
        vBox_right_bottom.getChildren().addAll(label_list, vBox_files);


        scrollPane_bottom.setContent(vBox_right_bottom);
        vBox_right.getChildren().addAll(scrollPane_top, scrollPane_bottom);
        pane.setRight(vBox_right);
        Scene scene = new Scene(pane);
        primaryStage.setWidth(960);
        primaryStage.setHeight(600);
        UiUtils.center(true, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("LabelTool.png"));
        primaryStage.show();

        open_dir.setOnMouseClicked((event) -> {
            ButtonEvent.clickOpenDirButton(vBox_files);
        });


        canvas.setOnMousePressed((event) -> {
            CanvasEvent.getProposalRegion(event);
        });

    }

    public static void updateLabel() {

        vBox_right_top.getChildren().clear();
        Label label_label = new Label("Label List");
        label_label.setStyle("-fx-background-color: #000;" +
                "-fx-text-fill:#fff;" +
                "-fx-pref-width: 190;" +
                "-fx-alignment: CENTER;" +
                "-fx-padding: 3 0 3 0"
        );
        vBox_right_top.getChildren().add(label_label);
        for (String label : set) {
            Label item = new Label("  " + label);
            item.setPrefWidth(180);
            item.setPrefHeight(20);
            item.setStyle("-fx-background-color: #fff;" +
                    "-fx-border-color:#F5F5F5;" +
                    "-fx-border-style: SOLID;"
            );
            item.setOnMouseEntered((event) -> {
                LabelEvent.hover(item);
            });
            vBox_right_top.getChildren().add(item);
        }
    }
}
