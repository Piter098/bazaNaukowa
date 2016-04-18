/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna.modale;

import bazyprojekt.BazyProjekt;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class M_dodajSektorController implements Initializable {

    private boolean edit = false;
    private String symbol = "";

    @FXML
    private TextField tSymbol;
    @FXML
    private Button bOK;
    @FXML
    private Label lTytul;

    @FXML
    private void odblokujOK() {
        if (tSymbol.getText().isEmpty()) {
            bOK.setDisable(true);
        }
        else {
            bOK.setDisable(false);
        }
    }

    @FXML
    private void handleOK(ActionEvent event) {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                String symbol2 = tSymbol.getText();
                if (!edit) {
                    zapytanie = "INSERT INTO sektory(symbol)\n"
                            + "VALUES(?)";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                    st.setString(1, symbol2);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE sektory SET\n"
                            + "symbol=? where symbol=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                    System.out.println("S: " + symbol2);
                    st.setString(1, symbol2);
                    st.setString(2, symbol);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();
    }

    private void wczytajDane() {
        tSymbol.setText(symbol);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setlTytul(String tytul) {
        this.lTytul.setText(tytul);
    }

    public void setEdit(boolean b, String sym) {
        System.out.println(b);
        this.edit = b;
        this.symbol = sym;
        wczytajDane();

    }

}
