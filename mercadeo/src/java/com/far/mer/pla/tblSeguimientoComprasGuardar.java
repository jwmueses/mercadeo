/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Actividad;
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
public class tblSeguimientoComprasGuardar extends HttpServlet {

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

        String idActCom = request.getParameter("idActCom");
        String id_actividad = request.getParameter("idAct");
        String fecha = request.getParameter("fac_fecha");
        String ruc = request.getParameter("fac_ruc");
        String proveedor = request.getParameter("fac_proveedor");
        String detalle = request.getParameter("fac_detalle");
        String num_documento = request.getParameter("fac_num_documento");
        String cantidad = request.getParameter("fac_cantidad");
        String fac_base_12 = request.getParameter("fac_base_12");
        String fac_base_0 = request.getParameter("fac_base_0");
        String iva = request.getParameter("fac_iva");
        String total = request.getParameter("fac_total");
        
        int alto = request.getParameter("alto")!=null ? Integer.parseInt(request.getParameter("alto")) : 400;
        cantidad = cantidad.compareTo("")!=0 ? cantidad : "1";
        fac_base_12 = fac_base_12.compareTo("")!=0 ? fac_base_12 : "0";
        fac_base_0 = fac_base_0.compareTo("")!=0 ? fac_base_0 : "0";
        iva = iva.compareTo("")!=0 ? iva : "0";
        
        boolean ok = false;
        String html = "";
        String funciones = "";
        String msg = "";
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        if(idActCom.compareTo("-1")==0){
            if(objActividad.ingresarFactura(id_actividad, fecha, ruc, proveedor, num_documento, cantidad, fac_base_12, fac_base_0, iva, total, detalle)){
                ok = true;
            }
        }else{
            if(objActividad.actualizarFactura(idActCom, fecha, ruc, proveedor, num_documento, cantidad, fac_base_12, fac_base_0, iva, total, detalle)){
                ok = true;
            }
        }
        
        objActividad.actualizaDistribucionGastoFarmacias(id_actividad);
        
        if(ok){
            ResultSet rsAct = objActividad.getFacturas(id_actividad);
            html = "obj»axGasto^frm»" + objActividad.tblFacturas(rsAct, alto);
            float total_gasto = objActividad.total_gasto();
            funciones = "^fun»mer_seguimientoSetGastoTotal("+total_gasto+");";
        }else{
            msg = "^msg»" + objActividad.getError();
        }
        
        try {
            out.print(html+funciones+msg);
        } finally {
            objActividad.cerrar();
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
