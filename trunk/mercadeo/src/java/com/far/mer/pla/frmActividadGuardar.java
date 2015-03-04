/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.mer.pla.clas.Actividad;
import com.far.mer.pla.clas.Configuracion;
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
public class frmActividadGuardar extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;
    
    private String _svr_mail = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
        
        this._svr_mail = config.getServletContext().getInitParameter("_SVR_MAIL");
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
        //String usuario = (String)sesion.getAttribute("usuario");
        String empleado = (String)sesion.getAttribute("empleado");
        String cargo = (String)sesion.getAttribute("cargo");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String remitente = objConfiguracion.getValor("mail_remitente");
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            //String WHERE = request.getParameter("WHERE");
            //String p = request.getParameter("p") != null ? request.getParameter("p") : "0";
            
            String id = request.getParameter("id_actividad");
            String id_estrategia = request.getParameter("id_estrategia1");
            String id_plan_mercadeo = request.getParameter("id_plan_mercadeo2");
            int limCrono = Integer.parseInt( request.getParameter("limCrono") );
            //int limAdjAct = Integer.parseInt( request.getParameter("limAdjAct") );
            String tipo_pago = request.getParameter("tipo_pago");
            String actividad = request.getParameter("actividad");
            String fecha_ini = request.getParameter("act_fecha_ini");
            String fecha_fin = request.getParameter("act_fecha_fin");
            /*String mecanica_tipo = request.getParameter("mecanica_tipo");
            String mecanica = request.getParameter("mecanica");
            String aplica_prom_p_v = request.getParameter("aplica_prom_p_v");
            String ope_fecha_ini = request.getParameter("ope_fecha_ini");
            String ope_fecha_fin = request.getParameter("ope_fecha_fin");*/
            String usuario_seg = request.getParameter("usuario_seg");
            String responsable_seg = request.getParameter("responsable_seg");
            String usuario_eje = request.getParameter("usuario_eje");
            String responsable_eje = request.getParameter("responsable_eje");
            String pre_tipo = request.getParameter("pre_tipo");
            String pre_proveedor = request.getParameter("pre_proveedor");
            String pre_cantidad = request.getParameter("pre_cantidad");
            String pre_p_u = request.getParameter("pre_p_u");
            String pre_total = request.getParameter("pre_total");
            String monto = request.getParameter("ax_monto_act");
            //monto = tipo_pago.compareTo("f")==0 ? monto : pre_total;
            String pre_id_tipo_plan_cuenta = request.getParameter("pre_id_tipo_plan_cuenta");
            //String pre_crono_adqui = request.getParameter("pre_crono_adqui");
            
            /*mecanica_tipo = mecanica_tipo!=null ? mecanica_tipo : "";
            aplica_prom_p_v = aplica_prom_p_v!=null ? aplica_prom_p_v : "";*/
                        
            // formulario y archivos adjuntos    -----------------------------------------------------------------------------------------------------------------
            /*String archivos = "";
            String descripciones = "";
            for(int i=0; i<=limAdjAct; i++){
                if(request.getParameter("archivo"+i)!=null){
                    archivos += request.getParameter("archivo"+i) + "&;";
                    descripciones += request.getParameter("descripcion_arch"+i) + "&;";
                }
            }
            if(archivos.compareTo("")!=0){
                archivos = archivos.substring(0, archivos.length()-2);
                descripciones = descripciones.substring(0, descripciones.length()-2);
            }*/
            
            // cronograma de adquisiciones    ----------------------------------------------------------------------------------------------
            String crono_fechas = "";
            String crono_descripciones = "";
            String tabla = "";
            for(int i=0; i<=limCrono; i++){
                if(request.getParameter("crono_fecha"+i)!=null){
                    crono_fechas += request.getParameter("crono_fecha"+i) + "&;";
                    crono_descripciones += request.getParameter("crono_descripcion"+i) + "&;";
                    tabla += "<tr><td>"+request.getParameter("crono_fecha"+i)+"</td><td>"+request.getParameter("crono_descripcion"+i)+"</td></tr>";
                }
            }
            if(crono_fechas.compareTo("")!=0){
                crono_fechas = crono_fechas.substring(0, crono_fechas.length()-2);
                crono_descripciones = crono_descripciones.substring(0, crono_descripciones.length()-2);
            }

            if(!objActividad.hayActividad(id, id_estrategia, actividad)){
                if(id.compareTo("-1")==0){
                    String pk = objActividad.ingresar(id_estrategia, tipo_pago, actividad, fecha_ini, fecha_fin, usuario_seg, responsable_seg, usuario_eje, responsable_eje, pre_tipo, 
                            pre_proveedor, pre_cantidad, pre_p_u, pre_total, monto, pre_id_tipo_plan_cuenta, crono_fechas, crono_descripciones);
                    if(pk.compareTo("-1")!=0){
                        String sec_tipo_plan = "";
                        String plan_mercadeo = "";
                        try{
                            ResultSet rs = objActividad.consulta("select * from tbl_plan_mercadeo where id_plan_mercadeo="+id_plan_mercadeo);
                            if(rs.next()){
                                sec_tipo_plan = rs.getString("sec_tipo_plan")!=null ? rs.getString("sec_tipo_plan") : "";
                                plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        String asunto = "NOTIFICACION DE SEGUIMIENTO";
                        String mensaje = "Estimado(a) .<br /><br />" +
                        "Por medio de la presente se pone a consideraci&oacute;n que se ha creado el plan de Mercadeo Nro. <strong>"+sec_tipo_plan + " con nombre " + plan_mercadeo+"</strong>; en el cual se le ha asignado la siguiente actividad:<br /><br /><br />";
                        
                        mensaje += "<table width=\"100%\">"
                                + "<tr><td><strong>Nombre de la actividad</strong></td><td>"+actividad+"</td></tr>"
                                + "<tr><td><strong>Fecha de inicio</strong></td><td>"+fecha_ini+"</td></tr>"
                                + "<tr><td><strong>Fecha de t&eacute;rmino</strong></td><td>"+fecha_fin+"</td></tr>"
                                + "<tr><td><strong>Responsable de seguimiento</strong></td><td>"+responsable_seg+"</td></tr>"
                                + "<tr><td><strong>Responsable de ejecuci&oacute;n</strong></td><td>"+responsable_eje+"</td></tr>"
                                + "<tr><td><strong>Tipo</strong></td><td>"+this.getTipo(pre_tipo)+"</td></tr>"
                                + "<tr><td><strong>Proveedor sugerido</strong></td><td>"+pre_proveedor+"</td></tr>"
                                + "<tr><td><strong>Cantidad</strong></td><td>"+pre_cantidad+"</td></tr>"
                                + "<tr><td><strong>Total</strong></td><td>"+pre_total+"</td></tr>"
                                + "<tr><td colspan=\"2\"><strong>Fecha de cronograma</strong></td></tr>"
                                + "</table>";
                        if(tabla.compareTo("")!=0){
                            mensaje += "<table width=\"100%\">";
                            mensaje += "<tr><th>FECHA</th><th>DESCRIPCION</th></tr>";
                            mensaje += tabla;
                            mensaje += "</table>";
                        }
                        mensaje += "<br /><br /><br />Atentamente,<br />" +
                        "<strong>"+empleado + "<br />" +
                        cargo+"</strong>";
        
                        String msg = "Información guardada satisfactoriamente.";
                        if(Correo.enviar(this._svr_mail, remitente, this.getEMail(usuario_seg), this.getEMail(usuario_eje), "", asunto, mensaje, true)){
                            
                        }else{
                            msg = "Se ha creado la actividad pero con el siguiente mensaje de error: " + Correo.getError();
                        }
                        /*PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
                        boolean hayActividades = objPlanMerca.existenActividades(id_plan_mercadeo);
                        int estado = 1;
                        int plan_completo = 0;
                        try{
                            ResultSet rs = objPlanMerca.getPlanMercadeo(id_plan_mercadeo);
                            if(rs.next()){
                                estado = rs.getString("estado")!=null ? rs.getInt("estado") : 1;
                                plan_completo = (rs.getString("plan_completo")!=null) ? rs.getInt("plan_completo") : 0;
                                rs.close();
                            }
                        }catch(Exception e){
                        }finally{
                            objPlanMerca.cerrar();
                        }
                        String actBtn = "_('btnAut').style.visibility='hidden';";
                        if((estado==1 || estado==2) && plan_completo==0 && hayActividades){
                            actBtn = "_('btnAut').style.visibility='visible';";
                        }*/
                        
                        res = "obj»axActividades^fun»mer_getEstrategias();_('pm_d_5').innerHTML='';^msg»"+msg+"^frm»"+this.getActividads(objActividad, id_estrategia);
                    }else{
                        res = "msg»" + objActividad.getError();
                    }
                }else{
                    if(objActividad.actualizar(id, id_estrategia, tipo_pago, actividad, fecha_ini, fecha_fin, usuario_seg, responsable_seg, 
                            usuario_eje, responsable_eje, pre_tipo, pre_proveedor, pre_cantidad, pre_p_u, 
                            pre_total, monto, pre_id_tipo_plan_cuenta, crono_fechas, crono_descripciones)){
                        res = "obj»axActividades^fun»mer_getEstrategias();^msg»Información guardada satisfactoriamente.^frm»"+this.getActividads(objActividad, id_estrategia);
                    }else{
                            res = "msg»" + objActividad.getError();
                        }
                }
            }else{
                res = "msg»Cambie de nombre de la actividad ya que el nombre se encuentra registrado.";
            }
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            objConfiguracion.cerrar();
            objActividad.cerrar();
        }
        
        try{
            out.print(res);
        }finally {
            out.close();
        }
    } 
    
    public String getEMail(String usuario)
    {
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select Email from usuarios with (nolock) where lower(NombreCorto) = '"+usuario+"'");
            if(rs.next()){
                return rs.getString("Email")!=null?rs.getString("Email"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objDB.cerrar();
        }
        return "";
    }
    
    public String getTipo(String pre_tipo)
    {
        if (pre_tipo.compareTo("b")==0){
            return "Bienes";
        }
        if (pre_tipo.compareTo("s")==0){
            return "Servicio";
        }
        if (pre_tipo.compareTo("a")==0){
            return "Activo fijo";
        }
        if (pre_tipo.compareTo("p")==0){
            return "Premio";
        }
        if (pre_tipo.compareTo("i")==0){
            return "Inventario";
        }
        return "";
    }
    
    public String getActividads(Actividad objActividad, String id_estrategia)
    {
        String html = "";
        try{
            ResultSet rsActividades = objActividad.getActividades(id_estrategia);
            int i=0;
            String id_actividad = "";
            String actividad = "";
            float pre_total = 0;
            float sumatoria = 0;
            html += "<table width=\"100%\">";
            html += "<tr><th width=\"300\">ACTIVIDAD</th><th>TOTAL</th><th>&nbsp;</th></tr>";
            while(rsActividades.next()){
                id_actividad = rsActividades.getString("id_actividad")!=null ? rsActividades.getString("id_actividad") : "";
                actividad = rsActividades.getString("actividad")!=null ? rsActividades.getString("actividad") : "";
                pre_total = rsActividades.getString("pre_total")!=null ? rsActividades.getFloat("pre_total") : 0;
                html += "<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td onclick=\"mer_actividadEditar("+id_actividad+")\"><input type=\"hidden\" id=\"id_actividad"+i+"\" name=\"id_actividad"+i+"\" value=\""+id_actividad+"\" />"+actividad+"</td>";
                html += "<td align=\"right\" onclick=\"mer_actividadEditar("+id_actividad+")\">"+pre_total+"</td>";
                html += "<td align=\"right\"> <div class=\"borrar\" title=\"Eliminar\" onclick=\"mer_actividadEliminar("+id_actividad+");\">&nbsp;</div></td>";
                html += "</tr>";
                sumatoria += pre_total;
                i++;
            }
            html += "<tr><td colspan=\"2\" align=\"right\">"+sumatoria+"</th><th>&nbsp;</th></tr>";
            html += "</table>";
        }catch(Exception e){
            e.printStackTrace();
        }
        return html;
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
