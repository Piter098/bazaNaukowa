/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.okna;

import bazyprojekt.okna.modale.M_dodajPokojController;
import bazyprojekt.BazyProjekt;
import bazyprojekt.okna.modale.M_dodajLabController;
import bazyprojekt.okna.modale.M_dodajPojazdController;
import bazyprojekt.okna.modale.M_dodajPracownikaController;
import bazyprojekt.okna.modale.M_dodajSektorController;
import bazyprojekt.rekordy.Badanie;
import bazyprojekt.rekordy.Laboratorium;
import bazyprojekt.rekordy.Pojazd;
import bazyprojekt.rekordy.Pokoj;
import bazyprojekt.rekordy.Posterunek;
import bazyprojekt.rekordy.Pracownik;
import bazyprojekt.rekordy.Sektor;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Piter
 */
public class O_nadzorcaController implements Initializable {

    //Listy
    ObservableList<Badanie> itemsBadania = FXCollections.observableArrayList();
    ObservableList<Sektor> itemsSektory = FXCollections.observableArrayList();
    ObservableList<Pojazd> itemsPojazdy = FXCollections.observableArrayList();
    ObservableList<Pokoj> itemsPokoje = FXCollections.observableArrayList();
    ObservableList<Laboratorium> itemsLaboratoria = FXCollections.observableArrayList();
    ObservableList<Posterunek> itemsPosterunki = FXCollections.observableArrayList();
    ObservableList<Pracownik> itemsPracownicy = FXCollections.observableArrayList();

    @FXML
    private TableView<Badanie> tablicaBadan = new TableView<>();
    @FXML
    private TableView<Sektor> tablicaSektorow = new TableView<>();
    @FXML
    private TableView<Pojazd> tablicaPojazdow = new TableView<>();
    @FXML
    private TableView<Pokoj> tablicaPokoi = new TableView<>();
    @FXML
    private TableView<Laboratorium> tablicaLaboratoriow = new TableView<>();
    @FXML
    private TableView<Posterunek> tablicaPosterunkow = new TableView<>();
    @FXML
    private TableView<Pracownik> tablicaPracownikow = new TableView<>();

    //Rekordy
    Badanie bad;
    Sektor sek;
    Pracownik prac;
    Pokoj pok;
    Pojazd poj;
    Laboratorium lab;
    Posterunek pos;
    Pracownik pra;

    //Badania
    @FXML
    private Label lBTytul;
    @FXML
    private Label lBNumer;
    @FXML
    private TextArea tBTresc;
    @FXML
    private TableColumn<Badanie, String> tabBID;
    @FXML
    private TableColumn<Badanie, String> tabBTytul;
    @FXML
    private Button bBWyslij;
    @FXML
    private Button bBUsun;

    //Sektory
    @FXML
    private Label lSSymbol;
    @FXML
    private Label lSPokoje;
    @FXML
    private Label lSLaby;
    @FXML
    private Label lSPosterunki;
    @FXML
    private TableColumn<Sektor, String> tabSSymbol;
    @FXML
    private Button bSEdytuj;
    @FXML
    private Button bSUsun;

    //Pokoje
    @FXML
    private Label lPokID;
    @FXML
    private Label lPokSektor;
    @FXML
    private Label lPokMieszkancy;
    @FXML
    private Label lPokWielkosc;
    @FXML
    private TableColumn<Pokoj, String> tabPokID;
    @FXML
    private TableColumn<Pokoj, String> tabPokSektor;
    @FXML
    private TableColumn<Pokoj, String> tabPokMiejsca;
    @FXML
    private TableColumn<Pokoj, String> tabPokWielkosc;
    @FXML
    private Button bPokEdytuj;
    @FXML
    private Button bPokUsun;

    //Pojazdy
    @FXML
    private Label lPojNumer;
    @FXML
    private Label lPojSekcja;
    @FXML
    private Label lPojIdSekcji;
    @FXML
    private Label lPojZaloga;
    @FXML
    private Label lPojMagazyn;
    @FXML
    private Label lPojPredkosc;
    @FXML
    private Label lPojTyp;
    @FXML
    private TableColumn<Pojazd, String> tabPojNumer;
    @FXML
    private TableColumn<Pojazd, String> tabPojZaloga;
    @FXML
    private TableColumn<Pojazd, String> tabPojMagazyn;
    @FXML
    private TableColumn<Pojazd, String> tabPojPredkosc;
    @FXML
    private TableColumn<Pojazd, String> tabPojTyp;
    @FXML
    private Button bPojEdytuj;
    @FXML
    private Button bPojUsun;

