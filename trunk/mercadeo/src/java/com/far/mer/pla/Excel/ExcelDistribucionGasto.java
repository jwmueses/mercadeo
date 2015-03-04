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
public class ExcelDistribucionGasto extends HttpServlet {

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

        String num_plan = request.getParameter("nro");
        String tipo = request.getParameter("tpy1");
        String id_plan_mercadeo = request.getParameter("idPM1");
        String usuario = request.getParameter("usr1");

        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsPlan = objDB.consulta("select plan_mercadeo, usuario_creacion from tbl_plan_mercadeo where sec_tipo_plan="+num_plan);
        String plan_mercadeo="";
        String usuario_creacion="";
        int estado = 0;
        try{
            if(rsPlan.next()){
                plan_mercadeo = rsPlan.getString("plan_mercadeo")!=null ? rsPlan.getString("plan_mercadeo") : "";
                usuario_creacion = rsPlan.getString("usuario_creacion")!=null ? rsPlan.getString("usuario_creacion") : "";
                estado = rsPlan.getString("estado")!=null ? rsPlan.getInt("estado") : 0;
                rsPlan.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        String where = "where";
        if(tipo.compareTo("0")!=0){
             where += " P.id_tipo_plan='"+tipo+"'";
        }
        if(id_plan_mercadeo.compareTo("0")!=0){
             where += (where.compareTo("where")!=0 ? " and" : "") + " P.id_plan_mercadeo="+id_plan_mercadeo;
        }
        if(usuario.compareTo("0")!=0){
             where += (where.compareTo("where")!=0 ? " and" : "") + " P.usuario_creacion='"+usuario+"'";
        }
        if(where.compareTo("where")==0){
            where = "";
        }
        if(num_plan.compareTo("")!=0){
             where = "where sec_tipo_plan="+num_plan;
        }
        
        ResultSet registros = objDB.consulta("select P.sec_tipo_plan, F.ruc, F.proveedor, PF.nombre, PF.centro_costos, " + 
        "PF.subtotal_12, PF.subtotal_0, PF.iva, PF.gasto " +
        "from ((((tbl_tipo_plan as T with (nolock) inner join tbl_plan_mercadeo as P with (nolock) on T.id_tipo_plan=P.id_tipo_plan) " +
        "inner join tbl_plan_mercadeo_farmacia as PF on PF.id_plan_mercadeo=P.id_plan_mercadeo) " + 
        "inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) " +
        "inner join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia) " + 
        "inner join tbl_actividad_compra as F with (nolock) on A.id_actividad=F.id_actividad " +
        where +
        " order by P.plan_mercadeo");
        
        String subtitulo = "Distribución de Gasto";
        Excel reporte = new Excel(subtitulo);
        
        String [] cabTabla = new String [] {"No. PLAN", "RUC", "PROVEEDOR", "OFICINA", "CENTRO DE COSTOS", "BASE 12%", "BASE 0%", "IVA", "VALOR FACTURA"};
        String [] tipos = new String [] {"String", "String", "String", "String", "String", "Number", "Number", "Number", "Number"};
        
        String xls = "";
        if(estado==10){
            xls = reporte.setMensaje("El plan se encuentra anulado");
        }else{
            xls = reporte.lista(registros, cabTabla, tipos);
        }

        out.print(xls);

        try{
            rsPlan.close();
            //registros.close();
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
