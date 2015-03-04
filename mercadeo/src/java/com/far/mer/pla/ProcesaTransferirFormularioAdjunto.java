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

package com.far.mer.pla;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.far.lib.ArchivadorWeb;

/**
 *
 * @author Jorge
 */
public class ProcesaTransferirFormularioAdjunto extends HttpServlet {
    private String _dir = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._dir = config.getServletContext().getInitParameter("_DIR");
    }

    /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
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

        ArchivadorWeb archivo = new ArchivadorWeb(this._dir + "actividades\\");
        try {
            String r = "<script language='javascript' type='text/javascript'>window.top.window.ProcesaTransferirFormularioAdjunto('Ha ocurrido un error en el proceso de subida del archivo.', '');</script>";
            if( archivo.subir(request, 4) ){
                String nombre = archivo.getNombre();
                r = "<script language='javascript' type='text/javascript'>window.top.window.ProcesaTransferirFormularioAdjunto('0', '"+nombre+"');</script>";
            }else{
                r = "<script language='javascript' type='text/javascript'>window.top.window.ProcesaTransferirFormularioAdjunto('"+archivo.getError()+"', '');</script>";
            }
            
            out.print(r);
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
