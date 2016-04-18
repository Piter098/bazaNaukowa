/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.rekordy;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Piter
 */
public class Pojazd {

    private IntegerProperty numer;
    private IntegerProperty predkosc;
    private IntegerProperty zaloga;
    private IntegerProperty magazyn;
    private StringProperty typ;
    private StringProperty sekcja;
    private StringProperty numerSekcji;

    /**
     * @return the numer
     */
    public IntegerProperty getNumer() {
        return numer;
    }

    /**
     * @param numer the numer to set
     */
    public void setNumer(IntegerProperty numer) {
        this.numer = numer;
    }

    /**
     * @return the predkosc
     */
    public IntegerProperty getPredkosc() {
        return predkosc;
    }

    /**
     * @param predkosc the predkosc to set
     */
    public void setPredkosc(IntegerProperty predkosc) {
        this.predkosc = predkosc;
    }

    /**
     * @return the zaloga
     */
    public IntegerProperty getZaloga() {
        return zaloga;
    }

    /**
     * @param zaloga the zaloga to set
     */
    public void setZaloga(IntegerProperty zaloga) {
        this.zaloga = zaloga;
    }

    /**
     * @return the magazyn
     */
    public IntegerProperty getMagazyn() {
        return magazyn;
    }

    /**
     * @param magazyn the magazyn to set
     */
    public void setMagazyn(IntegerProperty magazyn) {
        this.magazyn = magazyn;
    }

    /**
     * @return the typ
     */
    public StringProperty getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(StringProperty typ) {
        this.typ = typ;
    }

    /**
     * @return the sekcja
     */
    public StringProperty getSekcja() {
        return sekcja;
    }

    /**
     * @param sekcja the sekcja to set
     */
    public void setSekcja(StringProperty sekcja) {
        this.sekcja = sekcja;
    }

    /**
     * @return the numerSekcji
     */
    public StringProperty getNumerSekcji() {
        return numerSekcji;
    }

    /**
     * @param numerSekcji the numerSekcji to set
     */
    public void setNumerSekcji(StringProperty numerSekcji) {
        this.numerSekcji = numerSekcji;
    }
}
