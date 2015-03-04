/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.DatosDinamicos;
import com.far.lib.Fecha;
import com.far.mer.pla.clas.Actividad;
import com.far.mer.pla.clas.ActividadTipo;
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

/**
 *
 * @author Jorge
 */
public class frmActividad extends HttpServlet {

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

        String msg = "";
        String id = request.getParameter("id");
        String id_plan_mercadeo = request.getParameter("id_plan_mercadeo1");
        String fecha_creacion = request.getParameter("fecha_creacion");
        String id_estrategia = request.getParameter("id_estrategia");
        String id_tipo_plan = request.getParameter("id_tipo_plan");
        int estado = Integer.parseInt( request.getParameter("estado") );
        int plan_completo = Integer.parseInt( request.getParameter("plan_completo") );
        
        boolean modificar = (estado==1 || estado==2) && plan_completo==0 ? true : false;
        String lectura = (estado==1 || estado==2) && plan_completo==0 ? "" : "readonly";
        String desactivado = (estado==1 || estado==2) && plan_completo==0 ? "" : "disabled=\"true\"";

        String tipo_pago = "i";
        String actividad = "";
        String monto = "0";
        String fecha_creacion_act = Fecha.getFecha("SQL");
        String fecha_ini = Fecha.sumar(Fecha.getFecha("SQL"), Calendar.DAY_OF_MONTH, 9);
        String fecha_fin = fecha_ini;
        
