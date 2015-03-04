/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.lib;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author Jorge
 */
public class Matriz {

    public static String[][] ResultSetAMatriz(ResultSet rs)
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
            String ma[][] = new String[fil][col+2];
            int i=0;
            int k=0;
            int j=1;
            while(rs.next()){
                for(j=1; j<=col; j++) {
                    k = j-1;
                    ma[i][k] = (rs.getString(j)!=null) ? rs.getString(j) : "";
                }
                ma[i][j-1]="0";
                ma[i][j]="f";
                i++;
            }
            return ma;
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    /**
* Búsqueda binaria de una clave en una tabla de referencia. Se requiere
* que los valores estén ordenados.
* @param $clave string. clave principal a buscar en la tabla de referencia.
* @param $vector array. De claves principales de la tabla de referencia.
* @return retorna el índice si la clave se encuantra en el vector caso contrario -1.
*/
    public static int enMatriz(String[][] matriz, String clave, int pos)
    {
        if(matriz!=null){
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
        }
        return -1;
    }

     /**
* Búsqueda binaria de una clave en una tabla de referencia. Se requiere
* que los valores estén ordenados.
* @param $clave string. clave principal a buscar en la tabla de referencia.
* @param $vector array. De claves principales de la tabla de referencia.
* @return retorna el índice si la clave se encuantra en el vector caso contrario -1.
*/
    public static int enMatriz(String[][] matriz, float clave, int pos)
    {
        if(matriz!=null){
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
        }
        return -1;
    }


}
