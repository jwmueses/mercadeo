/**
** @version 1.0
** @package FARMAENLACE.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2011 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
* FACTURAPYMES es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia
* Attribution-NonCommercial-NoDerivs 3.0 Unported,
* de acuerdo con la publicada por la CREATIVE COMMONS CORPORATION.
*/

package com.far.mer.seg;

import com.far.lib.BaseDatos;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.ResultSet;
import com.far.lib.Fecha;
import com.far.lib.Robot;


/**
 *
 * @author Jorge
 */
public class Index1 extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String segu_ip = null;
    private int segu_puerto = 1433;
    private String segu_db = null;
    private String segu_usuario = null;
    private String segu_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.segu_ip = config.getServletContext().getInitParameter("SEGU_IP");
        this.segu_puerto = Integer.parseInt(config.getServletContext().getInitParameter("SEGU_PUERTO"));
        this.segu_db = config.getServletContext().getInitParameter("SEGU_DB");
        this.segu_usuario = config.getServletContext().getInitParameter("SEGU_USUARIO");
        this.segu_clave = config.getServletContext().getInitParameter("SEGU_CLAVE");
    }
    /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String usuario = (String)session.getAttribute("usuario");
        //String id_rol = (String)session.getAttribute("id_rol");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        
        Robot objRobot = new Robot(this._ip, this._puerto, this._db, this._usuario, this._clave);
        objRobot.ejecutar();
        objRobot.cerrar();
        
        PrintWriter out = response.getWriter();

        try {
            out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            out.print("<head>");
            out.print("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">");
            out.print("<title>SISTEMA DE ADMINISTRACIÓN DE PANES DE MERCADEO</title>");
            out.print("<link href=\"img/favicon.ico\" type=\"image/x-icon\" rel=\"shortcut icon\" />");
            out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"nucleo/Nucleo.css\">");
            out.print("<script type=\"text/javascript\" src=\"nucleo/Nucleo.js\"></script>");
            out.print("<script type=\"text/javascript\" src=\"mercadeo.js\"></script>");


            out.print("<script type=\"text/javascript\" language=\"javascript\">");
            out.print("var _USR_='" + usuario + "';");
            //out.print("var _ROL_=" + id_rol + ";");
            out.print("var _FECHA_='" + Fecha.getFecha("ISO") + "';");
            out.print("var _MENUS_=Array();");
            out.print("var _INV_INI_=false;");
            out.print("var _AN_ACT_="+Fecha.getAnio()+";");
            out.print("var _MS_ACT_="+Fecha.getMes()+";");
            BaseDatos db = new BaseDatos(this.segu_ip, this.segu_puerto, this.segu_db, this.segu_usuario, this.segu_clave);
            try{
                /*ResultSet res_cont = db.consulta("select count(ventana) from vta_ventana where id_rol=" + id_rol + ";");
                res_cont.next();

                ResultSet res_mod = db.consulta("select ventana from vta_ventana where id_rol=" + id_rol + " order by ventana;");
                List modulos = new ArrayList();
                while(res_mod.next()){
                    modulos.add(res_mod.getString("ventana"));
                }
                Collections.sort(modulos);*/
                ResultSet rs = db.consulta("select Transaccion from Atribuciones where NombreCorto='" + usuario + "' and Modulo='MDPLANMERC' order by Transaccion;");
                while(rs.next()){
                    out.print("_MENUS_.push('" + ((rs.getString(1)!=null)?rs.getString(1):"") + "');");
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                db.cerrar();
            }
            out.print("</script>");
            out.print("</head>");

out.print("<body onload=\"ini()\" onresize=\"red()\">");

out.print("<noscript>"+
"<p class=\"nota\">"+
"La p&aacute;gina que est&aacute;s viendo requiere de JAVASCRIPT para su correcto funcionamiento."+
"Si lo ha deshabilitado intencionalmente, por favor, vuelve a activarlo."+
"</p>"+
"</noscript>");


out.print("<div id=\"grmenu\">"+
                "<div id=\"logo\">&nbsp;</div>"+
                "<div id=\"menuizq\"></div>"+
                "<div id=\"menuder\"><div id=\"usuario\">Bienvenido<br />"+usuario+"</div></div>"+
            "</div>");

out.print("<div id=\"cuerpo\">");

out.print("<div id=\"filtro\"></div>");

out.print("<div id=\"iconos\"></div>");

out.print("<div id=\"gredicion\">"+
                "<div id=\"edicion\" class=\"tabla\">"+

                    "<div class=\"fila\">"+
                        "<div id=\"d_0\" class=\"columna indicador\">&nbsp;</div>"+
                        "<div id=\"d_1\" class=\"columna marco\" onscroll=\"\">&nbsp;</div>"+
                        "<div id=\"d_2\" class=\"columna indicador\">&nbsp;</div>"+
                        "<div id=\"d_3\" class=\"columna marco\">&nbsp;</div>"+
                        "<div id=\"d_4\" class=\"columna indicador\">&nbsp;</div>"+
                        "<div id=\"d_5\" class=\"columna marco\">&nbsp;</div>"+
                        "<div id=\"d_6\" class=\"columna indicador\">&nbsp;</div>"+
                    "</div>"+
                "</div>"+

            "</div>");

out.print("</div>");

out.print("<div id=\"areabody\"></div>");
out.print("<div id=\"temp\" style=\"display:none\"></div>");

out.print("</body>");
out.print("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
