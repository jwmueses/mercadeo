/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.DatosDinamicos;
import com.far.lib.Fecha;
import com.far.lib.Matriz;
import com.far.mer.pla.clas.Estrategia;
import com.far.mer.pla.clas.Laboratorio;
import com.far.mer.pla.clas.PlanMercadeo;
import com.far.mer.pla.clas.TipoPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class frmPlanMercadeo extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    /*private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;*/

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        /*this.easy_ip = config.getServletContext().getInitParameter("EASY_IP");
        this.easy_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASY_PUERTO"));
        this.easy_db = config.getServletContext().getInitParameter("EASY_DB");
        this.easy_usuario = config.getServletContext().getInitParameter("EASY_USUARIO");
        this.easy_clave = config.getServletContext().getInitParameter("EASY_CLAVE");*/
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

        String id = request.getParameter("id");
        int alto = Integer.parseInt(request.getParameter("alB"));
        //int ancho = Integer.parseInt(request.getParameter("anB"));

        alto=alto-85;

        Estrategia objEstrategia = new Estrategia(this._ip, this._puerto, this._db, this._usuario, this._clave);
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String msg = "";
        String sec_tipo_plan = "";
        String id_tipo_plan="";
        String fecha_creacion="";
        String plan_mercadeo="";
        String fecha_ini="";
        String fecha_fin="";
        int evalua_ventas = 1;
        String promedio_ventas="0";
        String fecha_ini_averificar="";
        String fecha_fin_averificar="";
        String tipo_dist_gasto="";
        String proyeccion_ventas="0";
        String premio="";
        String adjuntos="";
        String total_auspicio = "";
        
        String mecanica_tipo = "";
        String mecanica = "";
        String aplica_prom_p_v = "";
        String ope_fecha_ini = "";
        String ope_fecha_fin = "";
        //int registro_operaciones = 0;
        int aprobada_operaciones = 0;
        int aprobado_mark_vent = 0;
        int aprobado_comercial = 0;
        
        /*String motivo_rechazo = "";
        String motivo_rechazo_operacion = "";*/
        
        String tipo_alcance="";
        String tipo_alcance_de="";
        int estado = 1;
        int plan_completo = 0;
        int abierto=1;
        String estado_txt = "";
        int numAuspiciosAutorizados = 0;
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objPlanMerca.getPlanMercadeo(id);
                if(rs.next()){
                    sec_tipo_plan = rs.getString("sec_tipo_plan")!=null ? rs.getString("sec_tipo_plan") : "-1";
                    id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                    plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                    fecha_creacion = rs.getString("fecha_creacion")!=null ? rs.getString("fecha_creacion") : Fecha.getFecha("SQL");
                    tipo_alcance = rs.getString("tipo_alcance")!=null ? rs.getString("tipo_alcance") : "";
                    evalua_ventas = rs.getString("evalua_ventas")!=null ? rs.getInt("evalua_ventas") : 1;
                    tipo_alcance_de = rs.getString("tipo_alcance_de")!=null ? rs.getString("tipo_alcance_de") : "";
                    fecha_ini = rs.getString("fecha_ini")!=null ? rs.getString("fecha_ini") : "";
                    fecha_fin = rs.getString("fecha_fin")!=null ? rs.getString("fecha_fin") : "";
                    promedio_ventas = rs.getString("promedio_ventas")!=null ? rs.getString("promedio_ventas") : "0";
                    fecha_ini_averificar = rs.getString("fecha_ini_averificar")!=null ? rs.getString("fecha_ini_averificar") : "";
                    fecha_fin_averificar = rs.getString("fecha_fin_averificar")!=null ? rs.getString("fecha_fin_averificar") : "";
                    tipo_dist_gasto = rs.getString("tipo_dist_gasto")!=null ? rs.getString("tipo_dist_gasto") : "";
                    proyeccion_ventas = rs.getString("proyeccion_ventas")!=null ? rs.getString("proyeccion_ventas") : "0";
                    premio = rs.getString("premio")!=null ? rs.getString("premio") : "";
                    adjuntos = rs.getString("adjuntos")!=null ? rs.getString("adjuntos") : "";
                    total_auspicio = rs.getString("total_auspicio")!=null ? rs.getString("total_auspicio") : "";
                    
                    mecanica_tipo = rs.getString("mecanica_tipo")!=null ? rs.getString("mecanica_tipo") : "";
                    mecanica = rs.getString("mecanica")!=null ? rs.getString("mecanica") : "";
                    aplica_prom_p_v = rs.getString("aplica_prom_p_v")!=null ? rs.getString("aplica_prom_p_v") : "";
                    ope_fecha_ini = rs.getString("ope_fecha_ini")!=null ? rs.getString("ope_fecha_ini") : "";
                    ope_fecha_fin = rs.getString("ope_fecha_fin")!=null ? rs.getString("ope_fecha_fin") : "";
                    //registro_operaciones = rs.getString("registro_operaciones")!=null ? rs.getInt("registro_operaciones") : 0;
                    aprobada_operaciones = rs.getString("aprobada_operaciones")!=null ? rs.getInt("aprobada_operaciones") : 0;
                    aprobado_mark_vent = rs.getString("aprobado_mark_vent")!=null ? rs.getInt("aprobado_mark_vent") : 0;
                    aprobado_comercial = rs.getString("aprobado_comercial")!=null ? rs.getInt("aprobado_comercial") : 0;
                    /*motivo_rechazo = rs.getString("motivo_rechazo")!=null ? rs.getString("motivo_rechazo") : "";
                    motivo_rechazo_operacion = rs.getString("motivo_rechazo_operacion")!=null ? rs.getString("motivo_rechazo_operacion") : "";
                    */
                    
                    estado = rs.getString("estado")!=null ? rs.getInt("estado") : 1;
                    plan_completo = (rs.getString("plan_completo")!=null) ? rs.getInt("plan_completo") : 0;
                    abierto = (rs.getString("abierto")!=null) ? rs.getInt("abierto") : 1;
                    estado_txt = rs.getString("estado_txt")!=null ? rs.getString("estado_txt") : "";
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }
        
        boolean modificar = (estado==1 || estado==2) && plan_completo==0 ? true : false;
        String lectura = (estado==1 || estado==2) && plan_completo==0 ? "" : "readonly";
        String desactivado = (estado==1 || estado==2) && plan_completo==0 ? "" : "disabled=\"true\"";
        //String ax_evalua_ventas = evalua_ventas==1 ? "block" : "none";
        
        ResultSet rsFarmacias = objPlanMerca.consulta("select * from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id+" order by nombre");
        ResultSet rsProductosFiltro = objPlanMerca.consulta("select * from tbl_plan_mercadeo_producto_filtro with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_producto_filtro");
        ResultSet rsProductos = objPlanMerca.consulta("select * from tbl_plan_mercadeo_producto with (nolock) where id_plan_mercadeo="+id+" order by descripcion");

        String [][] tipo_alcance1 = {{"m","Mercadeo"},{"c","Convenciones"},{"i","Inauguraci&oacute;n"}};
        
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsTipos = objTipoPlan.getTiposPlanes(id_tipo_plan, usuario);
        if(id_tipo_plan.compareTo("")==0){
            try{
                if(rsTipos.next()){
                    id_tipo_plan = rsTipos.getString("id_tipo_plan")!=null ? rsTipos.getString("id_tipo_plan") : "";
                    rsTipos.beforeFirst();
                }
            }catch(Exception ex){
                msg = ex.getMessage();
            }
        }
        
        int dia_semana = Fecha.getDiaSemana();
        String fecha_promedios = Fecha.sumar(Fecha.getFecha("ISO"), Calendar.DATE, (dia_semana==2?-4:-2) );

        try {
            if(objPlanMerca.hayAuspiciosSinConfirmarUsuario(usuario) && id.compareTo("-1")==0){
            
                out.print("msg»Confirme los auspicios pendientes antes de crear un nuevo plan de mercadeo");
                
            }else{
                
                out.print("obj»vta1_html^msg»"+msg+"^foc»plan_mercadeo^fun»_('pm_edicion').style.width = (parseInt(_('pm_d_1').style.width) + parseInt(_('pm_d_3').style.width) + parseInt(_('pm_d_5').style.width) + 90) + 'px';"
                        + (modificar?"":"_('id_tipo_plan').disabled=_('tipo_alcance_de').disabled='true';")+"^frm»"+
                "<div id=\"pm_gredicion\" style=\"background-color:#EAEAEA;overflow:auto;\">"+
                    "<div id=\"pm_edicion\" class=\"tabla\">"+

                        "<div class=\"fila\">"+
                            "<div id=\"pm_d_0\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                            "<div id=\"pm_d_1\" class=\"columna marco\" style=\"height:"+alto+"px;width:530px\" onmouseover=\"_('pm_gredicion').scrollLeft=0;\" >");

                out.print("<form action=\"frmPlanMercadeoGuardar\" onsubmit=\"return mer_mercadeoGuardar(this)\" autocomplete=\"off\">");
                out.print("<input type=\"hidden\" id=\"id_plan_mercadeo\" name=\"id_plan_mercadeo\" value=\""+id+"\" />");
                out.print("<input type=\"hidden\" id=\"limFar\" name=\"limFar\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limProdFil\" name=\"limProdFil\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limProd\" name=\"limProd\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limVen\" name=\"limVen\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limCli\" name=\"limCli\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limAdj\" name=\"limAdj\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limProv\" name=\"limProv\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"limAdjAct\" name=\"limAdjAct\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"hoy\" value=\""+Fecha.getFecha("SQL")+"\" />");
                out.print("<input type=\"hidden\" id=\"fecha_promedios\" value=\""+fecha_promedios+"\" />");
                out.print("<input type=\"hidden\" id=\"fecha_creacion\" value=\""+fecha_creacion+"\" />");
                out.print("<input type=\"hidden\" id=\"estado\" name=\"estado\" value=\""+estado+"\" />");
                out.print("<input type=\"hidden\" id=\"notOpera\" name=\"notOpera\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"plan_completo\" name=\"plan_completo\" value=\""+plan_completo+"\" />");
                out.print("<input type=\"hidden\" id=\"ant_total_auspicio\" name=\"ant_total_auspicio\" value=\""+total_auspicio+"\" />");
                out.print("<input type=\"hidden\" id=\"aprobada_operaciones\" name=\"aprobada_operaciones\" value=\""+aprobada_operaciones+"\" />");
                out.print("<h3>FORMULARIO DE PLAN DE MERCADEO</h3>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td width=\"200\">No. de plan de mercadeo: <span class=\"marca\">*</span> </td>");
                out.print("<td>"+sec_tipo_plan+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td nowrap>Tipo de plan: <span class=\"marca\">*</span></td>");
                //out.print("<td>"+DatosDinamicos.combo(rsTipos, "id_tipo_plan", id_tipo_plan, "mer_getLaboratorios();mer_getUsrConfAusp();mer_verBtnAutorizacion(0);", "", 230)+"</td>");
                out.print("<td>"+DatosDinamicos.combo(rsTipos, "id_tipo_plan", id_tipo_plan, "mer_getLaboratorios();mer_verBtnAutorizacion(0);", "", 230)+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Nombre del plan de mercadeo: <span class=\"marca\">*</span> </td>");
                out.print("<td><input type=\"text\" id=\"plan_mercadeo\" name=\"plan_mercadeo\" style=\"width:220px\" "+lectura+" value=\""+plan_mercadeo+"\" onkeypress=\"mer_verBtnAutorizacion(0);\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Estado del proceso: </td>");
                out.print("<td>"+estado_txt+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Estado: <span class=\"marca\">*</span> </td>");
                out.print("<td><label><input type=\"radio\" id=\"e0\" onclick=\"mer_verBtnAutorizacion(0);\" name=\"abierto\" "+desactivado+" value=\"1\" "+((abierto==1) ? "checked" : "")+" /> Activo</label> &nbsp;&nbsp;&nbsp; " +
                        "<label><input type=\"radio\" id=\"e1\" onclick=\"mer_verBtnAutorizacion(0);\" name=\"abierto\" "+desactivado+" value=\"0\" "+((abierto==0) ? "checked" : "")+" /> Inactivo</label></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Alcance: <span class=\"marca\">*</span> </td>");
                out.print("<td><label><input type='radio' id='a0' name='tipo_alcance' "+desactivado+" onclick=\"mer_verBtnAutorizacion(0);mer_setAlcance(1);\" value='f' "+((tipo_alcance.compareTo("f")==0) ? "checked" : "")+" />Farmacias</label> &nbsp;&nbsp;&nbsp; " +
                        "<label><input type='radio' id='a1' name='tipo_alcance' "+desactivado+" onclick=\"mer_verBtnAutorizacion(0);mer_setAlcance(0);\" value='d' "+((tipo_alcance.compareTo("d")==0) ? "checked" : "")+" />Distribuci&oacute;n</label></td>");
                out.print("</tr>");
                

                // farmacias
                out.print("<tr><td colspan=\"2\" >");
                
                    out.print("<table id=\"axEvaVen\" style=\"display:"+( tipo_alcance.compareTo("f")==0 ? "block" : "none" )+"\">");
                    out.print("<tr>");
                    out.print("<td width=\"200\">Eval&uacute;a ventas: <span class=\"marca\">*</span> </td>");
                    out.print("<td><label><input type=\"radio\" id=\"ev0\" onclick=\"mer_verBtnAutorizacion(0);mer_setEvaluaVentas(1)\" name=\"evalua_ventas\" "+desactivado+" value=\"1\" "+((evalua_ventas==1) ? "checked" : "")+" /> Si</label> &nbsp;&nbsp;&nbsp; " +
                            "<label><input type=\"radio\" id=\"ev1\" onclick=\"mer_verBtnAutorizacion(0);mer_setEvaluaVentas(0)\" name=\"evalua_ventas\" "+desactivado+" value=\"0\" "+((evalua_ventas==0) ? "checked" : "")+" /> No</label></td>");
                    out.print("</tr>");
                    out.print("</table>");

                    out.print("<table id=\"alcFar\" style=\"display:"+( tipo_alcance.compareTo("f")==0 && evalua_ventas==1 ? "block" : "none" )+"\">");
                    out.print("<tr>");
                    out.print("<td width=\"200\">Tipo de alcance: <span class=\"marca\">*</span> </td>");
                    out.print("<td>"+DatosDinamicos.combo(tipo_alcance1, "tipo_alcance_de", tipo_alcance_de, "mer_setTipoAlcanceDe(this.value);mer_verBtnAutorizacion(0);", "", 226)+"</td>");
                    out.print("</tr>");

                    out.print("<tr>");
                    out.print("<td><span id=\"axFI\">"+((tipo_alcance_de.compareTo("i")==0) ? "Per&iacute;odo a evaluar" : "Fecha para calcular promedio de ventas")+" desde:</span> <span class=\"marca\">*</span> </td>");
                    out.print("<td><input type=\"text\" id=\"fecha_ini\" name=\"fecha_ini\" style=\"width:80px\" value=\""+fecha_ini+"\" readonly />");
                    if(modificar){
                        out.print("<input type=\"button\" value=\"...\" "+lectura+" onClick=\"SelectorFecha.crear('fecha_ini', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                    }
                    out.print("</td>");
                    out.print("</tr>");

                    out.print("<tr>");
                    out.print("<td><span id=\"axFF\">"+((tipo_alcance_de.compareTo("i")==0) ? "Per&iacute;odo a evaluar" : "Fecha para calcular promedio de ventas")+" hasta:</span> <span class=\"marca\">*</span> </td>");
                    out.print("<td><input type=\"text\" id=\"fecha_fin\" name=\"fecha_fin\" style=\"width:80px\" value=\""+fecha_fin+"\" readonly />");
                    if(modificar){
                        out.print("<input type=\"button\" value=\"...\" "+lectura+" onClick=\"SelectorFecha.crear('fecha_fin', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                    }
                    out.print("</td>");    
                    out.print("</tr>");
                    
                    out.print("<tr>");
                    out.print("<td colspan=\"2\">");
                    
                        out.print("<table id=\"axFechProyVentas\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">");
                        out.print("<tr>");
                        out.print("<td colspan=\"2\">");
                        out.print("Rango de fechas para evaluar la proyecci&oacute;n de ventas");  
                        out.print("</td>");   
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td width=\"196\"><span>Fecha desde:</span> <span class=\"marca\">*</span> </td>");
                        out.print("<td><input type=\"text\" id=\"fecha_ini_averificar\" name=\"fecha_ini_averificar\" style=\"width:80px\" value=\""+fecha_ini_averificar+"\" readonly />");
                        if(modificar){
                            out.print("<input type=\"button\" value=\"...\" "+lectura+" onClick=\"SelectorFecha.crear('fecha_ini_averificar', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                        }
                        out.print("</td>");
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td><span>Fecha hasta:</span> <span class=\"marca\">*</span> </td>");
                        out.print("<td><input type=\"text\" id=\"fecha_fin_averificar\" name=\"fecha_fin_averificar\" style=\"width:80px\" value=\""+fecha_fin_averificar+"\" readonly />");
                        if(modificar){
                            out.print("<input type=\"button\" value=\"...\" "+lectura+" onClick=\"SelectorFecha.crear('fecha_fin_averificar', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                        }
                        out.print("</td>");    
                        out.print("</tr>");
                        out.print("</table>");
                        
                    out.print("</td>");    
                    out.print("</tr>");
                    /*out.print("<tr>");
                    out.print("<td><span id=\"axVal\">Promedio de ventas:</span> <span class=\"marca\">*</span> </td>");
                    out.print("<td><input type=\"text\" id=\"promedio_ventas\" name=\"promedio_ventas\" style=\"width:80px\" value=\""+promedio_ventas+"\" onkeypress=\"_numero(event)\" /></td>");
                    out.print("</tr>");*/

                    out.print("<tr><td colspan=\"2\">"); 

                        //  no es de inauguracion  FILTRO DE PRODUCTOS ----------------------------------
                        out.print("<div id=\"alcProdFil\" class=\"psPanelVerde\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">");

                            out.print("<div style=\"text-align:right\"><input id=\"btnProd\" type=\"button\" value=\"Mostrar productos\" onclick=\"mer_mostrarProductos()\" />");
                            if(modificar){
                                out.print("&nbsp; <input type=\"button\" value=\"Agregar productos\" onclick=\"mer_filtarProductos()\" />");
                            }
                            out.print("</div>");

                            out.print("<div id=\"axProductos\" style=\"display:none\">");

                                out.print("<table>"); // filtro de productos
                                out.print("<tr>");
                                out.print("<th width=\"150\">CATEGORIA</th>");
                                out.print("<th width=\"150\">LABORATORIO</th>");
                                out.print("<th width=\"150\">LINEA</th>");
                                out.print("<th width=\"20\">&nbsp;</th>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblProductosFiltro\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String cod_nivel="";
                                    String desc_nivel="";
                                    String clase="";
                                    String desc_clase="";
                                    String linea="";
                                    String desc_linea="";
                                    while(rsProductosFiltro.next()){
                                        cod_nivel = rsProductosFiltro.getString("cod_nivel")!=null ? rsProductosFiltro.getString("cod_nivel") : "";
                                        desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                                        clase = rsProductosFiltro.getString("clase")!=null ? rsProductosFiltro.getString("clase") : "";
                                        desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                                        linea = rsProductosFiltro.getString("linea")!=null ? rsProductosFiltro.getString("linea") : "";
                                        desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"rPF"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"cod_nivel"+i+"\" name=\"cod_nivel"+i+"\" value=\""+cod_nivel+"\" />"
                                                + "<input type=\"text\" id=\"desc_nivel"+i+"\" name=\"desc_nivel"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_nivel+"\" /></div>"
                                        + "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"clase"+i+"\" name=\"clase"+i+"\" value=\""+clase+"\" />"
                                                + "<input type=\"text\" id=\"desc_clase"+i+"\" name=\"desc_clase"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_clase+"\" /></div>"
                                        + "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"linea"+i+"\" name=\"linea"+i+"\" value=\""+linea+"\" />"
                                                + "<input type=\"text\" id=\"desc_linea"+i+"\" name=\"desc_linea"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_linea+"\" /></div>"+
                                        "<div class=\"columna\" style=\"width:18px\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rPF"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>"); // del filtro
                                
                                
                                out.print("<table>");  // productos individuales
                                out.print("<tr>");
                                out.print("<th width=\"453\">PRODUCTO</th>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblProductos\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String codigo="";
                                    String descripcion="";
                                    while(rsProductos.next()){
                                        codigo = rsProductos.getString("codigo")!=null ? rsProductos.getString("codigo") : "";
                                        descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"rP"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:435px\"><input type=\"hidden\" id=\"codigo"+i+"\" name=\"codigo"+i+"\" value=\""+codigo+"\" />"
                                                + "<input type=\"hidden\" id=\"descripcion"+i+"\" name=\"descripcion"+i+"\" value=\""+descripcion+"\" />"+descripcion+"</div>"+
                                        "<div class=\"columna\" style=\"width:18px\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rP"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>"); // productos individuales

                            out.print("</div>");   //   axProductos

                        out.print("</div>"); 
                        //  no es de inauguracion  FILTRO DE PRODUCTOS   -----------------------------------------------------------

                        out.print("<br />"); 


                        //  no es de inauguracion  FILTRO DE FARMACIAS ----------------------------------
                        float sump_gasto = 0;
                        out.print("<div id=\"alcFarFil\" class=\"psPanelVerde\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">");

                            out.print("<div style=\"text-align:right\"><input id=\"btnFar\" type=\"button\" value=\"Mostrar farmacias\" onclick=\"mer_mostrarFarmacias()\" />");
                            if(modificar){    
                                out.print("&nbsp; <input type=\"button\" value=\"Agregar farmacias\" onclick=\"mer_filtarFarmacias()\" />");
                            }   
                            out.print("</div>");

                            out.print("<div id=\"axFarmacias\" style=\"display:none\">");

                                out.print("<table class='jm_diaMarcado' style='font-size:10px;'>");
                                out.print("<tr valign='top'>");
                                out.print("<th width=\"195\">FARMACIA</th>"
                                        + "<th width=\"70\">PROMEDIO VENTAS</th>"
                                        + "<th width=\"40\">% CRECI MIENTO</th>"
                                        + "<th width=\"70\">TOTAL PROYECTA DO</th>"
                                        + "<th width=\"70\">% DISTRIBUCION GASTO</th>"
                                        + "<th width=\"20\">&nbsp;</th>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='2'>&nbsp;</td>"
                                        + "<td><input type=\"text\" class=\"caja\" id=\"p_crecimiento\" style=\"width:40px\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_setPCcrecimiento();mer_verBtnAutorizacion(0);\" "+lectura+" /></td>"
                                        + "<td>&nbsp;</td>"
                                        + "<th><input type=\"text\" class=\"caja\" id=\"p_gasto\" style=\"width:40px\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_setPGasto();mer_verBtnAutorizacion(0);\" "+lectura+" /></td>"
                                        + "<th>&nbsp;</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblFarmacias\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String oficina="";
                                    String nombre="";
                                    String p_ventas = "0";
                                    String p_crecimiento= "0";
                                    float p_gasto= 0;
                                    String proy_ventas="0";
                                    while(rsFarmacias.next()){
                                        oficina = rsFarmacias.getString("oficina")!=null ? rsFarmacias.getString("oficina") : "";
                                        nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                                        p_ventas = rsFarmacias.getString("p_ventas")!=null ? rsFarmacias.getString("p_ventas") : "0";
                                        p_crecimiento = rsFarmacias.getString("p_crecimiento")!=null ? rsFarmacias.getString("p_crecimiento") : "0";
                                        p_gasto = rsFarmacias.getString("p_gasto")!=null ? rsFarmacias.getFloat("p_gasto") : 0;
                                        sump_gasto += p_gasto;
                                        proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"rF"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:195px\">"
                                                + "<input type=\"hidden\" id=\"oficina"+i+"\" name=\"oficina"+i+"\" value=\""+oficina+"\" />"
                                                + "<input type=\"text\" id=\"nombre"+i+"\" style=\"width:193px\" class=\"texto\" name=\"nombre"+i+"\" value=\""+nombre+"\" /></div>"+
                                        "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"p_ventas"+i+"\" name=\"p_ventas"+i+"\" style=\"width:73px\" value=\""+p_ventas+"\" readonly /></div>"+
                                        "<div class=\"columna\" style=\"width:45px;\"><input type=\"text\" class=\"caja\" onkeypress=\"_evaluar(event, '0123456789.');mer_verBtnAutorizacion(0);\" id=\"p_crecimiento"+i+"\" name=\"p_crecimiento"+i+"\" style=\"width:43px\" value=\""+p_crecimiento+"\" onkeyup=\"mer_calcularProyecciones()\" "+lectura+" /></div>"+
                                        "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"proy_ventas"+i+"\" name=\"proy_ventas"+i+"\" style=\"width:73px\" value=\""+proy_ventas+"\" readonly /></div>"
                                        + "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" onkeypress=\"_evaluar(event, '0123456789.');mer_verBtnAutorizacion(0);mer_calcularGastosPermitir(event);\" id=\"p_gasto"+i+"\" name=\"p_gasto"+i+"\" style=\"width:43px\" value=\""+p_gasto+"\" onkeyup=\"mer_calcularGastosPorcentaje()\" "+lectura+" /></div>"+
                                        "<div class=\"columna\" style=\"width:20px;\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rF"+i+"');mer_calcularVentas();mer_calcularGastos();mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>");

                                out.print("<div class=\"tabla\">");
                                out.print("<div class=\"fila jm_filaImp\" id=\"rFt\">"+
                                        "<div class=\"columna\" style=\"width:195px\">&nbsp;</div>"+
                                        "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"promedio_ventas\" name=\"promedio_ventas\" style=\"width:73px\" value=\""+promedio_ventas+"\" readonly /></div>"+
                                        "<div class=\"columna\" style=\"width:45px;\">&nbsp;</div>"+
                                        "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"proyeccion_ventas\" name=\"proyeccion_ventas\" style=\"width:73px\" value=\""+proyeccion_ventas+"\" readonly /></div>"+
                                        "<div class=\"columna\" style=\"width:65px;\"><input type=\"text\" class=\"caja\" id=\"porcentaje_gasto\" style=\"width:43px\" value=\""+Math.round(sump_gasto)+"\" readonly /></div>"+
                                        "<div class=\"columna\" style=\"width:20px\">&nbsp;</div>"+
                                        "</div>");
                                out.print("</div>");


                            out.print("</div>");   //   axFarmacias

                        //out.print("</div>"); 

                        //  no es de inauguracion  FILTRO DE FARMACIAS   -----------------------------------------------------------

                        if(modificar){
                            out.print("<p style=\"text-align:right\">");
                            out.print("<input type=\"hidden\" id=\"tipo_dist_gasto\" name=\"tipo_dist_gasto\" value=\""+tipo_dist_gasto+"\" />");
                            out.print("<input type=\"button\" value=\"Distribuci&oacute;n de gasto\" onclick=\"mer_setDistGasto();mer_verBtnAutorizacion(0);\" /> &nbsp;&nbsp;&nbsp;&nbsp; ");
                            out.print("<input type=\"button\" value=\"Calcular promedios de ventas\" onclick=\"mer_calcularVentas();mer_verBtnAutorizacion(0);\" />");
                            out.print("<div id=\"axVentas\" style=\"display:none\"></div>");
                            out.print("</p>");
                        }
                        out.print("</div>");


                        //  es de inauguracion
                        
                        
                        out.print("<div id=\"alcFarIna\" class=\"psPanelVerde\" style=\"display:"+((tipo_alcance_de.compareTo("i")==0) ? "block" : "none")+"\">");

                            out.print("<div style=\"text-align:right\"><input id=\"btnFar1\" type=\"button\" value=\"Mostrar farmacias\" onclick=\"mer_mostrarFarmacias(1)\" />");
                            if(modificar){    
                                out.print("&nbsp; <input type=\"button\" value=\"Agregar farmacias\" onclick=\"mer_filtarFarmacias(1)\" />");
                            }   
                            out.print("</div>");

                            out.print("<div id=\"axFarmacias1\" style=\"display:none\">");

                                out.print("<table>");
                                out.print("<tr>");
                                out.print("<th width=\"315\">FARMACIA</th>"
                                        + "<th width=\"80\">Proy. Ventas</th>"
                                        + "<th width=\"20\">&nbsp;</th>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblFarmacias1\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String oficina="";
                                    String nombre="";
                                    String proy_ventas="0";
                                    rsFarmacias.beforeFirst();
                                    while(rsFarmacias.next()){
                                        oficina = rsFarmacias.getString("oficina")!=null ? rsFarmacias.getString("oficina") : "";
                                        nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                                        proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"r1F"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:315px\"><input type=\"hidden\" id=\"oficina"+i+"\" name=\"oficina"+i+"\" value=\""+oficina+"\" />"+
                                        "<input type=\"text\" id=\"nombre"+i+"\" style=\"width:313px\" class=\"texto\" name=\"nombre"+i+"\" onkeypress=\"mer_verBtnAutorizacion(0);\" value=\""+nombre+"\" /></div>"+
                                        "<div class=\"columna\" style=\"width:85px;\"><input type=\"text\" class=\"caja\" id=\"proy_ventas"+i+"\" name=\"proy_ventas"+i+"\" onkeypress=\"mer_verBtnAutorizacion(0);\" style=\"width:73px\" value=\""+proy_ventas+"\" /></div>"+
                                        "<div class=\"columna\" style=\"width:20px;\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('r1F"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>");

                            out.print("</div>");   //   axFarmacias1

                        out.print("</div>"); 
                        
                        /*String oficina0="";
                        String nombre0="";
                        try{
                            if(rsFarmacias.next()){
                                oficina0 = rsFarmacias.getString("oficina")!=null ? rsFarmacias.getString("oficina") : "";
                                nombre0 = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                            }
                        }catch(Exception e){
                            msg = e.getMessage();
                        }
                        out.print("<table id=\"alcFarIna\" style=\"display:"+((tipo_alcance_de.compareTo("i")==0) ? "block" : "none")+"\">");
                        out.print("<tr>");
                        out.print("<td width=\"200\">Farmacia: <span class=\"marca\">*</span> </td>");
                        out.print("<td><div id=\"axTxtFar\"></div>");
                        out.print("<input type=\"hidden\" id=\"oficinai\" name=\"oficinai\" value=\""+oficina0+"\" />");
                        out.print("<input type=\"text\" id=\"nombrei\" name=\"nombrei\" size=\"28\" class=\"buscar\" onfocus=\"this.select();\" onkeyup=\"mer_getFarmacia();\" onkeydown=\"_NoE(event);\" title=\"farmacias\" value=\""+nombre0+"\" "+lectura+" />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");*/


                    out.print("</td></tr>");

                    out.print("</table>");  // tabla farmacias

                out.print("</td></tr>");   //   fin farmacias





                // distribucion
                out.print("<tr><td colspan=\"2\">");

                    out.print("<table id=\"alcDis\" style=\"display:"+( tipo_alcance.compareTo("d")==0 ? "block" : "none" )+"\" >");
                    out.print("<tr>");
                    out.print("<td valign=\"top\" width=\"200\">Consulta de facturas: <span class=\"marca\">*</span> </td>");
                    out.print("<td nowrap><span>desde: <input type=\"text\" id=\"dfecha_ini\" name=\"dfecha_ini\" style=\"width:80px\" value=\""+fecha_ini+"\" readonly />");
                    if(modificar){
                        out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('dfecha_ini', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                    }    
                    out.print("</span><span><br />hasta: &nbsp;</sapan><input type=\"text\" id=\"dfecha_fin\" name=\"dfecha_fin\" style=\"width:80px\" value=\""+fecha_fin+"\" readonly />");
                    if(modificar){
                        out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('dfecha_fin', 'SQL', 'mer_verBtnAutorizacion(0);');\" />");
                    }   
                    out.print("</span></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign=\"top\">Valor a comparar la sumatoria de las facturas: <span class=\"marca\">*</span> </td>");
                    out.print("<td><input type=\"text\" id=\"dpromedio_ventas\" name=\"dpromedio_ventas\" style=\"width:220px\" value=\""+promedio_ventas+"\" onkeypress\"_numero(event);mer_verBtnAutorizacion(0);\" "+lectura+" /></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign=\"top\">% o premio a entregarse: <span class=\"marca\">*</span> </td>");
                    out.print("<td><input type=\"text\" id=\"premio\" name=\"premio\" style=\"width:220px\" value=\""+premio+"\" onkeypress=\"mer_verBtnAutorizacion(0);\" "+lectura+" /></td>");
                    out.print("</tr>");

                    /*  vendedores */
                    out.print("<tr><td colspan=\"2\">");

                        out.print("<div class=\"psPanelVerde\">");
                            if(modificar){
                                out.print("<div id=\"axTxtVen\" class=\"h31\">VENDEDORES: &nbsp; ");
                                out.print("<input type=\"text\" id=\"txtVen\" size=\"46\" class=\"buscar\" onfocus=\"this.value='';\" onkeyup=\"mer_getVendedores();\" onkeydown=\"_NoE(event);\" title=\"Busqueda por nombre\" />");
                                out.print("</div>");
                            }
                            out.print("<table>");
                            out.print("<tr>");
                            out.print("<th width=\"453\">VENDEDOR</th>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<div id=\"axVen\" class=\"tabla borde\">");
                            try{
                                int i=0;
                                String codigo_vendedor = "";
                                String nombre_vendedor = "";
                                ResultSet rs = objPlanMerca.getVendedores(id);
                                while(rs.next()){
                                    codigo_vendedor = rs.getString("codigo_vendedor")!=null ? rs.getString("codigo_vendedor") : "";
                                    nombre_vendedor = rs.getString("nombre_vendedor")!=null ? rs.getString("nombre_vendedor") : "";
                                    out.print("<div class=\"jm_filaImp\" id=\"rV"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<div class=\"columna\" style=\"width:435px\"><input type=\"hidden\" id=\"codigo_vendedor"+i+"\" name=\"codigo_vendedor"+i+"\" value=\""+codigo_vendedor+"\" />"+
                                    "<input type=\"hidden\" id=\"nombre_vendedor"+i+"\" name=\"nombre_vendedor"+i+"\" value=\""+nombre_vendedor+"\" />"+nombre_vendedor+"</div>"+
                                    "<div class=\"columna\" style=\"width:25px\">");
                                    if(modificar){
                                        out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rV"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                    }  
                                    out.print("</div>"
                                    + "</div>");
                                    i++;
                                }
                                rs.close();
                            }catch(Exception ex){
                                msg = ex.getMessage();
                            }
                            out.print("</div>");

                        out.print("</div>"); /* fin lista vendedores */

                        out.print("<br />");
                        
                    out.print("</td></tr>");/* fin vendedores */

                    
                    /*   productos en distribucion   */
                    out.print("<tr><td colspan=\"2\">");
                    //  distribucion  FILTRO DE PRODUCTOS ----------------------------------
                        out.print("<div id=\"alcProdFil1\" class=\"psPanelVerde\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">");

                            out.print("<div style=\"text-align:right\"><input id=\"btnProd1\" type=\"button\" value=\"Mostrar productos\" onclick=\"mer_mostrarProductos(1)\" />");
                            if(modificar){
                                out.print("&nbsp; <input type=\"button\" value=\"Agregar productos\" onclick=\"mer_filtarProductos(1)\" />");
                            }
                            out.print("</div>");

                            out.print("<div id=\"axProductos1\" style=\"display:none\">");

                                out.print("<table>"); // filtro de productos
                                out.print("<tr>");
                                out.print("<th width=\"150\">CATEGORIA</th>");
                                out.print("<th width=\"150\">LABORATORIO</th>");
                                out.print("<th width=\"150\">LINEA</th>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblProductosFiltro1\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String cod_nivel="";
                                    String desc_nivel="";
                                    String clase="";
                                    String desc_clase="";
                                    String linea="";
                                    String desc_linea="";
                                    while(rsProductosFiltro.next()){
                                        cod_nivel = rsProductosFiltro.getString("cod_nivel")!=null ? rsProductosFiltro.getString("cod_nivel") : "";
                                        desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                                        clase = rsProductosFiltro.getString("clase")!=null ? rsProductosFiltro.getString("clase") : "";
                                        desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                                        linea = rsProductosFiltro.getString("linea")!=null ? rsProductosFiltro.getString("linea") : "";
                                        desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"r1PF"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"cod_nivel"+i+"\" name=\"cod_nivel"+i+"\" value=\""+cod_nivel+"\" />"
                                                + "<input type=\"text\" id=\"desc_nivel"+i+"\" name=\"desc_nivel"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_nivel+"\" /></div>"
                                        + "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"clase"+i+"\" name=\"clase"+i+"\" value=\""+clase+"\" />"
                                                + "<input type=\"text\" id=\"desc_clase"+i+"\" name=\"desc_clase"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_clase+"\" /></div>"
                                        + "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"linea"+i+"\" name=\"linea"+i+"\" value=\""+linea+"\" />"
                                                + "<input type=\"text\" id=\"desc_linea"+i+"\" name=\"desc_linea"+i+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc_linea+"\" /></div>"+
                                        "<div class=\"columna\" style=\"width:18px\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('r1PF"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>"); // del filtro
                                
                                
                                
                                out.print("<table>");
                                out.print("<tr>");
                                out.print("<th width=\"453\">PRODUCTO</th>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div id=\"axTblProductos1\" class=\"tabla\">");
                                try{
                                    int i=0;
                                    String codigo="";
                                    String descripcion="";
                                    rsProductos.beforeFirst();
                                    while(rsProductos.next()){
                                        codigo = rsProductos.getString("codigo")!=null ? rsProductos.getString("codigo") : "";
                                        descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                                        out.print("<div class=\"fila jm_filaImp\" id=\"r1P"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                        "<div class=\"columna\" style=\"width:435px\"><input type=\"hidden\" id=\"codigo"+i+"\" name=\"codigo"+i+"\" value=\""+codigo+"\" />"
                                                + "<input type=\"hidden\" id=\"descripcion"+i+"\" name=\"descripcion"+i+"\" value=\""+descripcion+"\" />"+descripcion+"</div>"+
                                        "<div class=\"columna\" style=\"width:18px\">");
                                        if(modificar){
                                            out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('r1P"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                        }
                                        out.print("</div>"+
                                        "</div>");
                                        i++;
                                    }
                                }catch(Exception e){
                                    msg = e.getMessage();
                                }
                                out.print("</div>");

                            out.print("</div>");   //   axProductos

                        out.print("</div>"); 
                        //  distribucion  FILTRO DE PRODUCTOS   -----------------------------------------------------------

                        out.print("<br />"); 
                    out.print("</td></tr>");/* fin productos distribucion */
                    
                    
                    
                    /*  clientes  */
                    out.print("<tr><td colspan=\"2\">");

                        out.print("<div class=\"psPanelVerde\">");
                            if(modificar){
                                out.print("<div id=\"axTxtCli\" class=\"h31\">CLIENTES: &nbsp; ");
                                out.print("<input type=\"text\" id=\"txtCli\" size=\"46\" class=\"buscar\" onfocus=\"this.value='';\" onkeyup=\"mer_getClientes();\" onkeydown=\"_NoE(event);\" title=\"Busqueda por nombre comercial\" />");
                                out.print("</div>");
                            }
                            out.print("<table>");
                            out.print("<tr>");
                            out.print("<th width=\"453\">CLIENTE</th>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<div id=\"axCli\" class=\"tabla borde\">");
                            try{
                                int i=0;
                                String numero_idcliente = "";
                                String nombre_comercial = "";
                                ResultSet rs = objPlanMerca.getClientes(id);
                                while(rs.next()){
                                    numero_idcliente = rs.getString("numero_idcliente")!=null ? rs.getString("numero_idcliente") : "";
                                    nombre_comercial = rs.getString("nombre_comercial")!=null ? rs.getString("nombre_comercial") : "";
                                    out.print("<div class=\"jm_filaImp\" id=\"rC"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<div class=\"columna\" style=\"width:435px\"><input type=\"hidden\" id=\"numero_idcliente"+i+"\" name=\"numero_idcliente"+i+"\" value=\""+numero_idcliente+"\" />"+
                                    "<input type=\"hidden\" id=\"nombre_comercial"+i+"\" name=\"nombre_comercial"+i+"\" value=\""+nombre_comercial+"\" />"+nombre_comercial+"</div>"+
                                    "<div class=\"columna\" style=\"width:25px\">");
                                    if(modificar){
                                        out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rC"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                    }
                                    out.print("</div>"
                                    + "</div>");
                                    i++;
                                }
                                rs.close();
                            }catch(Exception ex){
                                msg = ex.getMessage();
                            }
                            out.print("</div>");

                        out.print("</div>"); /* fin lista clientes */

                    out.print("</td></tr>");/* fin clientes */

                    /*  adjuntos  */
                    out.print("<tr><td colspan=\"2\">");
                        out.print("<div>");
                            if(modificar){
                                out.print("<div style=\"text-align:right\"><input type=\"button\" value=\"Adjuntar archivo\" onclick=\"adjuntarArchivo('ProcesaTransferirAdjunto')\" /></div>");
                            }
                            out.print("<div id=\"axAadjuntos\" class=\"tabla\">"); /* adjuntos */
                            if(adjuntos.compareTo("")!=0){
                                String vecAdj[] = adjuntos.split("|");
                                for(int i=0; i<vecAdj.length; i++){
                                    out.print("<span id=\"axAdjunto"+i+"\" class=\"psPanelNaranja axAdjunto\">");
                                    out.print("<input style=\"width:190px;float:left\" id=\"adjunto"+i+"\" name=\"adjunto"+i+"\" class=\"cajaNaranja\" readonly type=\"text\" value=\""+vecAdj[i]+"\" title=\""+vecAdj[i]+"\" />");
                                    if(modificar){
                                        out.print("<div class=\"borrar\" style=\"float:right;margin-top:4px;\" title=\"Eliminar\" onclick=\"_R('axAdjunto"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div>");
                                    }
                                    out.print("</span>");
                                }
                            }
                            out.print("</div>");   /* adjuntos */

                        out.print("</div>");
                    out.print("</td></tr>");/* fin adjuntos */

                    out.print("</table>"); /*  tabla de distribucion  */


                out.print("</td></tr>");



                out.print("</table>");



                /*    AUSPICIOS      */
                
                
                    out.print("<h3 class=\"borde psPanelAzul\" style=\"color:#000\">AUSPICIOS</h3>");

                    out.print("<div id=\"axLabsAus\">");
                        out.print("<table cellpadding=\"0\" cellspacing=\"0\">");
                        Laboratorio objLaboratorio = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);
                        try{
                            ResultSet rsLabs = objLaboratorio.getLaboratoriosAsignables(id, id_tipo_plan);

                            ResultSet rsAus = objPlanMerca.getAuspicios(id);
                            String laboratorios[][] = Matriz.ResultSetAMatriz(rsAus);
                            String id_laboratorio = "";
                            String nombre_comercial = "";
                            float presupuesto = 0;
                            float monto = 0;
                            float saldo = 0;
                            int i=0;
                            int pos=0;
                            out.print("<tr><th width=\"300\">LABORATORIO ESTRAT&Eacute;GICO</th><th>PRE. DIS.</th><th>MONTO</th><th>SALDO</th></tr>");
                            while(rsLabs.next()){
                                id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                                nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                                //presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                                saldo = rsLabs.getString("saldo")!=null ? rsLabs.getFloat("saldo") : 0;
                                pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                                monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                                presupuesto = saldo + monto;
                                //presupuesto += monto;
                                if(modificar){
                                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                                    out.print("<td width=\"300\"><input type=\"hidden\" id=\"idLab"+i+"\" name=\"idLab"+i+"\" value=\""+id_laboratorio+"\" />"+nombre_comercial+"</td>"
                                            + "<td><input type=\"hidden\" id=\"presupuesto"+i+"\" name=\"presupuesto"+i+"\" value=\""+presupuesto+"\" />"+presupuesto+"</td>");
                                    out.print("<td><input type=\"text\" class=\"caja\" id=\"monto"+i+"\" name=\"monto"+i+"\" size=\"6\" value=\""+monto+"\" onkeypress=\"_numero(event);\" "
                                            + "onkeyup=\"_('saldo"+i+"').value=parseFloat(_('presupuesto"+i+"').value)-parseFloat(this.value);mer_verBtnAutorizacion(0);mer_sumAuspicios();\" "+lectura+" /></td>");
                                    out.print("<td><input type=\"text\" class=\"caja\" id=\"saldo"+i+"\" size=\"6\" value=\""+saldo+"\" readonly /></td>");
                                    out.print("</tr>");
                                }else{
                                    if(monto>0){
                                        out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                                        out.print("<td width=\"300\"><input type=\"hidden\" id=\"idLab"+i+"\" name=\"idLab"+i+"\" value=\""+id_laboratorio+"\" />"+nombre_comercial+"</td>"
                                                + "<td>"+presupuesto+"</td>");
                                        out.print("<td> &nbsp;&nbsp; "+monto+"</td>");
                                        out.print("<td> &nbsp;&nbsp; "+saldo+"</td>");
                                        out.print("</tr>");
                                    }
                                }
                                i++;
                            }
                        }catch(Exception ex){
                            msg = ex.getMessage();
                        }finally{
                            objLaboratorio.cerrar();
                        }
                        out.print("</table>");
                    out.print("</div>");   /* axLabsAus  */

                    
                    
                    /*     LABORATORIOS NO ESTRATEGICOS     */
                    

                    if(modificar){
                        out.print("<div>");
                            out.print("<hr />");
                            out.print("<div id=\"axTxt\" style=\"float:left;height:30px;padding-top:3px\">Buscar el Formulario: &nbsp; </div>");
                            out.print("<div class=\"boton fondoIcono\" style=\"background-position:-1020px 0px;float:left;width:20px !important;height:19px;cursor:pointer;\" onclick=\"mer_getAuspicios()\" ></div>");
                        out.print("</div>");
                        out.print("<div style=\"clear:both;\"></div>");
                    }
                    out.print("<table>");
                        out.print("<tr>");
                        out.print("<th width=\"250\">LABORATORIO NO ESTRAT&Eacute;GICO</th><th width=\"84\">MONTO</th><th width=\"84\"># FORM.</th><th width=\"25\">&nbsp;</th><th width=\"20\">&nbsp;</th>");
                        out.print("</tr>");
                    out.print("</table>");
                    out.print("<div id=\"axProv\" class=\"tabla\" style=\"width:480px\">");
                    int i=0;
                    //float monto_total_no_est = 0;
                    try{

                        String numero_idproveedor = "";
                        String nombre_comercial = "";
                        String monto = "";
                        String fecha_registro = "";
                        String num_formulario = "";
                        boolean confirmado = false;
                        int eliminado = 0;
                        ResultSet rsProv = objPlanMerca.getProveedores(id);
                        while(rsProv.next()){
                            numero_idproveedor = rsProv.getString("numero_idproveedor")!=null ? rsProv.getString("numero_idproveedor") : "";
                            nombre_comercial = rsProv.getString("nombre_comercial")!=null ? rsProv.getString("nombre_comercial") : "";
                            num_formulario = rsProv.getString("num_formulario")!=null ? rsProv.getString("num_formulario") : "";
                            fecha_registro = rsProv.getString("fecha_registro")!=null ? rsProv.getString("fecha_registro") : "";
                            confirmado = rsProv.getString("confirmado")!=null ? rsProv.getBoolean("confirmado") : false;
                            eliminado = rsProv.getString("eliminado")!=null ? rsProv.getInt("eliminado") : 0;
                            monto = rsProv.getString("monto")!=null ? rsProv.getString("monto") : "0";
                            //if(eliminado==0){
                                //total_auspicio += Float.parseFloat(monto);
                            //}
                            out.print("<div id=\"fPrv"+i+"\" "+(eliminado==1 ? "class=\"fila jm_filaEli\"" : "class=\"fila jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\"")+" >"+
                            "<div class=\"columna\" style=\"width:250px\"><input type=\"hidden\" id=\"ruc"+i+"\" name=\"ruc"+i+"\" value=\""+numero_idproveedor+"\" />"
                            + "<input type=\"hidden\" id=\"fechReg"+i+"\" name=\"fechReg"+i+"\" value=\""+fecha_registro+"\" />"
                            + "<input type=\"hidden\" id=\"nomCom"+i+"\" name=\"nomCom"+i+"\" value=\""+nombre_comercial+"\" />"
                            + "<input type=\"text\" class=\"caja\" style=\"width:248px;\" value=\""+nombre_comercial+"\" readonly /></div>");
                            if(modificar && eliminado==0){
                                out.print("<div class=\"columna\" style=\"width:84px\"><input type=\"text\" class=\"caja\" onkeypress=\"_numero(event)\" id=\"montop"+i+"\" name=\"montop"+i+"\" style=\"width:80px;\" value=\""+monto+"\" readonly /></div>"+
                                "<div class=\"columna\" style=\"width:84px\"><input type=\"text\" class=\"texto\" id=\"numForm"+i+"\" name=\"numForm"+i+"\" style=\"width:80px\" value=\""+num_formulario+"\" readonly /></div>");
                            }else{
                                out.print("<div class=\"columna\" style=\"width:84px;text-align:right;height:14px\">"+monto+" &nbsp;&nbsp;</div>"+
                                "<div class=\"columna\" style=\"width:84px;padding-right:10px;height:14px\">"+num_formulario+" &nbsp;</div>");
                            }
                            
                            out.print("<div class=\"columna\" style=\"width:25px\">");
                                if(eliminado==0){
                                    if(!confirmado){
                                        out.print("<div id=\"confr"+i+"\" class=\"cancelar\" title=\"Confirmar\" onclick=\"mer_confirmarGasto("+i+", "+monto+", '"+num_formulario+"');\">&nbsp;</div>");
                                    }else{
                                        out.print("<div class=\"aprobado\" title=\"Confirmado\" >&nbsp;</div>");
                                        numAuspiciosAutorizados++;
                                    }
                                }else{
                                    out.print("<div class=\"columna\">&nbsp;</div>");
                                }
                            out.print("</div>");
                            
                            if(modificar && eliminado==0 && !confirmado){
                                out.print("<div class=\"columna\" style=\"width:20px\"><div class=\"borrar\" title=\"Eliminar\" id=\"elAusNoEst"+i+"\" onclick=\"_R('fPrv"+i+"');mer_verBtnAutorizacion(0);\">&nbsp;</div></div>");
                            }else{
                                out.print("<div class=\"columna\" style=\"width:20px;height:14px\">&nbsp;</div>");
                            }
                            out.print("</div>");
                            
                            i++;
                        }
                        rsProv.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</div>");
                    
                    out.print("<p style=\"text-align:center\">Total: <input type=\"tex\" class=\"caja\" id=\"total_auspicio\" name=\"total_auspicio\" value=\""+total_auspicio+"\" /> &nbsp;&nbsp;&nbsp;&nbsp; </p>");

                    out.print("<br />");

                    /* tiempo para confirmacion de auspicios  */
                    /*out.print("<div id=\"axConfAus\" class=\"tabla borde psPanelVerde\" style=\"width:480px;display:"+(i>0?"block":"none")+"\">");
                    out.print("<h4 class=\"psPanelVerde\" style=\"color:#000\">NUMERO DE DIAS PARA CONFIRMAR AUSPICIOS</h4>");
                    out.print("<table><tr>");
                    out.print("<th style=\"width:378px\">USUARIO</th>");
                    out.print("<th style=\"width:77px\"># DIAS</th>");
                    out.print("</tr></table>");
                    try{
                        ResultSet rsUsCoAu = objPlanMerca.getUsuariosConfAusp(id);
                        String tiempos[][] = Matriz.ResultSetAMatriz(rsUsCoAu);

                        int j=0;
                        String id_tipo_plan_usuario = "";
                        String usuarioConf = "";
                        String num_dias = "";
                        int pos=-1;
                        ResultSet rsUsTipoPlan = objPlanMerca.getUsuariosTipoPlan(id_tipo_plan);
                        while(rsUsTipoPlan.next()){
                            id_tipo_plan_usuario = rsUsTipoPlan.getString("id_tipo_plan_usuario")!=null ? rsUsTipoPlan.getString("id_tipo_plan_usuario") : "";
                            usuarioConf = rsUsTipoPlan.getString("usuario")!=null ? rsUsTipoPlan.getString("usuario") : "";
                            pos = Matriz.enMatriz(tiempos, id_tipo_plan_usuario, 0);
                            num_dias = pos!=-1?tiempos[pos][1]:"";
                            //num_dias = rsUsCoAu.getString("num_dias")!=null ? rsUsCoAu.getString("num_dias") : "";
                            out.print("<div class=\"fila jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                            "<div class=\"columna\" style=\"width:378px\">"+usuarioConf+"</div>"+
                            "<div class=\"columna\" style=\"width:77px\">"+num_dias+"</div>"+
                            "</div>");
                            j++;
                        }
                        rsUsCoAu.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</div>");*/

                    
                    
            /*  MECANICA    */   
                    
                    
            out.print("<h3 class=\"borde psPanelAzul\" style=\"color:#000\">MECANICA</h3>");
                    
            out.print("<table>");
            out.print("<tr>");
            out.print("<td>Tipo de mec&aacute;nica: <span class=\"marca\">*</span> </td>");
            out.print("<td><label><input type=\"radio\" id=\"mt0\" name=\"mecanica_tipo\" value=\"t\" "+((estado==1 || estado==2) && plan_completo==0 && aprobada_operaciones==0 ? "" : "disabled=\"true\"")+" onclick=\"mer_verBtnAutorizacion(0);_('axMecText').style.display='block';_('axMecOper').style.display='none';\" "+((mecanica_tipo.compareTo("t")==0) ? "checked" : "")+" /> Texto</label> &nbsp;&nbsp;&nbsp; " +
                    "<label><input type=\"radio\" id=\"mt1\" name=\"mecanica_tipo\" value=\"o\" "+((estado==1 || estado==2) && plan_completo==0 && aprobada_operaciones==0 ? "" : "disabled=\"true\"")+" onclick=\"mer_verBtnAutorizacion(0);_('axMecText').style.display='none';_('axMecOper').style.display='block';\" "+((mecanica_tipo.compareTo("o")==0) ? "checked" : "")+" /> Operaciones</label></td>");
            out.print("</tr>");
            
            //  mecanica de texto
            out.print("<tr><td colspan=\"2\" >");
                out.print("<table id=\"axMecText\" style=\"display:"+((mecanica_tipo.compareTo("t")==0) ? "block" : "none")+"\">");
                out.print("<tr valign=\"top\">");
                    out.print("<td width=\"185\">Mec&aacute;nica: <span class=\"marca\">*</span></td>");
                    out.print("<td><textarea id=\"mecanica\" name=\"mecanica\" style=\"width:295px;height:100px\" onkeypress=\"mer_verBtnAutorizacion(0);\" "+lectura+" >"+mecanica+"</textarea></td>");
                out.print("</tr>");
                out.print("</table>");
            out.print("</td></tr>");
            
            //  mecanica de operaciones
            out.print("<tr><td colspan=\"2\">");
            
                out.print("<table id=\"axMecOper\" style=\"display:"+((mecanica_tipo.compareTo("o")==0) ? "block" : "none")+"\">");
                out.print("<tr>");
                out.print("<td width=\"185\">Aplica para: <span class=\"marca\">*</span> </td>");
                out.print("<td><label onclick=\"_('notOpera').value=1;mer_verBtnAutorizacion(0);\"><input type=\"radio\" id=\"ap0\" name=\"aplica_prom_p_v\" "+((estado==1 || estado==2) && plan_completo==0 && aprobada_operaciones==0 ? "" : "disabled=\"true\"")+" value=\"p\" "+((aplica_prom_p_v.compareTo("p")==0) ? "checked" : "")+" /> Promociones</label> &nbsp;&nbsp;&nbsp; " +
                        "<label onclick=\"_('notOpera').value=1;mer_verBtnAutorizacion(0);\"><input type=\"radio\" id=\"ap1\" name=\"aplica_prom_p_v\" "+((estado==1 || estado==2) && plan_completo==0 && aprobada_operaciones==0 ? "" : "disabled=\"true\"")+" value=\"c\" "+((aplica_prom_p_v.compareTo("c")==0) ? "checked" : "")+" /> Convenios</label></td>");
                out.print("</tr>");
                
                if(modificar && aprobada_operaciones==0){
                    out.print("<tr><td>&nbsp;</td>");
                    out.print("<td><input type=\"button\" value='adjuntar formulario y/o archivo' onClick=\"_('notOpera').value=1;adjuntarArchivo('ProcesaTransferirFormularioAdjunto');\" /></td>");
                    out.print("</tr>");
                }
                
                out.print("<tr><td colspan=\"2\" class=\"psPanelVerde\">");
                    out.print("<table>");  //  de adjuntos
                        out.print("<tr>");
                        out.print("<th width=\"100\">ARCHIVO</th><th width=\"300\">DESCRIPCI&Oacute;N</th><th width=\"25\">&nbsp;</th>");
                        out.print("</tr>");
                    out.print("</table>");
                    out.print("<div id=\"axFrmAdj\" class=\"tabla borde\" style=\"width:480px\">");
                    int j=0;
                    try{
                        String archivo = "";
                        String descripcion = "";
                        ResultSet rsAdjMecanica = objPlanMerca.getAdjuntos(id);
                        while(rsAdjMecanica.next()){
                            archivo = rsAdjMecanica.getString("archivo")!=null ? rsAdjMecanica.getString("archivo") : "";
                            descripcion = rsAdjMecanica.getString("descripcion")!=null ? rsAdjMecanica.getString("descripcion") : "";
                            out.print("<div class=\"fila\" id=\"rFrAd"+j+"\">"+
                            "<div class=\"columna\" style=\"width:150px;\"><input type=\"text\" class=\"texto\" id=\"archivo"+j+"\" name=\"archivo"+j+"\" style=\"width:140px;border:0px\" value=\""+archivo+"\" readonly /></div>"+
                            "<div class=\"columna\" style=\"width:300px\"><input type=\"text\" class=\"texto\" id=\"descripcion_arch"+j+"\" name=\"descripcion_arch"+j+"\" style=\"width:290px\" onkeyup=\"_('notOpera').value=1;mer_verBtnAutorizacion(0);\" value=\""+descripcion+"\" "+((estado==1 || estado==2) && plan_completo==0 && aprobada_operaciones==0 ? "" : "readonly")+" /></div>"+
                            "<div class=\"columna\" style=\"width:25px\">");
                            if(modificar && aprobada_operaciones==0){
                                out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rFrAd"+j+"');_('notOpera').value=1;mer_verBtnAutorizacion(0);\" >&nbsp;</div>");
                            }
                            out.print("</div></div>");
                            j++;
                        }
                        rsAdjMecanica.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</div>");

                
                out.print("</td></tr>");
                out.print("<tr>");
                out.print("<td><span id=\"axopFI\">Fecha de inicio:</span> <span class=\"marca\">*</span> </td>");
                out.print("<td><input type=\"text\" id=\"ope_fecha_ini\" name=\"ope_fecha_ini\" style=\"width:80px\" value=\""+ope_fecha_ini+"\" readonly />");
                if(modificar && aprobada_operaciones==0){    
                    out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ope_fecha_ini', 'SQL', 'mer_verBtnAutorizacion(0);');_('notOpera').value=1;\" />");
                } 
                out.print("</td></tr>");
                out.print("<tr>");
                out.print("<td><span id=\"axopFF\">Fecha de t&eacute;rmino:</span> <span class=\"marca\">*</span> </td>");
                out.print("<td><input type=\"text\" id=\"ope_fecha_fin\" name=\"ope_fecha_fin\" style=\"width:80px\" value=\""+ope_fecha_fin+"\" readonly />");
                if(modificar && aprobada_operaciones==0){
                    out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ope_fecha_fin', 'SQL', 'mer_verBtnAutorizacion(0);');_('notOpera').value=1;\" />");
                }
                out.print("</td></tr>");
                out.print("</table>");  //   de adjuntos
                
                out.print("</td></tr>"); 
                out.print("</table>");   //  de mecanica
                    
                    
                String verBtn = (id.compareTo("-1")!=0 && modificar && objPlanMerca.existenActividades(id)) ? "visible" : "hidden";
                if(mecanica_tipo.compareTo("o")==0){
                    verBtn = (aprobada_operaciones==1 && plan_completo==0) ? "visible" : "hidden";
                }
                out.print("<table width=\"100%\"><tr>");
                out.print("<td><input type=\"hidden\" id=\"btnAutVer\" value=\""+verBtn+"\" />"
                        + "<input id=\"btnAut\" style=\"visibility:"+verBtn+"\" type=\"button\" value=\"Enviar Plan a autorizaciones\" onclick=\"mer_mercadeoEnviarAAut("+id+")\" /></td>");
                if(modificar){
                    out.print("<td align=\"center\"><input id=\"btnGrab\" type=\"submit\" value=\"Guardar Plan\" onclick=\"mer_verBtnAutorizacion(1);\" /> &nbsp; </td>");
                }
                /* 1=creados		2=rechazado marketing	3=aprobados operaciones		4=autorizacion marketing	
                5=autorizacion ventas		6=autorizacion comercial	7=rechazado ventas	8=rechazado ventas
                9=terminados  10=Anulados*/
                // 10=anulado se anula solo si esta en estado creado
                if(id.compareTo("-1")!=0 && (estado==1 || estado==10) && numAuspiciosAutorizados==0 && plan_completo==0){  
                    out.print("<td align=\"right\"><input id=\"btnAnular\" type=\"button\" value=\"Anular Plan\" onclick=\"mer_mercadeoAnular();\" /> &nbsp; </td>");
                }
                //out.print("</div>");

                out.print("</tr></table>");
                
                out.print("</td></tr>");

                out.print("<br />");


                if(id.compareTo("-1")!=0){
                    out.print("<h3>ESTRATEGIAS</h3>");
                    if(modificar){
                        out.print("<div><input type=\"button\" value=\"Nueva estrategia\" onclick=\"mer_estrategiaEditar(-1)\" /></div>");
                    }
                    out.print("<div id=\"axEstrategias\" class=\"borde psPanelVerde\">");
                    try{
                        ResultSet rsEstrategias = objEstrategia.getEstrategias(id);
                        String id_estrategia = "";
                        String estrategia = "";
                        String num_actividades = "";
                        float pre_totales = 0;
                        float sumatoria = 0;
                        out.print("<table>");
                        out.print("<tr><th width=\"300\">ESTRATEGIA</th><th width=\"50\"># ACT.</th><th width=\"70\">TOTALES</th><th width=\"25\">&nbsp;</th></tr>");
                        while(rsEstrategias.next()){
                            id_estrategia = rsEstrategias.getString("id_estrategia")!=null ? rsEstrategias.getString("id_estrategia") : "";
                            estrategia = rsEstrategias.getString("estrategia")!=null ? rsEstrategias.getString("estrategia") : "";
                            num_actividades = rsEstrategias.getString("num_actividades")!=null ? rsEstrategias.getString("num_actividades") : "";
                            pre_totales = rsEstrategias.getString("pre_totales")!=null ? rsEstrategias.getFloat("pre_totales") : 0;
                            out.print("<tr style=\"cursor:pointer\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                            out.print("<td style=\"cursor:pointer\" onclick=\"mer_estrategiaEditar("+id_estrategia+")\"><input type=\"hidden\" id=\"id_estrategia"+i+"\" name=\"id_estrategia"+i+"\" value=\""+id_estrategia+"\" />"+estrategia+"</td>");
                            out.print("<td style=\"cursor:pointer\" align=\"right\" onclick=\"mer_estrategiaEditar("+id_estrategia+")\">"+num_actividades+"</td>");
                            out.print("<td style=\"cursor:pointer\" align=\"right\" onclick=\"mer_estrategiaEditar("+id_estrategia+")\">"+pre_totales+"</td>");
                            out.print("<td align=\"right\">");
                            if(modificar){
                                out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"mer_estrategiaEliminar("+id_estrategia+");\">&nbsp;</div>");
                            }
                            out.print("</td></tr>");
                            sumatoria += pre_totales;
                            i++;
                        }
                        out.print("<tr><td colspan=\"3\" align=\"right\"><input type=\"hidden\" id=\"sum_estgs\" value=\""+sumatoria+"\" />"+
                                sumatoria+"</th><th>&nbsp;</th></tr>");
                        out.print("</table>");
                    }catch(Exception e){
                        msg = e.getMessage();
                    }
                    out.print("</div>");
                }
                out.print("</form>");
                out.print("<br />");

                
                /*if(mecanica_tipo.compareTo("o")==0){
                    out.print("<table width=\"100%\">");
                    if(aprobada_operaciones==0){
                        out.print("<tr>");
                        out.print("<td colspan=\"2\"><br />Motivo de aprobaci&oacute;n o rechazo:</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan=\"2\"><textarea id=\"motivo\" name=\"motivo\" cols=\"62\">"+motivo_rechazo_operacion+"</textarea></td>");
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td><input type=\"button\" id=\"btnApr0\" value=\"Rechazar\" onclick=\"mer_operacionesAprobar("+id+", 0)\" /></td>");
                        out.print("<td align=\"right\"><input type=\"button\" id=\"btnApr1\" value=\"Aprobar\" onclick=\"mer_operacionesAprobar("+id+", 1)\" /></td>");
                        out.print("</tr>");
                    }else{
                        out.print("<tr><td colspan=\"2\"><p>Motivo de "+(aprobada_operaciones==1?"aprobaci&oacute;n":"rechazo")+" de operaciones: "+motivo_rechazo_operacion+"</p></td></tr>");
                    }
                    out.print("</table>");
                }
                
                if(motivo_rechazo.compareTo("")!=0 && aprobado_mark_vent){
                    out.print("<p>Motivo de "+(aprobada_operaciones==1?"aprobaci&oacute;n":"rechazo")+": "+motivo_rechazo_operacion+"</p>");
                }*/

                out.print("</div>"+

                            "<div id=\"pm_d_2\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                            "<div id=\"pm_d_3\" class=\"columna marco\" style=\"height:"+alto+"px;width:420px\" onmouseover=\"_('pm_gredicion').scrollLeft=(parseInt(_('pm_gredicion').scrollWidth)/4.5);\">&nbsp;</div>"+
                            "<div id=\"pm_d_4\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                            "<div id=\"pm_d_5\" class=\"columna marco\" style=\"height:"+alto+"px;width:520px\" onmouseover=\"_('pm_gredicion').scrollLeft=parseInt(_('pm_edicion').style.width);\">&nbsp;</div>"+
                            "<div id=\"pm_d_6\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                            //"<div id=\"pm_d_7\" class=\"columna marco\" style=\"height:"+alto+"px;width:300px\">&nbsp;</div>"+
                            //"<div id=\"pm_d_8\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                        "</div>"+

                    "</div>"+
                "</div>");
            }
            
        } finally {
            objEstrategia.cerrar();
            objTipoPlan.cerrar();
            objPlanMerca.cerrar();
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
