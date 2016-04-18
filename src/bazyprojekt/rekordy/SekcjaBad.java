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
public class SekcjaBad {
    private IntegerProperty numer;
    private IntegerProperty zaloga;
    private IntegerProperty miejsca;
    private IntegerProperty prowiant;
    private IntegerProperty zasoby; 

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
     * @return the prowiant
     */
    public IntegerProperty getProwiant() {
        return prowiant;
    }

    /**
     * @param prowiant the prowiant to set
     */
    public void setProwiant(IntegerProperty prowiant) {
        this.prowiant = prowiant;
    }

    /**
     * @return the zasoby
     */
    public IntegerProperty getZasoby() {
        return zasoby;
    }

    /**
     * @param zasoby the zasoby to set
     */
    public void setZasoby(IntegerProperty zasoby) {
        this.zasoby = zasoby;
    }
}
