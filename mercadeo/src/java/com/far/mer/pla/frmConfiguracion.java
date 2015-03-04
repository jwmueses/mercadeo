/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Configuracion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class frmConfiguracion extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);

        try {
            out.print("obj»vta1_html^frm»");
            out.print("<form action=\"frmConfiguracionGuardar\" onsubmit=\"return mer_configuracionGuardar(this)\" autocomplete=\"off\">");
            out.print("<table cellpadding=\"2\" width=\"100%\">");
            out.print("<tr>");
            out.print("<td>Correo del Remitente:</td>");
            out.print("<td><input type=\"text\" id=\"mail_remitente\" name=\"mail_remitente\" size=\"35\" value=\""+objConfiguracion.getValor("mail_remitente")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Correo de autorizaci&oacute;n de operaciones:</td>");
            out.print("<td><input type=\"text\" id=\"mail_operaciones\" name=\"mail_operaciones\" size=\"35\" value=\""+objConfiguracion.getValor("mail_operaciones")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Correo de autorizaci&oacute;n de marketing:</td>");
            out.print("<td><input type=\"text\" id=\"mail_marketing\" name=\"mail_marketing\" size=\"35\" value=\""+objConfiguracion.getValor("mail_marketing")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Correo de autorizaci&oacute;n de ventas:</td>");
            out.print("<td><input type=\"text\" id=\"mail_ventas\" name=\"mail_ventas\" size=\"35\" value=\""+objConfiguracion.getValor("mail_ventas")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Correo de autorizaci&oacute;n del departamento comercial:</td>");
            out.print("<td><input type=\"text\" id=\"mail_comercial\" name=\"mail_comercial\" size=\"35\" value=\""+objConfiguracion.getValor("mail_comercial")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Correo para compras internas:</td>");
            out.print("<td><input type=\"text\" id=\"mail_compras_internas\" name=\"mail_compras_internas\" size=\"35\" value=\""+objConfiguracion.getValor("mail_compras_internas")+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>No. de d&iacute;as para confirmaci&oacute;n de auspicios:</td>");
            out.print("<td><input type=\"text\" id=\"admin_tiempos_conf\" name=\"admin_tiempos_conf\" size=\"35\" maxlength=\"2\" onkeypress=\"_numero(event)\" value=\""+objConfiguracion.getValor("admin_tiempos_conf")+"\" /></td>");
            out.print("</tr>");

            out.print("<tr>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
            out.print("</tr>");
            
            out.print("</table>");
            out.print("</form>");
        } finally {
            objConfiguracion.cerrar();
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