    //Laboratoria
    @FXML
    private Label lLabNumer;
    @FXML
    private Label lLabSektor;
    @FXML
    private Label lLabDziedzina;
    @FXML
    private Label lLabPracownicy;
    @FXML
    private Label lLabPrzydzialy;
    @FXML
    private TableColumn<Laboratorium, String> tabLabNumer;
    @FXML
    private TableColumn<Laboratorium, String> tabLabSektor;
    @FXML
    private TableColumn<Laboratorium, String> tabLabDziedzina;
    @FXML
    private Button bLabEdytuj;
    @FXML
    private Button bLabUsun;

    //Posterunki
    @FXML
    private Label lPosNumer;
    @FXML
    private Label lPosSektor;
    @FXML
    private Label lPosPracownicy;
    @FXML
    private Label lPosPrzydzialy;
    @FXML
    private Label lPosPojazdy;
    @FXML
    private TableColumn<Posterunek, String> tabPosNumer;
    @FXML
    private TableColumn<Posterunek, String> tabPosSektor;
    @FXML
    private Button bPosUsun;

    //Pracownicy
    @FXML
    private Label lPraNumer;
    @FXML
    private Label lPraImie;
    @FXML
    private Label lPraNazwisko;
    @FXML
    private Label lPraSekcja;
    @FXML
    private Label lPraSekcjaNum;
    @FXML
    private Label lPraMieszkanie;
    @FXML
    private Label lPraPrzydzial;
    @FXML
    private Label lPraDataUr;
    @FXML
    private TableColumn<Pracownik, String> tabPraNumer;
    @FXML
    private TableColumn<Pracownik, String> tabPraImie;
    @FXML
    private TableColumn<Pracownik, String> tabPraNazwisko;
    @FXML
    private TableColumn<Pracownik, String> tabPraMieszkanie;
    @FXML
    private Button bPraEdytuj;
    @FXML
    private Button bPraUsun;

