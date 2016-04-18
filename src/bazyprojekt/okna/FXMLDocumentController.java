/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna;

import bazyprojekt.BazyProjekt;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Piter
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button bPracownicy;
    @FXML
    private Button bNaukowiec;
    @FXML
    private Button bPlanista;
    @FXML
    private Button bDowodca;
    @FXML
    private Button bNadzorca;
    @FXML
    private Button bReconnect;
    
    @FXML
    private Label lbl;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
	Parent root = null;
	Boolean zmienScene = false;
	if (event.getSource() == bPracownicy) {
	    //get reference to the button's stage         
	    //stage = (Stage) bPracownicy.getScene().getWindow();
	    //load up OTHER FXML document
	    root = FXMLLoader.load(getClass().getResource("O_pracownik.fxml"));
	    zmienScene = true;
	} else if (event.getSource() == bNaukowiec) {
	    //stage = (Stage) bNaukowiec.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("O_naukowiec.fxml"));
	    zmienScene = true;
	} else if (event.getSource() == bPlanista) {
	    //stage = (Stage) bNaukowiec.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("O_planista.fxml"));
	    zmienScene = true;
	} else if (event.getSource() == bDowodca) {
	    //stage = (Stage) bNaukowiec.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("O_dowodca.fxml"));
	    zmienScene = true;
	} else if (event.getSource() == bNadzorca) {
	    //stage = (Stage) bNaukowiec.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("O_nadzorca.fxml"));
	    zmienScene = true;
	} else if (event.getSource() == bReconnect) {
	    //stage = (Stage) bNaukowiec.getScene().getWindow();
            BazyProjekt.polaczenie.connect();
	    zmienScene = false;
	}
	if(zmienScene == true){
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    BazyProjekt.scena.setScene(scene);
	    BazyProjekt.scena.show();
	}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	if(BazyProjekt.polaczenie.getConnected()){
	    lbl.setText("Połączono");
	}
	else{
	    lbl.setText("BRAK POŁĄCZENIA");
	}
    }

}
