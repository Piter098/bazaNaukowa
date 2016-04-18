/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna.modale;

import bazyprojekt.BazyProjekt;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class M_dodajPojazdController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tTyp;
    @FXML
    private TextField tMagazyn;
    @FXML
    private TextField tZaloga;
    @FXML
    private TextField tPredkosc;
    @FXML
    private Button bOK;

    private boolean edit;
    private int id;

    @FXML
    private void odblokujOK() {
        if (!tTyp.getText().isEmpty()
                && !tPredkosc.getText().isEmpty()
                && !tMagazyn.getText().isEmpty()
                && !tZaloga.getText().isEmpty()) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    @FXML
    private void wczytajID() {

        if (!edit) {
            try {
                String zapytanie = "SELECT max(numer_rej) as id FROM pojazdy";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);

                ResultSet rs = BazyProjekt.polaczenie.getResult();
                if (rs.next()) {
                    id = rs.getInt("id") + 1;
                }
                BazyProjekt.polaczenie.closeZapytanie();

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
                id = 1;
            }
        }
        lNumer.setText("#" + String.format("%09d", id));
    }

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT * FROM pojazdy where numer_rej=?";
            PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
            st.setInt(1, id);
            BazyProjekt.polaczenie.doZapytaniePrepared(st);

            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tTyp.setText(rs.getString("typ"));
                tPredkosc.setText(rs.getString("predkosc"));
                tMagazyn.setText(rs.getString("poj_magazynu"));
                tZaloga.setText(rs.getString("poj_zalogi"));

            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleOK(ActionEvent event) throws IOException {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE pojazdy IN EXCLUSIVE MODE");
                wczytajID();
                if (!edit) {

                    zapytanie = "INSERT INTO pojazdy(numer_rej,typ,predkosc,poj_magazynu,poj_zalogi)\n"
                            + "VALUES(?,?,?,?,?)";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, id);
                    st.setString(2, tTyp.getText());
                    st.setInt(3, Integer.parseInt(tPredkosc.getText()));
                    st.setInt(4, Integer.parseInt(tMagazyn.getText()));
                    st.setInt(5, Integer.parseInt(tZaloga.getText()));

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE pojazdy SET\n"
                            + "typ=?,predkosc=?,poj_magazynu=?,poj_zalogi=?\n"
                            + "WHERE numer_rej=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setString(1, tTyp.getText());
                    st.setInt(2, Integer.parseInt(tPredkosc.getText()));
                    st.setInt(3, Integer.parseInt(tMagazyn.getText()));
                    st.setInt(4, Integer.parseInt(tZaloga.getText()));

                    st.setInt(5, id);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setlTytul(String tytul) {
        this.lTytul.setText(tytul);
    }

    public void setEdit(boolean b, int id) {
        System.out.println(b);
        this.edit = b;

        this.id = id;
        wczytajID();
        if (b) {
            wczytajDane();
        }

    }
}
