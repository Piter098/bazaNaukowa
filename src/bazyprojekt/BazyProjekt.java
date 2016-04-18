/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt;

import java.io.IOException;
import javafx.application.Application;
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
public class BazyProjekt extends Application {

    /**
     * Aktualne okno programu
     */
    public static Stage scena;
    public static Common c = new Common();
    public static BazaPolaczenie polaczenie = new BazaPolaczenie();
    
    @Override
    public void start(Stage stage) throws Exception {
	BazyProjekt.polaczenie.connect();
	
	scena = stage;
	Parent root = FXMLLoader.load(getClass().getResource("okna/FXMLDocument.fxml"));

	Scene scene = new Scene(root);

	stage.setScene(scene);
	stage.show();
	
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

}
