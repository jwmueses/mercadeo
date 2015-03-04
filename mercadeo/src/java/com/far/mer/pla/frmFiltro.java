/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.DatosDinamicos;
import com.far.lib.Fecha;
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
public class frmFiltro extends HttpServlet {

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
        
        int op = Integer.parseInt(request.getParameter("op"));

        BaseDatos objBaseDatos = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        ResultSet rsUsuarios = objBaseDatos.consulta("select distinct usuario_creacion, usuario_creacion as usuario_creacion1 "
                + "from tbl_plan_mercadeo where usuario_creacion is not null order by usuario_creacion");
        String matUsuarios[][] = Matriz.ResultSetAMatriz(rsUsuarios);
        
        ResultSet rsTipos = objBaseDatos.consulta("select distinct TP.id_tipo_plan, TP.nombre from tbl_tipo_plan as TP with (nolock) inner join tbl_tipo_plan_usuario as TPU with (nolock) "
                + "on TPU.id_tipo_plan=TP.id_tipo_plan order by TP.nombre");
        if(Matriz.enMatriz(matUsuarios, usuario, 0)!=-1){  
            rsTipos = objBaseDatos.consulta("select distinct TP.id_tipo_plan, TP.nombre from tbl_tipo_plan as TP with (nolock) inner join tbl_tipo_plan_usuario as TPU with (nolock) "
                + "on TPU.id_tipo_plan=TP.id_tipo_plan where TPU.alias='"+usuario+"' order by TP.nombre");
        }
        try {
            switch(op){
                case 1:
                    out.print("obj»axFiltarPM^fun»mer_getPlanesMercadeo(0)^frm»");
                    out.print("<table>");
                    out.print("<tr><td>Estado: </td><td><select id=\"estadoPM\" style=\"width:150px\" onmousedown=\"CMB=false;\" onchange=\"mer_getPlanesMercadeo(0);mer_getActividades(-1, 0, 0);\">" +
                                "<option value=\"0\">TODOS</option>" +
                                "<option value=\"1\" selected>creados</option>" +
                                "<option value=\"2,7,8\">rechazados</option>" +
                                "<option value=\"3\">aprobados</option>" +
                                "<option value=\"4,5,6\">autorizados</option>" +
                                "<option value=\"9\">terminados</option>" +
                                "<option value=\"10\">anulados</option>" +
                                "</select></td></tr>");
                    if(Matriz.enMatriz(matUsuarios, usuario, 0)==-1){
                        out.print("<tr><td>Usuario:</td><td>" + DatosDinamicos.combo(rsUsuarios, "usr", "", "mer_getPlanesMercadeo(0);mer_getActividades(-1, 0, 0);", " TODOS ", 130) + "</td></tr>");
                    }else{
                        out.print("<tr><td colspan=\"2\"><input type=\"hidden\" id=\"usr\" name=\"usr\" value=\""+usuario+"\" /></td></tr>");
                    }
                    /*out.print("<tr><td>Desde:</td><td><input type=\"text\" id=\"fi\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('fi', 'SQL');\" /></td></tr>"
                            + "<tr><td>Hasta: </td><td><input type=\"text\" id=\"ff\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ff', 'SQL');\" /></td></tr>");*/
                    out.print("<tr><td colspan=\"2\"><input type=\"button\" value=\"Filtrar\" onclick=\"mer_getPlanesMercadeo(0);mer_getActividades(-1, 0, 0);\" /></td></tr>");
                    out.print("</table>");
                break;
                    
                case 2:
                    out.print("obj»filtro^fun»mer_seguimientoBuscar()^frm»");
                    out.print("No.:<input type=\"text\" id=\"txt\" size=\"8\" onkeypress=\"_numero(event);if(_gKC(event)==13){mer_seguimientoBuscar();}\" />");
                    out.print(" &nbsp;&nbsp; Tipo:" + DatosDinamicos.combo(rsTipos, "tpy", "", "", " TODOS ", 200) );
                    if(Matriz.enMatriz(matUsuarios, usuario, 0)==-1){
                        out.print(" &nbsp;&nbsp; Usuario:" + DatosDinamicos.combo(rsUsuarios, "usr", "", "", " TODOS ", 130) );
                    }else{
                        out.print("<input type=\"hidden\" id=\"usr\" name=\"usr\" value=\""+usuario+"\" />");
                    }
                    out.print(" &nbsp;&nbsp; Autorizados desde:<input type=\"text\" id=\"fi\" value=\""+Fecha.getFecha("SQL")+"\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('fi', 'SQL');\" />"
                            + " hasta <input type=\"text\" id=\"ff\" value=\""+Fecha.getFecha("SQL")+"\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ff', 'SQL');\" />");
                    out.print(" &nbsp; <input type=\"button\" value=\"Filtrar\" onclick=\"mer_seguimientoBuscar()\" />");
                break;
                    
                case 3:
                    String id_tipo_plan = "-1";
                    try{
                        if(rsTipos.next()){
                            id_tipo_plan = rsTipos.getString("id_tipo_plan")!= null ? rsTipos.getString("id_tipo_plan") : "-1";
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    ResultSet rsPlanes = objBaseDatos.consulta("select id_plan_mercadeo, plan_mercadeo from tbl_plan_mercadeo with (nolock) "
                            + "where id_tipo_plan='"+id_tipo_plan+"' "+(Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and usuario_creacion='"+usuario+"'" : "")+" order by plan_mercadeo");
                    
                    out.print("obj»vta1_html^frm»");
                    out.print("<div id=\"cmbTipoTodos\" style=\"display:none\">" + DatosDinamicos.combo(rsTipos, "<<ID>>", "", "mer_reporteGetPlanes()", "TODOS", 260) + "</div>");
                    out.print("<div id=\"cmbTipoNoTodos\" style=\"display:none\">" + DatosDinamicos.combo(rsTipos, "<<ID>>", "", "mer_reporteGetPlanes()", 260) + "</div>");
                    out.print("<form onsubmit=\"return reporte(this)\" autocomplete=\"off\">");
                    out.print("<table width=\"99%\">");
                    out.print("<tr><td colspan=\"2\"><label><input type='radio' id='z0' name='z' checked value='PresupuestoConsolidado' onclick=\"mer_reporteOpcion()\" /> Presupuesto consolidado</label></td></tr>");
                    out.print("<tr><td colspan=\"2\"><label><input type='radio' id='z1' name='z' value='PlanMercadeo' onclick=\"mer_reporteOpcion()\" /> Plan de Mercadeo</label></td></tr>");
                    out.print("<tr><td colspan=\"2\"><label><input type='radio' id='z2' name='z' value='DistribucionGasto' onclick=\"mer_reporteOpcion()\" /> Distribuci&oacute;n de gasto</label></td></tr>");
                    out.print("<tr><td colspan=\"2\"><label><input type='radio' id='z3' name='z' value='PlanMercadeoDetallado' onclick=\"mer_reporteOpcion()\" /> Plan de mercadeo detallado</label></td></tr>");
                    out.print("<tr><td colspan=\"2\"><label><input type='radio' id='z4' name='z' value='PlanMercadeoCumplimiento' onclick=\"mer_reporteOpcion()\" /> Consolidado cumplimiento de ventas y presupuesto</label></td></tr>");
                    
                    out.print("<tr><td colspan=\"2\"><hr /></td></tr>");
                    out.print("<tr><td width=\"150\">No. de plan de mercadeo: </td><td><input type=\"text\" id=\"nro\" name=\"nro\" size=\"15\" onkeypress=\"_('idPM1').value=0;\" /></td></tr>");
                    
                    if(Matriz.enMatriz(matUsuarios, usuario, 0)==-1){
                        out.print("<tr><td>Usuario de creaci&oacute;n: </td><td>" + DatosDinamicos.combo(rsUsuarios, "usr1", "", "mer_reporteGetTiposPlanes(this.value)", " TODOS ", 260) + "</td></tr>");
                    }else{
                        out.print("<tr><td><input type=\"hidden\" id=\"usr1\" name=\"usr1\" value=\""+usuario+"\" /></td></tr>");
                    }
                    out.print("<tr><td width=\"150\">Tipo: </td><td id=\"axTpy1\">" + DatosDinamicos.combo(rsTipos, "tpy1", "", "mer_reporteGetPlanes()", "TODOS", 260) + "</td></tr>");
                    
                    out.print("<tr><td width=\"150\">Planes de mercadeo: </td><td id=\"axIdPM1\">" + DatosDinamicos.combo(rsPlanes, "idPM1", "", "_('nro').value='';", "TODOS", 260) + "</td></tr>");
                    
                    
                    out.print("<tr><td colspan=\"2\">");
                        out.print("<table width=\"99%\" id=\"grIdOfi\" style=\"display:none\">");
                            out.print("<tr><td width=\"148\">Sub-reporte de la oficina: </td><td id=\"axIdOfi\">" + DatosDinamicos.combo(rsPlanes, "idOficina", "", "_('nro').value='';", "TODOS", 260) + "</td></tr>");
                        out.print("</table>");
                    out.print("</td></tr>");
                    out.print("<tr><td colspan=\"2\">");
                        out.print("<table width=\"99%\" id=\"grFech\" style=\"display:none\">");
                            out.print("<tr><td width=\"148\" id=\"etFech\">Per&iacute;odo desde: </td><td><input type=\"text\" id=\"fi1\" name=\"fi1\" value=\""+Fecha.getFecha("SQL")+"\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('fi1', 'SQL');\" />"
                                + " hasta <input type=\"text\" id=\"ff1\" name=\"ff1\" value=\""+Fecha.getFecha("SQL")+"\" size=\"8\" readOnly /><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ff1', 'SQL');\" /></td></tr>");
                        out.print("</table>");
                    out.print("</td></tr>");
                    
                    out.print("<tr><td colspan=\"2\"><hr /></td></tr>");
                    out.print("<tr><td><label><input type='radio' id='x0' name='x' value='Pdf' checked /> PDF</label>");
                    out.print("</td><td><label id=\"axX1\" style=\"float:left\"><input type='radio' id='x1' name='x' value='Excel' /> EXCEL</label> "
                            + "<label id=\"axX2\" style=\"display:none;width:150px;float:right\"><input type='radio' id='x2' name='x' value='Word' /> WORD </label></td></tr>");
                    out.print("<tr><td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Imprimir\" /></td></tr>");
                    out.print("</table>");
                    out.print("</form>");
                break;
                    
                case 4:
                    ResultSet rsPlanesSegEjecucion = objBaseDatos.consulta("select distinct P.id_plan_mercadeo, P.plan_mercadeo "
                            + "from (tbl_plan_mercadeo as P with(nolock) inner join tbl_estrategia as E with(nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) "
                            + "inner join tbl_actividad as A with(nolock) on E.id_estrategia=A.id_estrategia "
                            + "where A.usuario_eje='"+usuario+"' and A.eje_finalizada_fecha is null "
                            + "order by P.plan_mercadeo");
                    out.print("obj»filtro^fun»mer_getActivicadesEjecucion()^frm»");
                    out.print("Planes de mercadeo: </td><td id=\"axIdPM1\">" + DatosDinamicos.combo(rsPlanesSegEjecucion, "idPMSegEje", "", "mer_getActivicadesEjecucion()", "Seleccione un Plan de Mercadeo", 260) );
                    out.print(" &nbsp; <input type=\"button\" value=\"Filtrar\" onclick=\"mer_getActivicadesEjecucion()\" />");
                break;
            }
        } finally {
            objBaseDatos.cerrar();
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
