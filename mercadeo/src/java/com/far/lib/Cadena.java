/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.lib;

/**
 *
 * @author Jorge
 */
public class Cadena {
    public static boolean esNumero(String cad)
    {
        try{
            Long.parseLong(cad);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public static String redondear(String valor)
    {
        double res = (Math.round(Double.valueOf(valor) * Math.pow(10, 2)) / Math.pow(10, 2));
        return String.valueOf(res);
    }
    
    public static String truncar(double num)
    {
        if(num>0){
            num = num + 0.0009f;
        }
        String cad2 = String.valueOf(num).replace(".", ":");
        String cad[] = cad2.split(":");
        String res = "";
        if(cad.length>1){
            cad[1] += "000";
            res = cad[1].substring(0,2);
        }
        return cad[0]+"."+res;
    }
    
    public static String truncar(String num2)
    {
        double num = Double.valueOf(num2);
        return Cadena.truncar(num);
    }
    
    public static String rellenarCeros(float valor, int longitud)
    {
        String res = "";
        int nums_ocupados = String.valueOf(valor).length();
        for(int i=0; i<longitud-nums_ocupados; i++){
            res += "0";
        }
        return res + valor;
    }
      
    public static String rellenarCeros(String valor, int longitud)
    {
        String res = "";
        int nums_ocupados = valor.length();
        for(int i=0; i<longitud-nums_ocupados; i++){
            res += "0";
        }
        return res + valor;
    }
        
    public static String rellenarCeros(int valor, int longitud)
    {
        String res = "";
        int nums_ocupados = String.valueOf(valor).length();
        for(int i=0; i<longitud-nums_ocupados; i++){
            res += "0";
        }
        return res + valor;
    }  
    
}
