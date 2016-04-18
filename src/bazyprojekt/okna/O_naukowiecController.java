/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna;

import bazyprojekt.BazyProjekt;
import bazyprojekt.okna.modale.M_idPracController;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class O_naukowiecController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int idBadania;

    @FXML
    private Label lNumer;
    @FXML
    private TextField tNazwa;
    @FXML
    private TextArea tTresc;
    @FXML
    private Button btnWczytaj;
    @FXML
    private Button btnZapisz;

    @FXML
    private void handlePowrot(ActionEvent event) throws IOException {
	BazyProjekt.c.handlePowrot(event);
    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
	try {

	    String zapytanie = "SELECT id_badania, temat, tresc\n"
		    + "FROM badania WHERE id_badania=" + idBadania;
	    BazyProjekt.polaczenie.doZapytanie(zapytanie);
	    System.out.println(idBadania);
	    ResultSet rs = BazyProjekt.polaczenie.getResult();

	    if (rs.next()) {
		lNumer.setText("#" + String.format("%09d", (Integer) rs.getInt("ID_BADANIA")));
		tNazwa.setText(rs.getString("TEMAT"));
		tTresc.setText(rs.getString("TRESC"));
	    }

	    BazyProjekt.polaczenie.closeZapytanie();
	} catch (SQLException ex) {
	    Logger.getLogger(O_pracownikController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @FXML
    private void przetworzBadanie(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_idPrac.fxml"));
	Parent root = (Parent) loader.load();
	Stage stage;
	stage = new Stage();
	stage.initModality(Modality.WINDOW_MODAL);
	stage.initOwner(BazyProjekt.scena);
	//create a new scene with root and set the stage
	Scene scene = new Scene(root);
	//BazyProjekt.scena.setScene(scene);
	stage.setScene(scene);

	M_idPracController controler = loader.getController();
	controler.setTresc("Podaj id badania");

	stage.showAndWait();
	if (event.getSource() == btnZapisz) {
	    zapiszBadanie(Integer.parseInt(controler.getNumer()));
	}
	idBadania = Integer.parseInt(controler.getNumer());
	handleRefresh(null);
	//lbl.setText(controler.getNumer());
    }

    private void zapiszBadanie(int id) {
	try {
	    String zapytanie = "SELECT id_badania\n"
		    + "FROM badania WHERE id_badania=" + id;
	    BazyProjekt.polaczenie.doZapytanie(zapytanie);
	    
	    if (BazyProjekt.polaczenie.getResult().next()) {
		zapytanie = "UPDATE badania SET temat=?, tresc=?\n"
			+ "WHERE id_badania=?";
		
	    } else {
		zapytanie = "INSERT INTO badania(temat,tresc,id_badania)\n"
			+ "VALUES(?,?,?)";
	    }
	    
	    BazyProjekt.polaczenie.closeZapytanie();
	    
	    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
	    st.setString(1, tNazwa.getText());
	    st.setString(2, tTresc.getText());
	    st.setInt(3, id);
	    BazyProjekt.polaczenie.doZmianyPrepared(st);
	    
	    BazyProjekt.polaczenie.closeZapytanie();
	} catch (SQLException ex) {
	    Logger.getLogger(O_naukowiecController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }

}
