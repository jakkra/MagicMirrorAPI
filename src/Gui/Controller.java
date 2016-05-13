package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Created by kasper on 2016-05-12.
 */


public class Controller {

    @FXML
    private Button updateButton;


    @FXML
    private void forceUpdateFeed(ActionEvent e){
        System.out.println("HELLO BUTTON");
    }
}
