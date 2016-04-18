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
public class M_przydzielPojazdController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tNazwa;
    @FXML
    private RadioButton rBadawcze;
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
        if ((!cbSektory.getSelectionModel().isEmpty() || rBadawcze.isSelected())
                && !cbNumery.getSelectionModel().isEmpty()) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT * FROM pojazdy where numer_rej=" + id;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                if (rs.getString("SEKCJA_BADAWCZA_NUMER") != null) {
                    rBadawcze.setSelected(true);
                    cbNumery.setDisable(false);
                    wczytajIdentyfikatory();
                    cbNumery.getSelectionModel().select(rs.getString("SEKCJA_BADAWCZA_NUMER"));
                }
                else if (rs.getString("POST_OCHRONY_NUMER") != null && rs.getString("POST_OCHRONY_SEKTOR_SYMBOL") != null) {
                    rPosterunki.setSelected(true);
                    cbSektory.setDisable(false);
                    cbNumery.setDisable(false);
                    cbSektory.getSelectionModel().select(rs.getString("POST_OCHRONY_SEKTOR_SYMBOL"));
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
                zapytanie = "UPDATE pojazdy SET "
                        + "SEKCJA_BADAWCZA_NUMER=?,"
                        + "POST_OCHRONY_NUMER=?, POST_OCHRONY_SEKTOR_SYMBOL=?\n"
                        + "WHERE numer_rej=?";

                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                odczytajZmienne();
                if (rBadawcze.isSelected()) {
                    st.setInt(1, numer);
                    st.setNull(2, Types.INTEGER);
                    st.setNull(3, Types.VARCHAR);

                }
                else if (rPosterunki.isSelected()) {
                    st.setNull(1, Types.INTEGER);
                    st.setInt(2, numer);
                    st.setString(3, symbolSektora);

                }
                st.setInt(4, id);

                BazyProjekt.polaczenie.doZmianyPrepared(st);

                BazyProjekt.polaczenie.closeZapytanie();

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

    /**
     * @param tytul the tytul to set
     */
    public void setlTytul(String tytul) {
        this.lTytul.setText(tytul);
    }

    public void setId(int id) {
        this.id = id;
        wczytajDane();
    }

}
