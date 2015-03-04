/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Laboratorio;
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
public class frmLaboratorioEstrategicoReasignacionGuardar extends HttpServlet {

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
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        Laboratorio objLaboratorio = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            String id_laboratorio = request.getParameter("id_lab");
            int tope = Integer.parseInt(request.getParameter("limite"));
            
            String id_tipo_planes = "";
            String presupuestos = "";
            String axPresupuesto = "";
            for(int i=0; i<=tope; i++){
                if(request.getParameter("id_tipo_plan"+i)!=null){
                    axPresupuesto = request.getParameter("presupuesto"+i);
                    axPresupuesto = axPresupuesto.compareTo("")!=0 ? axPresupuesto : "0";
                    if(Float.parseFloat(axPresupuesto) > 0){
                        id_tipo_planes += "'" + request.getParameter("id_tipo_plan"+i) + "',";
                        presupuestos += axPresupuesto + ",";
                    }
                }
            }
            if(id_tipo_planes.compareTo("")!=0){
                id_tipo_planes = id_tipo_planes.substring(0, id_tipo_planes.length()-1);
                presupuestos = presupuestos.substring(0, presupuestos.length()-1);
            }
            
            //if(objLaboratorio.verificaReasignarPresupuestos(id_laboratorio, id_tipo_planes, presupuestos)){
                if(objLaboratorio.reasignarPresupuestos(id_laboratorio, usuario, id_tipo_planes, presupuestos)){
                    res = "msg»Re-asignación de presupuestos registrada satisfactoriamente.";
                }else{
                    res = "msg»" + objLaboratorio.getError();
                }
            /*}else{
                res = "msg»Alguno de los planes de mercadeo ya ha gastado el presupuesto y no se puede hacer una reasignación menor a lo gastado.";
            }*/
            
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }

        try{
            out.print(res);
        }finally {
            objLaboratorio.cerrar();
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
