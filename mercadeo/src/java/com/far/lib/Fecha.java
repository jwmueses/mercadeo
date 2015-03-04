/**
* @version 1.0
* @package FACTURAPYMES.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2010 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
* FACTURAPYMES! es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia Pública
* General GNU, de acuerdo con la publicada por la Free Software
* Foundation, versión 2 de la licencia o cualquier versión posterior.
*/

package com.far.lib;
import java.util.Calendar;

public class Fecha
{
/**
 * Funci�n que construye una cadena con la fecha actual.
 * @param tipo. El tipo de format de la fecha: por defecto ISO o SQL.
 * @return una cadena con la fecha actual.
 */
    public static String getFecha(String tipo)
    {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH) + 1;
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        String cad = anio + "-" + mes + "-" + dia;
        if(tipo.toUpperCase().compareTo("SQL")==0){
            cad = dia + "/" + mes + "/" + anio;
        }
        return cad;
    }

/**
 * Construye un entero con el d�a actual.
 * @return el d�a actual.
 */
    public static int getDiaSemana()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static String getTxtDiaSemana()
    {
        String cad = "";
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_WEEK);
        switch(dia){
            case 1: cad = "domingo"; break;
            case 2: cad = "lunes"; break;
            case 3: cad = "martes"; break;
            case 4: cad = "miércoles"; break;
            case 5: cad = "jueves"; break;
            case 6: cad = "viernes"; break;
            case 7: cad = "sábado"; break;
        }
        return cad;
    }

    /*public static String get5DiasLaborables(String fecha_ini)
    {
        String fecha_fin = Fecha.getFecha("SQL");
        try{
            BaseDatos objDB = new BaseDatos();
            ResultSet rs = objDB.consulta("select get5DiasLaborables('"+fecha_ini+"')");
            if(rs.next()){
                fecha_fin = rs.getString(1)!=null ? rs.getString(1) : fecha_fin;
                rs.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return fecha_fin;
    }*/

    public static String getFecha(String tipo, int parteFecha, int valor)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(parteFecha, parteFecha+valor);
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH) + 1;
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        String cad = anio + "-" + mes + "-" + dia;
        if(tipo.toUpperCase().compareTo("SQL")==0){
            cad = dia + "/" + mes + "/" + anio;
        }
        return cad;
    }

    public static long getTimeStamp(String fecha)
    {
        Calendar cal = Calendar.getInstance();
        int anio = Fecha.datePart("anio", fecha);
        int mes = Fecha.datePart("mes", fecha);
        int dia = Fecha.datePart("dia", fecha);
        cal.set(anio, mes, dia);
        return cal.getTimeInMillis();
    }

    /**
 * Funci�n que construye una cadena con la hora actual.
 * @param tipo. El tipo de format de la hora HH:mm:ss.
 * @return una cadena con la hora actual.
 */
    public static String getHora()
    {
        Calendar cal = Calendar.getInstance();
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);
        int segundo = cal.get(Calendar.SECOND);
        String cad = hora + ":" + minuto + ":" + segundo;
        return cad;
    }

/**
 * Construye un entero con el a�o actual.
 * @return el a�o actual.
 */
    public static int getAnio()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

/**
 * Construye un entero con el mes actual.
 * @return el mes actual.
 */
    public static int getMes()
    {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.MONTH) + 1);
    }

/**
 * Construye un entero con el d�a actual.
 * @return el d�a actual.
 */
    public static int getDia()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDiaSemana(String fecha)
    {
        Calendar cal = Calendar.getInstance();
        int anio = Fecha.datePart("anio", fecha);
        int mes = Fecha.datePart("mes", fecha);
        int dia = Fecha.datePart("dia", fecha);
        cal.set(anio, mes-1, dia);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

 /**
  * Construye un entero con el �ltimo d�a del mes.
  * @param anio. El a�o para calcular el �ltimo d�a del mes.
  * @param mes. El mes para calcular �ltimo d�a del mes.
  * @return El �ltimo d�a del mes.
  */
    public static int getUltimoDiaMes(int anio, int mes)
    {
        int dia=0;
        switch(mes){
             case 1:  // Enero
             case 3:  // Marzo
             case 5:  // Mayo
             case 7:  // Julio
             case 8:  // Agosto
             case 10:  // Octubre
             case 12: // Diciembre
                dia=31;
             break;
             case 4:  // Abril
             case 6:  // Junio
             case 9:  // Septiembre
             case 11: // Noviembre
                dia=30;
             break;
             case 2:  // Febrero
                if (((anio%100 == 0) && (anio%400 == 0)) || ((anio%100 != 0) && (anio% 4 == 0))){
                    dia=29;  // Año Bisiesto
                }else{
                    dia=28;
                }
             break;
             default: dia=0;
        }
        return dia;
    }

    public static String sumar(String fecha, int campo, int num)
    {
        Calendar cal = Calendar.getInstance();
        int anio = Fecha.datePart("anio", fecha);
        int mes = Fecha.datePart("mes", fecha)-1;
        int dia = Fecha.datePart("dia", fecha);
        cal.set(anio, mes, dia);
        cal.add(campo, num);

        anio = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH) + 1;
        dia = cal.get(Calendar.DAY_OF_MONTH);
        String cad = anio + "-" + mes + "-" + dia;
        if(fecha.toUpperCase().indexOf("/")>=0){
            cad = dia + "/" + mes + "/" + anio;
        }

        return cad;
    }

