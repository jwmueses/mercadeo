/**	
* @version 1.0
* @package FARMAENLACE.
* @author Jorge Washington Mueses Cevallos.	
* @copyright Copyright (C) 2007 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
* PHPCoreMueses! es un software de libre distribuci�n, que puede ser
* copiado y distribuido bajo los t�rminos de la Licencia P�blica
* General GNU, de acuerdo con la publicada por la Free Software
* Foundation, versi�n 2 de la licencia o cualquier versi�n posterior.
*/

package com.far.mer.seg.clas;
import com.far.lib.BaseDatos;
import java.sql.ResultSet;

public class Usuario extends BaseDatos{
    public Usuario(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    public ResultSet getUsuarios()
    {
        ResultSet r = this.consulta("SELECT top 10 alias, alias as alias1 FROM tbl_usuario with (nolock) WHERE estado=1 order by alias;");
        return r;
    }
    public ResultSet autenticar(String u, String c)
    {
        ResultSet r = this.consulta("SELECT * FROM vta_usuario_farmacia WHERE aliasF='"+u+"' and clave=HashBytes('MD5', '"+c+"');");
        return r;
    }
    public ResultSet nuevaClave(String u)
    {
        ResultSet r = this.consulta("SELECT * FROM vta_usuario_farmacia WHERE aliasF='"+u+"';");
        return r;
    }     
    public boolean cambiarClave(String u, String c)
    {
        String sql = "UPDATE tbl_usuario SET clave=HashBytes('MD5', '"+c+"') WHERE alias='"+u+"';";
        return this.ejecutar(sql);        
    }    
    public String getOficina(String ip)
    {
        String of = "-1";
        try{
            ip = ip.replace(".", ":");
            String vecDirRed[] = ip.split(":");
            String dirRed = vecDirRed[0]+"."+vecDirRed[1]+"."+vecDirRed[2];
            ResultSet rs = this.consulta("SELECT oficina FROM OFICINA_IP WHERE ip_red='"+dirRed+"' and "+vecDirRed[vecDirRed.length-1]+" between ip_rango_inicial and ip_rango_final;");
            if(rs.next()){
                of = rs.getString("oficina")!=null ? rs.getString("oficina") : "-1";
                rs.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return of;
    }

}