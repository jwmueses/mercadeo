/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.DatosDinamicos;
import com.far.mer.pla.clas.TipoPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class filFarmacias extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.easy_ip = config.getServletContext().getInitParameter("EASY_IP");
        this.easy_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASY_PUERTO"));
        this.easy_db = config.getServletContext().getInitParameter("EASY_DB");
        this.easy_usuario = config.getServletContext().getInitParameter("EASY_USUARIO");
        this.easy_clave = config.getServletContext().getInitParameter("EASY_CLAVE");
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        int alto = Integer.parseInt( request.getParameter("al") );
        String grupo = request.getParameter("grupo");
        
        BaseDatos objEasy = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        ResultSet rsZonas = objEasy.consulta("select zona, descripcion from tbl_zona with (nolock) order by descripcion");
        
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsSucursales = objTipoPlan.getSucursales(usuario);
        
        try {
            out.print("obj»vta2_html^foc»txtFar^frm»");
            out.print("<form id=\"frm\" action=\"getTblFarmacias\" onsubmit=\"return Ajax.enviarForm(this, true)\" autocomplete=\"off\">");
            out.print("<table width=\"100%\">");
            out.print("<tr>");
            out.print("<td nowrap>Sucursal: "+DatosDinamicos.combo(rsSucursales, "idSuc", "", "", "", 160)+"</td>");
            out.print("<td nowrap>Provincia: "+DatosDinamicos.combo(rsZonas, "idProv", "", "mer_getCiudades()", " TODAS ", 130)+"</td>");
            out.print("<td nowrap>Ciudad: <span id=\"axCiudades\">TODAS<input type=\"hidden\" id=\"idCiud\" name=\"idCiud\" value=\"0\" /></span></td>");
            out.print("<td align=\"right\"><input type=\"submit\" value=\"Filtrar\" onmousedown=\"_('txtFar').value=''\" /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"4\"><hr /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"2\">Farmacia: ");
            out.print("<input type=\"text\" id=\"txtFar\" name=\"txtFar\" size=\"48\" class=\"buscar\" onfocus=\"this.select();\" onkeyup=\"return Ajax.enviarForm(_('frm'))\" onkeydown=\"_NoE(event);\" title=\"farmacia\" />");
            out.print("</td>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"button\" value=\"Agregar farmacias\" onclick=\"mer_agregarFarmacias("+grupo+")\" /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"4\"><div style=\"width:750px;font-weight:bold;border:1px solid #CCC\"><input type=\"checkbox\" onclick=\"mer_marcar(this.checked)\" /> &nbsp;&nbsp;&nbsp; FARMACIAS</div>"
                    + "<div id=\"axFarmaciasFil\" style=\"overflow:auto;width:750px;height:"+(alto-260)+"px;border:1px solid #CCC\"></td>");
            out.print("</tr>");
            
            out.print("</table>");
            out.print("</form>");
        } finally {     
            objTipoPlan.cerrar();
            objEasy.cerrar();
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
