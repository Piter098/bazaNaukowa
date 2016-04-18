/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazyprojekt;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Piter
 */
public class BazaPolaczenie {

    private Connection con;
    private Properties connectionProps = new Properties();
    private ResultSet rs;
    private int zmiany = 0;
    private Statement stmt;
    private PreparedStatement stmtPrep;

    public void connect() {
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    connectionProps.put("user", "ukrytezewzgledowbezpieczenstawa");
	    connectionProps.put("password", "ukrytezewzgledowbezpieczenstawa");

	    con = DriverManager.getConnection("jdbc:oracle:thin:@admlab2-main.cs.put.poznan.pl:1521:dblab01", connectionProps);

	} catch (ClassNotFoundException | SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	    System.exit(-1);
	}

    }

    public ResultSet getResult() {
	return rs;
    }
    
    public Statement getStatement(){
	return stmt;
    }
    
    public int getOstatnieZmiany(){
	return zmiany;
    }

    public void doZapytanie(String zap) {
	try {
	    stmt = con.createStatement();
	    rs = stmt.executeQuery(zap);

	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void doZapytaniePrepared(PreparedStatement st) {
	try {
	    rs = st.executeQuery();

	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void doCommit(){
        try {
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public int doZmiany(String zap){
	zmiany = 0;
	try {
	    stmt = con.createStatement();
	    zmiany = stmt.executeUpdate(zap);

	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
	return zmiany;
    }
    
    public PreparedStatement doPrepareStatement(String str){
	try {
	    stmtPrep = con.prepareStatement(str);
	    return stmtPrep;
	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
    
    public int doZmianyPrepared(PreparedStatement st){
	zmiany = 0;
	try {
	    zmiany = st.executeUpdate();

	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
	return zmiany;
    }

    public void closeZapytanie() {
	try {
	    if(!rs.isClosed())
		rs.close();
	    if(!stmt.isClosed())
		stmt.close();
	    if(stmtPrep != null && !stmtPrep.isClosed())
		stmtPrep.close();
	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public boolean getConnected() {
	try {
	    return con.isValid(100);
	} catch (SQLException ex) {
	    Logger.getLogger(BazaPolaczenie.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }
}