        String usuario_seg = "";
        String responsable_seg = "";
        String usuario_eje = "";
        String responsable_eje = "";
        String pre_tipo = "";
        String pre_proveedor = "";
        String pre_cantidad = "";
        String pre_p_u = "";
        String pre_total = "";
        String pre_id_tipo_plan_cuenta = "";
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);

        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objActividad.getActividad(id);
                if(rs.next()){
                    tipo_pago = rs.getString("tipo_pago")!=null ? rs.getString("tipo_pago") : "";
                    actividad = rs.getString("actividad")!=null ? rs.getString("actividad") : "";
                    monto = rs.getString("monto")!=null ? rs.getString("monto") : "0";
                    fecha_creacion_act = rs.getString("fecha_creacion_act")!=null ? rs.getString("fecha_creacion_act") : "";
                    fecha_ini = rs.getString("sql_fecha_ini")!=null ? rs.getString("sql_fecha_ini") : "";
                    fecha_fin = rs.getString("sql_fecha_fin")!=null ? rs.getString("sql_fecha_fin") : "";
                    usuario_seg = rs.getString("usuario_seg")!=null ? rs.getString("usuario_seg") : "";
                    responsable_seg = rs.getString("responsable_seg")!=null ? rs.getString("responsable_seg") : "";
                    usuario_eje = rs.getString("usuario_eje")!=null ? rs.getString("usuario_eje") : "";
                    responsable_eje = rs.getString("responsable_eje")!=null ? rs.getString("responsable_eje") : "";
                    pre_tipo = rs.getString("pre_tipo")!=null ? rs.getString("pre_tipo") : "";
                    pre_proveedor = rs.getString("pre_proveedor")!=null ? rs.getString("pre_proveedor") : "";
                    pre_cantidad = rs.getString("pre_cantidad")!=null ? rs.getString("pre_cantidad") : "";
                    pre_p_u = rs.getString("pre_p_u")!=null ? rs.getString("pre_p_u") : "";
                    pre_total = rs.getString("pre_total")!=null ? rs.getString("pre_total") : "";
                    pre_id_tipo_plan_cuenta = rs.getString("pre_id_tipo_plan_cuenta")!=null ? rs.getString("pre_id_tipo_plan_cuenta") : "";
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }
        
        ActividadTipo objActividadTipo = new ActividadTipo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsConceptos = objActividadTipo.getTiposPlanes();
        
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsCuentas = objTipoPlan.getTipoPlanCuentasActividad(id_tipo_plan);
        
        String [][] tipo_presupuesto = {{"b","Bienes"},{"s","Servicio"},{"a","Activo fijo"},{"p","Premio"},{"i","Inventario"}};
        
        

        try {
            out.print("obj»pm_d_5^fun»"+(tipo_pago.compareTo("f")==0 ? "_('actividad').value=_('actividadf').options[_('actividadf').selectedIndex].text;" : "")+(modificar?"":"_('pre_tipo').disabled=_('pre_id_tipo_plan_cuenta').disabled='true';")+
                    "^foc»actividad^msg»"+msg+"^frm»");
            out.print("<form action=\"frmActividadGuardar\" onsubmit=\"return mer_actividadGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_actividad\" name=\"id_actividad\" value=\""+id+"\" />");
            out.print("<input type=\"hidden\" id=\"id_estrategia1\" name=\"id_estrategia1\" value=\""+id_estrategia+"\" />");
            out.print("<input type=\"hidden\" id=\"id_plan_mercadeo2\" name=\"id_plan_mercadeo2\" value=\""+id_plan_mercadeo+"\" />");
            out.print("<input type=\"hidden\" id=\"usuario_seg\" name=\"usuario_seg\" value=\""+usuario_seg+"\" />");
            out.print("<input type=\"hidden\" id=\"usuario_eje\" name=\"usuario_eje\" value=\""+usuario_eje+"\" />");
            out.print("<input type=\"hidden\" id=\"limCrono\" name=\"limCrono\" value=\"0\" />");
            //out.print("<input type=\"hidden\" id=\"limAdjAct\" name=\"limAdjAct\" value=\"0\" />");
            out.print("<input type=\"hidden\" id=\"ax_monto_act\" name=\"ax_monto_act\" value=\""+monto+"\" />");
            out.print("<input type=\"hidden\" id=\"fecha_crea\" value=\""+fecha_creacion_act+"\" />");
            
            out.print("<h3>FORMULARIO DE ACTIVIDAD</h3>");
            out.print("<table>");
            out.print("<tr>");
            out.print("<td>Tipo de pago: <span class=\"marca\">*</span> </td>");
            out.print("<td><label><input type='radio' id='p0' name='tipo_pago' "+desactivado+" value='f' "+(tipo_pago.compareTo("f")==0 ? "checked" : "")+" onclick=\"_('actf').style.display='block';_('acti').style.display='none';_('ax_monto_act').value=_('actividadf').value;_('actividad').value=this.options[this.selectedIndex].text;\" /> En farmacia</label> &nbsp;&nbsp;&nbsp; " +
                    "<label><input type='radio' id='p1' name='tipo_pago' "+desactivado+" value='i' "+(tipo_pago.compareTo("i")==0 ? "checked" : "")+" onclick=\"_('actf').style.display='none';_('acti').style.display='block';\" /> Compras internas</label></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td nowrap>Actividad: <span class=\"marca\">*</span> </td>");
            out.print("<td>");
                out.print("<div id=\"actf\" style=\"display:"+(tipo_pago.compareTo("f")==0?"block":"none")+"\">"+this.combo(rsConceptos, "actividadf", actividad, "_('ax_monto_act').value=this.value;_('actividad').value=this.options[this.selectedIndex].text;", 305)+ "</div>");
                out.print("<div id=\"acti\" style=\"display:"+(tipo_pago.compareTo("i")==0?"block":"none")+"\"><input type=\"text\" id=\"actividad\" name=\"actividad\" size=\"45\" maxlength=\"60\" value=\""+actividad+"\" "+lectura+" /></div>");
            out.print("</td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td><span id=\"axFI1\">Fecha de inicio:</span> <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"act_fecha_ini\" name=\"act_fecha_ini\" style=\"width:80px\" value=\""+fecha_ini+"\" readonly />");
            if(modificar){
                out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('act_fecha_ini', 'SQL');\" />");
            }
            out.print("</td></tr>");
            out.print("<tr>");
            out.print("<td><span id=\"axFF1\">Fecha de t&eacute;rmino:</span> <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"act_fecha_fin\" name=\"act_fecha_fin\" style=\"width:80px\" value=\""+fecha_fin+"\" readonly />");
            if(modificar){
                out.print("<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('act_fecha_fin', 'SQL');\" />");
            }  
            out.print("</td></tr>");
            
            
            
            out.print("<tr>");
            out.print("<td width=\"200\">Responsable de seguimiento:</td>");
            out.print("<td><span id=\"axResseg\"></span>");
            out.print("<input type=\"text\" id=\"responsable_seg\" name=\"responsable_seg\" size=\"42\" maxlength=\"80\" class=\"buscar\" onfocus=\"this.select();\" onkeyup=\"mer_getResponsable('seg');\" onkeydown=\"_NoE(event);\" title=\"responsable\" value=\""+responsable_seg+"\" "+lectura+" />");
            out.print("</td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td width=\"200\">Responsable de ejecuci&oacute;n: <span class=\"marca\">*</span> </td>");
            out.print("<td><span id=\"axReseje\"></span>");
            out.print("<input type=\"text\" id=\"responsable_eje\" name=\"responsable_eje\" size=\"42\" maxlength=\"80\" class=\"buscar\" onfocus=\"this.select();\" onkeyup=\"mer_getResponsable('eje');\" onkeydown=\"_NoE(event);\" title=\"responsable\" value=\""+responsable_eje+"\" "+lectura+" />");
            out.print("</td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td width=\"200\">Tipo: <span class=\"marca\">*</span> </td>");
            out.print("<td>"+DatosDinamicos.combo(tipo_presupuesto, "pre_tipo", pre_tipo, "", "", 180)+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Proveedor sugerido: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"pre_proveedor\" name=\"pre_proveedor\" size=\"45\" maxlength=\"80\" value=\""+pre_proveedor+"\" "+lectura+" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Cantidad: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"pre_cantidad\" name=\"pre_cantidad\" style=\"text-align:right\" size=\"15\" maxlength=\"20\" value=\""+pre_cantidad+"\" onkeypress=\"_numero(event)\" onkeyup=\"mer_calcTotal()\" "+lectura+" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Precio unitario incluido IVA: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"pre_p_u\" name=\"pre_p_u\" style=\"text-align:right\" size=\"15\" maxlength=\"20\" value=\""+pre_p_u+"\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_calcTotal()\" "+lectura+" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Total: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"pre_total\" name=\"pre_total\" style=\"text-align:right\" size=\"15\" maxlength=\"20\" value=\""+pre_total+"\" readonly /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Cuenta contable: <span class=\"marca\">*</span></td>");
            out.print("<td>"+DatosDinamicos.combo(rsCuentas, "pre_id_tipo_plan_cuenta", pre_id_tipo_plan_cuenta, "", "", 305)+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td><span id=\"axCrAd\" nowrap valign=\"top\">Cronograma de Entregas:</span> </td>");
            out.print("<td>");
            if(modificar){
                out.print("<input type=\"text\" id=\"axFecha\" style=\"width:80px\" value=\""+Fecha.sumar(fecha_creacion, Calendar.DAY_OF_MONTH, 12)+"\" onkeypress=\"_evaluar(event, '0123456789/')\" />"
                    + "<input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('axFecha', 'SQL', 'mer_setFecha()');\" />"
                    + " &nbsp;&nbsp;&nbsp; <input type=\"button\" value='agregar' onClick=\"mer_setFecha()\" />");
            }
            out.print("</td></tr>");
            
            out.print("<tr><td colspan=\"2\" class=\"psPanelGris\">");
            out.print("<table>");
                    out.print("<tr>");
                    out.print("<th width=\"30\">FECHA</th><th width=\"320\">DESCRIPCION</th><th width=\"25\">&nbsp;</th>");
                    out.print("</tr>");
                out.print("</table>");
            out.print("<div id=\"ax_pre_crono_adqui\" class=\"tabla\">");
            try{    
                int i=0;
                String fecha = "";
                String descripcion = "";
                ResultSet rsCrono = objActividad.getActividadesCronograma(id);
                while(rsCrono.next()){
                    fecha = rsCrono.getString("fecha_sql")!=null ? rsCrono.getString("fecha_sql") : "";
                    descripcion = rsCrono.getString("descripcion")!=null ? rsCrono.getString("descripcion") : "";
                    out.print("<div class=\"jm_filaImp\" id=\"axCrAd"+i+"\" style=\"height:23px\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                    "<div class=\"columna\" style=\"width:80px\"><input type=\"hidden\" id=\"crono_fecha"+i+"\" name=\"crono_fecha"+i+"\" value=\""+fecha+"\" />"+fecha+"</div>"+
                    "<div class=\"columna\" style=\"width:380px\"><input type=\"text\" class=\"texto\" id=\"crono_descripcion"+i+"\" name=\"crono_descripcion"+i+"\" style=\"width:370px\" value=\""+descripcion+"\" "+lectura+" /></div>"+
                    "<div class=\"columna\" style=\"width:25px\">");
                    if(modificar){
                        out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('axCrAd"+i+"');\">&nbsp;</div>");
                    }
                    out.print("</div></div>");
                    i++;
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
            out.print("</div>");
            out.print("</td></tr>");
            
            if(modificar){
                out.print("<tr>");
                out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
                out.print("</tr>");
            }
            
            out.print("</table>");
            out.print("</form>");
        } finally {
            objActividadTipo.cerrar();
            objTipoPlan.cerrar();
            objActividad.cerrar();
            out.close();
        }
    }
    
    
    public String combo(ResultSet dat, String nom, String mar, String onC, int ancho)
    {
        String sel="";        
        String cad="<select id=\""+nom+"\" name=\""+nom+"\" onchange=\""+onC+"\" style=\"width:"+ancho+"px;\">";
        try{
            while(dat.next()){
                String monto = dat.getString("monto")!=null ? dat.getString("monto") : "0";
                String concepto = dat.getString("concepto")!=null ? dat.getString("concepto") : "";
                cad+="<option "+sel+" value=\"" + monto + "\" "+(concepto.compareTo(mar)==0 ? "selected" : "")+"  >" + concepto + "</option>";
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        cad+="</select>"; 
        return cad;
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
