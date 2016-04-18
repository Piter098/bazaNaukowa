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
public class Pracownik {

    private IntegerProperty id;
    private IntegerProperty saldo;
    private StringProperty imie;
    private StringProperty nazwisko;
    private StringProperty ranga;
    private StringProperty przydzial;
    private StringProperty numerSekcji;
    private StringProperty sekcja;
    private StringProperty mieszkanie;
    private StringProperty urodziny;

    /**
     * @return the id
     */
    public IntegerProperty getId() {
	return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(IntegerProperty id) {
	this.id = id;
    }

    /**
     * @return the imie
     */
    public StringProperty getImie() {
	return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(StringProperty imie) {
	this.imie = imie;
    }

    /**
     * @return the nazwisko
     */
    public StringProperty getNazwisko() {
	return nazwisko;
    }

    /**
     * @param nazwisko the nazwisko to set
     */
    public void setNazwisko(StringProperty nazwisko) {
	this.nazwisko = nazwisko;
    }

    /**
     * @return the ranga
     */
    public StringProperty getRanga() {
	return ranga;
    }

    /**
     * @param ranga the ranga to set
     */
    public void setRanga(StringProperty ranga) {
	this.ranga = ranga;
    }

    /**
     * @return the przydzial
     */
    public StringProperty getPrzydzial() {
	return przydzial;
    }

    /**
     * @param przydzial the przydzial to set
     */
    public void setPrzydzial(StringProperty przydzial) {
	this.przydzial = przydzial;
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
     * @return the mieszkanie
     */
    public StringProperty getMieszkanie() {
        return mieszkanie;
    }

    /**
     * @param mieszkanie the mieszkanie to set
     */
    public void setMieszkanie(StringProperty mieszkanie) {
        this.mieszkanie = mieszkanie;
    }

    /**
     * @return the saldo
     */
    public IntegerProperty getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(IntegerProperty saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the urodziny
     */
    public StringProperty getUrodziny() {
        return urodziny;
    }

    /**
     * @param urodziny the urodziny to set
     */
    public void setUrodziny(StringProperty urodziny) {
        this.urodziny = urodziny;
    }
}
