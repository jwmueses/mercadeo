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

package com.far.mer.pla.clas;
import java.sql.ResultSet;
import com.far.lib.BaseDatos;

public class Configuracion extends BaseDatos{
    public Configuracion(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    public String getValor(String param)
    {
        String valor = "";
        try{
            ResultSet r = this.consulta("SELECT valor FROM tbl_configuracion with (nolock) where parametro='"+param+"';");
            if(r.next()){
                valor = (r.getString("valor")!=null) ? r.getString("valor") : "";
                r.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return valor;
    }
    public boolean setValor(String param, String valor)
    {
        return this.ejecutar("UPDATE tbl_configuracion SET valor='"+valor+"' WHERE parametro='"+param+"';");
    }
    
}