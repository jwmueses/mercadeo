/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.PlanMercadeo;
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
public class frmPlanMercdeoConfAuspicioGuardar extends HttpServlet {

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
        //HttpSession sesion = request.getSession(true);
        //String usuario = (String)sesion.getAttribute("usuario");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            String WHERE = request.getParameter("WHERE");
            String p = request.getParameter("p") != null ? request.getParameter("p") : "0";

            String id_plan_mercadeo = request.getParameter("id_plan_mercadeo");
            

            //  usuarios con limite de tiempo para confirmacion  --------------------------------------------------------------------------------------
            int j=0;
            String id_tipo_plan_usuarios = "";
            String nums_dias = "";
            while(request.getParameter("id_tipo_plan_usuario"+j)!=null){
                id_tipo_plan_usuarios += request.getParameter("id_tipo_plan_usuario"+j) + ",";
                nums_dias += (request.getParameter("num_dias"+j).compareTo("")!=0 ? request.getParameter("num_dias"+j) : "0" ) + ",";
                j++;
            }
            if(id_tipo_plan_usuarios.compareTo("")!=0){
                id_tipo_plan_usuarios = id_tipo_plan_usuarios.substring(0, id_tipo_plan_usuarios.length()-1);
                nums_dias = nums_dias.substring(0, nums_dias.length()-1);
            }

            if(objPlanMercadeo.setConfAuspicios(id_plan_mercadeo, id_tipo_plan_usuarios, nums_dias)){
                res = "tbl»"+objPlanMercadeo.paginarJSON("tbl_plan_mercadeo", "id_plan_mercadeo,sec_tipo_plan,plan_mercadeo", WHERE, Integer.parseInt(p), 100)+
                                "^id»-1^msg»Información guardada satisfactoriamente.";
            }else{
                res = "msg»" + objPlanMercadeo.getError();
            }
                
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        } finally {
            objPlanMercadeo.cerrar();
        }

        try{
            out.print(res);
        }finally {
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
