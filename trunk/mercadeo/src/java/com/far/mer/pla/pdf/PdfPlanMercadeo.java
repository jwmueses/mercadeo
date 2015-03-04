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
import com.far.lib.Matriz;

/**
 *
 * @author Jorge
 */
public class PdfPlanMercadeo extends HttpServlet {
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
        
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");

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
                + "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan "
                + "where P.estado in(6,9,10) and P.sec_tipo_plan="+num_plan + (Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and P.usuario_creacion='"+usuario+"'" : "") );
            
            rsPlan = objDB.consulta("select * from tbl_plan_mercadeo with (nolock) where sec_tipo_plan="+num_plan);
        }else{
            registros = objDB.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, "
                + "CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
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
        
        String titulo = "FARMAENLACE";
        String ruc = "RUC: 1791984722001";
        String subtitulo = "Plan de Mercadeo Completo";
        String direccion = "AV. CAPITAN RAFAEL RAMOS E2-210 Y CASTELLI";
        String pie_pag = "Ecuador";
        Pdf objPdf = new Pdf(true, titulo, ruc, subtitulo, direccion, pie_pag);
        /*if(objDB.getFilas(registros)>0){
            objPdf.PlanMercadeo(response, registros, objDB);
        }else{
            objPdf.setMensaje(response, "El plan de mercadeo " + nombre_plan.toUpperCase() + " aún no se encuentra autorizado por el/la COORDINADOR(A) COMERCIAL o no tiene autorización para ver el reporte");
        }*/
            
        if(estado==10){
            objPdf.setMensaje(response, "El plan se encuentra anulado");
        }else{
            if(objDB.getFilas(registros)>0){
                objPdf.PlanMercadeo(response, registros, objDB);
            }else{
                objPdf.setMensaje(response, "El plan de mercadeo " + nombre_plan.toUpperCase() + " aún no se encuentra autorizado por el/la COORDINADOR(A) COMERCIAL o no tiene autorización para ver el reporte");
            }
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
