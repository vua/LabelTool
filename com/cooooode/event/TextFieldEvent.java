package com.cooooode.event;
import com.cooooode.ui.App;
import com.cooooode.utils.UiUtils;
import javafx.beans.Observable;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TextFieldEvent {
    public static void setLabel(KeyEvent event, TextField labelvalue, Stage stage){

        String label=labelvalue.getText().trim();
        if(event.getCode().equals(KeyCode.ENTER)){

            if(!label.equals("")) {
                UiUtils.addItemForSet(label);
                App.labels.get(App.index).add(label);
                UiUtils.repaint();
            }else{
                UiUtils.removeLastOne();
                UiUtils.repaint();
            }
            stage.close();
        }
    }
}
