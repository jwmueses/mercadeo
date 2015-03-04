/**
* @version 1.0
* @package FARMAENLACE.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2007 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
* PHPCoreMueses! es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia Pública
* General GNU, de acuerdo con la publicada por la Free Software
* Foundation, versión 2 de la licencia o cualquier versión posterior.
*/
package com.far.mer.seg;

import com.far.mer.seg.clas.Usuario;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Administrador
 */
public class cambiarClaveGuardar extends HttpServlet {

    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
    }

    /**
    * Procesa peticiones HTTP del método <code>POST</code>.
    * @param request petición al servlet
    * @param response respuesta del servlet
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession(true);
        String u = (String)sesion.getAttribute("usuario");
        String c = request.getParameter("r_clave");
        String r = "err»1^msg»A ocurrido un error inesperado. Por favor, vuelva a intentarlo más tarde.";
        Usuario usr = new Usuario(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        if(usr.cambiarClave(u, c)){
            r= "err»0^vta»vta_clave^fun»window.open('Salir','_parent');^msg»Actualización de la contraseña registrada satisfactoriamente";
        }
        
        try {
            out.print(r);
        } finally {
            usr.cerrar();
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
