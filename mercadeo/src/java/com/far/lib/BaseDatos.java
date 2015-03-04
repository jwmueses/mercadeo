/**	
 * @version 1.0
 * @package jm.web.
 * @author Jorge Washington Mueses Cevallos.	
 * @copyright Copyright (C) 2008 por Jorge W. Mueses Cevallos. 
 * Todos los derechos reservados.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
*/

package com.far.lib;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BaseDatos
{
    private Connection con = null;
    private String error = "";
    private int numPaginas = 0;
	
/**
 * Constructor de la clase DataBase que crea una conexi�n a una base de datos
 * SqlServer2k5.
 * @param m. IP de la maquina del servisor de base de datos SqlServer2k5.
 * @param p. Puerto de escucha del servidor de base de datos.
 * @param db. Nombre de la base de datos a conectarse.
 * @param u. Nombre del usuario de la base de datos.
 * @param c. Contrase�a del usuario de la base de datos.
 */    
    public BaseDatos (String m, int p, String db, String u, String c)
    {    
        try{ 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            this.con = DriverManager.getConnection("jdbc:sqlserver://" + m + ":" + p + ";databaseName=" + db + ";user=" + u +";password=" + c + ";");         
        }catch(ClassNotFoundException e){ 
            this.error = e.getMessage();
        }catch(Exception ex){
            this.error = ex.getMessage();
        }      
    } 
    
    public Connection getConexion()
    {
        return this.con;
    }

/**
 * Funci�n que ejecuta una instrucci�n SELECT en el servidor de Base de datos.
 * @param cad. Cadena SQL - SELECT.
 * @return Retorna una objeto ResulSet(juego de registros).
 */
    public ResultSet consulta(String cad) 
    { 
        ResultSet r = null;
        try{
            Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            r = st.executeQuery(this.decodificarURI(cad));
            this.error = "";
        }catch(Exception e){  
            this.error = e.getMessage();
        }     
        return r;
    } 

    /**
 * Funci�n que ejecuta una instrucci�n INSERT en el servidor para tablas con claves autogeneradas
 * de Base de datos.
 * @param cad. Cadena SQL - INSERT.
 * @return Retorna la clave primaria generada e por el comando insert.
 */
    public String insertar(String sql)
    {
        String pk = "-1";
        if(sql.toLowerCase().indexOf("insert") == 0 ){
            try{
                Statement st = this.con.createStatement();
                int r = st.executeUpdate(this.decodificarURI(sql), Statement.RETURN_GENERATED_KEYS);
                if(r>0){
                    ResultSet rs = st.getGeneratedKeys();
                    if(rs.next()){
                        pk = rs.getString(1) != null ? rs.getString(1) : "-1";
                        rs.close();
                    }
                }
                st.close();
                this.error = "";
            }catch(Exception e){
                pk = "-1";
                this.error = e.getMessage();
            }
        }
        return pk;
    }
    
/**
 * Ejecuta un grupo de transacciones contenidas en un ArrayList,
 * manteniendo las propiedades ACID.
 * @param tr. Un ArrayList de instrucciones SQL.
 * @return true o falase seg�n si se han realizado todas las transaccciones con exito o no.
 */    
    public boolean transacciones(List tr)
    {
        try{
            this.con.setAutoCommit(false);
            Statement st = this.con.createStatement();
            Iterator it = tr.iterator();
            while(it.hasNext()){
                st.executeUpdate(this.decodificarURI((String)it.next()));
            }
            this.con.commit();
            st.close();
            this.con.setAutoCommit(true);
            this.error = "";
            return true;
        }catch (SQLException ex) {
            this.error = ex.getMessage();
            try {
                this.con.rollback();
                this.con.setAutoCommit(true);
            }
            catch (SQLException se) {
                this.error = se.getMessage();
            }
        }
        return false;
    }  
    
/**
 * Funci�n que ejecuta una instrucci�n INSERT, UPDATE o DELETE en el servidor 
 * de Base de datos.
 * @param cad. Cadena SQL - INSERT, UPDATE o DELETE.
 * @return Retorna verdadero o false seg�n si se ejecut� o no la instrucci�n.
 */       
    public boolean ejecutar(String cad) 
    { 
        int r = -1;
        try{
            Statement st = this.con.createStatement();
            r = st.executeUpdate(this.decodificarURI(cad));
            st.close();
            this.error = "";
            if(r>0){
                return true;
            }            
        }catch(Exception e){  
            this.error = e.getMessage();
        }     
        return false;
    }    
    
/**
 * Funci�n que codifica un objeto ResultSet(juego de registros) en una cadena
 * JSON.
 * @param r. Un objeto Resultset(juego de registros) que contiene el resultado.
 * de una seltencia SELECT. 
 * @return
 */    
    public String getJSON(ResultSet r) 
    { 
        String json = "{tbl:[";
        try{
            r.beforeFirst();
            ResultSetMetaData mdata = r.getMetaData();      
            int col = mdata.getColumnCount();      
            int i=0;
            while(r.next()){
                json += "{";
                i=0;
                for(int j=1; j<=col; j++){                   
                json += i + ":\"" + ((r.getString(j)!=null)?r.getString(j).replace('"', '|'):"") + "\",";
                i++;
                }
                json = json.substring(0, json.length()-1);
                json += "},";
            } 
            json = json.substring(0, json.length()-1);
            json += "]}";
            r.beforeFirst();
            this.error = "";
        }catch(Exception e){
            this.error = e.getMessage();
        }     
        return json;
    }
    
/**
 * Funci�n que codifica una consulta SELECT paginada en una cadena en formato
 * JSON.
 * @param t. Nombre de la tabla para la consulta.
 * @param c. Nombre de los campos de la tabla. 
 * @param w. Clausula WHERE para la consulta.
 * @param p. El n�mero de la p�gina a retornar.
 * @param fxp. El n�mero de registros por p�gina.
 * @return Una cadena codificada en formato JSON y paginada.
 */
    public String paginarJSON(String t, String c, String w, int p, int fxp)
    {
        String json = "{tbl:[";
        int tope = (p+1)*fxp;
        String dist = "";
        if(c.toLowerCase().indexOf("distinct")>=0){
            c = c.replace("distinct", "");
            dist = "distinct";
        }
        String SQL = "SELECT "+dist+" TOP(" + tope + ") " + c + " FROM " + t +" "+ w;
        int numPags = 0;
        try{
            Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rPag = st.executeQuery(this.decodificarURI("SELECT "+dist+" " + c + " FROM " + t +" "+ w));
            numPags = (this.getFilas(rPag)-1)/fxp;
            rPag.close();
            ResultSet r = st.executeQuery(this.decodificarURI(SQL));
            ResultSetMetaData mdata = r.getMetaData();
            int ini = tope-fxp;
            int i = 0;
            while(i<ini) {
                r.next();
                i++;
            }        
            while(r.next()){
                json += "{";
                i = 0;
                for(int j=1; j<=mdata.getColumnCount(); j++) {
                    json += i + ":\"" + ((r.getString(j)!=null)?r.getString(j).replace('"', '|'):"") + "\",";
                    i++;
                }
                json = json.substring(0, json.length()-1);      
                json += "},";
            }
            json = json.substring(0, json.length()-1);      
            json += "]}";
            r.close();
            st.close();
            this.error = "";
        }catch(Exception e){
            this.error = e.getMessage();
        }
        return numPags+"|"+json;
    }
  
/**
 * Funci�n que codifica una consulta SELECT paginada en una cadena en formato
 * JSON.
 * @param t. Nombre de la tabla para la consulta.
 * @param c. Nombre de los campos de la tabla. 
 * @param w. Clausula WHERE para la consulta.
 * @param p. El n�mero de la p�gina a retornar.
 * @param fxp. El n�mero de registros por p�gina.
 * @return Una cadena codificada en formato JSON y paginada.
 */
    public String[][] paginar(String t, String c, String w, int p, int fxp)
    {
        String matriz[][] = null;
        int tope = (p+1)*fxp;
        String dist = "";
        if(c.toLowerCase().contains("distinct")){
            c = c.replace("distinct", "");
            dist = "distinct";
        }
        String SQL = "SELECT "+dist+" TOP(" + tope + ") " + c + " FROM " + t +" "+ w;
        try{
            Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rPag = st.executeQuery(this.decodificarURI("SELECT "+dist+" " + c + " FROM " + t +" "+ w));
            this.numPaginas = (this.getFilas(rPag)-1)/fxp;
            rPag.close();
            ResultSet r = st.executeQuery(this.decodificarURI(SQL));
            r.last();
            int num_fil = r.getRow();
            r.beforeFirst();
            ResultSetMetaData mdata = r.getMetaData();
            int num_col = mdata.getColumnCount();
            int ini = tope-fxp;
            int i = 0;
            while(i<ini) {
                r.next();
                i++;
            }        
            matriz = new String[num_fil-i][num_col];
            i = 0;
            while(r.next()){
                for(int j=1; j<=num_col; j++) {
                    matriz[i][j-1] = (r.getString(j)!=null)?r.getString(j).replace('"', '|'):"";
                }
                i++;
            }
            r.close();
            st.close();
            this.error = "";
        }catch(Exception e){
            this.error = e.getMessage();
        }
        return matriz;
    }
    
    public long getNumPaginas()
    {
        return this.numPaginas;
    }
    
/**
 * Funci�n que calcula el n�mero de filas de un juego de registros.
 * @param rs. Un objeto Resultset(juego de registros) que contiene el resultado.
 * @return el n�mero de filas de un juego de registros.
 */
    public int getFilas(ResultSet rs)
    {                
        int cont=0;
        try{
            rs.last();
            cont = rs.getRow();
            rs.beforeFirst();
            this.error = "";
        }catch(Exception e){    
            this.error = e.getMessage();
        }
        return cont;
    }

/**
 * Funci�n que calcula el n�mero de columnas de un juego de registros.
 * @param rs. Un objeto Resultset(juego de registros) que contiene el resultado.
 * @return el n�mero de columnas de un juego de registros.
 */
    public int getColumnas(ResultSet rs)
    {                
        int cont=0;
        try{
            ResultSetMetaData mdata = rs.getMetaData();
            cont = mdata.getColumnCount();
            this.error = "";
        }catch(Exception e){    
            this.error = e.getMessage();
        }
        return cont;
    }
 
/**
 * Cierra una conexi�n abierta a una base de datos SqlServer2k5.
 */
    public void cerrar()
    {
        try{
            this.con.close();
            this.error = "";
        }catch (Exception e) {
            this.error = e.getMessage();
        }
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return this.error;
    }
 
/**
 * Funci�n que decodifica una cadena previamente codificada en formato propietario.
 * @param cad cadena a decodificar.
 * @return una cadena decodificada.
 */
    public String decodificarURI(String cad)
    {           
        cad = cad.replace("_^0;", "&");
        cad = cad.replace("_^1;", "+");
        cad = cad.replace("_^2;", "%");
        cad = cad.replace("_^3;", "''");
        cad = cad.replace("\\", "/");
        cad = cad.replace("|", "/");
        cad = cad.replace("^", "/");
        cad = cad.replace("\"", "''\''");
        return cad;
    }
}