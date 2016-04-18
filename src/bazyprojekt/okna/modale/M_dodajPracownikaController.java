/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna.modale;

import bazyprojekt.BazyProjekt;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class M_dodajPracownikaController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tImie;
    @FXML
    private TextField tNazwisko;
    @FXML
    private TextField tData;
    @FXML
    private ComboBox<String> cbSektory;
    @FXML
    private ComboBox<String> cbNumery;
    @FXML
    private Button bOK;

    private ObservableList<String> listSektory = FXCollections.observableArrayList();
    private ObservableList<String> listNumery = FXCollections.observableArrayList();

    private String symbolSektora = "";
    private int id = 0;
    private int numer = 0;
    private boolean edit;

    private boolean okClicked = false;

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
    private void wczytajIdentyfikatory() {

        try {
            symbolSektora = cbSektory.getSelectionModel().getSelectedItem();
            listNumery.clear();
            cbNumery.setDisable(false);
            String zapytanie;

            zapytanie = "SELECT numer FROM pokoj_mieszkalne WHERE sektor_symbol='" + symbolSektora + "' ORDER BY numer";

            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            while (rs.next()) {
                listNumery.add(rs.getString("NUMER"));
            }
            BazyProjekt.polaczenie.closeZapytanie();

            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void wybierzNumer() {
        if (cbNumery.getSelectionModel().getSelectedItem() != null) {
            numer = Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem());
            odblokujOK();
        }
    }

    @FXML
    private void odblokujOK() {
        if (!cbSektory.getSelectionModel().isEmpty()
                && !cbNumery.getSelectionModel().isEmpty()
                && !tImie.getText().isEmpty()
                && !tNazwisko.getText().isEmpty()
                && !tData.getText().isEmpty()) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT imie,nazwisko,TO_CHAR(data_ur,'YYYY/MM/DD') as urodziny,POKOJ_MIESZK_SEKTOR_SYMBOL,POKOJ_MIESZK_NUMER "
                    + "FROM pracownicy2 pr where numer=" + id;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tImie.setText(rs.getString("imie"));
                tNazwisko.setText(rs.getString("nazwisko"));
                tData.setText(rs.getString("urodziny"));
                cbSektory.setDisable(false);
                cbNumery.setDisable(false);
                cbSektory.getSelectionModel().select(rs.getString("POKOJ_MIESZK_SEKTOR_SYMBOL"));
                wczytajIdentyfikatory();
                cbNumery.getSelectionModel().select(rs.getString("POKOJ_MIESZK_NUMER"));

            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void odczytajZmienne() {
        if (cbNumery.getSelectionModel().getSelectedItem() != null) {
            numer = Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem());
        }
        if (cbSektory.getSelectionModel().getSelectedItem() != null) {
            symbolSektora = cbSektory.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void handleOK(ActionEvent event) throws IOException {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie;

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE przydzialy2 IN EXCLUSIVE MODE");
                wczytajID();
                if (!edit) {
                    zapytanie = "INSERT INTO pracownicy2(numer,imie,nazwisko,data_ur, POKOJ_MIESZK_NUMER, POKOJ_MIESZK_SEKTOR_SYMBOL)\n"
                            + "VALUES(?,?,?,to_date(?,'YYYY/MM/DD'),?,?)";
                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    odczytajZmienne();
                    st.setInt(1, id);
                    st.setString(2, tImie.getText());
                    st.setString(3, tNazwisko.getText());
                    st.setString(4, tData.getText());
                    st.setInt(5, numer);
                    st.setString(6, cbSektory.getSelectionModel().getSelectedItem());

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE pracownicy2 SET "
                            + "imie=?,nazwisko=?,data_ur=to_date(?,'YYYY/MM/DD'),\n"
                            + "POKOJ_MIESZK_NUMER=?, POKOJ_MIESZK_SEKTOR_SYMBOL=?\n"
                            + "WHERE numer=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setString(1, tImie.getText());
                    st.setString(2, tNazwisko.getText());
                    st.setString(3, tData.getText());
                    odczytajZmienne();
                    st.setInt(4, numer);
                    st.setString(5, symbolSektora);

                    st.setInt(6, id);
                    System.out.println("ID: "+id);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }

            } catch (SQLException ex) {
                Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSektory.setItems(listSektory);
        cbNumery.setItems(listNumery);
        wczytajSektory();

    }

    public void setlTytul(String tytul) {
        this.lTytul.setText(tytul);
    }

    public void setEdit(boolean b, int id) {
        System.out.println(b);
        this.edit = b;
        this.id = id;
        wczytajID();
        wczytajDane();

    }

    private void wczytajID() {

        System.out.println(">>>" + edit + " " + id);
        if (!edit) {
            try {
                String zapytanie = "SELECT max(numer) as id FROM pracownicy2";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                if (rs.next()) {
                    id = rs.getInt("id") + 1;
                }
                BazyProjekt.polaczenie.closeZapytanie();
            } catch (SQLException ex) {
                Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lNumer.setText("#" + String.format("%09d", id));
    }

    public void setId(int id) {
        this.id = id;
        wczytajDane();
    }

}
