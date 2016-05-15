package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import java.util.*;

/**
 * Created by kasper on 2016-05-12.
 */


public class Controller {

    @FXML
    private Button updateButton;
    @FXML
    private TextArea bottomRightText;
    @FXML
    private TextArea bottomLeftText;
    @FXML
    private TextArea topRightText;
    @FXML
    private TextArea topLeftText;
    private TextArea [] textAreas;

    public Controller(){

    }


    @FXML
    public void initialize() {
        textAreas = new TextArea[] {bottomLeftText,bottomRightText,topLeftText,topRightText};
        for(TextArea t : textAreas){
            t.setEditable(false);
            
        }
        System.out.println("second");
    }



    @FXML
    private void forceUpdateFeed(ActionEvent e){
        System.out.println("HELLO BUTTON");
        bottomLeftText.setText("bottom Left");
        bottomRightText.setText("bottom Right");
        topLeftText.setText("Top Left");
        topRightText.setText("Top Right");
    }
}
