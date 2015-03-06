/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.mer.pla.clas.Configuracion;
import com.far.mer.pla.clas.PlanMercadeo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
public class frmPlanMercadeoAnular extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String AUS_ip = null;
    private int AUS_puerto = 1433;
    private String AUS_db = null;
    private String AUS_usuario = null;
    private String AUS_clave = null;
    
    private String _svr_mail = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.AUS_ip = config.getServletContext().getInitParameter("AUS_IP");
        this.AUS_puerto = Integer.parseInt(config.getServletContext().getInitParameter("AUS_PUERTO"));
        this.AUS_db = config.getServletContext().getInitParameter("AUS_DB");
        this.AUS_usuario = config.getServletContext().getInitParameter("AUS_USUARIO");
        this.AUS_clave = config.getServletContext().getInitParameter("AUS_CLAVE");
        
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
        String usuario = (String)sesion.getAttribute("usuario");
        String empleado = (String)sesion.getAttribute("empleado");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        String id_plan_mercadeo = request.getParameter("id");
        String id_tipo_plan = request.getParameter("idTP");
        String plan_mercadeo = request.getParameter("planMer");
        String motivo_anulacion = request.getParameter("motivo");
        
        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String remitente = objConfiguracion.getValor("mail_remitente");
        
        BaseDatos objAuspicio = new BaseDatos(this.AUS_ip, this.AUS_puerto, this.AUS_db, this.AUS_usuario, this.AUS_clave);
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        boolean noEstrategicos=true;
        try{
            ResultSet rsAusNoEstrategicos = objPlanMercadeo.getProveedores(id_plan_mercadeo);
            String numero_idproveedor="";
            String nombre_comercial="";
            String num_formulario="";
            String monto="0";
            String auspicio_manual="m";
            int confirmado=0;
            int eliminado=0;
            while(rsAusNoEstrategicos.next()){
                numero_idproveedor = rsAusNoEstrategicos.getString("numero_idproveedor")!=null ? rsAusNoEstrategicos.getString("numero_idproveedor") : "";
                monto = rsAusNoEstrategicos.getString("monto")!=null ? rsAusNoEstrategicos.getString("monto") : "0";
                nombre_comercial = rsAusNoEstrategicos.getString("nombre_comercial")!=null ? rsAusNoEstrategicos.getString("nombre_comercial") : "";
                num_formulario = rsAusNoEstrategicos.getString("num_formulario")!=null ? rsAusNoEstrategicos.getString("num_formulario") : "";
                confirmado = rsAusNoEstrategicos.getString("confirmado")!=null ? rsAusNoEstrategicos.getInt("confirmado") : 0;
                eliminado = rsAusNoEstrategicos.getString("eliminado")!=null ? rsAusNoEstrategicos.getInt("eliminado") : 0;
                auspicio_manual = rsAusNoEstrategicos.getString("auspicio_manual")!=null ? rsAusNoEstrategicos.getString("auspicio_manual") : "m";
                if(eliminado==0){
                    // va a estado creado     1=creado; 2=anulado; 3=confirmado; 4=confirmado anulado; 5=abierto; 9=cerrado
                    objAuspicio.ejecutar("update tbl_auspicio set tipo_confirmacion='"+auspicio_manual+"', estado='1' where num_auspicio='"+num_formulario+"';");
                    if(confirmado==1){
                        noEstrategicos = false;
                        res = "msg»Existen laboratorios no estratégicos confirmados, se debe dar de baja la deuda en el CDP para poder continuar";

                        String para = "";
                        String asunto = "SOLICITUD DE BAJA DE DEUDA";
                        String mensaje = "Estimado(a) Sr(a).\n" +
                        "El plan de mercadeo con nombre: "+plan_mercadeo+" se encuentra en proceso de anulaci&oacute;n. Por tal motivo, se ha solicitado la baja de la deuda en el CDP para el proveedor "+nombre_comercial+
                        "con n&uacute;mero de formulario "+num_formulario+".\n" +
                        "\n" +
                        "\n" +
                        "Att.\n" +
                        empleado + "\n";

                        Correo.enviar(this._svr_mail, remitente, para, "", "", asunto, mensaje, true);
                    }
                }
            }
            objPlanMercadeo.ejecutar("update tbl_plan_mercadeo_proveedor set eliminado=1 where id_plan_mercadeo="+id_plan_mercadeo+";");    
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        
        List sql = new ArrayList();
        boolean estrategicos = false;
        try{
            ResultSet rsAusEstrategicos = objPlanMercadeo.getAuspicios(id_plan_mercadeo);
            String id_laboratorio="";
            String monto="0";
            while(rsAusEstrategicos.next()){
                id_laboratorio = rsAusEstrategicos.getString("id_laboratorio")!=null ? rsAusEstrategicos.getString("id_laboratorio") : "";
                monto = rsAusEstrategicos.getString("monto")!=null ? rsAusEstrategicos.getString("monto") : "0";
                sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                    + "values("+id_laboratorio+", '"+id_tipo_plan+"', '"+usuario+"', getdate(), 'Anulación de plan de mercadeo: "+plan_mercadeo+"', "+monto+", 1);");
            }
            if(objPlanMercadeo.transacciones(sql)){
                estrategicos = true;
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        /*try{
            ResultSet rsAct = objActividad.getActividadPlanMercadeo(id_actividad);
            if(rsAct.next()){
                actividad = rsAct.getString("actividad")!=null?rsAct.getString("actividad"):"";
                plan_mercadeo = rsAct.getString("plan_mercadeo")!=null?rsAct.getString("plan_mercadeo"):"";
                usuario_creacion = rsAct.getString("usuario_creacion")!=null?rsAct.getString("usuario_creacion"):"";
                rsAct.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        
        
        
        String para_ususario_creador = "";
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select Email from usuarios with (nolock) where lower(NombreCorto) = '"+usuario_creacion+"'");
            if(rs.next()){
                para_ususario_creador = rs.getString("Email")!=null?rs.getString("Email"):"";
                rs.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }finally{
            objDB.cerrar();
        }
        
        String asunto = "AUTORIZACION GERENTE DE OPERACIONES " + (aprobada.compareTo("1")==0 ? "ACEPTADA" : "RECHAZADA");
        
        String mensaje = "Sr(a).\n" +
        "ADMINISTRADOR(A) DE PLANES DE MERCADEO\n" +
        "Por favor revisar la actividad: "+actividad+" del plan de mercadeo con nombre: "+plan_mercadeo+" el mismo que ya fue aprobado por la GERENCIA DE OPERACIONES.\n" +
        "\n" +
        "\n" +
        "Att.\n" +
        empleado + "\n" +
        "GERENTE DE OPERACIONES";
        if(aprobada.compareTo("0")==0){
            mensaje = "Sr(a).\n" +
            "ADMINISTRADOR(A) DE PLANES DE MERCADEO\n" +
            "Por favor revisar la actividad: "+actividad+" del plan de mercadeo con nombre: "+plan_mercadeo+" el mismo que fue rechazado por la GERENCIA DE OPERACIONES.\n" +
            "El motivo del rechazo es: "+motivo+"\n" +
            "\n" +
            "Att.\n" +
            empleado + "\n" +
            "GERENTE DE OPERACIONES";
        }*/
        
        try {
            if(estrategicos && noEstrategicos){
                if(objPlanMercadeo.anular(id_plan_mercadeo, motivo_anulacion)){
                    res = "fun»_R('vta2');_R('bloq_vta2');_R('vta1');_R('bloq_vta1');mer_getPlanesMercadeo(0);^msg»Plan de Mercadeo anulado satisfactoriamente.";
                }else{
                    res = "msg»" + objPlanMercadeo.getError();
                }
            }

        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            objAuspicio.cerrar();
            objConfiguracion.cerrar();
            objPlanMercadeo.cerrar();
        }
        
        try{
            out.print(res);
        }finally {
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
