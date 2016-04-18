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
public class Laboratorium {
    private StringProperty dziedzina;
    private StringProperty sektor;
    private IntegerProperty numer;
    private IntegerProperty pracownicy;
    private IntegerProperty przydzialy;

    /**
     * @return the dziedzina
     */
    public StringProperty getDziedzina() {
        return dziedzina;
    }

    /**
     * @param dziedzina the dziedzina to set
     */
    public void setDziedzina(StringProperty dziedzina) {
        this.dziedzina = dziedzina;
    }

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
     * @return the pracownicy
     */
    public IntegerProperty getPracownicy() {
        return pracownicy;
    }

    /**
     * @param pracownicy the pracownicy to set
     */
    public void setPracownicy(IntegerProperty pracownicy) {
        this.pracownicy = pracownicy;
    }

    /**
     * @return the sektor
     */
    public StringProperty getSektor() {
        return sektor;
    }

    /**
     * @param sektor the sektor to set
     */
    public void setSektor(StringProperty sektor) {
        this.sektor = sektor;
    }

    /**
     * @return the przydzialy
     */
    public IntegerProperty getPrzydzialy() {
        return przydzialy;
    }

    /**
     * @param przydzialy the przydzialy to set
     */
    public void setPrzydzialy(IntegerProperty przydzialy) {
        this.przydzialy = przydzialy;
    }
}
