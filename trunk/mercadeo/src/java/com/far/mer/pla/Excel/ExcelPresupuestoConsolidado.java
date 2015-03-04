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
public class ExcelPresupuestoConsolidado extends HttpServlet {

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
        //String fecha_ini = request.getParameter("fi1");
        //String fecha_fin = request.getParameter("ff1");
        
        //String where = "where fecha_aprobacion between '"+fecha_ini+"' and '"+fecha_fin+"'";
        
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

        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet registros = objDB.consulta("select P.sec_tipo_plan, P.plan_mercadeo, T.nombre, "+ 
        "CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, CONVERT(VARCHAR, P.fecha_mark_vent, 103) as fecha_mark_vent, "
                + "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, " +
        "T.p_incremento, T.coordinador, T.centro_costos_coordinador, sum(A.pre_total), sum(A.monto), sum(A.pre_total) - sum(A.monto), " +
        "case P.estado when 1 then 'CREADO' when 2 then 'RECHAZADO MARKETING' when 3 then 'APROBADO OPERACIONES' when 4 then 'APROBADO MARKETING' " +
        "when 5 then 'APROBADO VENTAS' when 6 then 'AUTORIZADO COMERCIAL' when 7 then 'RECHAZADO VENTAS' when 8 then 'RECHAZADO COMERCIAL' "
                + "when 9 then 'TERMINADO' when 10 then 'ANULADO' else '' end as estado " +
        "from ((tbl_tipo_plan as T with (nolock) inner join tbl_plan_mercadeo as P with (nolock) on T.id_tipo_plan=P.id_tipo_plan) " +
        "left outer join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) " +
        "left outer join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia " +
        where +
        " group by P.sec_tipo_plan, P.plan_mercadeo, T.nombre, P.fecha_creacion, fecha_mark_vent, fecha_aprobacion, T.p_incremento, " +
        "T.coordinador, T.centro_costos_coordinador, P.estado " +
        "order by T.nombre,P.plan_mercadeo");
        
        String subtitulo = "Presupuesto consolidado";
        Excel reporte = new Excel(subtitulo);
        
        String [] cabTabla = new String [] {"No. DE PLAN", "NOMBRE DEL PLAN", "TIPO", "FECHA CREACION", "FECHA APROBACION", "FECHA APROBACION COMERCIAL", 
            "% INCREMENTO", "COORDINADOR", "CENTRO DE COSTOS COORDINADOR", "PRES. TOTAL", "TOTAL GASTO", "DIFERENCIA", "ESTADO"};
        String [] tipos = new String [] {"String", "String", "String", "String", "String", "String", "Number", "String", "String", "Number", "Number", "Number", "String"};
        
        String xls = "";
        if(num_plan.compareTo("")!=0){
            int estado = 0;
            try{
                ResultSet rs = objDB.consulta("select estado from tbl_plan_mercadeo with(nolock) where sec_tipo_plan="+num_plan);
                if(rs.next()){
                    estado = rs.getString("estado")!=null ? rs.getInt("estado") : 0;
                    rs.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(estado==10){
                xls = reporte.setMensaje("El plan se encuentra anulado");
            }else{
                xls = reporte.lista(registros, cabTabla, tipos);
            }
        }else{
            xls = reporte.lista(registros, cabTabla, tipos);
        }

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
