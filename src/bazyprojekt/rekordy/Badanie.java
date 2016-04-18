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
public class Badanie {
    
    private IntegerProperty numer;
    private StringProperty tytul;
    private StringProperty tresc;

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
     * @return the tytul
     */
    public StringProperty getTytul() {
        return tytul;
    }

    /**
     * @param tytul the tytul to set
     */
    public void setTytul(StringProperty tytul) {
        this.tytul = tytul;
    }

    /**
     * @return the tresc
     */
    public StringProperty getTresc() {
        return tresc;
    }

    /**
     * @param tresc the tresc to set
     */
    public void setTresc(StringProperty tresc) {
        this.tresc = tresc;
    }
    
}
