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
public class Pokoj {
    private IntegerProperty numer;
    private StringProperty sektor;
    private IntegerProperty wielkosc;
    private IntegerProperty miejsca;
    private IntegerProperty mieszkancy;

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
     * @return the wielkosc
     */
    public IntegerProperty getWielkosc() {
        return wielkosc;
    }

    /**
     * @param wielkosc the wielkosc to set
     */
    public void setWielkosc(IntegerProperty wielkosc) {
        this.wielkosc = wielkosc;
    }

    /**
     * @return the miejsca
     */
    public IntegerProperty getMiejsca() {
        return miejsca;
    }

    /**
     * @param miejsca the miejsca to set
     */
    public void setMiejsca(IntegerProperty miejsca) {
        this.miejsca = miejsca;
    }

    /**
     * @return the mieszkancy
     */
    public IntegerProperty getMieszkancy() {
        return mieszkancy;
    }

    /**
     * @param mieszkancy the mieszkancy to set
     */
    public void setMieszkancy(IntegerProperty mieszkancy) {
        this.mieszkancy = mieszkancy;
    }
    
}
