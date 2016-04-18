/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna.modale;

import bazyprojekt.BazyProjekt;
import bazyprojekt.rekordy.Przydzial;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class M_edytujPrzydzialController implements Initializable {

    @FXML
    private Label lTytul;
    @FXML
    private Label lNumer;
    @FXML
    private Label lImie;
    @FXML
    private Label lNazwisko;
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
    private TableView<Przydzial> tablicaPrzydzialow;
    @FXML
    private TableColumn<Przydzial, String> tabId;
    @FXML
    private TableColumn<Przydzial, String> tabNazwa;
    @FXML
    private TableColumn<Przydzial, String> tabSekcja;
    @FXML
    private TableColumn<Przydzial, String> tabNumerSekcji;
    @FXML
    private Button bOK;

    private ObservableList<String> listSektory = FXCollections.observableArrayList();
    private ObservableList<String> listNumery = FXCollections.observableArrayList();
    private ObservableList<Przydzial> listPrzydzialy = FXCollections.observableArrayList();

    private String symbolSektora = "";
    private int id = 0;
    private int numer = 0;
    private int numerPrzydzialu = 0;

    @FXML
    private void doComboBoxy() {
        odblokujOK();
        listNumery.clear();
        listPrzydzialy.clear();
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
        tablicaPrzydzialow.getSelectionModel().clearSelection();
    }

    private void wczytajSektory() {
        try {
            listSektory.clear();
            listPrzydzialy.clear();
            String zapytanie = "SELECT symbol FROM sektory ORDER BY symbol";
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            while (rs.next()) {
                listSektory.add(rs.getString("SYMBOL"));
            }
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_edytujPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void wczytajIdentyfikatory() {

        try {
            symbolSektora = cbSektory.getSelectionModel().getSelectedItem();
            listNumery.clear();
            listPrzydzialy.clear();
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
            Logger.getLogger(M_edytujPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void wybierzNumer() {
        if (cbNumery.getSelectionModel().getSelectedItem() != null) {
            numer = Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem());
            wczytajPrzydzialy();
        }
    }

    @FXML
    private void odblokujOK() {
        if (!tablicaPrzydzialow.getSelectionModel().isEmpty()
                && (!cbSektory.getSelectionModel().isEmpty() || rBadawcze.isSelected())
                && !cbNumery.getSelectionModel().isEmpty()) {
            bOK.setDisable(false);
        }
        else {
            bOK.setDisable(true);
        }
    }

    private void wczytajID() {
        lNumer.setText("#" + String.format("%09d", id));
    }

    private void wczytajPrzydzialy() {
        try {
            numer = Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem());
            symbolSektora = cbSektory.getSelectionModel().getSelectedItem();
            listPrzydzialy.clear();
            String zapytanie;
            if (rBadawcze.isSelected()) {
                /*zapytanie = "SELECT numer, nazwa, SEKCJA_BADAWCZA_NUMER FROM przydzialy2 "
                        + "WHERE SEKCJA_BADAWCZA_NUMER=" + numer
                        + " ORDER BY numer";*/
                zapytanie = "SELECT numer, nazwa, SEKCJA_BADAWCZA_NUMER FROM przydzialy2\n"
                        + "WHERE SEKCJA_BADAWCZA_NUMER=" + numer+"\n"
                        + "AND (SELECT count(prac.numer)\n"
                        + "FROM (sekcje_badawcze sb LEFT JOIN przydzialy2 przyd ON sb.numer=przyd.SEKCJA_BADAWCZA_NUMER)\n"
                        + "LEFT JOIN pracownicy2 prac ON przyd.numer=prac.NUMER_PRZYDZ GROUP BY sb.NUMER\n"
                        + "having sb.numer=" + numer + ")<(SELECT liczba_miejsc FROM sekcje_badawcze WHERE numer=" + numer + ")\n"
                        + "ORDER BY numer";
            }
            else if (rLaboratoria.isSelected()) {
                zapytanie = "SELECT numer, nazwa, LABORATORIUM_NUMER, LABORATORIUM_SYMBOL FROM przydzialy2 "
                        + "WHERE LABORATORIUM_NUMER=" + numer + " AND LABORATORIUM_SYMBOL='" + symbolSektora + "'\n"
                        + "ORDER BY numer";
            }
            else if (rPosterunki.isSelected()) {
                zapytanie = "SELECT numer, nazwa, POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL FROM przydzialy2 "
                        + "WHERE POST_OCHRONY_NUMER=" + numer + " AND POST_OCHRONY_SYMBOL='" + symbolSektora + "'\n"
                        + "ORDER BY numer";
            }
            else {
                return;
            }
            System.out.println(zapytanie);
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            while (rs.next()) {
                Przydzial p = new Przydzial();
                p.setNumer(new SimpleIntegerProperty(rs.getInt("NUMER")));
                p.setNazwa(new SimpleStringProperty(rs.getString("NAZWA")));
                if (rBadawcze.isSelected()) {
                    p.setSekcja(new SimpleStringProperty("Sekcja badawcza"));
                    p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("SEKCJA_BADAWCZA_NUMER")));
                    p.setSymbol_sektora(new SimpleStringProperty(""));
                }
                else if (rLaboratoria.isSelected()) {
                    p.setSekcja(new SimpleStringProperty("Laboratorium"));
                    p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("LABORATORIUM_NUMER")));
                    p.setSymbol_sektora(new SimpleStringProperty(rs.getString("LABORATORIUM_SYMBOL")));
                }
                else if (rPosterunki.isSelected()) {
                    p.setSekcja(new SimpleStringProperty("Posterunek ochrony"));
                    p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("POST_OCHRONY_NUMER")));
                    p.setSymbol_sektora(new SimpleStringProperty(rs.getString("POST_OCHRONY_SYMBOL")));
                }
                listPrzydzialy.add(p);
            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();

        } catch (SQLException ex) {
            Logger.getLogger(M_edytujPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void wczytajDane() {
        try {
            String zapytanie = "SELECT imie, nazwisko, nazwa, b.numer as numerPrzydzialu,"
                    + "SEKCJA_BADAWCZA_NUMER,"
                    + "LABORATORIUM_SYMBOL,LABORATORIUM_NUMER,"
                    + "POST_OCHRONY_NUMER,POST_OCHRONY_SYMBOL "
                    + "FROM pracownicy2 a JOIN przydzialy2 b ON numer_przydz=b.numer "
                    + "WHERE a.numer=" + id;
            BazyProjekt.polaczenie.doZapytanie(zapytanie);
            ResultSet rs = BazyProjekt.polaczenie.getResult();
            if (rs.next()) {
                lImie.setText(rs.getString("IMIE"));
                lNazwisko.setText(rs.getString("NAZWISKO"));
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
                wczytajPrzydzialy();
                Przydzial p = new Przydzial();
                p.setNumer(new SimpleIntegerProperty(rs.getInt("numerPrzydzialu")));

                p.setNazwa(new SimpleStringProperty(rs.getString("NAZWA")));

                for (Przydzial x : listPrzydzialy) {
                    if (x.getNumer().getValue() == p.getNumer().getValue()) {
                        System.out.println("WYBRANO: " + x);
                        tablicaPrzydzialow.getSelectionModel().select(x);
                        break;
                    }
                }
            }
            BazyProjekt.polaczenie.closeZapytanie();
            odblokujOK();
        } catch (SQLException ex) {
            Logger.getLogger(M_edytujPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void odczytajZmienne() {
        if (cbNumery.getSelectionModel().getSelectedItem() != null) {
            numer = Integer.parseInt(cbNumery.getSelectionModel().getSelectedItem());
        }
        if (cbSektory.getSelectionModel().getSelectedItem() != null) {
            symbolSektora = cbSektory.getSelectionModel().getSelectedItem();
        }
        if (tablicaPrzydzialow.getSelectionModel().getSelectedItem() != null) {
            numerPrzydzialu = tablicaPrzydzialow.getSelectionModel().getSelectedItem().getNumer().getValue();
        }
    }

    @FXML
    private void handleOK(ActionEvent event) throws IOException {
        Stage s = (Stage) bOK.getScene().getWindow();
        String zapytanie = "";

        if (event.getSource() == bOK) {
            try {
                //((Stage)((Node)event.getSource()).getScene().getWindow()).close();

                //BazyProjekt.polaczenie.doZapytanie("LOCK TABLE przydzialy2 IN EXCLUSIVE MODE");
                //wczytajID();
                zapytanie = "UPDATE pracownicy2 SET numer_przydz=?\n"
                        + "WHERE numer=?";

                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);

                odczytajZmienne();
                st.setInt(1, numerPrzydzialu);
                st.setInt(2, id);

                BazyProjekt.polaczenie.doZmianyPrepared(st);

                BazyProjekt.polaczenie.closeZapytanie();

            } catch (SQLException ex) {
                Logger.getLogger(M_edytujPrzydzialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //wczytajID();
        tablicaPrzydzialow.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Przydzial>() {
            public void changed(ObservableValue<? extends Przydzial> ov,
                    Przydzial old_val, Przydzial new_val) {
                System.out.println(new_val);
                if (new_val != null) {
                    numerPrzydzialu = new_val.getNumer().getValue();
                }
                odblokujOK();
            }
        });

        cbSektory.setItems(listSektory);
        cbNumery.setItems(listNumery);
        tabId.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
        tabNazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwa());
        tabSekcja.setCellValueFactory(cellData -> cellData.getValue().getSekcja());
        tabNumerSekcji.setCellValueFactory(cellData -> cellData.getValue().getNumerSekcji());
        tablicaPrzydzialow.setItems(listPrzydzialy);
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
        wczytajID();
        wczytajDane();
    }

}
