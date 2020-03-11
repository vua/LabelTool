package com.cooooode.utils;

import com.cooooode.event.CanvasEvent;
import com.cooooode.ui.App;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.INACTIVE;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UiUtils {
    public static void center(boolean app, Stage... stages) {
        /*
            居中显示
        */
        Rectangle2D rect = Screen.getPrimary().getVisualBounds();
        //Rectangle2D rect=Screen.getPrimary().getBounds();
        double window_width = rect.getWidth();
        double window_height = rect.getHeight();
        for (Stage stage : stages) {
            double stage_width = stage.getWidth();
            double stage_height = stage.getHeight();
            double marginX = (window_width - stage_width) / 2;
            double marginY = (window_height - stage_height) / 2;
            if (app) {
                App.marginX = marginX;
                App.marginY = marginY;
            }
            stage.setX(marginX);
            stage.setY(marginY);
        }
    }

    public static void updateMarginX() {

    }

    public static void bind(Node node, Event event) {

    }

    public static void openImage(InputStream input) throws IOException {
        App.is = input;

        App.ctx.clearRect(0, 0, 600, 600);
        Image image = new Image(App.is);
        String path = "logo";

        App.locs.add(new ArrayList<>());
        App.labels.add(new ArrayList<>());
        App.path = path;
        App.paths.add(path);

        App.index = App.paths.indexOf(path);
        App.ratios.add(Math.max(image.getHeight(), image.getWidth()) / 600);
        App.widths.add((int) image.getWidth());
        App.heights.add((int) image.getHeight());

        input.close();
        input = UiUtils.class.getClassLoader().getResourceAsStream("LabelTool.png");
        Image image_ = new Image(input, 600, 600, true, true);
        App.ctx.drawImage(image_, 0, 0);
        input.close();
    }

    public static void openImage(String path) throws IOException {
        if (File.separator.equals("\\"))
            path = path.replaceAll("\\\\", "/");
        if (!App.paths.contains(path)) {

            App.locs.add(new ArrayList<>());
            App.labels.add(new ArrayList<>());
            App.path = path;
            App.paths.add(path);

        }
        if (path.equals("logo"))
            App.is = UiUtils.class.getClassLoader().getResourceAsStream("LabelTool.png");
        else
            App.is = new FileInputStream(new File(path));
        App.index = App.paths.indexOf(path);

        App.ctx.clearRect(0, 0, 600, 600);
        Image image = new Image(App.is);

        if (App.index >= App.ratios.size() - 1) {
            App.ratios.add(Math.max(image.getHeight(), image.getWidth()) / 600);
            App.widths.add((int) image.getWidth());
            App.heights.add((int) image.getHeight());
        }

        if (path.equals("logo"))
            App.is = UiUtils.class.getClassLoader().getResourceAsStream("LabelTool.png");
        else
            App.is = new FileInputStream(new File(path));
        Image image_ = new Image(App.is, 600, 600, true, true);
        App.ctx.drawImage(image_, 0, 0);
        App.is.close();

    }

    public static void addItemForSet(String key) {
        if (!App.set.contains(key)) {
            App.set.add(key);
            App.updateLabel();
            try {
                addLabel2LabelTxt(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addLabel2LabelTxt(String key) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File("./label.txt"), true)
                        , "utf-8"))
        ) {
            writer.write(key + "\r\n");
        }

    }

    public static void drawRegionProposal(double... loc) {

        App.ctx.fillRect(loc[0], loc[1], loc[2], loc[3]);
        App.ctx.strokeRoundRect(loc[0], loc[1], loc[2], loc[3], 0, 0);
    }

    public static void drawAllRegionProposal(List<double[]> locs) {
        for (double[] loc : locs)
            UiUtils.drawRegionProposal(loc[0], loc[1], loc[2] - loc[0], loc[3] - loc[1]);
    }

    public static void drawLabel(double xmin, double ymin, String label) {
        App.ctx.setFill(Color.rgb(0, 0, 0, 1.0));
        App.ctx.fillRect(xmin, ymin - 20, 100, 20);
        App.ctx.setFill(Color.rgb(255, 255, 255, 1.0));
        App.ctx.fillText("  " + label, xmin, ymin - 5);
        App.ctx.setFill(Color.rgb(0, 0, 0, 0.3));
    }

    public static void removeLastOne() {
        int lastLocIdxOfCurrentImg = App.locs.get(App.index).size() - 1;
        if (lastLocIdxOfCurrentImg >= 0)
            App.locs.get(App.index).remove(lastLocIdxOfCurrentImg);
    }

    public static void repaint() {
        App.ctx.clearRect(0, 0, 600, 600);

        try {
            openImage(App.path);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        List<String> labels = App.labels.get(App.index);
        List<double[]> locs = App.locs.get(App.index);
        for (int i = 0; i < locs.size(); i++) {
            double[] loc = locs.get(i);
            String label = labels.get(i);
            App.ctx.setFill(Color.rgb(0, 0, 0, 0.3));
            App.ctx.setStroke(Color.rgb(178, 58, 235, 1.0));
            App.ctx.setLineWidth(2);
            drawRegionProposal(loc[0], loc[1], loc[2] - loc[0], loc[3] - loc[1]);

            drawLabel(loc[0], loc[1], label);
        }
    }

    public static void drawAllLabel() {

    }

    public static void loadLabel(InputStream input) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(input))
        ) {
            String label;
            while ((label = reader.readLine()) != null)
                App.set.add(label);
        }

    }

    public static void loadLabel(String path) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(new File(path))
                                , "utf-8")
                )
        ) {
            String label;
            while ((label = reader.readLine()) != null)
                App.set.add(label);
        }
    }
}
