/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla.Excel;

import com.far.lib.BaseDatos;
import com.far.lib.Excel;
import com.far.lib.Matriz;
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
public class ExcelPlanMercadeo extends HttpServlet {

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
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        
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
        String id_plan_mercadeo = request.getParameter("idPM1");
        
        
        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        ResultSet rsUsuarios = objDB.consulta("select distinct usuario_creacion, usuario_creacion as usuario_creacion1 "
                + "from tbl_plan_mercadeo where usuario_creacion is not null order by usuario_creacion");
        String matUsuarios[][] = Matriz.ResultSetAMatriz(rsUsuarios);
        
        
        ResultSet registros = null;
        ResultSet rsPlan = null;
        if(num_plan.compareTo("")!=0){        
            registros = objDB.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan "
                + "where P.estado in(6,9,10) and P.sec_tipo_plan="+num_plan + (Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and P.usuario_creacion='"+usuario+"'" : "") );
            
            rsPlan = objDB.consulta("select * from tbl_plan_mercadeo with (nolock) where sec_tipo_plan="+num_plan);
        }else{
            registros = objDB.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan "
                + "where P.estado in(6,9,10) and P.id_plan_mercadeo="+id_plan_mercadeo + (Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and P.usuario_creacion='"+usuario+"'" : "") );
            
            rsPlan = objDB.consulta("select * from tbl_plan_mercadeo with (nolock) where id_plan_mercadeo="+id_plan_mercadeo);
        }
        
        String nombre_plan = "";
        int estado = 0;
        try{
            if(rsPlan.next()){
                nombre_plan = rsPlan.getString("plan_mercadeo")!=null ? rsPlan.getString("plan_mercadeo") : "";
                estado = rsPlan.getString("estado")!=null ? rsPlan.getInt("estado") : 0;
                rsPlan.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String subtitulo = "Plan de Mercadeo Completo";
        Excel reporte = new Excel(subtitulo);
        String xls = "El plan de mercadeo " + nombre_plan.toUpperCase() + " aún no se encuentra autorizado por el/la COORDINADOR(A) COMERCIAL o no tiene autorización para ver el reporte";
        if(estado==10){
            xls = reporte.setMensaje("El plan se encuentra anulado");
        }else{
            if(objDB.getFilas(registros)>0){
                xls = reporte.PlanMercadeo(response, registros, objDB);
            }
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
