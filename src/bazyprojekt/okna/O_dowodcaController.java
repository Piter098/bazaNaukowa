/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna;

import bazyprojekt.BazyProjekt;
import bazyprojekt.okna.modale.M_dodajSekcjeController;
import bazyprojekt.okna.modale.M_przydzielPojazdController;
import bazyprojekt.rekordy.Pojazd;
import bazyprojekt.rekordy.SekcjaBad;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class O_dowodcaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList<SekcjaBad> itemsSekcje = FXCollections.observableArrayList();
    ObservableList<Pojazd> itemsPojazdy = FXCollections.observableArrayList();

    @FXML
    private TableView<SekcjaBad> tablicaSekcji = new TableView<>();
    @FXML
    private TableView<Pojazd> tablicaPojazdow = new TableView<>();

    @FXML
    private TableColumn<SekcjaBad, String> tabID;
    @FXML
    private TableColumn<SekcjaBad, String> tabZaloga;
    @FXML
    private TableColumn<SekcjaBad, String> tabMiejsca;
    @FXML
    private TableColumn<SekcjaBad, String> tabProwiant;
    @FXML
    private TableColumn<SekcjaBad, String> tabZasoby;

    @FXML
    private TableColumn<Pojazd, String> tabNumer;
    @FXML
    private TableColumn<Pojazd, String> tabPredkosc;
    @FXML
    private TableColumn<Pojazd, String> tabPojMagazynu;
    @FXML
    private TableColumn<Pojazd, String> tabPojZalogi;
    @FXML
    private TableColumn<Pojazd, String> tabTyp;
    @FXML
    private TableColumn<Pojazd, String> tabSekcja;
    @FXML
    private TableColumn<Pojazd, String> tabNumerSekcji;

    @FXML
    private Label lNumer;
    @FXML
    private Label lZaloga;
    @FXML
    private Label lProwiant;
    @FXML
    private Label lZasoby;

    @FXML
    private VBox vbOpis;

    @FXML
    private Tab karta1;
    @FXML
    private Tab karta2;
    @FXML
    private TabPane karty;

    @FXML
    private Button bNowaSekcja;
    @FXML
    private Button bEdytujSekcje;

    private SekcjaBad sek;
    private Pojazd poj;

    @FXML
    private void handlePowrot(ActionEvent event) throws IOException {
        BazyProjekt.c.handlePowrot(event);
    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        try {
            Tab wybranaKarta = karty.getSelectionModel().getSelectedItem();
            itemsPojazdy.clear();
            itemsSekcje.clear();

            vbOpis.setVisible(false);

            if (wybranaKarta == karta1) {
                String zapytanie = "SELECT sb.numer as sbId, liczba_miejsc, ilosc_prowiantu, ilosc_zasobow, count(prac.numer) as zaloga\n"
                        + "FROM (sekcje_badawcze sb LEFT JOIN przydzialy2 przyd ON sb.numer=przyd.SEKCJA_BADAWCZA_NUMER)\n"
                        + "LEFT JOIN pracownicy2 prac ON przyd.numer=prac.NUMER_PRZYDZ\n"
                        + "group by sb.numer, liczba_miejsc, ilosc_prowiantu, ilosc_zasobow order by sbId";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();

                while (rs.next()) {
                    SekcjaBad s = new SekcjaBad();
                    s.setNumer(new SimpleIntegerProperty(rs.getInt("sbId")));
                    s.setZaloga(new SimpleIntegerProperty(rs.getInt("zaloga")));
                    s.setMiejsca(new SimpleIntegerProperty(rs.getInt("liczba_miejsc")));
                    s.setProwiant(new SimpleIntegerProperty(rs.getInt("ilosc_prowiantu")));
                    s.setZasoby(new SimpleIntegerProperty(rs.getInt("ilosc_zasobow")));

                    //p.tryb = 1;
                    itemsSekcje.add(s);
                }

            }
            else if (wybranaKarta == karta2) {
                String zapytanie = "SELECT NUMER_REJ, PREDKOSC, POJ_MAGAZYNU, POJ_ZALOGI, TYP,"
                        + "sb.NUMER as NumerSekcji, (post.SEKTOR_SYMBOL||post.Numer) as NumerPosterunku\n"
                        + "FROM pojazdy poj LEFT JOIN SEKCJE_BADAWCZE sb\n"
                        + "ON poj.SEKCJA_BADAWCZA_NUMER=sb.NUMER\n"
                        + "LEFT JOIN POST_OCHRONY post\n"
                        + "ON (poj.POST_OCHRONY_NUMER=post.NUMER AND poj.POST_OCHRONY_SEKTOR_SYMBOL=post.SEKTOR_SYMBOL)\n"
                        + "order by NUMER_REJ";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Pojazd p = new Pojazd();
                    p.setNumer(new SimpleIntegerProperty(rs.getInt("NUMER_REJ")));
                    p.setPredkosc(new SimpleIntegerProperty(rs.getInt("PREDKOSC")));
                    p.setMagazyn(new SimpleIntegerProperty(rs.getInt("POJ_MAGAZYNU")));
                    p.setZaloga(new SimpleIntegerProperty(rs.getInt("POJ_ZALOGI")));
                    p.setTyp(new SimpleStringProperty(rs.getString("TYP")));

                    String temp = "-";
                    if (rs.getString("NumerSekcji") != null) {
                        p.setSekcja(new SimpleStringProperty("Sekcja badawcza"));
                        temp = rs.getString("NumerSekcji");
                    }
                    else if (rs.getString("NumerPosterunku") != null) {
                        p.setSekcja(new SimpleStringProperty("Posterunek ochrony"));
                        temp = rs.getString("NumerPosterunku");
                    }
                    else {
                        p.setSekcja(new SimpleStringProperty("-"));
                    }
                    p.setNumerSekcji(new SimpleStringProperty(temp));
                    //p.tryb = 1;
                    itemsPojazdy.add(p);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(O_planistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void dodajSekcje(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajSekcje.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajSekcjeController controler = loader.getController();
        if (event.getSource() == bEdytujSekcje && !tablicaSekcji.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj sekcje");
            controler.setEdit(true, sek.getNumer().getValue());
        }
        else {
            controler.setEdit(false, 0);
        }
        stage.showAndWait();

        handleRefresh(null);
    }

    @FXML
    private void usunSekcje(ActionEvent event) throws IOException {
        if (!tablicaSekcji.getSelectionModel().isEmpty()) {
            try {
                String zapytanie = "";
                zapytanie = "DELETE FROM sekcje_badawcze WHERE numer=?";
                
                int id = sek.getNumer().getValue();
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setInt(1, id);
                
                BazyProjekt.polaczenie.doZmianyPrepared(st);
                
                BazyProjekt.polaczenie.closeZapytanie();
                handleRefresh(null);
            } catch (SQLException ex) {
                Logger.getLogger(O_dowodcaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void przydzielPojazd(ActionEvent event) throws IOException {
        if (poj != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_przydzielPojazd.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage;
            stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(BazyProjekt.scena);
            //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            //BazyProjekt.scena.setScene(scene);
            stage.setScene(scene);
            M_przydzielPojazdController controler = loader.getController();
            controler.setId(poj.getNumer().getValue());

            stage.showAndWait();

            handleRefresh(null);
        }
    }
    
    @FXML
    private void wyczyscPrzydzial(ActionEvent event) throws IOException {
        if (!tablicaPojazdow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie = "";
                zapytanie = "UPDATE pojazdy SET SEKCJA_BADAWCZA_NUMER=null, POST_OCHRONY_NUMER=null, POST_OCHRONY_SEKTOR_SYMBOL=null WHERE numer_rej=?";
                
                int id = poj.getNumer().getValue();
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setInt(1, id);
                
                BazyProjekt.polaczenie.doZmianyPrepared(st);
                
                BazyProjekt.polaczenie.closeZapytanie();
                handleRefresh(null);
            } catch (SQLException ex) {
                Logger.getLogger(O_dowodcaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void pelnyOpis(SekcjaBad s) {
        if (s != null) {
            vbOpis.setVisible(true);

            lNumer.setText("#" + String.format("%09d", s.getNumer().getValue()));
            lZaloga.setText(s.getZaloga().getValue().toString() + "/" + s.getMiejsca().getValue().toString());
            lProwiant.setText(s.getProwiant().getValue().toString());
            lZasoby.setText(s.getZasoby().getValue().toString());
        }
        else {
            vbOpis.setVisible(false);
            lNumer.setText("");
            lZaloga.setText("");
            lProwiant.setText("");
            lZasoby.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //listaPracownikow.setItems(itemsPracownicy);

            tablicaSekcji.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<SekcjaBad>() {
                public void changed(ObservableValue<? extends SekcjaBad> ov,
                        SekcjaBad old_val, SekcjaBad new_val) {
                    System.out.println(new_val);
                    sek = new_val;
                    pelnyOpis(new_val);
                    if (tablicaSekcji.getSelectionModel().isEmpty()) {
                        bEdytujSekcje.setDisable(true);
                    }
                    else {
                        bEdytujSekcje.setDisable(false);
                    }
                }
            });

            tablicaPojazdow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Pojazd>() {
                public void changed(ObservableValue<? extends Pojazd> ov,
                        Pojazd old_val, Pojazd new_val) {
                    System.out.println(new_val);
                    poj = new_val;
                }
            });

            karty.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    System.out.println("Tab Selection changed");
                    try {
                        handleRefresh(null);
                    } catch (IOException ex) {
                        Logger.getLogger(O_planistaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            tabID.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabZaloga.setCellValueFactory(cellData -> cellData.getValue().getZaloga().asString());
            tabMiejsca.setCellValueFactory(cellData -> cellData.getValue().getMiejsca().asString());
            tabProwiant.setCellValueFactory(cellData -> cellData.getValue().getProwiant().asString());
            tabZasoby.setCellValueFactory(cellData -> cellData.getValue().getZasoby().asString());
            tablicaSekcji.setItems(itemsSekcje);

            tabNumer.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabPojZalogi.setCellValueFactory(cellData -> cellData.getValue().getZaloga().asString());
            tabPojMagazynu.setCellValueFactory(cellData -> cellData.getValue().getMagazyn().asString());
            tabPredkosc.setCellValueFactory(cellData -> cellData.getValue().getPredkosc().asString());
            tabTyp.setCellValueFactory(cellData -> cellData.getValue().getTyp());
            tabSekcja.setCellValueFactory(cellData -> cellData.getValue().getSekcja());
            tabNumerSekcji.setCellValueFactory(cellData -> cellData.getValue().getNumerSekcji());
            tablicaPojazdow.setItems(itemsPojazdy);

            handleRefresh(null);

            //Pracownik p = new Pracownik();
            //p.id = 3;
            //p.setImie(new SimpleStringProperty("A"));
            //itemsPracownicy.add(p);
            //p.nazwisko = "B";
        } catch (IOException ex) {
            Logger.getLogger(O_planistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
