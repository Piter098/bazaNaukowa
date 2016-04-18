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
public class M_dodajPrzydzialController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tNazwa;
    @FXML
    private RadioButton rBadawcze;
    @FXML
    private RadioButton rLaboratoria;
    @FXML
    private RadioButton rPosterunki;
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

    @FXML
    private void doComboBoxy() {
        odblokujOK();
        listNumery.clear();
        if (rBadawcze.isSelected()) {
            cbSektory.setDisable(true);
            wczytajIdentyfikatory();
        }
        else if (rLaboratoria.isSelected()) {
            cbSektory.setDisable(false);
        }
        else if (rPosterunki.isSelected()) {
            cbSektory.setDisable(false);
        }
        cbSektory.getSelectionModel().clearSelection();
        cbNumery.getSelectionModel().clearSelection();
    }

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
            if (rBadawcze.isSelected()) {
                zapytanie = "SELECT numer FROM sekcje_badawcze ORDER BY numer";
            }
            else if (rLaboratoria.isSelected()) {
                zapytanie = "SELECT numer FROM laboratoria WHERE sektor_symbol='" + symbolSektora + "' ORDER BY numer";
            }
            else if (rPosterunki.isSelected()) {
                zapytanie = "SELECT numer FROM post_ochrony WHERE sektor_symbol='" + symbolSektora + "' ORDER BY numer";
            }
            else {
                return;
            }

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
        if (!tNazwa.getText().isEmpty()
                && (!cbSektory.getSelectionModel().isEmpty() || rBadawcze.isSelected())
                && !cbNumery.getSelectionModel().isEmpty()) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    private void wczytajID() {

        System.out.println(">>>" + edit + " " + id);
        if (!edit) {
            try {
                String zapytanie = "SELECT max(numer) as id FROM przydzialy2";
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

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT * FROM przydzialy2 where numer=" + id;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tNazwa.setText(rs.getString("NAZWA"));
                if (rs.getString("SEKCJA_BADAWCZA_NUMER") != null) {
                    rBadawcze.setSelected(true);
                    cbNumery.setDisable(false);
                    wczytajIdentyfikatory();
                    cbNumery.getSelectionModel().select(rs.getString("SEKCJA_BADAWCZA_NUMER"));
                }
                else if (rs.getString("LABORATORIUM_SYMBOL") != null && rs.getString("LABORATORIUM_NUMER") != null) {
                    rLaboratoria.setSelected(true);
                    cbSektory.setDisable(false);
                    cbNumery.setDisable(false);
                    cbSektory.getSelectionModel().select(rs.getString("LABORATORIUM_SYMBOL"));
                    wczytajIdentyfikatory();
                    cbNumery.getSelectionModel().select(rs.getString("LABORATORIUM_NUMER"));
                }
                else if (rs.getString("POST_OCHRONY_NUMER") != null && rs.getString("POST_OCHRONY_SYMBOL") != null) {
                    rPosterunki.setSelected(true);
                    cbSektory.setDisable(false);
                    cbNumery.setDisable(false);
                    cbSektory.getSelectionModel().select(rs.getString("POST_OCHRONY_SYMBOL"));
                    wczytajIdentyfikatory();
                    cbNumery.getSelectionModel().select(rs.getString("POST_OCHRONY_NUMER"));
                }
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
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE przydzialy2 IN EXCLUSIVE MODE");
                wczytajID();
                if (!edit) {
                    if (rBadawcze.isSelected()) {
                        zapytanie = "INSERT INTO przydzialy2(numer,nazwa,SEKCJA_BADAWCZA_NUMER)\n"
                                + "VALUES(?,?,?)";
                    }
                    else if (rLaboratoria.isSelected()) {
                        zapytanie = "INSERT INTO przydzialy2(numer,nazwa, LABORATORIUM_NUMER, LABORATORIUM_SYMBOL)\n"
                                + "VALUES(?,?,?,?)";
                    }
                    else if (rPosterunki.isSelected()) {
                        zapytanie = "INSERT INTO przydzialy2(numer,nazwa, POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL)\n"
                                + "VALUES(?,?,?,?)";
                    }
                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, id);
                    st.setString(2, tNazwa.getText());
                    st.setInt(3, Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem()));

                    if (rLaboratoria.isSelected() || rPosterunki.isSelected()) {
                        st.setString(4, cbSektory.getSelectionModel().getSelectedItem());
                    }

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE przydzialy2 SET nazwa=?,"
                            + "SEKCJA_BADAWCZA_NUMER=?,"
                            + "LABORATORIUM_NUMER=?, LABORATORIUM_SYMBOL=?,"
                            + "POST_OCHRONY_NUMER=?, POST_OCHRONY_SYMBOL=?\n"
                            + "WHERE numer=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setString(1, tNazwa.getText());
                    odczytajZmienne();
                    if (rBadawcze.isSelected()) {
                        st.setInt(2, numer);
                        st.setNull(3, Types.INTEGER);
                        st.setNull(4, Types.VARCHAR);
                        st.setNull(5, Types.INTEGER);
                        st.setNull(6, Types.VARCHAR);

                    }
                    else if (rLaboratoria.isSelected()) {
                        st.setNull(2, Types.INTEGER);
                        st.setInt(3, numer);
                        st.setString(4, symbolSektora);
                        st.setNull(5, Types.INTEGER);
                        st.setNull(6, Types.VARCHAR);

                    }
                    else if (rPosterunki.isSelected()) {
                        st.setNull(2, Types.INTEGER);
                        st.setNull(3, Types.INTEGER);
                        st.setNull(4, Types.VARCHAR);
                        st.setInt(5, numer);
                        st.setString(6, symbolSektora);

                    }
                    st.setInt(7, id);

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
        System.out.println(">" + edit);
        wczytajID();
        cbSektory.setItems(listSektory);
        cbNumery.setItems(listNumery);
        wczytajSektory();

    }

    /**
     * @param tytul the tytul to set
     */
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

}
