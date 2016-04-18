/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna.modale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class M_idPracController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Stage s;
    @FXML
    private Button okBtn;

    @FXML
    private TextField numer;

    @FXML
    private Label idTresc;

    public void handleOK(ActionEvent event) throws IOException {
        //((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        s = (Stage) okBtn.getScene().getWindow();
        s.close();
    }

    public String getNumer() {
        if (!"".equals(numer.getText())) {
            return numer.getText();
        }
        return "0";
    }

    public void setTresc(String str) {
        idTresc.setText(str);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
