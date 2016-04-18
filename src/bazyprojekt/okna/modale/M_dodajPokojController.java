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
public class M_dodajPokojController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tMiejsca;
    @FXML
    private TextField tWielkosc;
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
            Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void odblokujOK() {
        if (!tMiejsca.getText().isEmpty()
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
                String zapytanie = "SELECT max(numer) as id FROM pokoj_mieszkalne where sektor_symbol=?";
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setString(1, cbSektory.getSelectionModel().getSelectedItem());
                System.out.println("SYMBOL= " + cbSektory.getSelectionModel().getSelectedItem());
                BazyProjekt.polaczenie.doZapytaniePrepared(st);

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
            String zapytanie = "SELECT * FROM pokoj_mieszkalne where numer=? and sektor_symbol=?";
            PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
            st.setInt(1, id);
            st.setString(2, sektor);
            BazyProjekt.polaczenie.doZapytaniePrepared(st);

            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tMiejsca.setText(rs.getString("LICZBA_MIEJSC"));
                tWielkosc.setText(rs.getString("wielkosc"));
                cbSektory.getSelectionModel().select(rs.getString("sektor_symbol"));

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

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE pokoj_mieszkalne IN EXCLUSIVE MODE");
                wczytajID();
                if (!edit) {

                    zapytanie = "INSERT INTO pokoj_mieszkalne(numer,sektor_symbol,wielkosc,liczba_miejsc)\n"
                            + "VALUES(?,?,?,?)";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, id);
                    st.setString(2, cbSektory.getSelectionModel().getSelectedItem());
                    st.setInt(3, Integer.parseInt(tWielkosc.getText()));
                    st.setInt(4, Integer.parseInt(tMiejsca.getText()));

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE pokoj_mieszkalne SET\n"
                            + "wielkosc=?,liczba_miejsc=?\n"
                            + "WHERE numer=? and sektor_symbol=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, Integer.parseInt(tWielkosc.getText()));
                    st.setInt(2, Integer.parseInt(tMiejsca.getText()));

                    st.setInt(3, id);
                    st.setString(4, sektor);

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
