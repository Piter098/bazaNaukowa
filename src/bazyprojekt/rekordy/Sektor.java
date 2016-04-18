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
public class Sektor {
    private StringProperty symbol;
    private IntegerProperty liczbaPok;
    private IntegerProperty liczbaLab;
    private IntegerProperty liczbaPost;

    /**
     * @return the symbol
     */
    public StringProperty getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(StringProperty symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the liczbaPok
     */
    public IntegerProperty getLiczbaPok() {
        return liczbaPok;
    }

    /**
     * @param liczbaPok the liczbaPok to set
     */
    public void setLiczbaPok(IntegerProperty liczbaPok) {
        this.liczbaPok = liczbaPok;
    }

    /**
     * @return the liczbaLab
     */
    public IntegerProperty getLiczbaLab() {
        return liczbaLab;
    }

    /**
     * @param liczbaLab the liczbaLab to set
     */
    public void setLiczbaLab(IntegerProperty liczbaLab) {
        this.liczbaLab = liczbaLab;
    }

    /**
     * @return the liczbaPost
     */
    public IntegerProperty getLiczbaPost() {
        return liczbaPost;
    }

    /**
     * @param liczbaPost the liczbaPost to set
     */
    public void setLiczbaPost(IntegerProperty liczbaPost) {
        this.liczbaPost = liczbaPost;
    }
}
