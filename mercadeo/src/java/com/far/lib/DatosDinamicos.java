/**
** @version 1.0
** @package FACTURAPYMES.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2011 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
** FACTURAPYMES es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia
* Attribution-NonCommercial-NoDerivs 3.0 Unported,
* de acuerdo con la publicada por la CREATIVE COMMONS CORPORATION.
*/

package com.far.lib;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DatosDinamicos {
    
    private static String _arbolCom = "";
    private static int _col = 0;

    
/**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param opt. Valores y etiquetas para las opciones del objeto select.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(String [][] opt, String nom, String mar, String onC)
    {
        String sel="";      
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\">");
        for(int i=0; i<opt.length; i++){
            sel = (opt[i][0].compareTo(mar)==0) ? "selected='selected'" : "";
            cad.append("<option "+sel+" value=\"" + opt[i][0] + "\">" + opt[i][1] + "</option>");
        }
        cad.append("</select>"); 
        return cad.toString();
    }

    /**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param opt. Valores y etiquetas para las opciones del objeto select.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(String [][] opt, String nom, String mar, String onC, String opc)
    {
        String sel="";
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\">");
        if(opc.compareTo("")!=0){
            cad.append("<option value='0'>" + opc + "</option>");
        }
        for(int i=0; i<opt.length; i++){
            sel = (opt[i][0].compareTo(mar)==0) ? "selected='selected'" : "";
            cad.append("<option "+sel+" value=\"" + opt[i][0] + "\">" + opt[i][1] + "</option>");
        }
        cad.append("</select>");
        return cad.toString();
    }
    
    
       /**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param opt. Valores y etiquetas para las opciones del objeto select.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(String [][] opt, String nom, String mar, String onC, String opc, int ancho)
    {
        String sel="";
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\" style=\"width:"+ancho+"px;\">");
        if(opc.compareTo("")!=0){
            cad.append("<option value='0'>" + opc + "</option>");
        }
        for(int i=0; i<opt.length; i++){
            sel = (opt[i][0].compareTo(mar)==0) ? "selected='selected'" : "";
            cad.append("<option "+sel+" value=\"" + opt[i][0] + "\">" + opt[i][1] + "</option>");
        }
        cad.append("</select>");
        return cad.toString();
    }
    
    
/**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param dat. Objeto ResulSet(juego de registros).
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(ResultSet dat, String nom, String mar, String onC)
    {
        String sel="";      
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\">");
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected='selected'" : "") : "";
                for(int i=2; i<=lim; i++){
                    et+=dat.getString(i)+" &nbsp;";
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\">" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }
    
    /**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param dat. Objeto ResulSet(juego de registros).
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(ResultSet dat, String nom, String mar, String onC, int anch)
    {
        String sel="";      
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\" style=\"width:"+anch+"px;\">");
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected='selected'" : "") : "";
                for(int i=2; i<=lim; i++){
                    et+=dat.getString(i)+" &nbsp;";
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\">" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }

        /**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param dat. Objeto ResulSet(juego de registros).
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(ResultSet dat, String nom, String mar, String onC, int anch, String activo)
    {
        String sel=""; 
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\" "+activo+" style=\"width:"+anch+"px;\">");
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected" : "") : "";
                for(int i=2; i<=lim; i++){
                    et+=dat.getString(i)+" &nbsp;";
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\">" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }
    
    /**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param dat. Objeto ResulSet(juego de registros).
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(ResultSet dat, String nom, String mar, String onC, String opt)
    {
        String sel="";
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\">");
        if(opt.compareTo("")!=0){
            cad.append("<option value='0'>" + opt + "</option>");
        }
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected='selected'" : "") : "";
                for(int i=2; i<=lim; i++){
                    et+=dat.getString(i)+" &nbsp;";
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\">" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }
    
/**
 * Funci�n que construye una cadena de etiqueta SELECT.
 * @param dat. Objeto ResulSet(juego de registros).
 * @param nom. Nombre del objeto HTML.
 * @param mar. Valor de la opci�n seleccionada.
 * @param onC. Funci�n JavaScript a ejecutar en el evento onChange.
 * @return una cadena con la etiqueta SELECT.
 */
    public static String combo(ResultSet dat, String nom, String mar, String onC, String opt, int anch)
    {
        String sel="";
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\" style=\"width:"+anch+"px;\">");
        if(opt.compareTo("")!=0){
            cad.append("<option value='0'>" + opt + "</option>");
        }
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected='selected'" : "") : "";
                for(int i=2; i<=lim; i++){
                    et+=dat.getString(i)+" &nbsp;";
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\">" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }
    
    public static String combo(ResultSet dat, String nom, String mar, String onFoc, String evt, String onF, String opt, int anch)
    {
        String sel="";   
        StringBuilder cad = new StringBuilder();
        cad.append("<select id=\""+nom+"\" name=\""+nom+"\" style=\"width:"+anch+"px;\" onkeyup=\""+evt+"\" onfocus=\""+onFoc+"\" onchange=\""+onF+"\" onblur=\"_R('mSg')\">");
                /* "onblur=\"_('mSg').style.display='none'\">"; */
        if(opt.compareTo("")!=0){
            cad.append("<option value='0' onmousemove=\""+evt+"\" >" + opt + "</option>");
        }
        try{
            ResultSetMetaData mdata = dat.getMetaData();
            int lim = mdata.getColumnCount();
            String et="";
            dat.beforeFirst();
            while(dat.next()){
                sel = (dat.getString(1)!=null) ? ((dat.getString(1).compareTo(mar)==0) ? "selected='selected'" : "") : "";
                for(int i=2; i<=lim; i++){
                    if(i==lim){
                        et+=dat.getString(i);
                    }else{
                        et+=dat.getString(i)+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
                    }
                }
                cad.append("<option "+sel+" value=\"" + dat.getString(1) + "\" onmousemove=\""+evt+"\" >" + et + "</option>");
                et="";
            }
            dat.beforeFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad.append("</select>"); 
        return cad.toString();
    }
    
/**
 * Funci�n que transforma un ResultSet en una matriz.
 * @param rs. ResultSet.
 * @return una matriz de cadenas.
 */       
    public static String[][] ResultSetToMatriz(ResultSet rs)
    {        
        try{
            /*filas*/
            rs.last();
            int fil = rs.getRow();
            rs.beforeFirst();
            /*columnas*/
            ResultSetMetaData mdata = rs.getMetaData();
            int col = mdata.getColumnCount();
            /*parsear*/
            String ma[][] = new String[fil][col];
            int i=0;
            int k=0;
            while(rs.next()){
                for(int j=1; j<=col; j++) {
                    k = j-1;
                    ma[i][k] = (rs.getString(j)!=null) ? rs.getString(j) : "";
                }
                i++;
            }
            return ma;
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return new String[0][0];
    }

    
    /**
* B�squeda binaria de una clave en una tabla de referencia. Se requiere
* que los valores est�n ordenados.
* @param $clave string. clave principal a buscar en la tabla de referencia.
* @param $vector array. De claves principales de la tabla de referencia.
* @return retorna el �ndice si la clave se encuantra en el vector caso contrario -1. 
*/
    public static int enMatriz(String[][] matriz, String clave, int pos)
    {
        int Iarriba = matriz.length - 1; 
        int Iabajo = 0;
        int Icentro = 0; 
        while(Iabajo <= Iarriba){ 
            Icentro = (Iarriba + Iabajo) / 2;
            if(matriz[Icentro][pos].compareTo(clave)==0){
                return Icentro; 
            }
            else if(matriz[Icentro][pos].compareTo(clave)>0){
                    Iarriba = Icentro - 1; 
                 }else {
                    Iabajo = Icentro + 1;      
                 }
        }
        return -1;        
    }   
    
     /**
* B�squeda binaria de una clave en una tabla de referencia. Se requiere
* que los valores est�n ordenados.
* @param $clave string. clave principal a buscar en la tabla de referencia.
* @param $vector array. De claves principales de la tabla de referencia.
* @return retorna el �ndice si la clave se encuantra en el vector caso contrario -1. 
*/
    public static int enMatriz(String[][] matriz, float clave, int pos)
    {
        int Iarriba = matriz.length - 1; 
        int Iabajo = 0;
        int Icentro = 0;
        float posicion = 0;
        while(Iabajo <= Iarriba){             
            Icentro = (Iarriba + Iabajo) / 2;
            posicion = Float.valueOf(matriz[Icentro][pos]);
            if(posicion == clave){
                return Icentro; 
            }
            else if(clave < posicion){
                    Iarriba = Icentro - 1; 
                 }else {
                    Iabajo = Icentro + 1;      
                 }
        }
        return -1;        
    }
    
    public static float valMaximo(String[][] matriz, int pos)
    {
        float max=0;
        float aux=0;
        for(int i=0; i<matriz.length; i++){
            aux = Float.valueOf(matriz[i][pos]);
            if(aux > max){
                max = aux;
            }
        }
        return max;
    }
    
    
/**
* B�squeda binaria de una clave en una tabla de referencia. Se requiere
* que los valores est�n ordenados.
* @param $clave string. clave principal a buscar en la tabla de referencia.
* @param $vector array. De claves principales de la tabla de referencia.
* @return retorna el �ndice si la clave se encuantra en el vector caso contrario -1. 
*/
    public static int comprobarRef(String clave, String[][] matriz, int pos)
    {
        /* Busqueda Binaria
        int Iarriba = matriz.length - 1; 
        int Iabajo = 0;
        int Icentro = 0; 
        while(Iabajo <= Iarriba){ 
            Icentro = (Iarriba + Iabajo) / 2;
            if(matriz[Icentro][pos].compareTo(clave)==0){
                return Icentro; 
            }
            else if(matriz[Icentro][pos].compareTo(clave)<0){
                    Iarriba = Icentro - 1; 
                 }else {
                    Iabajo = Icentro + 1;      
                 }
        }*/
        for(int i=0; i<matriz.length; i++){
            if(matriz[i][pos].compareTo(clave)==0){
                return i;
            }
        }
        return -1;        
    }    
    
/**
* Funci�n que imprime los datos de una base de datos en formato
* de combobox o lista en forma de �rbol seg�n sea definido el
* par�metro filas, por defecto es 1 e indica que se mostrar� 
* un combo, si es mayor a 1, entonces se mostrar� una lista.
* @param datos. Juego de registros de datos.
* @param nombre. Nombre del tag <select> para que PHP reciba como variable.
* @param marca. C�digo del registro que el combo mostrar�.
* @param raiz. La ra�z a partir de la cual se va a imprimir los datos. 
* @param nivel. El nivel de impresi�n del arbol de cada item.
* @param onC. Funci�n javascript que se ejecuta cuando se ejecute el evento onChange.
* @return retorna una cadena con el objeto HTML SELECT. 
*/
    public static String arbolCombo(ResultSet dat, String nom, String mar, int raiz, int nivel, String onC)
    {
        String[][] datos = DatosDinamicos.ResultSetToMatriz(dat);
        DatosDinamicos._arbolCom = "<select id=\""+nom+"\" name=\""+nom+"\" style=\"width:245px;\" onchange=\""+onC+"\">";
        if(datos!=null){
            DatosDinamicos.recArbolCombo(datos, raiz, nivel, mar);
        }
        DatosDinamicos._arbolCom += "</select>";
        return DatosDinamicos._arbolCom;
    }

/**
* Funci�n que imprime los datos de una base de datos en la etiqueta 
* <OPTION> en formato de �rbol.
* @param datos. matriz de datos.
* @param raiz. La ra�z a partir de la cual se va a imprimir los datos.
* @param nivel. El nivel de impresi�n del arbol de cada item.
* @param activo. Nombre del �tem a seleccionar.
* @return retorna un entero con la posici�n de la nueva cabeza. 
*/
    private static int recArbolCombo(String datos[][], int raiz, int nivel, String activo)
    {        
        String activo_aux = "";    
        if(DatosDinamicos.comprobarRef(datos[raiz][0],datos, 1) == -1){
            for(int i=0; i<datos.length; i++){
                if(datos[i][1].compareTo(datos[raiz][1])==0 && datos[i][3].compareTo("false")==0){
                    activo_aux = (datos[i][0].compareTo(activo)==0) ? "selected" : "";
                    if(DatosDinamicos.comprobarRef(datos[i][0],datos, 1) == -1){// cambio realizado
                        DatosDinamicos._arbolCom += "<option  value='"+datos[i][0]+"' "+activo_aux+">"+DatosDinamicos.tab(nivel)+datos[i][2]+"</option>";
                    }
                    datos[i][3] = "true";
                }
            }
            return 0;	
        }
        activo_aux = (datos[raiz][0].compareTo(activo)==0) ? "selected" : "";	
        if(nivel >= 0){
            DatosDinamicos._arbolCom += "<option class='combo1' value='"+datos[raiz][0]+"' "+activo_aux+">"+DatosDinamicos.tab(nivel)+datos[raiz][2]+"</option>";
        }
        datos[raiz][3] = "true";
        List newRaices = DatosDinamicos.raices(datos[raiz][0], datos);
        nivel++;
        Iterator it = newRaices.iterator();
        String aux = "";
        while(it.hasNext()){
            aux = it.next().toString();
            DatosDinamicos.recArbolCombo(datos, Integer.parseInt(aux), nivel, activo);
        }
        return 0;
    }   

    
/**
* Funci�n que imprime los datos de una base de datos en formato
* de tabla HTML.
* @param datos. Juego de registros de datos.
* @param id. Identificador de la tabla para javascript.
* @param cab. Nombres de la cabecera de la tabla.
* @param anch. Ancho de cada campo de la tabla.
* @param alt. Alto de la tabla.
* @param raiz. C�digo del campo de la base de datos que ser� la ra�z.
* @param nivel. El nivel de impresi�n del �rbol de cada item.
* @param onC. Funci�n javascript que se ejecuta cuando se haga clic en el registro.
* @return retorna una cadena con el objeto HTML TABLE. 
*/
    public static String arbolTabla(ResultSet dat, String id, String cab[], int anch[], int alt, int raiz, int nivel, String onC)
    {
        String[][] datos = DatosDinamicos.ResultSetToMatriz(dat);
        String c="";
        DatosDinamicos._arbolCom = "<table class='tablaDet' border='0' cellpadding='0' cellspacing='1'><tr>";
        DatosDinamicos._arbolCom += "<TH class='jm_TH' width='19'>&nbsp;</TH>";
        DatosDinamicos._arbolCom += "<th class='jm_TH' colspan='2' "+c+" width='"+(anch[0])+"'>"+cab[0]+"</th>";
        DatosDinamicos._arbolCom += "<th class='jm_TH' colspan='2' "+c+" width='"+(anch[1])+"'>"+cab[1]+"</th>";
        DatosDinamicos._arbolCom += "</tr></table>";
        DatosDinamicos._arbolCom += "<div id='tblScroll' style='overflow:auto;width:"+(anch[0]+anch[1]+26)+"px;height:"+alt+"px;'>" +
                "<table class='tablaDet' border='0' cellpadding='0' cellspacing='1'>"; 
        if(datos!=null){
            DatosDinamicos.recArbolTabla(datos, id, anch, raiz, nivel, onC);
        }
        DatosDinamicos._arbolCom += "</table></div>";
        return DatosDinamicos._arbolCom;
    }

/**
* Funci�n que imprime los datos de una base de datos en la etiqueta 
* <tr><tD> en formato de �rbol.
* @param datos. matriz de datos.
* @param id. Identificador de la tabla para javascript.
* @param anch. Ancho de cada campo de la tabla.
* @param raiz. La ra�z a partir de la cual se va a imprimir los datos.
* @param nivel. El nivel de impresi�n del arbol de cada item.
* @param onC. Funci�n javascript que se ejecuta cuando se haga clic en el registro.
* @return retorna un entero con la posici�n de la nueva cabeza.
*/
    private static int recArbolTabla(String datos[][], String id, int anch[], int raiz, int nivel, String onC)
    {        
        String onCaux = "";
        if(DatosDinamicos.comprobarRef(datos[raiz][0],datos, 1) == -1){
            for(int i=0; i<datos.length; i++){
                if(datos[i][1].compareTo(datos[raiz][1])==0 && datos[i][3].compareTo("false")==0){
                    if(DatosDinamicos.comprobarRef(datos[i][0],datos, 1) == -1){// cambio realizado //
                        onCaux = onC.replace("^", datos[i][0]);
                        DatosDinamicos._arbolCom += "<TR id='"+id+"F"+i+"' class='jm_defecto' onmouseover=\"jm_filaSobre('"+id+"', '"+i+"');\" onmouseout=\"jm_filaFuera('"+id+"', '"+i+"');\">" +
                                "<TD width='10'><input type='checkbox' name=\"ch_"+id+i+"\" id=\"ch_"+id+i+"\" onclick=\"marcar('"+id+"', 'ch_"+id+i+"', '"+datos[i][0]+"', '"+datos[i][2]+"');\"></TD>";
                        DatosDinamicos._arbolCom += "<TD id='"+id+i+"1' class='jm_colDefecto' style=\"cursor:pointer;width:"+anch[0]+"px;\" onclick=\""+onCaux+";\" onmouseover=\"_$('"+id+i+"1').className='jm_columna';\" onmouseout=\"_$('"+id+i+"1').className='jm_colDefecto'\">" + DatosDinamicos.tab(nivel) + datos[i][2] + "</TD>";
                        DatosDinamicos._arbolCom += "<TD id='"+id+i+"1a' class='jm_colDefecto' style=\"cursor:pointer;width:"+anch[1]+"px;\" onclick=\""+onCaux+";\" onmouseover=\"_$('"+id+i+"1a').className='jm_columna';\" onmouseout=\"_$('"+id+i+"1a').className='jm_colDefecto'\">" + datos[i][4] + "</TD>";
                        DatosDinamicos._arbolCom += "</TR>"; //"<TD id='"+id+i+"2' class='jm_colDefecto' width='0' onmouseover=\"_$('"+id+i+"2').className='jm_columna';\" onmouseout=\"_$('"+id+i+"2').className='jm_colDefecto'\"></TD>";
                    }
                    datos[i][3] = "true";
                }
            }
            return 0;	
        }	
        if(nivel >= 0){
            onCaux = onC.replace("^", datos[raiz][0]);
            DatosDinamicos._arbolCom += "<TR id='"+id+"F"+raiz+"' class='jm_defecto' onmouseover=\"jm_filaSobre('"+id+"', '"+raiz+"');\" onmouseout=\"jm_filaFuera('"+id+"', '"+raiz+"');\">" +
                    "<TD width='10'><input type='checkbox' name=\"ch_"+id+raiz+"\" id=\"ch_"+id+raiz+"\" onclick=\"marcar('"+id+"', 'ch_"+id+raiz+"', '"+datos[raiz][0]+"', '"+datos[raiz][2]+"');\" ></TD>";
            DatosDinamicos._arbolCom += "<TD id='"+id+raiz+"1' class='jm_colDefecto' style=\"cursor:pointer;width:"+anch[0]+"px;\" onclick=\""+onCaux+";\" onmouseover=\"_$('"+id+raiz+"1').className='jm_columna';\" onmouseout=\"_$('"+id+raiz+"1').className='jm_colDefecto'\">" + DatosDinamicos.tab(nivel) + datos[raiz][2] + "</TD>";
            DatosDinamicos._arbolCom += "<TD id='"+id+raiz+"1a' class='jm_colDefecto' style=\"cursor:pointer;width:"+anch[1]+"px;\" onclick=\""+onCaux+";\" onmouseover=\"_$('"+id+raiz+"1a').className='jm_columna';\" onmouseout=\"_$('"+id+raiz+"1a').className='jm_colDefecto'\">" + datos[raiz][4] + "</TD>";
            DatosDinamicos._arbolCom += "</TR>"; //"<TD id='"+id+raiz+"2' class='jm_colDefecto' width='0' onmouseover=\"_$('"+id+raiz+"2').className='jm_columna';\" onmouseout=\"_$('"+id+raiz+"2').className='jm_colDefecto'\"></TD>";
        }
        datos[raiz][3] = "true";
        List newRaices = DatosDinamicos.raices(datos[raiz][0], datos);
        nivel++;
        Iterator it = newRaices.iterator();
        String aux = "";
        while(it.hasNext()){
            aux = it.next().toString();
            DatosDinamicos.recArbolTabla(datos, id, anch, Integer.parseInt(aux), nivel, onC);
        }
        return 0;
    }    
    
/**
* Funci�n que imprime el n�mero de espacios para formar el �rbol.
* @param nivel. El nivel que ocupa el �tem.
* @return cadena de espacios.
*/
    private static String tab(int nivel)
    {
        String res = "";
        for(int i=0; i<nivel; i++){
            res+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return res;
    }

/**
* Funci�n que almacena las nuevas ra�ces del �rbol.
* @param clave int. N�mero de la ra�z a buscar.
* @param a. Juego de registros de datos.
* @return un vector de ra�ces.
*/
    public static List raices(String clave, String[][] a) 
    { 
        List li = new ArrayList();
        for(int i=0; i<a.length; i++){ 
            if(a[i][1].compareTo(clave)==0){  
                li.add(i);
            }
        }
        return li;
    }        
}