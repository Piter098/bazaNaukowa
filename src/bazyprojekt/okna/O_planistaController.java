/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna;

import bazyprojekt.BazyProjekt;
import bazyprojekt.okna.modale.M_dodajPrzydzialController;
import bazyprojekt.okna.modale.M_edytujPrzydzialController;
import bazyprojekt.rekordy.Pracownik;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
public class O_planistaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ListView<Pracownik> listaPracownikow = new ListView<>();

    ObservableList<Pracownik> itemsPracownicy = FXCollections.observableArrayList();
    ObservableList<Przydzial> itemsPrzydzialy = FXCollections.observableArrayList();

    @FXML
    private TableView<Pracownik> tablicaPracownikow = new TableView<>();
    @FXML
    private TableView<Przydzial> tablicaPrzydzialow = new TableView<>();

    @FXML
    private TableColumn<Pracownik, String> tabID;
    @FXML
    private TableColumn<Pracownik, String> tabImie;
    @FXML
    private TableColumn<Pracownik, String> tabNazwisko;
    @FXML
    private TableColumn<Pracownik, String> tabPrzydzial;
    @FXML
    private TableColumn<Pracownik, String> tabSekcja2;
    @FXML
    private TableColumn<Pracownik, String> tabNumerSekcji2;

    @FXML
    private TableColumn<Przydzial, String> tabNumer;
    @FXML
    private TableColumn<Przydzial, String> tabNazwa;
    @FXML
    private TableColumn<Przydzial, String> tabSekcja;
    @FXML
    private TableColumn<Przydzial, String> tabNumerSekcji;

    @FXML
    private Label lNazwa;
    @FXML
    private Label lSekcja;
    @FXML
    private Label lIdSekcji;
    @FXML
    private Label lIdPrzydzialu;
    @FXML
    private VBox vbOpis;

    @FXML
    private Tab karta1;
    @FXML
    private Tab karta2;
    @FXML
    private TabPane karty;

    @FXML
    private Button bNowyPrzydzial;
    @FXML
    private Button bEdytujPrzydzial;

    private Przydzial przyd;
    private Pracownik prac;

    @FXML
    private void handlePowrot(ActionEvent event) throws IOException {
        BazyProjekt.c.handlePowrot(event);
    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        try {
            Tab wybranaKarta = karty.getSelectionModel().getSelectedItem();
            itemsPrzydzialy.clear();
            itemsPracownicy.clear();

            vbOpis.setVisible(false);

            if (wybranaKarta == karta1) {
                String zapytanie = "SELECT numer, nazwa,"
                        + "SEKCJA_BADAWCZA_NUMER, LABORATORIUM_NUMER, LABORATORIUM_SYMBOL, POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL\n"
                        + "FROM przydzialy2 order by numer";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();

                while (rs.next()) {
                    Przydzial p = new Przydzial();
                    p.setNumer(new SimpleIntegerProperty(rs.getInt("NUMER")));
                    p.setNazwa(new SimpleStringProperty(rs.getString("NAZWA")));

                    if (rs.getString("SEKCJA_BADAWCZA_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Sekcja badawcza"));
                        p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("SEKCJA_BADAWCZA_NUMER")));
                        p.setSymbol_sektora(new SimpleStringProperty(""));
                    }
                    else if (rs.getString("LABORATORIUM_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Laboratorium"));
                        p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("LABORATORIUM_NUMER")));
                        p.setSymbol_sektora(new SimpleStringProperty(rs.getString("LABORATORIUM_SYMBOL")));
                    }
                    else if (rs.getString("POST_OCHRONY_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Posterunek ochrony"));
                        p.setId_sekcji(new SimpleIntegerProperty(rs.getInt("POST_OCHRONY_NUMER")));
                        p.setSymbol_sektora(new SimpleStringProperty(rs.getString("POST_OCHRONY_SYMBOL")));
                    }
                    else {
                        p.setSekcja(new SimpleStringProperty(""));
                        p.setId_sekcji(new SimpleIntegerProperty(0));
                        p.setSymbol_sektora(new SimpleStringProperty(""));
                    }
                    //p.tryb = 1;
                    itemsPrzydzialy.add(p);
                }

            }
            else if (wybranaKarta == karta2) {
                String zapytanie = "SELECT a.numer as IdPracownika, imie, nazwisko, nazwa,\n"
                        + "SEKCJA_BADAWCZA_NUMER,"
                        + "LABORATORIUM_NUMER, LABORATORIUM_SYMBOL,"
                        + "POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL\n"
                        + "FROM pracownicy2 a left JOIN przydzialy2 b ON numer_przydz = b.numer ORDER BY a.numer";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Pracownik p = new Pracownik();
                    p.setId(new SimpleIntegerProperty(rs.getInt("IdPracownika")));
                    p.setImie(new SimpleStringProperty(rs.getString("IMIE")));
                    p.setNazwisko(new SimpleStringProperty(rs.getString("NAZWISKO")));
                    p.setPrzydzial(new SimpleStringProperty(rs.getString("nazwa")));

                    String temp = "";
                    if (rs.getString("SEKCJA_BADAWCZA_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Sekcja badawcza"));
                        temp = rs.getString("SEKCJA_BADAWCZA_NUMER");
                    }
                    else if (rs.getString("LABORATORIUM_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Laboratorium"));
                        temp = rs.getString("LABORATORIUM_SYMBOL") + rs.getInt("LABORATORIUM_NUMER");
                    }
                    else if (rs.getString("POST_OCHRONY_NUMER") != null) {
                        p.setSekcja(new SimpleStringProperty("Posterunek ochrony"));
                        temp = rs.getString("POST_OCHRONY_SYMBOL") + rs.getInt("POST_OCHRONY_NUMER");
                    }
                    else {
                        p.setPrzydzial(new SimpleStringProperty(""));
                        p.setSekcja(new SimpleStringProperty(""));
                        temp = "";
                    }
                    p.setNumerSekcji(new SimpleStringProperty(temp));
                    //p.tryb = 1;
                    itemsPracownicy.add(p);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(O_planistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void dodajPrzydzial(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajPrzydzial.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajPrzydzialController controler = loader.getController();
        if (event.getSource() == bEdytujPrzydzial && !tablicaPrzydzialow.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj przydział");
            controler.setEdit(true, przyd.getNumer().getValue());
        }
        else {
            controler.setEdit(false, 0);
        }
        stage.showAndWait();

        handleRefresh(null);
    }

    @FXML
    private void ustalPrzydzial(ActionEvent event) throws IOException {
        if (prac != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_edytujPrzydzial.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage;
            stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(BazyProjekt.scena);
            //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            //BazyProjekt.scena.setScene(scene);
            stage.setScene(scene);
            M_edytujPrzydzialController controler = loader.getController();
            controler.setId(prac.getId().getValue());

            stage.showAndWait();

            handleRefresh(null);
        }
    }
    
    @FXML
    private void wyczyscPrzydzial(ActionEvent event) throws IOException {
        if (!tablicaPracownikow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie = "";
                zapytanie = "UPDATE pracownicy2 SET numer_przydz=null WHERE numer=?";
                
                int id = prac.getId().getValue();
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

    private void pelnyOpis(Przydzial p) {
        if (p != null) {
            vbOpis.setVisible(true);

            lNazwa.setText(p.getNazwa().getValueSafe());
            lIdPrzydzialu.setText("#" + String.format("%09d", p.getNumer().getValue()));
            lSekcja.setText(p.getSekcja().getValueSafe());
            lIdSekcji.setText(p.getSymbol_sektora().getValueSafe() + p.getId_sekcji().getValue());
        }
        else {
            vbOpis.setVisible(false);
            lNazwa.setText("");
            lSekcja.setText("");
            lIdSekcji.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //listaPracownikow.setItems(itemsPracownicy);

            tablicaPracownikow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Pracownik>() {
                public void changed(ObservableValue<? extends Pracownik> ov,
                        Pracownik old_val, Pracownik new_val) {
                    System.out.println(new_val);
                    prac = new_val;
                }
            });

            tablicaPrzydzialow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Przydzial>() {
                public void changed(ObservableValue<? extends Przydzial> ov,
                        Przydzial old_val, Przydzial new_val) {
                    System.out.println(new_val);
                    przyd = new_val;
                    pelnyOpis(new_val);
                    if (tablicaPrzydzialow.getSelectionModel().isEmpty()) {
                        bEdytujPrzydzial.setDisable(true);
                    }
                    else {
                        bEdytujPrzydzial.setDisable(false);
                    }
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

            tabID.setCellValueFactory(cellData -> cellData.getValue().getId().asString());
            tabImie.setCellValueFactory(cellData -> cellData.getValue().getImie());
            tabNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwisko());
            tabPrzydzial.setCellValueFactory(cellData -> cellData.getValue().getPrzydzial());
            tabSekcja2.setCellValueFactory(cellData -> cellData.getValue().getSekcja());
            tabNumerSekcji2.setCellValueFactory(cellData -> cellData.getValue().getNumerSekcji());
            tablicaPracownikow.setItems(itemsPracownicy);

            tabNumer.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabNazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwa());
            tabSekcja.setCellValueFactory(cellData -> cellData.getValue().getSekcja());
            tabNumerSekcji.setCellValueFactory(cellData -> cellData.getValue().getNumerSekcji());
            tablicaPrzydzialow.setItems(itemsPrzydzialy);

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
