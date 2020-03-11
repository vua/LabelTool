package com.cooooode.event;

import com.cooooode.ui.App;
import com.cooooode.ui.LabelSelectStage;
import com.cooooode.utils.UiUtils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Color;


import java.io.IOException;

import java.util.List;

public class CanvasEvent {

    public static void getProposalRegion(MouseEvent event) {

        Canvas canvas = (Canvas) event.getSource();
        double x0 = event.getX();
        double y0 = event.getY();
        App.ctx.setFill(Color.rgb(0, 0, 0, 0.3));
        App.ctx.setStroke(Color.rgb(178, 58, 235, 1.0));
        App.ctx.setLineWidth(2);
        int[] i = new int[1];
        canvas.setOnMouseDragged((event1) -> {
            if (i[0] % 2 == 0) {
                double x1 = event1.getX();
                double y1 = event1.getY();
                double xmin = x0, xmax = x1, ymin = y0, ymax = y1;
                if (xmin > xmax) {
                    xmin = x1;
                    xmax = x0;
                }
                if (ymin > ymax) {
                    ymin = y1;
                    ymax = y0;
                }
                //B23AEE

                UiUtils.repaint();
                UiUtils.drawRegionProposal(xmin, ymin, xmax - xmin, ymax - ymin);
            } else {
                i[0]++;
            }
        });
        canvas.setOnMouseReleased((event2) -> {
            double x1 = event2.getX();
            double y1 = event2.getY();

            double xmin = x0, xmax = x1, ymin = y0, ymax = y1;
            if (xmin > xmax) {
                xmin = x1;
                xmax = x0;
            }
            if (ymin > ymax) {
                ymin = y1;
                ymax = y0;
            }

            UiUtils.repaint();
            UiUtils.drawRegionProposal(xmin, ymin, xmax - xmin, ymax - ymin);
            double[] loc = {xmin, ymin, xmax, ymax};

            App.locs.get(App.index).add(loc);

            LabelSelectStage.createLabelSelectStage(xmax, ymax);
        });
    }


}
