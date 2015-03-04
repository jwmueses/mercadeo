/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class getCuentas extends HttpServlet {
    private String easyc_ip = null;
    private int easyc_puerto = 1433;
    private String easyc_db = null;
    private String easyc_usuario = null;
    private String easyc_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.easyc_ip = config.getServletContext().getInitParameter("EASYC_IP");
        this.easyc_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASYC_PUERTO"));
        this.easyc_db = config.getServletContext().getInitParameter("EASYC_DB");
        this.easyc_usuario = config.getServletContext().getInitParameter("EASYC_USUARIO");
        this.easyc_clave = config.getServletContext().getInitParameter("EASYC_CLAVE");
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

        String txt = request.getParameter("txt").toLowerCase();
        String obj = request.getParameter("obj");
        String onClick = request.getParameter("onC");
        String msg = "";
        String html = "";
        BaseDatos objDB = new BaseDatos(this.easyc_ip, this.easyc_puerto, this.easyc_db, this.easyc_usuario, this.easyc_clave);
        try{
            ResultSet rs = objDB.consulta("select top(20) cuenta, descripcion from CUENTA with (nolock) where (lower(cuenta) like '"+txt+"%' or lower(descripcion) like '%"+txt+"%') and "
                    + "cuenta not in (select distinct cta_nivelup from CUENTA with (nolock) where cta_nivelup is not null) order by cuenta");
            msg = objDB.getError();
            String cuenta = "";
            String descripcion = "";
            while(rs.next()){
                cuenta = rs.getString("cuenta")!=null?rs.getString("cuenta"):"";
                descripcion = rs.getString("descripcion")!=null?rs.getString("descripcion"):"";
                html += "<option value=\"" + cuenta + "|" + descripcion + "\">" + cuenta + " &nbsp; " + descripcion+"</option>";
            }
            rs.close();
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»"+obj+"^msg»"+msg+"^frm»");
            out.print("<select id=\"cta\" size=\"5\" onclick=\""+onClick+"\">"+html+"</select>");
        } finally {
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
