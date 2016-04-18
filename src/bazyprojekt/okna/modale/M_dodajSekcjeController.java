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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class M_dodajSekcjeController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private TextField tProwiant;
    @FXML
    private TextField tMagazyn;
    @FXML
    private TextField tZaloga;
    @FXML
    private Button bOK;

    private int id = 0;
    private int prowiant = 0;
    private int magazyn = 0;
    private int zaloga = 0;
    private boolean edit;

    private boolean okClicked = false;

    @FXML
    private void odblokujOK() {
        odczytajZmienne();
        if (tProwiant != null && tMagazyn != null && tZaloga != null) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    private void wczytajID() {

        if (!edit) {
            try {
                String zapytanie = "SELECT max(numer) as id FROM sekcje_badawcze";
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
            String zapytanie = "SELECT * FROM sekcje_badawcze where numer=" + id;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                tProwiant.setText(rs.getString("ILOSC_PROWIANTU"));
                tMagazyn.setText(rs.getString("ILOSC_ZASOBOW"));
                tZaloga.setText(rs.getString("LICZBA_MIEJSC"));
            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_dodajPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void odczytajZmienne() {
        try {
            if (tProwiant.getText() != null) {
                prowiant = Integer.parseInt(tProwiant.getText());
                System.out.println("ASASDA " + tZaloga.getText());
            }
            else {
                prowiant = 0;
            }
            if (tMagazyn.getText() != null) {
                magazyn = Integer.parseInt(tMagazyn.getText());
            }
            else {
                magazyn = 0;
            }
            if (tZaloga.getText() != null) {
                zaloga = Integer.parseInt(tZaloga.getText());
            }
            else {
                zaloga = 0;
            }
        } catch (NumberFormatException e) {
            System.err.println("Nie numeryczne!");
        }
    }

    @FXML
    private void handleOK(ActionEvent event) throws IOException {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                BazyProjekt.polaczenie.doZapytanie("LOCK TABLE sekcje_badawcze IN EXCLUSIVE MODE");
                wczytajID();
                odczytajZmienne();
                if (!edit) {
                    zapytanie = "INSERT INTO sekcje_badawcze(numer,ilosc_prowiantu,ilosc_zasobow,liczba_miejsc)\n"
                            + "VALUES(?,?,?,?)";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                    st.setInt(1, id);
                    st.setInt(2, prowiant);
                    st.setInt(3, magazyn);
                    st.setInt(4, zaloga);

                    BazyProjekt.polaczenie.doZmianyPrepared(st);

                    BazyProjekt.polaczenie.closeZapytanie();
                }
                else {
                    zapytanie = "UPDATE sekcje_badawcze SET\n"
                            + "ilosc_prowiantu=?,"
                            + "ilosc_zasobow=?,"
                            + "liczba_miejsc=?\n"
                            + "WHERE numer=?";

                    PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                    System.out.println("prow: " + zaloga);
                    st.setInt(1, prowiant);
                    st.setInt(2, magazyn);
                    st.setInt(3, zaloga);
                    st.setInt(4, id);

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
}
