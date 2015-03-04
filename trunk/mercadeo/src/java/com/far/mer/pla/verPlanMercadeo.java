/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.Matriz;
import com.far.mer.pla.clas.Estrategia;
import com.far.mer.pla.clas.Laboratorio;
import com.far.mer.pla.clas.PlanMercadeo;
import com.far.mer.pla.clas.TipoPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class verPlanMercadeo extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String _url_anexo = null;
    
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
        
        this._url_anexo = config.getServletContext().getInitParameter("_URL_ANEXO");
        
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
        //HttpSession sesion = request.getSession(true);
        //String usuario = (String)sesion.getAttribute("usuario");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        int estado = Integer.parseInt(request.getParameter("estado" ));
        int alto = Integer.parseInt(request.getParameter("alB"));
        //int ancho = Integer.parseInt(request.getParameter("anB"));

        alto=alto-75;

        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String msg = "";
        String sec_tipo_plan = "";
        String id_tipo_plan="";
        String tipo_plan="";
        String plan_mercadeo="";
        String fecha_ini="";
        String fecha_fin="";
        String promedio_ventas="";
        String fecha_ini_averificar="";
        String fecha_fin_averificar="";
        String premio="";
        String mecanica_tipo = "";
        String mecanica = "";
        String aplica_prom_p_v = "";
        String ope_fecha_ini = "";
        String ope_fecha_fin = "";
        boolean registro_operaciones = false;
        boolean aprobada_operaciones = false;
        //String motivo_rechazo = "";
        String motivo_rechazo_operacion = "";
        String adjuntos="";
        String tipo_alcance="";
        String tipo_alcance_de="";
        int plan_completo = 0;
        
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objPlanMerca.getVerPlanMercadeo(id);
                if(rs.next()){
                    sec_tipo_plan = rs.getString("sec_tipo_plan")!=null ? rs.getString("sec_tipo_plan") : "-1";
                    id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                    tipo_plan = rs.getString("tipo_plan")!=null ? rs.getString("tipo_plan") : "";
                    plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                    tipo_alcance = rs.getString("tipo_alcance")!=null ? rs.getString("tipo_alcance") : "";
                    tipo_alcance_de = rs.getString("tipo_alcance_de")!=null ? rs.getString("tipo_alcance_de") : "";
                    fecha_ini = rs.getString("fecha_ini")!=null ? rs.getString("fecha_ini") : "";
                    fecha_fin = rs.getString("fecha_fin")!=null ? rs.getString("fecha_fin") : "";
                    promedio_ventas = rs.getString("promedio_ventas")!=null ? rs.getString("promedio_ventas") : "";
                    fecha_ini_averificar = rs.getString("fecha_ini_averificar")!=null ? rs.getString("fecha_ini_averificar") : "";
                    fecha_fin_averificar = rs.getString("fecha_fin_averificar")!=null ? rs.getString("fecha_fin_averificar") : "";
                    premio = rs.getString("premio")!=null ? rs.getString("premio") : "";
                    mecanica_tipo = rs.getString("mecanica_tipo")!=null ? rs.getString("mecanica_tipo") : "";
                    mecanica = rs.getString("mecanica")!=null ? rs.getString("mecanica") : "";
                    aplica_prom_p_v = rs.getString("aplica_prom_p_v")!=null ? rs.getString("aplica_prom_p_v") : "";
                    ope_fecha_ini = rs.getString("ope_fecha_ini")!=null ? rs.getString("ope_fecha_ini") : "";
                    ope_fecha_fin = rs.getString("ope_fecha_fin")!=null ? rs.getString("ope_fecha_fin") : "";
                    registro_operaciones = rs.getString("registro_operaciones")!=null ? rs.getBoolean("registro_operaciones") : false;
                    aprobada_operaciones = rs.getString("aprobada_operaciones")!=null ? rs.getBoolean("aprobada_operaciones") : false;
                    //motivo_rechazo = rs.getString("motivo_rechazo")!=null ? rs.getString("motivo_rechazo") : "";
                    motivo_rechazo_operacion = rs.getString("motivo_rechazo_operacion")!=null ? rs.getString("motivo_rechazo_operacion") : "";
                    adjuntos = rs.getString("adjuntos")!=null ? rs.getString("adjuntos") : "";
                    plan_completo = (rs.getString("plan_completo")!=null) ? rs.getInt("plan_completo") : 0;
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }
        
        ResultSet rsFarmacias = objPlanMerca.consulta("select * from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id+" order by nombre");
        ResultSet rsProductosFiltro = objPlanMerca.consulta("select * from tbl_plan_mercadeo_producto_filtro with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_producto_filtro");
        ResultSet rsProductos = objPlanMerca.consulta("select * from tbl_plan_mercadeo_producto with (nolock) where id_plan_mercadeo="+id+" order by descripcion");

        String tipo_alcance_de1 = "Mercadeo";
        if(tipo_alcance_de.compareTo("c")==0){
            tipo_alcance_de1 = "Mercadeo";
        }else if(tipo_alcance_de.compareTo("i")==0){
                   tipo_alcance_de1 = "Inauguraci&oacute;n";
        }
        
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);

        try {
            out.print("obj»vta1_html^msg»"+msg+"^fun»_('pm_edicion').style.width = (parseInt(_('pm_d_1').style.width) + parseInt(_('pm_d_3').style.width) + parseInt(_('pm_d_5').style.width) + 90) + 'px';^frm»"+
            "<div id=\"pm_gredicion\" style=\"background-color:#EAEAEA;overflow:auto;\">"+
                "<div id=\"pm_edicion\" class=\"tabla\">"+

                    "<div class=\"fila\">"+
                        "<div id=\"pm_d_0\" class=\"columna indicador\" style=\"height:"+alto+"px\">&nbsp;</div>"+
                        "<div id=\"pm_d_1\" class=\"columna marco\" style=\"height:"+alto+"px;width:500px\" onmouseover=\"_('pm_gredicion').scrollLeft=0;\" >");

            out.print("<form action=\"frmPlanMercadeoAprobar\" onsubmit=\"return Ajax.enviarForm(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_plan_mercadeo\" name=\"id_plan_mercadeo\" value=\""+id+"\" />");
            out.print("<input type=\"hidden\" id=\"aprobado\" name=\"aprobado\" value=\"0\" />");
            out.print("<input type=\"hidden\" id=\"estado\" name=\"estado\" value=\""+estado+"\" />");
            out.print("<input type=\"hidden\" id=\"id_tipo_plan\" name=\"id_tipo_plan\" value=\""+id_tipo_plan+"\" />");
            out.print("<h3>DATOS DEL PLAN DE MERCADEO</h3>");
            out.print("<table cellpadding=\"2\" width=\"100%\">");
            out.print("<tr>");
            out.print("<td>No. de plan de mercadeo:</td>");
            out.print("<td>"+sec_tipo_plan+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Tipo de plan:</td>");
            out.print("<td>"+tipo_plan+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Nombre del plan de mercadeo:</td>");
            out.print("<td>"+plan_mercadeo+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Alcance:</td>");
            out.print("<td>"+((tipo_alcance.compareTo("f")==0) ? "Farmacias" : "") +
                    ((tipo_alcance.compareTo("d")==0) ? "Distribuci&oacute;n" : "")+"</td>");
            out.print("</tr>");


            // farmacias
            if(tipo_alcance.compareTo("f")==0){
                out.print("<tr>");
                out.print("<td>Tipo de alcance:</td>");
                out.print("<td>"+tipo_alcance_de1+"</td>");
                out.print("</tr>");
                
                out.print("<tr>");
                out.print("<td>Promedio de ventas desde: </td>");
                out.print("<td>"+fecha_ini+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Promedio de ventas hasta:</td>");
                out.print("<td>"+fecha_fin+"</td>");
                out.print("</tr>");
                
                
                out.print("<tr>");
                    out.print("<td colspan=\"2\">");
                    
                        out.print("<table id=\"axFechProyVentas\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+">");
                        out.print("<tr>");
                        out.print("<td colspan=\"2\">");
                        out.print("Rango de fechas para evaluar la proyecci&oacute;n de ventas");  
                        out.print("</td>");   
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td width=\"196\"><span>"+((tipo_alcance_de.compareTo("i")==0) ? "Per&iacute;odo a evaluar" : "")+" desde:</span> <span class=\"marca\">*</span> </td>");
                        out.print("<td>"+fecha_ini_averificar+"");
                        out.print("</td>");
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td><span>"+((tipo_alcance_de.compareTo("i")==0) ? "Per&iacute;odo a evaluar" : "")+" hasta:</span> <span class=\"marca\">*</span> </td>");
                        out.print("<td>"+fecha_fin_averificar+"");
                        out.print("</td>");    
                        out.print("</tr>");
                        out.print("</table>");
                        
                    out.print("</td>");    
                    out.print("</tr>");
                
                out.print("<tr><td colspan=\"2\">"); 
                
                    //  no es de inauguracion   ----------------------------------
                if(tipo_alcance_de.compareTo("i")!=0){ 
                        
                    //  no es de inauguracion  FILTRO DE PRODUCTOS ----------------------------------
                    
                        out.print("<div style=\"text-align:right\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">"
                                + "<input id=\"btnProd\" type=\"button\" value=\"Mostrar productos\" onclick=\"mer_mostrarProductos()\" /></div>");
                        
                        out.print("<div class=\"psPanelGris\">");
                    
                            out.print("<table cellpadding=\"2\" width=\"100%\">");
                            out.print("<tr>");
                            out.print("<th width=\"150\">CATEGORIA</th>");
                            out.print("<th width=\"150\">LABORATORIO</th>");
                            out.print("<th width=\"150\">LINEA</th>");
                            out.print("</tr>");
                            try{
                                String desc_nivel="";
                                String desc_clase="";
                                String desc_linea="";
                                while(rsProductosFiltro.next()){
                                    desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                                    desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                                    desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<td>"+desc_nivel+"</td>"+
                                    "<td>"+desc_clase+"</td>"+
                                    "<td>"+desc_linea+"</td>"+
                                    "</tr>");
                                }
                            }catch(Exception e){
                                msg = e.getMessage();
                            }
                            out.print("</table>");
                            
                            out.print("<table cellpadding=\"2\" width=\"100%\">");
                            out.print("<tr>");
                            out.print("<th>PRODUCTO</th>");
                            out.print("</tr>");
                            try{
                                String descripcion="";
                                while(rsProductos.next()){
                                    descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<td>"+descripcion+"</td>"+
                                    "</tr>");
                                }
                            }catch(Exception e){
                                msg = e.getMessage();
                            }
                            out.print("</table>");
                    
                        out.print("</div>"); 
                    
                        
                        out.print("<br />"); 
                        
                        //  no es de inauguracion  FILTRO DE FARMACIAS   -----------------------------------------------------------
                        
                        out.print("<div style=\"text-align:right\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">"
                                + "<input id=\"btnFar\" type=\"button\" value=\"Mostrar farmacias\" onclick=\"mer_mostrarFarmacias()\" /></div>");
                        
                        out.print("<div id=\"axFarmacias\" style=\"display:none\" class=\"psPanelGris\">");
                    
                            out.print("<table cellpadding=\"2\" width=\"100%\">");
                            out.print("<tr>");
                            out.print("<th>FARMACIA</th>"
                                    + "<th width=\"70\">Prom. Ventas</th>"
                                    + "<th width=\"40\">%</th>"
                                    + "<th width=\"70\">Proy. Ventas</th>");
                            out.print("</tr>");
                            
                            try{
                                String nombre="";
                                String p_ventas="0";
                                String p_crecimiento="0";
                                String proy_ventas="0";
                                while(rsFarmacias.next()){
                                    nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                                    p_ventas = rsFarmacias.getString("p_ventas")!=null ? rsFarmacias.getString("p_ventas") : "0";
                                    p_crecimiento = rsFarmacias.getString("p_crecimiento")!=null ? rsFarmacias.getString("p_crecimiento") : "0";
                                    proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                                    out.print("<tr onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                    "<td>"+nombre+"</td>"+
                                    "<td align=\"right\">"+p_ventas+"</td>"+
                                    "<td align=\"right\">"+p_crecimiento+"</td>"+
                                    "<td align=\"right\">"+proy_ventas+"</td>"+
                                    "</tr>");
                                }
                            }catch(Exception e){
                                msg = e.getMessage();
                            }
                            
                            out.print("<tr>"+
                                    "<td colspan=\"2\" align=\"right\">"+promedio_ventas+"</td>"+
                                    "<td>&nbsp;</td>"+
                                    "</tr>");
                            out.print("</table>");
                            
                        out.print("</div>");   //   axFarmacias
                    
                    }
                    
                
                
                
                
                    //  no es de inauguracion  FILTRO DE PRODUCTOS   -----------------------------------------------------------
                    
                if(tipo_alcance_de.compareTo("i")==0){    
                    //  es de inauguracion
                    out.print("<div style=\"text-align:right\"><input id=\"btnFar1\" type=\"button\" value=\"Mostrar farmacias\" onclick=\"mer_mostrarFarmacias(1)\" />");
                    out.print("</div>");

                    out.print("<div id=\"axFarmacias1\">");

                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<th width=\"315\">FARMACIA</th>"
                                + "<th width=\"80\">Proy. Ventas</th>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<div id=\"axTblFarmacias1\" class=\"tabla\">");
                        try{
                            int i=0;
                            //String oficina="";
                            String nombre="";
                            String proy_ventas="0";
                            rsFarmacias.beforeFirst();
                            while(rsFarmacias.next()){
                                //oficina = rsFarmacias.getString("oficina")!=null ? rsFarmacias.getString("oficina") : "";
                                nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                                proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                                out.print("<div class=\"fila jm_filaImp\" id=\"r1F"+i+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                                "<div class=\"columna\" style=\"width:315px\">"+nombre+"</div>"+
                                "<div class=\"columna\" style=\"width:85px;\">"+proy_ventas+"</div>");
                                out.print("</div>");
                                i++;
                            }
                        }catch(Exception e){
                            msg = e.getMessage();
                        }
                        out.print("</div>");

                    out.print("</div>");   //   axFarmacias1
                }
                    
                out.print("</td></tr>");    
            }
            
            
            
            // distribucion
            if(tipo_alcance.compareTo("d")==0){
                //out.print("<tr><td colspan=\"2\">");
            
                //out.print("<table id=\"alcDis\" style=\"display:"+( tipo_alcance.compareTo("d")==0 ? "block" : "none" )+"\" >");
                out.print("<tr>");
                out.print("<td valign=\"top\">Consulta de facturas:  </td>");
                out.print("<td>desde: "+fecha_ini+"<br />hasta: &nbsp;"+fecha_fin+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td valign=\"top\">Valor a comparar la sumatoria de las facturas: </td>");
                out.print("<td>"+promedio_ventas+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td valign=\"top\">% o premio a entregarse: </td>");
                out.print("<td>"+premio+"</td>");
                out.print("</tr>");
                
                /*  vendedores */
                out.print("<tr><td colspan=\"2\">");
                    
                    out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelVerde\">");
                    out.print("<tr>");
                    out.print("<th>VENDEDOR</th>");
                    out.print("</tr>");
                    try{
                        String nombre_vendedor = "";
                        ResultSet rs = objPlanMerca.getVendedores(id);
                        while(rs.next()){
                            nombre_vendedor = rs.getString("nombre_vendedor")!=null ? rs.getString("nombre_vendedor") : "";
                            out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                            "<td>"+nombre_vendedor+"</td>"+
                            "</tr>");
                        }
                        rs.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</table>");

                out.print("</td></tr>");/* fin vendedores */
                
                
                
                /*   productos en distribucion   */
                out.print("<tr><td colspan=\"2\">");
                //  distribucion  FILTRO DE PRODUCTOS ----------------------------------
                    out.print("<div id=\"alcProdFil1\" class=\"psPanelGris\" style=\"display:"+((tipo_alcance_de.compareTo("i")!=0) ? "block" : "none")+"\">");

                        out.print("<div class=\"psPanelGris\">");

                            out.print("<table cellpadding=\"2\" width=\"100%\">");
                            out.print("<tr>");
                            out.print("<th width=\"150\">CATEGORIA</th>");
                            out.print("<th width=\"150\">LABORATORIO</th>");
                            out.print("<th width=\"150\">LINEA</th>");
                            out.print("</tr>");
                            try{
                                String desc_nivel="";
                                String desc_clase="";
                                String desc_linea="";
                                while(rsProductosFiltro.next()){
                                    desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                                    desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                                    desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<td>"+desc_nivel+"</td>"+
                                    "<td>"+desc_clase+"</td>"+
                                    "<td>"+desc_linea+"</td>"+
                                    "</tr>");
                                }
                            }catch(Exception e){
                                msg = e.getMessage();
                            }
                            out.print("</table>");
                            
                            out.print("<table cellpadding=\"2\" width=\"100%\">");
                            out.print("<tr>");
                            out.print("<th>PRODUCTO</th>");
                            out.print("</tr>");
                            try{
                                String descripcion="";
                                while(rsProductos.next()){
                                    descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                    "<td>"+descripcion+"</td>"+
                                    "</tr>");
                                }
                            }catch(Exception e){
                                msg = e.getMessage();
                            }
                            out.print("</table>");
                            
                            

                        out.print("</div>");   //   axProductos

                    out.print("</div>"); 
                    //  distribucion  FILTRO DE PRODUCTOS   -----------------------------------------------------------

                    out.print("<br />"); 
                out.print("</td></tr>");/* fin productos distribucion */

                
                
                
                /*  clientes  */
                out.print("<tr><td colspan=\"2\">");
                
                        out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelAzul\">");
                        out.print("<tr>");
                        out.print("<th>CLIENTE</th>");
                        out.print("</tr>");
                        try{
                            String nombre_comercial = "";
                            ResultSet rs = objPlanMerca.getClientes(id);
                            while(rs.next()){
                                nombre_comercial = rs.getString("nombre_comercial")!=null ? rs.getString("nombre_comercial") : "";
                                out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                "<td>"+nombre_comercial+"</td>"+
                                "</tr>");
                            }
                            rs.close();
                        }catch(Exception ex){
                            msg = ex.getMessage();
                        }
                        out.print("</table>");
                
                out.print("</td></tr>");/* fin clientes */
                
                
                /*  adjuntos  */
                out.print("<tr><td colspan=\"2\">");

                        out.print("<table class=\"psPanelGris\" width=\"100%\">"); /* adjuntos */
                        if(adjuntos.compareTo("")!=0){
                            String vecAdj[] = adjuntos.split("|");
                            for(int i=0; i<vecAdj.length; i++){
                                out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                                out.print("<td><a href=\"javascript:void(0)\" onclick=\"abrir('"+this._url_anexo+"/"+vecAdj[i]+"')\">"+vecAdj[i]+"</a></td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");   /* adjuntos */
                    
                out.print("</td></tr>");/* fin adjuntos */
                
                out.print("</table>"); /*  tabla de distribucion  */
                
                
            //out.print("</td></tr>");
            }
            
            
            out.print("</table>");
            


            /*    AUSPICIOS      */
                out.print("<h3>AUSPICIOS</h3>");
                
                    out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelVerde\">");
                    Laboratorio objLaboratorio = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);
                    try{
                        ResultSet rsLabs = objLaboratorio.getLaboratoriosAsignables(id, id_tipo_plan);

                        ResultSet rsAus = objPlanMerca.getAuspicios(id);
                        String laboratorios[][] = Matriz.ResultSetAMatriz(rsAus);
                        String id_laboratorio = "";
                        String nombre_comercial = "";
                        float presupuesto = 0;
                        float monto = 0;
                        int pos=0;
                        out.print("<tr><th>LABORATORIO ESTRAT&Eacute;GICO</th><th>PRES. DISP.</th><th>MONTO</th></tr>");
                        while(rsLabs.next()){
                            id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                            nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                            presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                            pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                            monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                            presupuesto += monto; 
                            if(monto>0){
                                out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                                out.print("<td>"+nombre_comercial+"</td>"
                                        + "<td align=\"right\">"+presupuesto+"</td>");
                                out.print("<td align=\"right\">"+monto+"</td>");
                                out.print("</tr>");
                            }
                        }
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }finally{
                        objLaboratorio.cerrar();
                    }
                    out.print("</table>");
                
                    
                out.print("<br />");

                ResultSet rsProv = objPlanMerca.getProveedores(id);
                if(objPlanMerca.getFilas(rsProv)>0){    
                    out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelGris\">");
                        out.print("<tr>");
                        out.print("<th>PROVEEDOR</th><th>MONTO</th><th># FORM.</th>");
                        out.print("</tr>");
                    //out.print("</table>");
                    //out.print("<div id=\"axProv\" class=\"tabla borde\" style=\"width:480px\">");
                    int i=0;
                    try{
                        String nombre_comercial = "";
                        String monto = "";
                        String num_formulario = "";
                        int eliminado = 0;
                        while(rsProv.next()){
                            nombre_comercial = rsProv.getString("nombre_comercial")!=null ? rsProv.getString("nombre_comercial") : "";
                            num_formulario = rsProv.getString("num_formulario")!=null ? rsProv.getString("num_formulario") : "";
                            monto = rsProv.getString("monto")!=null ? rsProv.getString("monto") : "";
                            eliminado = rsProv.getString("eliminado")!=null ? rsProv.getInt("eliminado") : 0;
                            out.print("<tr "+(eliminado==1 ? "class=\"fila jm_filaEli\"" : "class=\"fila jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\"")+" >"+
                            "<td>"+nombre_comercial+"</td>"+
                            "<td align=\"right\">"+monto+"</td>"+
                            "<td align=\"center\">"+num_formulario+"</td>"+
                            "</tr>");
                            i++;
                        }
                        rsProv.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</table>");
                
                
                    
                    
                    
                    /* tiempo para confirmacion de auspicios  */
                    /*if(i>0){
                        out.print("<div class=\"psPanelGris\">");
                        out.print("<h3 class=\"psPanelAzul\" style=\"color:#000\">NUMERO DE DIAS PARA CONFIRMAR AUSPICIOS</h3>");
                        out.print("<table cellpadding=\"2\" width=\"100%\"><tr>");
                        out.print("<th>USUARIO</th>");
                        out.print("<th># DIAS</th>");
                        out.print("</tr>");
                        try{
                            ResultSet rsUsCoAu = objPlanMerca.getUsuariosConfAusp(id);
                            String tiempos[][] = Matriz.ResultSetAMatriz(rsUsCoAu);

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
                                out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                                "<td>"+usuarioConf+"</td>"+
                                "<td align=\"center\">"+num_dias+"</td>"+
                                "</tr>");
                            }
                            rsUsCoAu.close();
                        }catch(Exception ex){
                            msg = ex.getMessage();
                        }
                        out.print("</table>");
                        out.print("</div>");
                    }*/
                }

            
            out.print("<br />");
            
            
            out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelGris\">");
            out.print("<tr>");
            out.print("<td>Tipo de mec&aacute;nica: </td>");
            out.print("<td>"+((mecanica_tipo.compareTo("t")==0) ? "Texto" : "")+
                    ((mecanica_tipo.compareTo("o")==0) ? "Operaciones" : "")+"</td>");
            out.print("</tr>");

            //  mecanica de texto
            if(mecanica_tipo.compareTo("t")==0){
                out.print("<tr valign=\"top\">");
                    out.print("<td>Mec&aacute;nica: </td>");
                    out.print("<td>"+mecanica+"</td>");
                out.print("</tr>");
            }

            //  mecanica de operaciones
            if(mecanica_tipo.compareTo("o")==0){
                out.print("<tr>");
                out.print("<td>Aplica para: </td>");
                out.print("<td>"+((aplica_prom_p_v.compareTo("p")==0) ? "Promociones" : "") +
                        ((aplica_prom_p_v.compareTo("c")==0) ? "Convenios" : "")+"</td>");
                out.print("</tr>");

                out.print("<tr><td colspan=\"2\" class=\"psPanelGris\">");
                    out.print("<table width=\"100%\">");
                        out.print("<tr>");
                        out.print("<th>ARCHIVO</th><th>DESCRIPCI&Oacute;N</th>");
                        out.print("</tr>");

                    int j=0;
                    try{
                        String archivo = "";
                        String descripcion = "";
                        ResultSet rsAdjMecanica = objPlanMerca.getAdjuntos(id);
                        while(rsAdjMecanica.next()){
                            archivo = rsAdjMecanica.getString("archivo")!=null ? rsAdjMecanica.getString("archivo") : "";
                            descripcion = rsAdjMecanica.getString("descripcion")!=null ? rsAdjMecanica.getString("descripcion") : "";
                            out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                            "<td><a href=\"javascript:void(0)\" onclick=\"abrir('"+this._url_anexo+"/actividades/"+archivo+"')\">"+archivo+"</a></td>"+
                            "<td> &nbsp; "+descripcion+"</td>"+
                            "</tr>");
                            j++;
                        }
                        rsAdjMecanica.close();
                    }catch(Exception ex){
                        msg = ex.getMessage();
                    }
                    out.print("</table>");


                out.print("</td></tr>");
                out.print("<tr>");
                out.print("<td>Fecha de inicio:</td>");
                out.print("<td>"+ope_fecha_ini+"</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Fecha de t&eacute;rmino:</td>");
                out.print("<td>"+ope_fecha_fin+"</td>");
                out.print("</tr>");
            }

            out.print("</table>");


            out.print("<br />");


            if(id.compareTo("-1")!=0){
                out.print("<h3>ESTRATEGIAS</h3>");
                Estrategia objEstrategia = new Estrategia(this._ip, this._puerto, this._db, this._usuario, this._clave);
                try{
                    ResultSet rsEstrategias = objEstrategia.getEstrategias(id);
                    String id_estrategia = "";
                    String estrategia = "";
                    String num_actividades = "";
                    float pre_totales = 0;
                    float sumatoria = 0;
                    out.print("<table cellpadding=\"2\" class=\"psPanelGris\" width=\"100%\">");
                    out.print("<tr><th>ESTRATEGIA</th><th># ACT.</th><th>TOTALES</th></tr>");
                    while(rsEstrategias.next()){
                        id_estrategia = rsEstrategias.getString("id_estrategia")!=null ? rsEstrategias.getString("id_estrategia") : "";
                        estrategia = rsEstrategias.getString("estrategia")!=null ? rsEstrategias.getString("estrategia") : "";
                        num_actividades = rsEstrategias.getString("num_actividades")!=null ? rsEstrategias.getString("num_actividades") : "";
                        pre_totales = rsEstrategias.getString("pre_totales")!=null ? rsEstrategias.getFloat("pre_totales") : 0;
                        out.print("<tr onclick=\"mer_estrategiaMostrar("+id_estrategia+")\" style=\"cursor:pointer\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                        out.print("<td>"+estrategia+"</td>");
                        out.print("<td align=\"center\">"+num_actividades+"</td>");
                        out.print("<td align=\"right\">"+pre_totales+"</td>");
                        out.print("</tr>");
                        sumatoria += pre_totales;
                    }
                    out.print("<tr><td colspan=\"3\" align=\"right\">"+sumatoria+"</th></tr>");
                    out.print("</table>");
                }catch(Exception e){
                    msg = e.getMessage();
                }finally{
                    objEstrategia.cerrar();
                }
            }
            out.print("<br />");
            
            if(mecanica_tipo.compareTo("o")==0){
                out.print("<table width=\"100%\">");
                if(!registro_operaciones){
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
                    out.print("<tr><td colspan=\"2\"><p>Motivo de "+(aprobada_operaciones?"aprobaci&oacute;n":"rechazo")+": "+motivo_rechazo_operacion+"</p></td></tr>");
                }
                out.print("</table>");
            }
            
            if( (estado==0 && plan_completo==1) ||  estado>=4 && estado<=6){
                out.print("<table width=\"100%\">");
                out.print("<tr valign=\"top\">"); 
                out.print("<td>Motivo de aprobaci&oacute;n o rechazo:</td>");
                out.print("<td><textarea id=\"motivo\" name=\"motivo\" cols=\"45\"></textarea></td>");
                out.print("</tr>");
                
                out.print("<tr>");
                out.print("<td><input type=\"submit\" value=\"Rechazar\" onmousedown=\"_('aprobado').value=0;\" /></td>");
                out.print("<td align=\"right\"><input type=\"submit\" value=\"Aprobar\" onmousedown=\"_('aprobado').value=1;\" /></td>");
                out.print("</tr>");
                out.print("</table>");
            }
            
            out.print("</form>");
            
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

        } finally {
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