/**
  * Transforma una fecha de formato ISO a una fecha en format SQL.
  * @param anio. fecha en formato ISO.
  * @return fecha en formato SQL.
  */
    public static String ISOaSQL(String fecha)
    {
        String fechaSQL = fecha;
        if(fecha.indexOf("-")>0){
            String vecFecha[] = fecha.split("-");
            fechaSQL = vecFecha[2] + "/" + vecFecha[1] + "/" + vecFecha[0];
        }
        return fechaSQL;
    }

    /**
  * Trunca una fecha y hora, y devuelve solo la parte de la fecha.
  * @param anio. fecha y hora.
  * @return fecha.
  */
    public static String truncFecha(String fecha)
    {
        String fechaSQL[] = fecha.split(" ");
        return fechaSQL[0];
    }

/**
 * Verifica si el día actual es fin de mes.
 * @return verdadero si es fin de mes caso contrario falso.
 */
    public static boolean esFinMes()
    {
        int anio = Fecha.getAnio();
        int mes = Fecha.getMes();
        String fin_mes = String.valueOf(anio) + "-" + String.valueOf(mes) + "-" + String.valueOf(Fecha.getUltimoDiaMes(anio, mes));
        String hoy = Fecha.getFecha("ISO");
        if(fin_mes.compareTo(hoy)==0){
            return true;
        }
        return false;
    }

    /**
     * Retorna parte de una fecha.
     * @param parte. Parte de la fecha que se quiere retornar ('anio', 'mes', 'dia').
     * @param fecha. La fecha de la cual se va a tomar la parte.
     * @return un número entero con la parte de la fecha ingresada como parámetro.
     */
    public static int datePart(String parte, String fecha)
    {
        String vec_fecha[] = null;
        int anio = 0;
        int dia = 0;
        if(fecha.indexOf("-")>0){
            vec_fecha = fecha.split("-");
            anio = Integer.parseInt(vec_fecha[0]);
            dia = Integer.parseInt(vec_fecha[2]);
        }else{
            vec_fecha = fecha.split("/");
            anio = Integer.parseInt(vec_fecha[2]);
            dia = Integer.parseInt(vec_fecha[0]);
        }
        if(parte.compareTo("anio")==0){
            return anio;
        }else if(parte.compareTo("mes")==0){
                return Integer.parseInt(vec_fecha[1]);
        }else if(parte.compareTo("dia")==0){
                return dia;
        }else{
            return -1;
        }
    }

    /**
     * Ingresado el año e el mes construye el texto del periodo utilizada en la facturación periodica tarifada.
     * @param año. Año que será parte dell período.
     * @param mes. Mes que será parte dell período.
     * @return retorna el período en formato texto.
     */
    public static String getTxtPeriodo(int anio, int mes)
    {
        String anio_mes = "";
        switch(mes){
            case 1: anio_mes = anio + " / enero"; break;
            case 2: anio_mes = anio + " / febrero"; break;
            case 3: anio_mes = anio + " / marzo"; break;
            case 4: anio_mes = anio + " / abril"; break;
            case 5: anio_mes = anio + " / mayo"; break;
            case 6: anio_mes = anio + " / junio"; break;
            case 7: anio_mes = anio + " / julio"; break;
            case 8: anio_mes = anio + " / agosto"; break;
            case 9: anio_mes = anio + " / septiembre"; break;
            case 10: anio_mes = anio + " / octubre"; break;
            case 11: anio_mes = anio + " / noviembre"; break;
            case 12: anio_mes = anio + " / diciembre"; break;
        }
        return anio_mes;
    }

/**
 * Ingresado el año e el mes construye el texto del periodo utilizada en la facturación periodica tarifada.
 * @param Fecha. Fecha que será parte del período.
 * @return retorna el período en formato texto.
*/
    public static String getTxtPeriodo(String fecha)
    {
        String anio_mes = "";
        int anio = Fecha.datePart("anio", fecha);
        int mes = Fecha.datePart("mes", fecha);
        switch(mes){
            case 1: anio_mes = anio + " / enero"; break;
            case 2: anio_mes = anio + " / febrero"; break;
            case 3: anio_mes = anio + " / marzo"; break;
            case 4: anio_mes = anio + " / abril"; break;
            case 5: anio_mes = anio + " / mayo"; break;
            case 6: anio_mes = anio + " / junio"; break;
            case 7: anio_mes = anio + " / julio"; break;
            case 8: anio_mes = anio + " / agosto"; break;
            case 9: anio_mes = anio + " / septiembre"; break;
            case 10: anio_mes = anio + " / octubre"; break;
            case 11: anio_mes = anio + " / noviembre"; break;
            case 12: anio_mes = anio + " / diciembre"; break;
        }
        return anio_mes;
    }

}