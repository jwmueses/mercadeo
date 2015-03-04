/**
 * @version 1.0
 * @package jm.web.
 * @author Jorge Washington Mueses Cevallos.
 * @copyright Copyright (C) 2008 por Jorge W. Mueses Cevallos.
 * Todos los derechos reservados.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
*/

package com.far.lib;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet que consigue los datos de una tabla en formato JSON.
 */
public class getTabla extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    private int _fxp = 50;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        this._fxp = Integer.parseInt(config.getServletContext().getInitParameter("_PAGINACION"));
    }


/**
 * Función que retorna al cliente los datos de una tabla paginada en formato JSON.
 * @param request servlet request
 * @param response servlet response
 * @return una cadena de respuesta al cliente.
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
        
        String t = request.getParameter("t");
        String c = request.getParameter("c");
        String w = request.getParameter("w");
        int p = Integer.parseInt(request.getParameter("p"));
        BaseDatos db = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            out.print("fun»_objT.setDatos('"+db.paginarJSON(t, c, w, p, this._fxp)+"')");
        } finally {
            db.cerrar();
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
