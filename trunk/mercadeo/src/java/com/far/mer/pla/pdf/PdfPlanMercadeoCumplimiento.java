/**
** @version 1.0
** @package FACTURAPYMES.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2011 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
** FACTURAPYMES es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia
* Attribution-NonCommercial-NoDerivs 3.0 Unported,
* de acuerdo con la publicada por la CREATIVE COMMONS CORPORATION.
*/

package com.far.mer.pla.pdf;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.ResultSet;
import com.far.lib.Pdf;

import com.far.lib.BaseDatos;

/**
 *
 * @author Jorge
 */
public class PdfPlanMercadeoCumplimiento extends HttpServlet {
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
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");

        String num_plan = request.getParameter("nro");
        String tipo = request.getParameter("tpy1");
        String id_plan_mercadeo = request.getParameter("idPM1");
        String usuario = request.getParameter("usr1");
        String idOficina = request.getParameter("idOfic1");
        String fecha_ini = request.getParameter("fi1");
        String fecha_fin = request.getParameter("ff1");
        
        String where = "where fecha_terminacion between '"+fecha_ini+"' and '"+fecha_fin+"' and estado=9";
        if(tipo.compareTo("0")!=0){
             where += " and P.id_tipo_plan='"+tipo+"'";
        }
        if(id_plan_mercadeo.compareTo("0")!=0){
             where += (where.compareTo("where")!=0 ? " and" : "") + " P.id_plan_mercadeo="+id_plan_mercadeo;
        }
        if(usuario.compareTo("0")!=0){
             where += (where.compareTo("where")!=0 ? " and" : "") + " P.usuario_creacion='"+usuario+"'";
        }
        if(num_plan.compareTo("")!=0){
             where = "where sec_tipo_plan="+num_plan+" and estado=9";
        }

        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String sql = "select P.sec_tipo_plan, P.plan_mercadeo, T.nombre, "+ 
        "CONVERT(VARCHAR, P.fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR, P.fecha_fin_averificar, 103) as fecha_fin_averificar, "+ 
        "proyeccion_ventas, ventas_reales, porcentaje_cumplimiento, total_auspicio, total_gasto, porcentaje_cumplimiento_gasto " +
        "from (tbl_tipo_plan as T with (nolock) inner join tbl_plan_mercadeo as P with (nolock) on T.id_tipo_plan=P.id_tipo_plan) " +
        where +
        "order by P.plan_mercadeo";
        String [] cabTabla = new String [] {"No.", "No. DE PLAN", "NOMBRE DEL PLAN", "TIPO", "EVALUACION DESDE", "EVALUACION HASTA", "TOTAL VENTAS PROYECTADAS", 
            "TOTAL VENTAS DEL PERIODO", "% DE CUMPLIMIENTO", "TOTAL PRESUPUESTO", "TOTAL GASTO", "% DE CUMPLIMIENTO"};
        float [] anchoTabla = new float[]{25f, 40f, 120f, 120f, 60f, 60f, 60f, 60f, 50f, 60f, 60f, 50F};
        int [] alineaciones = new int[]{0,0,0,0,0,0,2,2,2,2,2,2};
        
        if(idOficina.compareTo("0")!=0){
            sql = "select P.sec_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_ini_averificar, 103) as fecha_ini_averificar, "+ 
                "CONVERT(VARCHAR, P.fecha_fin_averificar, 103) as fecha_fin_averificar, PMO.oficina, PMO.nombre as nombre_oficina, " + 
                "PMO.proy_ventas, PMO.p_ventas_reales, PMO.porcentaje_cumplimiento, PMO.auspicio, PMO.gasto, PMO.porcentaje_cumplimiento_gasto " +
                "from tbl_plan_mercadeo as P with(nolock) inner join tbl_plan_mercadeo_farmacia as PMO with(nolock) on PMO.id_plan_mercadeo=P.id_plan_mercadeo "+ 
                "where PMO.id_plan_mercadeo=" + id_plan_mercadeo + " and PMO.oficina='" + idOficina + "' " +
                "order by P.plan_mercadeo, PMO.nombre";
            cabTabla = new String [] {"No.", "No. DE PLAN", "NOMBRE DEL PLAN", "EVALUACION DESDE", "EVALUACION HASTA", "Nro. DE OFICINA", 
                "NOMBRE DE OFICINA", "TOTAL VENTAS PROYECTADAS", "TOTAL VENTAS DEL PERIODO", "% DE CUMPLIMIENTO", "TOTAL PRESUPUESTO", 
                "TOTAL GASTO", "% DE CUMPLIMIENTO"};
            anchoTabla = new float[]{25f, 40f, 120f, 60f, 60f, 50f, 120f, 60f, 60f, 50f, 60f, 60f, 50F};
            alineaciones = new int[]{0,0,0,0,0,0,0,2,2,2,2,2,2};
        }
        
        String titulo = "FARMAENLACE";
        String ruc = "RUC: 1791984722001";
        String subtitulo = "Consolidado cumplimiento de ventas y cumplimiento de presupuesto";
        String direccion = "AV. CAPITAN RAFAEL RAMOS E2-210 Y CASTELLI";
        String pie_pag = "Ecuador";
        
        
        ResultSet registros = objDB.consulta(sql);
        
        Pdf objPdf = new Pdf(false, titulo, ruc, subtitulo, direccion, pie_pag);
        
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
                objPdf.setMensaje(response, "El plan se encuentra anulado");
            }else{
                objPdf.lista(response, registros, cabTabla, anchoTabla, alineaciones);
            }
        }else{
            objPdf.lista(response, registros, cabTabla, anchoTabla, alineaciones);
        }

        try{
            registros.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        objDB.cerrar();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
