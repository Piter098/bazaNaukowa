/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt.rekordy;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Piter
 */
public class Przydzial {
    private StringProperty nazwa;
    private StringProperty kategoria;
    private StringProperty sekcja;
    private IntegerProperty id_sekcji;
    private IntegerProperty numer;
    private StringProperty symbol_sektora;

    /**
     * @return the nazwa
     */
    public StringProperty getNazwa() {
	if(nazwa != null)
	    return nazwa;
	else
	    return new SimpleStringProperty("");
    }
    
    public StringProperty getNumerSekcji(){
        String out = "";
        if(symbol_sektora != null)
	    out = symbol_sektora.getValueSafe();
	if(id_sekcji != null)
	    out += id_sekcji.asString().get();
        return new SimpleStringProperty(out);
    }

    /**
     * @param nazwa the nazwa to set
     */
    public void setNazwa(StringProperty nazwa) {
	this.nazwa = nazwa;
    }

    /**
     * @return the sekcja
     */
    public StringProperty getKategoria() {
	if(kategoria != null)
	    return kategoria;
	else
	    return new SimpleStringProperty("");
    }

    /**
     * @param kategoria the kategoria to set
     */
    public void setKategoria(StringProperty kategoria) {
	this.kategoria = kategoria;
    }

    /**
     * @return the sekcja
     */
    public StringProperty getSekcja() {
	if(sekcja != null)
	    return sekcja;
	else
	    return new SimpleStringProperty("");
    }

    /**
     * @param sekcja the sekcja to set
     */
    public void setSekcja(StringProperty sekcja) {
	this.sekcja = sekcja;
    }

    /**
     * @return the id_sekcji
     */
    public IntegerProperty getId_sekcji() {
	if(id_sekcji != null)
	    return id_sekcji;
	else
	    return new SimpleIntegerProperty(-1);
    }

    /**
     * @param id_sekcji the id_sekcji to set
     */
    public void setId_sekcji(IntegerProperty id_sekcji) {
	this.id_sekcji = id_sekcji;
    }

    /**
     * @return the symbol_sektora
     */
    public StringProperty getSymbol_sektora() {
	if(symbol_sektora != null)
	    return symbol_sektora;
	else
	    return new SimpleStringProperty("");
    }

    /**
     * @param symbol_sektora the symbol_sektora to set
     */
    public void setSymbol_sektora(StringProperty symbol_sektora) {
	this.symbol_sektora = symbol_sektora;
    }

    /**
     * @return the numer
     */
    public IntegerProperty getNumer() {
        if(numer != null)
	    return numer;
	else
	    return new SimpleIntegerProperty(-1);
    }

    /**
     * @param numer the numer to set
     */
    public void setNumer(IntegerProperty numer) {
        this.numer = numer;
    }
}
