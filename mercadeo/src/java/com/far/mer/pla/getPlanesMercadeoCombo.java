/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.DatosDinamicos;
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
public class getPlanesMercadeoCombo extends HttpServlet {
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

        String id_tipo_plan = request.getParameter("idTP");
        String usuario = request.getParameter("usr");
        String deCumplimiento = request.getParameter("deCump");
        
        String msg = "";
        String html = "";
        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        /*ResultSet rsUsuarios = objDB.consulta("select distinct usuario_creacion, usuario_creacion as usuario_creacion1 "
                + "from tbl_plan_mercadeo where usuario_creacion is not null order by usuario_creacion");
        String matUsuarios[][] = Matriz.ResultSetAMatriz(rsUsuarios);*/
        
        try{
            String sql = "select id_plan_mercadeo, plan_mercadeo from tbl_plan_mercadeo with (nolock) "+ 
                    "where id_tipo_plan='"+id_tipo_plan+"' "+(usuario.compareTo("0")!=0 ? " and usuario_creacion='"+usuario+"'" : "")+
                    (deCumplimiento.compareTo("1")==0 ? " and estado=9" : "")+" order by plan_mercadeo";
            ResultSet rsPlanes = objDB.consulta(sql);
            html = DatosDinamicos.combo(rsPlanes, "idPM1", "", (deCumplimiento.compareTo("1")==0 ? "mer_reporteGetOficinas()" : ""), "TODOS", 260);
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»axIdPM1^fun»mer_reporteGetOficinas()^msg»"+msg+"^frm»"+html);
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
