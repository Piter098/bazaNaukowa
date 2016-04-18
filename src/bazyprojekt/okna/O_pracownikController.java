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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class O_pracownikController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int idPrac = 0;

    @FXML
    private Label lNumer;
    @FXML
    private Label lImie;
    @FXML
    private Label lNazwisko;
    @FXML
    private Label lPrzydzial;
    @FXML
    private Label lDataUr;
    @FXML
    private Label lMieszkanie;
    @FXML
    private Label lSekcja;
    @FXML
    private Label lSekcjaNum;
    @FXML
    private Label lTytul;
    @FXML
    private VBox vbOpis;

    @FXML
    private void handlePowrot(ActionEvent event) throws IOException {
        BazyProjekt.c.handlePowrot(event);
    }

    @FXML
    private void showModal() throws IOException {
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

        stage.showAndWait();
        idPrac = Integer.parseInt(controler.getNumer());
        //lNumer.setText(controler.getNumer());
    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        try {

            String zapytanie = "SELECT p.numer as ID_PRAC,imie,nazwisko,TO_CHAR(data_ur,'YYYY/MM/DD') as URODZINY,"
                    + "POKOJ_MIESZK_NUMER, POKOJ_MIESZK_SEKTOR_SYMBOL, r.nazwa as NAZWA_PRZYDZ,\n"
                    + "SEKCJA_BADAWCZA_NUMER, LABORATORIUM_NUMER, LABORATORIUM_SYMBOL,\n"
                    + "POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL\n"
                    + "FROM pracownicy2 p left JOIN przydzialy2 r ON p.numer_przydz = r.numer WHERE p.numer=" + idPrac;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            System.out.println(idPrac);
            ResultSet rs = BazyProjekt.polaczenie.getResult();

            if (rs.next()) {
                lNumer.setText("#" + String.format("%09d", (Integer) rs.getInt("ID_PRAC")));
                lImie.setText(rs.getString("IMIE"));
                lNazwisko.setText(rs.getString("NAZWISKO"));
                lPrzydzial.setText(rs.getString("NAZWA_PRZYDZ"));
                lDataUr.setText(rs.getString("URODZINY"));
                lMieszkanie.setText(rs.getString("POKOJ_MIESZK_SEKTOR_SYMBOL") + rs.getString("POKOJ_MIESZK_NUMER"));

                String temp;
                if (rs.getString("SEKCJA_BADAWCZA_NUMER") != null) {
                    lSekcja.setText("Sekcja badawcza");
                    temp = rs.getString("SEKCJA_BADAWCZA_NUMER");
                }
                else if (rs.getString("LABORATORIUM_NUMER") != null) {
                    lSekcja.setText("Laboratorium");
                    temp = rs.getString("LABORATORIUM_SYMBOL") + rs.getInt("LABORATORIUM_NUMER");
                }
                else if (rs.getString("POST_OCHRONY_NUMER") != null) {
                    lSekcja.setText("Posterunek ochrony");
                    temp = rs.getString("POST_OCHRONY_SYMBOL") + rs.getInt("POST_OCHRONY_NUMER");
                }
                else {
                    lSekcja.setText("");
                    temp = "";
                }
                lSekcjaNum.setText(temp);

                vbOpis.setVisible(true);
                lTytul.setText("Karta pracownika");
            }
            else {
                vbOpis.setVisible(false);
                lTytul.setText("Brak pracownika!");
            }

            BazyProjekt.polaczenie.closeZapytanie();
        } catch (SQLException ex) {
            Logger.getLogger(O_pracownikController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            showModal();
            handleRefresh(null);

        } catch (IOException ex) {
            Logger.getLogger(O_pracownikController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