    //Karty
    @FXML
    private TabPane karty;
    @FXML
    private Tab karta1;
    @FXML
    private Tab karta2;
    @FXML
    private Tab karta3;
    @FXML
    private Tab karta4;
    @FXML
    private Tab karta5;
    @FXML
    private Tab karta6;
    @FXML
    private Tab karta7;

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        try {
            Tab wybranaKarta = karty.getSelectionModel().getSelectedItem();
            itemsPojazdy.clear();
            itemsBadania.clear();
            itemsSektory.clear();
            itemsPokoje.clear();
            itemsLaboratoria.clear();
            itemsPosterunki.clear();
            itemsPracownicy.clear();

            //vbOpis.setVisible(false);
            if (wybranaKarta == karta1) {
                String zapytanie = "SELECT * FROM badania order by id_badania";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();

                while (rs.next()) {
                    Badanie b = new Badanie();
                    b.setNumer(new SimpleIntegerProperty(rs.getInt("id_badania")));
                    b.setTytul(new SimpleStringProperty(rs.getString("temat")));
                    b.setTresc(new SimpleStringProperty(rs.getString("tresc")));

                    //p.tryb = 1;
                    itemsBadania.add(b);
                }
                bPelnaTresc(null);
            }
            else if (wybranaKarta == karta2) {
                String zapytanie = "select symbol,\n"
                        + "(select count(*) from laboratoria where sektor_symbol=symbol) as Laboratoria,\n"
                        + "(select count(*) from post_ochrony where sektor_symbol=symbol) as Posterunki,\n"
                        + "(select count(*) from pokoj_mieszkalne where sektor_symbol=symbol) as Pokoje\n"
                        + "from sektory order by symbol";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Sektor s = new Sektor();
                    s.setSymbol(new SimpleStringProperty(rs.getString("symbol")));
                    s.setLiczbaLab(new SimpleIntegerProperty(rs.getInt("Laboratoria")));
                    s.setLiczbaPost(new SimpleIntegerProperty(rs.getInt("Posterunki")));
                    s.setLiczbaPok(new SimpleIntegerProperty(rs.getInt("Pokoje")));

                    //p.tryb = 1;
                    itemsSektory.add(s);
                }
                bPelnaTresc(null);
            }
            else if (wybranaKarta == karta3) {
                String zapytanie = "select pok.numer as numer_pokoju, sektor_symbol, LICZBA_MIEJSC, WIELKOSC, count(prac.numer) as mieszkancy\n"
                        + "from pokoj_mieszkalne pok\n"
                        + "left join pracownicy2 prac on pok.numer=pokoj_mieszk_numer\n"
                        + "and pok.sektor_symbol=prac.POKOJ_MIESZK_SEKTOR_SYMBOL\n"
                        + "group by pok.numer, sektor_symbol, LICZBA_MIEJSC, WIELKOSC order by sektor_symbol,numer_pokoju";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Pokoj p = new Pokoj();
                    p.setNumer(new SimpleIntegerProperty(rs.getInt("numer_pokoju")));
                    p.setSektor(new SimpleStringProperty(rs.getString("sektor_symbol")));
                    p.setMiejsca(new SimpleIntegerProperty(rs.getInt("LICZBA_MIEJSC")));
                    p.setMieszkancy(new SimpleIntegerProperty(rs.getInt("mieszkancy")));
                    p.setWielkosc(new SimpleIntegerProperty(rs.getInt("WIELKOSC")));

                    //p.tryb = 1;
                    itemsPokoje.add(p);
                }
                bPelnaTresc(null);
            }
            else if (wybranaKarta == karta4) {
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
                    itemsPojazdy.add(p);
                }
            }
            else if (wybranaKarta == karta5) {
                /*String zapytanie = "SELECT lab.numer as labId, SEKTOR_SYMBOL, DZIEDZINA_BADAN, count(prac.numer) as zaloga\n"
                        + "FROM (laboratoria lab LEFT JOIN przydzialy2 przyd ON lab.numer=przyd.LABORATORIUM_NUMER and lab.SEKTOR_SYMBOL=przyd.LABORATORIUM_SYMBOL)\n"
                        + "LEFT JOIN pracownicy2 prac ON przyd.numer=prac.NUMER_PRZYDZ\n"
                        + "group by lab.numer, lab.SEKTOR_SYMBOL, lab.DZIEDZINA_BADAN";
*/
                String zapytanie = "SELECT lab.numer as labId,DZIEDZINA_BADAN, SEKTOR_SYMBOL,\n"
                        + "(select count(*) from przydzialy2 przy where lab.numer=LABORATORIUM_NUMER\n"
                        + "and lab.SEKTOR_SYMBOL=LABORATORIUM_SYMBOL) as przydzialy,\n"
                        + "(select count(*) from pracownicy2 pra left join przydzialy2 przy on pra.NUMER_PRZYDZ=przy.NUMER\n"
                        + "where lab.numer=LABORATORIUM_NUMER and lab.SEKTOR_SYMBOL=LABORATORIUM_SYMBOL) as pracownicy\n"
                        + "from laboratoria lab order by SEKTOR_SYMBOL,labId";
                
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Laboratorium l = new Laboratorium();
                    l.setNumer(new SimpleIntegerProperty(rs.getInt("labId")));
                    l.setSektor(new SimpleStringProperty(rs.getString("SEKTOR_SYMBOL")));
                    l.setDziedzina(new SimpleStringProperty(rs.getString("DZIEDZINA_BADAN")));
                    l.setPracownicy(new SimpleIntegerProperty(rs.getInt("pracownicy")));
                    l.setPrzydzialy(new SimpleIntegerProperty(rs.getInt("przydzialy")));

                    itemsLaboratoria.add(l);
                }
            }
            else if (wybranaKarta == karta6) {
                String zapytanie = "SELECT pos.numer as postId, SEKTOR_SYMBOL,\n"
                        + "(select count(*) from "
                        + "przydzialy2 przy where pos.numer=POST_OCHRONY_NUMER "
                        + "and pos.SEKTOR_SYMBOL=POST_OCHRONY_SYMBOL) as przydzialy,\n"
                        + "(select count(*) from "
                        + "pracownicy2 pra left join przydzialy2 przy on pra.NUMER_PRZYDZ=przy.NUMER "
                        + "where pos.numer=POST_OCHRONY_NUMER and pos.SEKTOR_SYMBOL=POST_OCHRONY_SYMBOL) as pracownicy,\n"
                        + "(select count(*) from "
                        + "pojazdy poj where pos.numer=POST_OCHRONY_NUMER "
                        + "and pos.SEKTOR_SYMBOL=POST_OCHRONY_SEKTOR_SYMBOL) as pojazdy\n"
                        + "from post_ochrony pos order by SEKTOR_SYMBOL,postId";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Posterunek p = new Posterunek();
                    p.setNumer(new SimpleIntegerProperty(rs.getInt("postId")));
                    p.setSektor(new SimpleStringProperty(rs.getString("SEKTOR_SYMBOL")));
                    p.setPracownicy(new SimpleIntegerProperty(rs.getInt("pracownicy")));
                    p.setPrzydzialy(new SimpleIntegerProperty(rs.getInt("przydzialy")));
                    p.setPojazdy(new SimpleIntegerProperty(rs.getInt("pojazdy")));

                    itemsPosterunki.add(p);
                }
            }
            else if (wybranaKarta == karta7) {
                String zapytanie = "SELECT p.numer as ID_PRAC,imie,nazwisko,TO_CHAR(data_ur,'YYYY/MM/DD') as URODZINY,POKOJ_MIESZK_NUMER,\n"
                        + "POKOJ_MIESZK_SEKTOR_SYMBOL, r.nazwa as NAZWA_PRZYDZ,\n"
                        + "SEKCJA_BADAWCZA_NUMER, LABORATORIUM_NUMER, LABORATORIUM_SYMBOL,\n"
                        + "POST_OCHRONY_NUMER, POST_OCHRONY_SYMBOL\n"
                        + "FROM pracownicy2 p left JOIN przydzialy2 r ON p.numer_przydz = r.numer\n"
                        + "order by ID_PRAC";
                BazyProjekt.polaczenie.doZapytanie(zapytanie);
                ResultSet rs = BazyProjekt.polaczenie.getResult();
                while (rs.next()) {
                    Pracownik p = new Pracownik();
                    p.setId(new SimpleIntegerProperty(rs.getInt("ID_PRAC")));
                    p.setImie(new SimpleStringProperty(rs.getString("IMIE")));
                    p.setNazwisko(new SimpleStringProperty(rs.getString("nazwisko")));
                    p.setPrzydzial(new SimpleStringProperty(rs.getString("NAZWA_PRZYDZ")));
                    p.setUrodziny(new SimpleStringProperty(rs.getString("urodziny")));
                    p.setMieszkanie(new SimpleStringProperty(rs.getString("POKOJ_MIESZK_SEKTOR_SYMBOL") + rs.getString("POKOJ_MIESZK_NUMER")));
                    
                    String temp;
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

                    itemsPracownicy.add(p);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(O_planistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handlePowrot(ActionEvent event) throws IOException {
        BazyProjekt.c.handlePowrot(event);
    }

    private void bPelnaTresc(Object o) {
        if (o != null) {
            if (o.getClass() == Badanie.class) {
                Badanie b = (Badanie) o;
                bBWyslij.setDisable(false);
                bBUsun.setDisable(false);
                lBNumer.setText("#" + String.format("%09d", b.getNumer().getValue()));
                lBTytul.setText(b.getTytul().getValueSafe());
                tBTresc.setText(b.getTresc().getValueSafe());
            }
            else if (o.getClass() == Sektor.class) {
                Sektor s = (Sektor) o;
                bSEdytuj.setDisable(false);
                bSUsun.setDisable(false);
                lSSymbol.setText(s.getSymbol().getValueSafe());
                lSLaby.setText(s.getLiczbaLab().getValue().toString());
                lSPosterunki.setText(s.getLiczbaPost().getValue().toString());
                lSPokoje.setText(s.getLiczbaPok().getValue().toString());
            }
            else if (o.getClass() == Pokoj.class) {
                Pokoj p = (Pokoj) o;
                bPokEdytuj.setDisable(false);
                bPokUsun.setDisable(false);
                lPokID.setText("#" + String.format("%09d", p.getNumer().getValue()));
                lPokSektor.setText(p.getSektor().getValueSafe());
                lPokWielkosc.setText(p.getWielkosc().getValue().toString());
                lPokMieszkancy.setText(p.getMieszkancy().getValue().toString() + "/" + p.getMiejsca().getValue().toString());
            }
            else if (o.getClass() == Pojazd.class) {
                Pojazd p = (Pojazd) o;
                bPojEdytuj.setDisable(false);
                bPojUsun.setDisable(false);
                lPojNumer.setText("#" + String.format("%09d", p.getNumer().getValue()));
                lPojSekcja.setText(p.getSekcja().getValueSafe());
                lPojIdSekcji.setText(p.getNumerSekcji().getValueSafe());
                lPojZaloga.setText(p.getZaloga().getValue().toString());
                lPojMagazyn.setText(p.getMagazyn().getValue().toString());
                lPojPredkosc.setText(p.getPredkosc().getValue().toString());
                lPojTyp.setText(p.getTyp().getValueSafe());
            }
            else if (o.getClass() == Laboratorium.class) {
                Laboratorium l = (Laboratorium) o;
                bLabEdytuj.setDisable(false);
                bLabUsun.setDisable(false);
                lLabNumer.setText("#" + String.format("%09d", l.getNumer().getValue()));
                lLabSektor.setText(l.getSektor().getValueSafe());
                lLabDziedzina.setText(l.getDziedzina().getValueSafe());
                lLabPracownicy.setText(l.getPracownicy().getValue().toString());
                lLabPrzydzialy.setText(l.getPrzydzialy().getValue().toString());
            }
            else if (o.getClass() == Posterunek.class) {
                Posterunek p = (Posterunek) o;
                bPosUsun.setDisable(false);
                lPosNumer.setText("#" + String.format("%09d", p.getNumer().getValue()));
                lPosSektor.setText(p.getSektor().getValueSafe());
                lPosPracownicy.setText(p.getPracownicy().getValue().toString());
                lPosPrzydzialy.setText(p.getPrzydzialy().getValue().toString());
                lPosPojazdy.setText(p.getPojazdy().getValue().toString());
            }
            else if (o.getClass() == Pracownik.class) {
                Pracownik p = (Pracownik) o;
                bPraEdytuj.setDisable(false);
                bPraUsun.setDisable(false);
                lPraNumer.setText("#" + String.format("%09d", p.getId().getValue()));
                lPraImie.setText(p.getImie().getValueSafe());
                lPraNazwisko.setText(p.getNazwisko().getValueSafe());
                lPraPrzydzial.setText(p.getPrzydzial().getValueSafe());
                lPraDataUr.setText(p.getUrodziny().getValueSafe());
                lPraMieszkanie.setText(p.getMieszkanie().getValueSafe());
                lPraSekcja.setText(p.getSekcja().getValueSafe());
                lPraSekcjaNum.setText(p.getNumerSekcji().getValueSafe());
            }
        }
        else {
            bBWyslij.setDisable(true);
            bBUsun.setDisable(true);
            lBNumer.setText("");
            lBTytul.setText("");
            tBTresc.setText("");

            bSEdytuj.setDisable(true);
            bSUsun.setDisable(true);
            lSSymbol.setText("");
            lSLaby.setText("");
            lSPosterunki.setText("");
            lSPokoje.setText("");

            bPokEdytuj.setDisable(true);
            bPokUsun.setDisable(true);
            lPokID.setText("");
            lPokSektor.setText("");
            lPokWielkosc.setText("");
            lPokMieszkancy.setText("");

            bPojEdytuj.setDisable(true);
            bPojUsun.setDisable(true);
            lPojNumer.setText("");
            lPojSekcja.setText("");
            lPojIdSekcji.setText("");
            lPojZaloga.setText("");
            lPojMagazyn.setText("");
            lPojPredkosc.setText("");

            bLabEdytuj.setDisable(true);
            bLabUsun.setDisable(true);
            lLabNumer.setText("");
            lLabSektor.setText("");
            lLabDziedzina.setText("");
            lLabPracownicy.setText("");
            lLabPrzydzialy.setText("");

            bPosUsun.setDisable(true);
            lPosNumer.setText("");
            lPosSektor.setText("");
            lPosPracownicy.setText("");
            lPosPrzydzialy.setText("");
            lPosPojazdy.setText("");

            bPraEdytuj.setDisable(true);
            bPraUsun.setDisable(true);
            lPraNumer.setText("");
            lPraImie.setText("");
            lPraNazwisko.setText("");
            lPraPrzydzial.setText("");
            lPraDataUr.setText("");
            lPraMieszkanie.setText("");
            lPraSekcja.setText("");
            lPraSekcjaNum.setText("");
        }
    }

    @FXML
    private void handleWyslij() {
        if (bad != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Wiadomość od systemu");
            alert.setHeaderText("Badanie zostało wysłane do sztabu!"); //ta akurat :P
            alert.setContentText("#" + bad.getNumer().getValue() + ": " + bad.getTytul().getValueSafe());
            alert.showAndWait();
        }
    }

    @FXML
    private void usunBadanie(ActionEvent event) throws IOException {
        if (!tablicaBadan.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM badania WHERE id_badania=?";

                int id = bad.getNumer().getValue();
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
    private void handleNowySektor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajSektor.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajSektorController controler = loader.getController();
        if (event.getSource() == bSEdytuj && !tablicaSektorow.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj sekcje");
            controler.setEdit(true, sek.getSymbol().getValueSafe());
        }
        else {
            controler.setEdit(false, "");
        }
        stage.showAndWait();

        handleRefresh(null);
    }

    @FXML
    private void usunSektor(ActionEvent event) throws IOException {
        if (!tablicaSektorow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM sektory WHERE symbol=?";

                String id = sek.getSymbol().getValueSafe();
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setString(1, id);

                BazyProjekt.polaczenie.doZmianyPrepared(st);

                BazyProjekt.polaczenie.closeZapytanie();
                handleRefresh(null);
            } catch (SQLException ex) {
                Logger.getLogger(O_dowodcaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void handleNowyPokoj(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajPokoj.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajPokojController controler = loader.getController();
        if (event.getSource() == bPokEdytuj && !tablicaPokoi.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj pokój");
            controler.setEdit(true, pok.getNumer().getValue(), pok.getSektor().getValueSafe());
        }
        else {
            controler.setEdit(false, 0, "");
        }
        stage.showAndWait();

        handleRefresh(null);
    }

    @FXML
    private void usunPokoj(ActionEvent event) throws IOException {
        if (!tablicaPokoi.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM pokoj_mieszkalne WHERE numer=? AND sektor_symbol=?";

                int id = pok.getNumer().getValue();
                String sektor = pok.getSektor().getValueSafe();
                PreparedStatement st = BazyProjekt.polaczenie.doPrepareStatement(zapytanie);
                st.setInt(1, id);
                st.setString(2, sektor);

                int zmiany = BazyProjekt.polaczenie.doZmianyPrepared(st);
                System.out.println("Usunieto " + zmiany);
                BazyProjekt.polaczenie.closeZapytanie();
                handleRefresh(null);
            } catch (SQLException ex) {
                Logger.getLogger(O_dowodcaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void handleNowyPojazd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajPojazd.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajPojazdController controler = loader.getController();
        if (event.getSource() == bPojEdytuj && !tablicaPojazdow.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj pojazd");
            controler.setEdit(true, poj.getNumer().getValue());
        }
        else {
            controler.setEdit(false, 0);
        }
        stage.showAndWait();

        handleRefresh(null);
    }

    @FXML
    private void usunPojazd(ActionEvent event) throws IOException {
        if (!tablicaPojazdow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM pojazdy WHERE numer_rej=?";

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

    @FXML
    private void handleNowyLab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajLab.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajLabController controler = loader.getController();
        if (event.getSource() == bLabEdytuj && !tablicaLaboratoriow.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj laboratorium");
            controler.setEdit(true, lab.getNumer().getValue(), lab.getSektor().getValueSafe());
        }
        else {
            controler.setEdit(false, 0, "");
        }
        stage.showAndWait();

        handleRefresh(null);
    }
    @FXML
    private void usunLab(ActionEvent event) throws IOException {
        if (!tablicaLaboratoriow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM laboratoria WHERE numer=?";

                int id = lab.getNumer().getValue();
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
    private void handleNowyPost(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajPost.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        stage.showAndWait();

        handleRefresh(null);
    }
    @FXML
    private void usunPost(ActionEvent event) throws IOException {
        if (!tablicaPosterunkow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM post_ochrony WHERE numer=?";

                int id = pos.getNumer().getValue();
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
    private void handleNowyPrac(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modale/M_dodajPracownika.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(BazyProjekt.scena);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //BazyProjekt.scena.setScene(scene);
        stage.setScene(scene);

        M_dodajPracownikaController controler = loader.getController();
        if (event.getSource() == bPraEdytuj && !tablicaPracownikow.getSelectionModel().isEmpty()) {
            controler.setlTytul("Edytuj pracownika");
            controler.setEdit(true, pra.getId().getValue());
        }
        else {
            controler.setEdit(false, 0);
        }
        stage.showAndWait();

        handleRefresh(null);
    }
    @FXML
    private void usunPrac(ActionEvent event) throws IOException {
        if (!tablicaPracownikow.getSelectionModel().isEmpty()) {
            try {
                String zapytanie;
                zapytanie = "DELETE FROM pracownicy2 WHERE numer=?";

                int id = pra.getId().getValue();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            tablicaBadan.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Badanie>() {
                public void changed(ObservableValue<? extends Badanie> ov,
                        Badanie old_val, Badanie new_val) {
                    System.out.println(new_val);
                    bad = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaSektorow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Sektor>() {
                public void changed(ObservableValue<? extends Sektor> ov,
                        Sektor old_val, Sektor new_val) {
                    System.out.println(new_val);
                    sek = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaPokoi.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Pokoj>() {
                public void changed(ObservableValue<? extends Pokoj> ov,
                        Pokoj old_val, Pokoj new_val) {
                    System.out.println(new_val);
                    pok = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaPojazdow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Pojazd>() {
                public void changed(ObservableValue<? extends Pojazd> ov,
                        Pojazd old_val, Pojazd new_val) {
                    System.out.println(new_val);
                    poj = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaLaboratoriow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Laboratorium>() {
                public void changed(ObservableValue<? extends Laboratorium> ov,
                        Laboratorium old_val, Laboratorium new_val) {
                    System.out.println(new_val);
                    lab = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaPosterunkow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Posterunek>() {
                public void changed(ObservableValue<? extends Posterunek> ov,
                        Posterunek old_val, Posterunek new_val) {
                    System.out.println(new_val);
                    pos = new_val;
                    bPelnaTresc(new_val);
                }
            });

            tablicaPracownikow.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Pracownik>() {
                public void changed(ObservableValue<? extends Pracownik> ov,
                        Pracownik old_val, Pracownik new_val) {
                    System.out.println(new_val);
                    pra = new_val;
                    bPelnaTresc(new_val);
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

            tabBID.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabBTytul.setCellValueFactory(cellData -> cellData.getValue().getTytul());
            tablicaBadan.setItems(itemsBadania);

            tabSSymbol.setCellValueFactory(cellData -> cellData.getValue().getSymbol());
            tablicaSektorow.setItems(itemsSektory);

            tabPokID.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabPokSektor.setCellValueFactory(cellData -> cellData.getValue().getSektor());
            tabPokMiejsca.setCellValueFactory(cellData -> cellData.getValue().getMiejsca().asString());
            tabPokWielkosc.setCellValueFactory(cellData -> cellData.getValue().getWielkosc().asString());
            tablicaPokoi.setItems(itemsPokoje);

            tabPojNumer.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabPojTyp.setCellValueFactory(cellData -> cellData.getValue().getTyp());
            tabPojZaloga.setCellValueFactory(cellData -> cellData.getValue().getZaloga().asString());
            tabPojMagazyn.setCellValueFactory(cellData -> cellData.getValue().getMagazyn().asString());
            tabPojPredkosc.setCellValueFactory(cellData -> cellData.getValue().getPredkosc().asString());
            tablicaPojazdow.setItems(itemsPojazdy);

            tabLabNumer.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabLabSektor.setCellValueFactory(cellData -> cellData.getValue().getSektor());
            tabLabDziedzina.setCellValueFactory(cellData -> cellData.getValue().getDziedzina());
            tablicaLaboratoriow.setItems(itemsLaboratoria);

            tabPosNumer.setCellValueFactory(cellData -> cellData.getValue().getNumer().asString());
            tabPosSektor.setCellValueFactory(cellData -> cellData.getValue().getSektor());
            tablicaPosterunkow.setItems(itemsPosterunki);

            tabPraNumer.setCellValueFactory(cellData -> cellData.getValue().getId().asString());
            tabPraImie.setCellValueFactory(cellData -> cellData.getValue().getImie());
            tabPraNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwisko());
            tabPraMieszkanie.setCellValueFactory(cellData -> cellData.getValue().getMieszkanie());
            tablicaPracownikow.setItems(itemsPracownicy);

            handleRefresh(null);
        } catch (IOException ex) {
            Logger.getLogger(O_nadzorcaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
