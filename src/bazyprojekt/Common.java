/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Piter
 */
public class Common {
    public void handlePowrot(ActionEvent event) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("okna/FXMLDocument.fxml"));
	Stage stage;
	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	//create a new scene with root and set the stage
	Scene scene = new Scene(root);
	//BazyProjekt.scena.setScene(scene);
	stage.setScene(scene);
	stage.show();	
    }
}
