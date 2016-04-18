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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class M_dodajLabController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tDziedzina;
    @FXML
    private ComboBox<String> cbSektory;
    @FXML
    private Button bOK;

    private boolean edit;
    private int id;
    private String sektor;

    private ObservableList<String> listSektory = FXCollections.observableArrayList();

    private void wczytajSektory() {
        try {
            listSektory.clear();
            String zapytanie = "SELECT symbol FROM sektory ORDER BY symbol";
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            while (rs.next()) {
                listSektory.add(rs.getString("SYMBOL"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajLabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void odblokujOK() {
        if (!tDziedzina.getText().isEmpty()
                && !cbSektory.getSelectionModel().isEmpty()) {
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
                String zapytanie = "SELECT max(numer) as id FROM laboratoria where sektor_symbol=?";
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setString(1, cbSektory.getSelectionModel().getSelectedItem());
                System.out.println("SYMBOL= " + cbSektory.getSelectionModel().getSelectedItem());
                BazyProjekt.polaczenie.doZapytaniePrepared(st);

                ResultSet rs = BazyProjekt.polaczenie.getResult();
                if (rs.next()) {
                    id = rs.getInt("id") + 1;
                }
                BazyProjekt.polaczenie.closeZapytanie();
                odblokujOK();

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajLabController.class.getName()).log(Level.SEVERE, null, ex);
                id = 1;
            }
        }
        lNumer.setText("#" + String.format("%09d", id));
    }

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT * FROM laboratoria where numer=? and sektor_symbol=?";
            PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
            st.setInt(1, id);
            st.setString(2, sektor);
            BazyProjekt.polaczenie.doZapytaniePrepared(st);

            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tDziedzina.setText(rs.getString("dziedzina_badan"));
                cbSektory.getSelectionModel().select(rs.getString("sektor_symbol"));

            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajLabController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleOK(ActionEvent event) throws IOException {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE laboratoria IN EXCLUSIVE MODE");
                wczytajID();
                if (!edit) {

                    zapytanie = "INSERT INTO laboratoria(numer,sektor_symbol,dziedzina_badan)\n"
                            + "VALUES(?,?,?)";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, id);
                    st.setString(2, cbSektory.getSelectionModel().getSelectedItem());
                    st.setString(3, tDziedzina.getText());

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE laboratoria SET\n"
                            + "dziedzina_badan=?\n"
                            + "WHERE numer=? and sektor_symbol=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setString(1, tDziedzina.getText());

                    st.setInt(2, id);
                    st.setString(3, sektor);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajLabController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wczytajID();
        cbSektory.setItems(listSektory);
        wczytajSektory();
    }

    public void setlTytul(String tytul) {
        this.lTytul.setText(tytul);
    }

    public void setEdit(boolean b, int id, String sektor) {
        System.out.println(b);
        this.edit = b;

        this.id = id;
        this.sektor = sektor;
        wczytajID();
        if (b) {
            cbSektory.setDisable(true);
            wczytajDane();
        }
        else {

            cbSektory.setDisable(false);
        }

    }
    
}
