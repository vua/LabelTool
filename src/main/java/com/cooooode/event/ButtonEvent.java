package com.cooooode.event;

import com.cooooode.ui.App;
import com.cooooode.ui.SaveProcessStage;
import com.cooooode.utils.CoreUtils;
import com.cooooode.utils.UiUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ButtonEvent {
    static final boolean isWinOs;

    static {
        if (File.separator.equals("\\"))
            isWinOs = true;
        else
            isWinOs = false;
    }

    public static void goBack() throws IOException {
        List<double[]> locs = App.locs.get(App.index);
        if (locs.size() > 0) {
            locs.remove(locs.size() - 1);
            UiUtils.openImage(App.path);
            UiUtils.repaint();
        }

    }

    public static void clickFileButton(String path) {
        try {
            UiUtils.openImage(path);
            UiUtils.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clickOpenDirButton(VBox vBox) {

        vBox.getChildren().clear();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(new Stage());
        System.out.println(new Date().toString() + " : [info] open folder " + dir);
        String firstFilePath = "";
        boolean first = true;
        if (dir != null) {
            App.allimagepaths = new ArrayList<>();
            for (File file : dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".jpg") ||
                            pathname.getName().endsWith(".png") ||
                            pathname.getName().endsWith(".bmp") ||
                            pathname.getName().endsWith(".gif") ||
                            pathname.getName().endsWith(".jpeg");
                }
            })) {

                String path = file.getAbsolutePath();
                if (first) {
                    firstFilePath = path;
                    first = !first;
                }
                String name = file.getName();
                Button button = new Button(name);

                if (isWinOs)
                    App.allimagepaths.add(path.replaceAll("\\\\", "/"));
                else
                    App.allimagepaths.add(path);
                button.setOnMouseEntered((event) -> {
                    LabelEvent.hover(button);
                    button.setOnMouseClicked((event1 -> {
                        App.path = path;
                        clickFileButton(path);
                    }));
                });

                button.setStyle("-fx-background-color: #fff;" +
                        "-fx-border-color:#F5F5F5;" +
                        "-fx-border-style: SOLID;"
                );
                button.setPrefWidth(180);


                vBox.getChildren().add(button);
            }

            try {
                if (!firstFilePath.equals(""))
                    UiUtils.openImage(firstFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void confirmLabel(TextField labelvalue, Stage stage) {

        String label = labelvalue.getText().trim();
        if (!label.equals("")) {
            App.labels.get(App.index).add(label);
            UiUtils.addItemForSet(label);
            stage.close();
            UiUtils.repaint();
        }
    }

    public static void nextImage() {

        int idx = App.allimagepaths.indexOf(App.path);
        if (idx++ < App.allimagepaths.size() - 1) {
            App.path = App.allimagepaths.get(idx);
            UiUtils.repaint();
        }
    }

    public static void prevImage() {
        int idx = App.allimagepaths.indexOf(App.path);
        if (--idx >= 0) {
            App.path = App.allimagepaths.get(idx);
            UiUtils.repaint();
        }
    }

    public static void selectSaveDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(new Stage());
        if (dir != null) {
            String old_save_path = App.save_path;
            App.save_path = dir.getAbsolutePath();
            System.out.println(new Date() + " : [info] save path " + old_save_path + " -> " + App.save_path);
        }
    }

    public static void save2LabelFile(String... args) {

        System.out.println(new Date().toString() + " : [info] save label file");
        App.final0_3 = new ArrayList<>();
        App.final4 = new ArrayList<>();
        App.final5_8 = new ArrayList<>();

        for (int i = 1; i < App.paths.size(); i++) {
            List<double[]> locs = App.locs.get(i);
            int l = locs.size();
            if (l == 0)
                continue;
            ArrayList<String> item1 = new ArrayList<>();
            ArrayList<int[]> item = new ArrayList<>();
            double ratio = App.ratios.get(i);
            int[] xmin = new int[l];
            int[] ymin = new int[l];
            int[] xmax = new int[l];
            int[] ymax = new int[l];
            for (int j = 0; j < l; j++) {
                xmin[j] = (int) Math.floor(locs.get(j)[0] * ratio);
                ymin[j] = (int) Math.floor(locs.get(j)[1] * ratio);
                xmax[j] = (int) Math.floor(locs.get(j)[2] * ratio);
                ymax[j] = (int) Math.floor(locs.get(j)[3] * ratio);
            }
            String absolutePath = App.paths.get(i);

            String fileName = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);


            String width = String.valueOf(App.widths.get(i));
            String height = String.valueOf(App.heights.get(i));
            item.add(xmin);//$0
            item.add(ymin);//$1
            item.add(xmax);//$2
            item.add(ymax);//$3

            item1.add(fileName);//$5
            item1.add(absolutePath);//$6
            item1.add(width);//$7
            item1.add(height);//$8
            App.final0_3.add(item);
            App.final4.add(App.labels.get(i));
            App.final5_8.add(item1);
        }

        if (App.final4.size() > 0) {
            boolean b = true;
            try {
                b = CoreUtils.parseFileTool(args[0]);
                if (b)
                    CoreUtils.writeFileTool(args[1], args[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (b) {
                Stage stage = SaveProcessStage.createLabelSelectStage(App.final4.size(), args);
                EventHandler<ActionEvent> eventHandler = e -> {
                    stage.close();
                };

                Timeline animation = new Timeline(new KeyFrame(Duration.millis(3000), eventHandler));
                animation.setCycleCount(1);
                animation.play();
            }
        } else {
            System.out.println(new Date().toString() + " : [warning] no marked file");
        }

    }
}
