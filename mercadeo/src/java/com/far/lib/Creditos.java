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

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Jorge
 */
public class Creditos extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String html = "obj»vta1_html^frm»";
            html += "<div style='font-family: Verdana, Arial, Helvetica, sans-serif;color:#000033;font-size:24px;text-align:center;'><strong>&nbsp;SISTEMA DE GESTI&Oacute;N DE PLANES DE MERCADEO </strong></div>";
            html += "<div style='font-family: Verdana, Arial, Helvetica, sans-serif;color:#003366;font-size:24px;text-align:right;'>&nbsp;Versi&oacute;n 1.0</div>";

            html += "<div style='font-family: Verdana, Arial, Helvetica, sans-serif;color:#000033;color:#000000;font-size:12px;'>";
            html += "    <p><br><strong>DESARROLLADOR:</strong> Jorge W. Mueses Cevallos.</p>";
            html += "    <p style='font-size:10px;color:#0000FF;'>Teléfono: (593) (06) 2 545 151 <br />";
            html += "    Móvil: (593) 0995204832<br />";
            html += "    Mail: jorge_mueses@yahoo.com</p>";
            html += "</div>";
            
            html += "<div style='text-align:right;'><input type=\"button\" value='Aceptar' onclick=\"_R('bloq_vta1');_R('vta1');\" /></div>";
            out.print(html);
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
