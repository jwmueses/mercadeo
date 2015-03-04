/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla.Excel;

import com.far.lib.BaseDatos;
import com.far.lib.Excel;
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
public class ExcelPlanMercadeoGastos extends HttpServlet {

    private String _ip = null;
    private int _puerto = 5432;
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
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if(request.getHeader("User-Agent").toLowerCase().contains("windows")){
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-disposition", "inline; filename=PlanMercadeoGastos.xls;");
        }else{
            response.setContentType("text/xml;");
            response.setHeader("Content-disposition", "attachment; filename=PlanMercadeoGastos.ods;");
        }
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String idPlanMercadeo = request.getParameter("id");
        String plan = request.getParameter("plan");
        //String p_incremento = request.getParameter("p");

        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet registros = objDB.consulta("select case when tipo_pago='i' then 'COMPRAS INTERNAS' else 'PAGO EN FARMACIA' end, actividad, "
                + "pre_cantidad, pre_p_u, pre_total, F.fecha, F.proveedor, F.detalle, F.num_documento, F.cantidad, F.total "
                + "from tbl_actividad as A with (nolock) inner join tbl_actividad_compra as F with (nolock) on A.id_actividad=F.id_actividad "
                + "where id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+idPlanMercadeo+");");
        
        String subtitulo = "Presupuesto vs. gasto del plan de mercadeo  " + plan;
        Excel reporte = new Excel(subtitulo);
        
        String [] cabTabla = new String [] {"MEDIO", "CONCEPTO", "UNIDADES", "COSTO / UNITARIO", "PRESUPUESTO TOTAL", "FECHA", "PROVEEDOR", "DETALLE", "No. DOC.", "CANTIDAD", "VALOR"};
        String [] tipos = new String [] {"String", "String", "Number", "Number", "Number", "String", "String", "String", "String", "Number", "Number"};
        
        String xls = reporte.lista(registros, cabTabla, tipos);

        out.print(xls);

        try{
            registros.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        objDB.cerrar();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
