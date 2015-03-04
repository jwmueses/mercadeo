/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.TipoPlan;
import java.io.IOException;
import java.io.PrintWriter;
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
public class frmTipoPlanGuardar extends HttpServlet {

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
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            String WHERE = request.getParameter("WHERE");
            String p = request.getParameter("p") != null ? request.getParameter("p") : "0";
            
            String id_tipo_plan_ant = request.getParameter("id_tipo_plan_ant").toUpperCase();
            String id_tipo_plan = request.getParameter("id_tipo_plan").toUpperCase();
            String nombre = request.getParameter("nombre");
            String cedula = request.getParameter("cedula");
            String centro_costos_coordinador = request.getParameter("centro_costos_coordinador");
            String coordinador = request.getParameter("coordinador");
            String mail_responsable = request.getParameter("mail_responsable");
            String p_incremento = request.getParameter("p_incremento");
            String dias_prolonga = request.getParameter("dias_prolonga");
            String auspicio_manual = request.getParameter("auspicio_manual");
            String estado = request.getParameter("estado");
            int tope = Integer.parseInt(request.getParameter("tope"));
            
            String cuentas = "";
            String descripciones = "";
            String codigos = "";
            String sucursales = "";
            for(int i=0; i<tope; i++){
                if(request.getParameter("c"+i)!=null){
                    cuentas += request.getParameter("c"+i) + ",";
                    descripciones += request.getParameter("d"+i) + ",";
                }
            }
            int i=0;
            String ch = "";
            while(request.getParameter("ch"+i)!=null){
                ch = request.getParameter("ch"+i);
                if(ch.toLowerCase().compareTo("true")==0){
                    codigos += request.getParameter("suc"+i) + ",";
                    sucursales += request.getParameter("nomSuc"+i) + ",";
                }
                i++;
            }

            if(cuentas.compareTo("")!=0){
                cuentas = cuentas.substring(0, cuentas.length()-1);
                descripciones = descripciones.substring(0, descripciones.length()-1);
            }
            if(codigos.compareTo("")!=0){
                codigos = codigos.substring(0, codigos.length()-1);
                sucursales = sucursales.substring(0, sucursales.length()-1);
            }

            if(id_tipo_plan_ant.compareTo("-1")==0){
                res = "msg»Acrónimo ya existen.";
                if(!objTipoPlan.idDuplicado(id_tipo_plan))
                    if(objTipoPlan.insertar(id_tipo_plan, usuario, cedula, nombre, coordinador, centro_costos_coordinador, mail_responsable, p_incremento, dias_prolonga, auspicio_manual, estado, cuentas, descripciones, codigos, sucursales)){
                        res = "tbl»"+objTipoPlan.paginarJSON("tbl_tipo_plan", "id_tipo_plan,id_tipo_plan,nombre,coordinador", WHERE, Integer.parseInt(p), 100)+
                                "^id»-1^msg»Información ingresada satisfactoriamente.";
                    }else{
                        res = "msg»" + objTipoPlan.getError();
                    }
            }else{
                if(objTipoPlan.actualizar(id_tipo_plan, usuario, cedula, nombre, coordinador, centro_costos_coordinador, mail_responsable, p_incremento, dias_prolonga, auspicio_manual, estado, cuentas, descripciones, codigos, sucursales)){
                    res = "tbl»"+objTipoPlan.paginarJSON("tbl_tipo_plan", "id_tipo_plan,id_tipo_plan,nombre,coordinador", WHERE, Integer.parseInt(p), 100)+
                                "^msg»Información guardada satisfactoriamente.";
                }else{
                        res = "msg»" + objTipoPlan.getError();
                    }
            }
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            objTipoPlan.cerrar();
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
